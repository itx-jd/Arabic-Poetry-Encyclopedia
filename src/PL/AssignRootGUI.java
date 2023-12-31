package PL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import BLL.IBLLFacade;
import TO.BookDTO;
import TO.PoemDTO;
import TO.RootDTO;
import TO.TokenDTO;
import TO.VerseDTO;
/**
 * This class represents a graphical user interface for assigning roots to verses.
 */
public class AssignRootGUI {

	JPanel panel;
	private JComboBox<String> bookComboBox;
	private JComboBox<String> poemComboBox;
	private JTable verseTable;
	private JTextArea rootsTextArea;
	private JButton saveButton;
	private JComboBox<String> rootSuggestionComboBox;

	private DefaultTableModel verseTableModel;

	private IBLLFacade iBLLFacade;
	private List<PoemDTO> poemList;
	private List<BookDTO> bookList;
	private List<VerseDTO> versesList;

	int selectedVerseId = -1;
	
	/**
     * Retrieves the panel containing the root assignment GUI components.
     *
     * @param iBLLFacade The interface to the business logic layer facade.
     * @return The constructed JPanel for root assignment.
     */
	public JPanel getPanel(IBLLFacade iBLLFacade) {

		this.iBLLFacade = iBLLFacade;

		panel = new JPanel(new BorderLayout());

		// Left Panel
		JPanel leftPanel = new JPanel(new GridBagLayout());
		GridBagConstraints leftGBC = new GridBagConstraints();
		leftGBC.insets = new Insets(5, 5, 5, 5);

		JLabel bookTitleLabel = new JLabel("Book Title");
		bookComboBox = new JComboBox<>();
		bookComboBox.setPreferredSize(new Dimension(200, 25));

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
		GridBagConstraints rightGBC = new GridBagConstraints();
		rightGBC.insets = new Insets(5, 5, 5, 5);

		rightGBC.gridx = 0;
		rightGBC.gridy = 0;
		rightPanel.add(new JLabel("Roots of Verse"), rightGBC);

		rightGBC.gridy++;
		rootsTextArea = new JTextArea();
		rootsTextArea.setLineWrap(true);
		JScrollPane rootsScrollPane = new JScrollPane(rootsTextArea);
		rootsScrollPane.setPreferredSize(new Dimension(300, 100));
		rightPanel.add(rootsScrollPane, rightGBC);

		rightGBC.gridy++;
		rightPanel.add(new JLabel("Suggested Roots"), rightGBC);

		rightGBC.gridy++;
		rootSuggestionComboBox = new JComboBox<>();
		rootSuggestionComboBox.setPreferredSize(new Dimension(200, 25));
		rightPanel.add(rootSuggestionComboBox, rightGBC);

		rightGBC.gridy++;
		rightGBC.weighty = 0.0;

		saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(100, 30));
		rightPanel.add(saveButton, rightGBC);

		// Add an ItemListener to handle book filter changes
		rootSuggestionComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					addRootFromSuggestion();
				}
			}
		});

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveRoot();
			}
		});

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		panel.add(splitPane, BorderLayout.CENTER);

		bookList = iBLLFacade.fetchBookRecords();
		poemList = iBLLFacade.fetchPoemRecords();

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

		poemComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				poemList = iBLLFacade.fetchPoemRecords();
				populateVerseTable();
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

		verseTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateSelectedVerse();
			}
		});

		versesList = new ArrayList<>();
		populateBookComboBox();

		return panel;
	}

	private void updateSelectedVerse() {

		int selectedRow = verseTable.getSelectedRow();
		if (selectedRow >= 0) {
			String verseText = verseTableModel.getValueAt(selectedRow, 0).toString();
			selectedVerseId = versesList.get(selectedRow).getVerseId();

			List<RootDTO> rootDTOList = iBLLFacade.getRootDTOListByVerseId(selectedVerseId);

			rootsTextArea.setText("");
			for (RootDTO rootDTO : rootDTOList) {
				rootsTextArea.append(rootDTO.getRootText() + "\n");
			}

			populateRootSuggestionComboBox(selectedVerseId);
			
		} else {
			rootsTextArea.setText("");
			rootSuggestionComboBox.removeAllItems();
		}
	}

	private void populateVerseTable() {
		
		verseTableModel.setRowCount(0);
		String selectedPoemTitle = "N/A";

		Object selectedPoemItem = poemComboBox.getSelectedItem();
		if (selectedPoemItem != null) {
			selectedPoemTitle = selectedPoemItem.toString();
		}

		int poemId = getPoemIdFromPoemTitle(selectedPoemTitle);
		versesList.clear();
		versesList = iBLLFacade.getVersesByPoemId(poemId);

		for (VerseDTO verse : versesList) {
			verseTableModel.addRow(new Object[] { verse.getVerseText() });
		}
	}

	private void populateRootSuggestionComboBox(int verseId) {
		
		List<TokenDTO> tokenDTOList = iBLLFacade.getTokenDTOListByVerseId(verseId);
		List<String> suggestedRoots = new ArrayList<>();

		for (TokenDTO tokenDTO : tokenDTOList) {
			String potentialRoot = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(tokenDTO.getWord())
					.getAllRootString();
			potentialRoot = potentialRoot.replaceAll("[:-]", "");

			if (potentialRoot.length() > 2 && potentialRoot.length() < 7) {
				suggestedRoots.add(potentialRoot);
			}
		}

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(suggestedRoots.toArray(new String[0]));
		rootSuggestionComboBox.setModel(model);
	}

	private void addRootFromSuggestion() {

		Object obj = rootSuggestionComboBox.getSelectedItem().toString();

		if (obj != null) {
			String selectedRoot = rootSuggestionComboBox.getSelectedItem().toString();
			rootsTextArea.append(selectedRoot + "\n");
		}

	}

	private void saveRoot() {
		iBLLFacade.deleteRootVerseByVerseID(selectedVerseId);

		String[] roots = rootsTextArea.getText().split("\\n");

		for (String rootText : roots) {
			if (!rootText.trim().isEmpty()) {
				int rootId = iBLLFacade.getRootIdFromRootText(rootText);

				if (rootId == -1) {
					RootDTO rootDTO = new RootDTO();
					rootDTO.setRootText(rootText.trim());
					rootDTO.setVerseId(selectedVerseId);
					rootDTO.setStatus("verified");

					boolean success = iBLLFacade.addRootRecord(rootDTO);

					rootId = iBLLFacade.getRootIdFromRootText(rootText);
				}

				if (!iBLLFacade.doesRootVerseExist(rootId, selectedVerseId)) {
					iBLLFacade.insertRootVerseRecord(rootId ,selectedVerseId, "verified");
				}
			}
		}

		JOptionPane.showMessageDialog(null, "Roots Of Verse Saved Successfully", "Success",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private int getPoemIdFromPoemTitle(String title) {
		for (PoemDTO poem : poemList) {
			if (poem.getPoemTitle().equals(title)) {
				return poem.getPoemId();
			}
		}
		return -1;
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

}
