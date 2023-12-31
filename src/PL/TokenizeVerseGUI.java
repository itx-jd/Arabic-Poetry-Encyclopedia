package PL;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import BLL.IBLLFacade;
import TO.BookDTO;
import TO.PoemDTO;
import TO.TokenDTO;
import TO.VerseDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a graphical user interface for TokenizeVerseGUI.
 */



public class TokenizeVerseGUI {

	JPanel panel;
	private JComboBox<String> bookComboBox;
	private JComboBox<String> poemComboBox;
	private JTable verseTable;
	private JTextArea misra1Field;
	private JTextArea misra2Field;
	private JButton splitButton;
	private JButton saveButton;

	private DefaultTableModel verseTableModel;

	private IBLLFacade iBLLFacade;
	private List<PoemDTO> poemList;
	private List<BookDTO> bookList;
	List<VerseDTO> versesList;
	TokenDTO tokenDTO;

	int selectedVerseId = -1;

	public JPanel getPanel(IBLLFacade iBLLFacade, TokenDTO tokenDTO) {
		this.iBLLFacade = iBLLFacade;
		this.tokenDTO = tokenDTO;

		bookList = iBLLFacade.fetchBookRecords();
		poemList = iBLLFacade.fetchPoemRecords();
		versesList = new ArrayList<>();

		panel = new JPanel(new BorderLayout());

		/// Left Panel
		JPanel leftPanel = new JPanel(new GridBagLayout());
		GridBagConstraints leftGBC = new GridBagConstraints();
		leftGBC.insets = new Insets(5, 5, 5, 5);

		JLabel bookTitleLabel = new JLabel("Book Title");
		bookComboBox = new JComboBox<>();
		bookComboBox.setPreferredSize(new Dimension(200, 25));

		// Add an ItemListener to handle book filter changes
		bookComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					populatePoemComboBox();
				}
			}
		});

		// Add a FocusListener to the comboBox
		bookComboBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				bookList = iBLLFacade.fetchBookRecords();
				populateBookComboBox();

			}

			@Override
			public void focusLost(FocusEvent e) {
				// Code to execute when the comboBox loses focus
			}
		});

		JLabel poemTitleLabel = new JLabel("Poem Title");
		poemComboBox = new JComboBox<>();
		poemComboBox.setPreferredSize(new Dimension(200, 25));

		leftGBC.gridx = 0;
		leftGBC.gridy = 0;
		leftPanel.add(bookTitleLabel, leftGBC);

		leftGBC.gridy++;
		leftPanel.add(bookComboBox, leftGBC);

		leftGBC.gridy++;
		leftPanel.add(poemTitleLabel, leftGBC);

		leftGBC.gridy++;
		leftPanel.add(poemComboBox, leftGBC);

		verseTableModel = new DefaultTableModel(new Object[] { "Verse Text" }, 0);
		verseTable = new JTable(verseTableModel);
		leftGBC.gridy++;
		leftGBC.gridheight = 4;
		leftPanel.add(new JScrollPane(verseTable), leftGBC);

		// Right Panel
		JPanel rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridy++;

		rightPanel.add(new JLabel("Misra 1"), gbc);
		gbc.gridy++;
		misra1Field = new JTextArea();
		misra1Field.setLineWrap(true);
		JScrollPane misra1ScrollPane = new JScrollPane(misra1Field);
		misra1ScrollPane.setPreferredSize(new Dimension(300, 80)); // Adjusted size
		rightPanel.add(misra1ScrollPane, gbc);
		
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridy++;

		rightPanel.add(new JLabel("Misra 2"), gbc);
		gbc.gridy++;
		misra2Field = new JTextArea();
		misra2Field.setLineWrap(true);
		JScrollPane misra2ScrollPane = new JScrollPane(misra2Field);
		misra2ScrollPane.setPreferredSize(new Dimension(300, 80)); // Adjusted size
		rightPanel.add(misra2ScrollPane, gbc);

		gbc.gridy++;

		// Split Button
		splitButton = new JButton("Split");
		splitButton.setPreferredSize(new Dimension(80, 30)); // Adjusted size
		gbc.gridy++;
		gbc.insets = new Insets(10, 0, 0, 0); // Add 10 pixels of top margin
		rightPanel.add(splitButton, gbc);
		
		
		splitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				splitVerse();
			}
		});

		gbc.gridy++;

		// Save Button
		saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(80, 30)); // Adjusted size
		gbc.insets = new Insets(10, 0, 0, 0); // Add 10 pixels of top margin
		rightPanel.add(saveButton, gbc);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToken();
			}
		});

		// Split Pane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		panel.add(splitPane, BorderLayout.CENTER);

		// Add an ItemListener to handle book filter changes
		poemComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					populateVerseTable();
				}
			}
		});

		// Add a FocusListener to the comboBox
		poemComboBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				poemList = iBLLFacade.fetchPoemRecords();
				populatePoemComboBox();

			}

			@Override
			public void focusLost(FocusEvent e) {
				// Code to execute when the comboBox loses focus
			}
		});

		// Add a ListSelectionListener to the JTable
		verseTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Call the method to update JTextAreas when a verse is selected
				updateSelectedVerse();
			}
		});

		populateBookComboBox();

		return panel;
	}

	private void updateSelectedVerse() {

		int selectedRow = verseTable.getSelectedRow();
		if (selectedRow >= 0) {
			String verseText = verseTableModel.getValueAt(selectedRow, 0).toString();

			selectedVerseId = versesList.get(selectedRow).getVerseId();

			// Split the verse text into misra1 and misra2 using some logic
			// For example, splitting by a comma
			String[] verseParts = verseText.split(",");
			if (verseParts.length >= 2) {
				misra1Field.setText(verseParts[0]);
				misra2Field.setText(verseParts[1]);
			} else {
				// Handle the case where the verse text doesn't contain a comma
				misra1Field.setText("Invalid verse format");
				misra2Field.setText("");
			}
		} else {
			// No row selected, clear JTextAreas
			misra1Field.setText("");
			misra2Field.setText("");
		}
	}

	private int getPoemIdFromPoemTitle(String title) {
		for (PoemDTO poem : poemList) {
			if (poem.getPoemTitle().equals(title)) {
				return poem.getPoemId();
			}
		}
		return -1;
	}

	private void populateVerseTable() {

		verseTableModel.setRowCount(0); // Clear existing rows

		String selectedPoemTitle = "N/A";

		Object selectedPoemItem = poemComboBox.getSelectedItem();
		if (selectedPoemItem != null) {
			selectedPoemTitle = selectedPoemItem.toString();
		}

		int poemId = getPoemIdFromPoemTitle(selectedPoemTitle);

		// Fetch verses for the specified poem
		versesList.clear();
		versesList = iBLLFacade.getVersesByPoemId(poemId);

		// Populate the table with verse text
		for (VerseDTO verse : versesList) {
			verseTableModel.addRow(new Object[] { verse.getVerseText() });
		}
	}

	private void populateBookComboBox() {
		bookComboBox.removeAllItems();
		for (BookDTO book : bookList) {
			bookComboBox.addItem(book.getBookTitle());
		}
		if (bookComboBox.getItemCount() > 0) {
			bookComboBox.setSelectedIndex(0);
			populatePoemComboBox();
		}
	}

	private void populatePoemComboBox() {
		poemComboBox.removeAllItems();
		String selectedBookTitle = bookComboBox.getSelectedItem().toString();
		int bookId = getBookIdFromBookTitle(selectedBookTitle);

		for (PoemDTO poem : poemList) {
			if (poem.getBookId() == bookId) {
				poemComboBox.addItem(poem.getPoemTitle());
			}
		}
		if (poemComboBox.getItemCount() > 0) {
			poemComboBox.setSelectedIndex(0);
		}
	}

	private int getBookIdFromBookTitle(String bookName) {
		for (BookDTO book : bookList) {
			if (book.getBookTitle().equals(bookName)) {
				return book.getBookId();
			}
		}
		return -1;
	}

	private void splitVerse() {

		String verse = misra1Field.getText() + misra2Field.getText();

		// Split misra1Text and misra2Text into tokens
		String[] tokens = verse.split("\\s+");

		// Display each token in separate JTextArea
		displayTokens(tokens, "Tokens");

	}

	private void displayTokens(String[] tokens, String title) {

		JFrame tokenFrame = new JFrame(title);
		tokenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tokenFrame.setSize(new Dimension(400, 200));

		JTextArea tokenTextArea = new JTextArea();
		tokenTextArea.setEditable(false);

		for (String token : tokens) {
			tokenTextArea.append(token + "\n");
		}

		JScrollPane tokenScrollPane = new JScrollPane(tokenTextArea);
		tokenFrame.add(tokenScrollPane);

		tokenFrame.setLocationRelativeTo(panel);
		tokenFrame.setVisible(true);
	}

	private void saveToken() {

		String verse = misra1Field.getText() + misra2Field.getText();

		// Split misra1Text and misra2Text into tokens
		String[] tokens = verse.split("\\s+");

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

			if (!iBLLFacade.doesTokenVerseExist(tokenId, selectedVerseId)) {
				iBLLFacade.insertTokenIntoVerse(tokenId, selectedVerseId);
			}

		}

		JOptionPane.showMessageDialog(null, "Tokens Inserted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

	}

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
}
