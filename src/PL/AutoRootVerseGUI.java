package PL;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import BLL.IBLLFacade;
import TO.BookDTO;
import TO.PoemDTO;
import TO.RootDTO;
import TO.TokenDTO;
import TO.VerseDTO;

/**
 * This class represents a graphical user interface for AutoRootVerseGUI.
 */

public class AutoRootVerseGUI {

	private JPanel panel;
	private JLabel bookLabel;
	private JComboBox<String> bookComboBox;
	private JLabel poemLabel;
	private JComboBox<String> poemComboBox;
	private JButton autoAssignButton;
	private JProgressBar progressBar;
	private IBLLFacade iBLLFacade;
	private List<BookDTO> bookList;
	private List<PoemDTO> poemList;
	/**
     * Retrieves the panel containing the automatic root assignment GUI components.
     *
     * @param iBLLFacade The interface to the business logic layer facade.
     * @return The constructed JPanel for automatic root assignment.
     */
	public JPanel getPanel(IBLLFacade iBLLFacade) {

		this.iBLLFacade = iBLLFacade;

		panel = new JPanel(new GridBagLayout());

		// Component initialization
		bookLabel = new JLabel("Select Book");
		bookComboBox = new JComboBox<>();

		poemLabel = new JLabel("Select Poem");
		poemComboBox = new JComboBox<>();

		autoAssignButton = new JButton("Auto Assign Roots");
		autoAssignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performAutoAssign();
			}
		});

		progressBar = new JProgressBar();

		// Add components to the frame using GridBagLayout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER; // Center horizontally

		panel.add(bookLabel, gbc);

		gbc.gridy++;
		gbc.gridwidth = 2; // Set gridwidth to 2 to span two columns for the JComboBox
		panel.add(bookComboBox, gbc);
		gbc.gridwidth = 1; // Reset gridwidth to default

		gbc.gridy++;
		panel.add(poemLabel, gbc);

		gbc.gridy++;
		gbc.gridwidth = 2; // Set gridwidth to 2 for the JComboBox
		panel.add(poemComboBox, gbc);
		gbc.gridwidth = 1; // Reset gridwidth to default

		gbc.gridy++;
		gbc.gridwidth = 2; // Set gridwidth to 2 for the JButton
		panel.add(autoAssignButton, gbc);
		gbc.gridwidth = 1; // Reset gridwidth to default

		gbc.gridy++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2; // Set gridwidth to 2 for the JProgressBar
		panel.add(progressBar, gbc);
		gbc.gridwidth = 1; // Reset gridwidth to default


		// Add an ItemListener to handle book filter changes
		bookComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					fetchPoemTitle();
				}
			}
		});

		bookComboBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusGained(FocusEvent e) {
				initializeBookTitle();
			}
		});

		fetchBookTitle();

		return panel;
	}

	private void initializeBookTitle() {

		bookList = iBLLFacade.fetchBookRecords();

		bookComboBox.removeAllItems();

		for (BookDTO book : bookList) {
			bookComboBox.addItem(book.getBookTitle());
		}

	}

	private void fetchBookTitle() {

		bookList = iBLLFacade.fetchBookRecords();
		bookComboBox.removeAllItems();

		for (BookDTO book : bookList) {
			bookComboBox.addItem(book.getBookTitle());
		}

		if (!bookList.isEmpty()) {
			bookComboBox.setSelectedIndex(0);
		}

	}

	private void fetchPoemTitle() {

		poemList = iBLLFacade.fetchPoemRecords();

		poemComboBox.removeAllItems();

		for (PoemDTO poem : poemList) {

			if (poem.getBookId() == getBookIdFromBookTitle(bookComboBox.getSelectedItem().toString())) {
				poemComboBox.addItem(poem.getPoemTitle());
			}

		}
	}

	int getBookIdFromBookTitle(String bookName) {

		for (BookDTO book : bookList) {

			if (book.getBookTitle().equals(bookName)) {
				return book.getBookId();
			}

		}

		return -1;

	}

	public void autoAssignRoot() {

		int poem_id = iBLLFacade.getPoemId(poemComboBox.getSelectedItem().toString());
		
		List<VerseDTO> verseDTOList = iBLLFacade.getVersesByPoemId(poem_id);

		// Token

		for (VerseDTO verseDTO : verseDTOList) {
			
			// Creating Tokens

			String verse[] = verseDTO.getVerseText().split(",");
			String verse_text = verse[0] + verse[1];

			String[] tokens = verse_text.split("\\s+");
			
			// Insertion


			// Check each token and insert if not present
			for (String token : tokens) {
				// Check if the token already exists in the database

				int tokenId = iBLLFacade.getTokenIdFromTokenText(token);

				if (tokenId == -1) {
					// Token doesn't exist, insert into the database
					TokenDTO newToken = new TokenDTO();
					newToken.setWord(token);

					newToken.setTag(getPOSTagForToken(newToken.getWord())); // Set the tag as needed

					// Insert the token into the database
					iBLLFacade.insertToken(newToken);

					tokenId = iBLLFacade.getTokenIdFromTokenText(newToken.getWord());

				}

				if (!iBLLFacade.doesTokenVerseExist(tokenId, verseDTO.getVerseId())) {
					iBLLFacade.insertTokenIntoVerse(tokenId, verseDTO.getVerseId());

				}

			}

			// Root

			List<TokenDTO> tokenDTOList = iBLLFacade.getTokenDTOListByVerseId(verseDTO.getVerseId());
			

			for (TokenDTO tokenDTO : tokenDTOList) {
				
				// Finding Root using Stemmer

				String potentialRoot = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance()
						.processToken(tokenDTO.getWord()).getAllRootString();

				potentialRoot = potentialRoot.replaceAll("[:-]", "");

				if (potentialRoot.length() > 2) {

					int root_id = iBLLFacade.getRootIdFromRootText(potentialRoot);


					if (root_id == -1) {
						// root doesn't exist, insert into the database

						iBLLFacade.addRootRecord(new RootDTO(potentialRoot));
						root_id = iBLLFacade.getRootIdFromRootText(potentialRoot);

					}

					if (!iBLLFacade.doesRootVerseExist(root_id, verseDTO.getVerseId())) {

						iBLLFacade.insertRootVerseRecord(root_id, verseDTO.getVerseId(), "automatic");

					}
				}

			}

		}

	}
	
	// POS Tagger For Token

	private String getPOSTagForToken(String token) {
		
		List<net.oujda_nlp_team.entity.Result> pos = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance()
				.processToken(token).getAllResults();

		if (!pos.isEmpty()) {
			String firstPartOfSpeech = pos.get(0).getPartOfSpeech();

			// Split the result string using the pipe character "|" and get the first part
			String[] parts = firstPartOfSpeech.split("\\|");
			String firstPart = parts[0];

			return firstPart;
		} else {

			return "N/A"; // IF No TAG FOUND
		}
	}

	private void performAutoAssign() {

		// Simulate progress using a SwingWorker
		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() throws Exception {
				
				// Start the loading bar while processing the file
				progressBar.setIndeterminate(true);
				
				autoAssignRoot();
				return null;
			}
			
			@Override
			protected void done() {
				// Stop the loading bar
				progressBar.setIndeterminate(false);
				JOptionPane.showMessageDialog(panel, "Roots Assigned Successfully", "Info",
						JOptionPane.INFORMATION_MESSAGE);
			}
		};

		// Execute the SwingWorker
		worker.execute();
	}
}
