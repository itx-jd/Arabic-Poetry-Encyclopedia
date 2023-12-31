package PL;

import java.awt.BorderLayout;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import BLL.IBLLFacade;
import TO.BookDTO;
import TO.PoemDTO;
import TO.VerseDTO;

/**
 * This class represents a graphical user interface for VerseGUI.
 */


public class VerseGUI {
	private JPanel panel;
	private List<PairPanel> pairPanels;
	private JSplitPane splitPane;
	private JPanel leftPanel;
	private JScrollPane rightScrollPane;
	private JPanel rightPanel;
	private JButton pushRecordButton;
	private JButton addFieldButton;
	private JLabel poemIdLabel;
	private JLabel bookIdLabel;

	private JComboBox<String> poemComboBox;
	JComboBox<String> bookFilterComboBox;

	IBLLFacade iBLLFacade;
	List<VerseDTO> verseDTOList;
	List<PoemDTO> poemList;
	List<String> poemTitlesList;
	List<BookDTO> bookList;
	String selectedBookTitle = "";

	public JPanel getPanel(IBLLFacade iBLLFacade, List<VerseDTO> verseDTOList) {

		this.iBLLFacade = iBLLFacade;
		this.verseDTOList = verseDTOList;

		bookList = iBLLFacade.fetchBookRecords();
		poemList = iBLLFacade.fetchPoemRecords();

		panel = new JPanel(new BorderLayout());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panel.add(splitPane);

		leftPanel = new JPanel(new GridBagLayout());
		leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		bookIdLabel = new JLabel("Book Title");
		bookFilterComboBox = new JComboBox<>();

		pushRecordButton = new JButton("Push Record");
		addFieldButton = new JButton("Add Fields");

		poemIdLabel = new JLabel("Poem Title");
		poemComboBox = new JComboBox<>();

		pairPanels = new ArrayList<>();
		populateBookFilterBox();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		// Add components to leftPanel using GridBagConstraints
		gbc.gridx = 0;
		gbc.gridy = 0;
		leftPanel.add(bookIdLabel, gbc);

		gbc.gridx = 1;
		leftPanel.add(bookFilterComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		leftPanel.add(poemIdLabel, gbc);

		gbc.gridx = 1;
		leftPanel.add(poemComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		leftPanel.add(addFieldButton, gbc);

		gbc.gridx = 1;
		leftPanel.add(pushRecordButton, gbc);

		rightScrollPane = new JScrollPane(rightPanel);
		rightScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightScrollPane);
		splitPane.setResizeWeight(0.3);

		// Buttons

		pushRecordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (pairPanels.size() < 1) {
					JOptionPane.showMessageDialog(panel, "Error: No Misra Found");
				} else {
					if (poemComboBox.getSelectedItem() != null) {

						String selectedPoemTitle = poemComboBox.getSelectedItem().toString();
						int poemId = iBLLFacade.getPoemId(selectedPoemTitle);

						// Delete all verses associated with the poem
						iBLLFacade.deleteVersesByPoemId(poemId);

						// Inserting the updated verses record

						verseDTOList.clear();

						for (PairPanel pairPanel : pairPanels) {

							if (!pairPanel.isEmpty()) {
								verseDTOList.add(new VerseDTO(pairPanel.getVerseText(), poemId));
							}

						}

						if (iBLLFacade.addVerseRecord(verseDTOList)) {
							JOptionPane.showMessageDialog(panel, "Poem Added Successfully");
							fetchVersesAndSetToPanel();
						} else {
							JOptionPane.showMessageDialog(panel, "Failed To Add Poem");
						}
					} else {
						JOptionPane.showMessageDialog(panel, "Error: Poem Title Not Found");
					}
				}
			}
		});

		addFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPairPanel();
			}
		});


		// Add an ItemListener to handle book filter changes
		bookFilterComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println("item change");
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedBookTitle = e.getItem().toString();
					populatePoemFilterBox(selectedBookTitle);
				}
			}
		});

		// Add a FocusListener to the comboBox
		bookFilterComboBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				initializeBookFilterBox();

			}

			@Override
			public void focusLost(FocusEvent e) {
				// Code to execute when the comboBox loses focus
			}
		});

		// Add an ItemListener to handle book filter changes
		poemComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					fetchVersesAndSetToPanel();
				}
			}
		});

		// Add a FocusListener to the comboBox
		poemComboBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
	
				if(!bookList.isEmpty()) {
					poemList = iBLLFacade.fetchPoemRecords();
					populatePoemFilterBox(selectedBookTitle);
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				// Code to execute when the comboBox loses focus
			}
		});

		return panel;
	}

	private void fetchVersesAndSetToPanel() {

		if (poemComboBox.getSelectedItem() != null) {

			String selectedPoemTitle = poemComboBox.getSelectedItem().toString();
			int poemId = iBLLFacade.getPoemId(selectedPoemTitle);

			// Use VerseDAO method to fetch verses for the specified poem
			List<VerseDTO> verses = iBLLFacade.getVersesByPoemId(poemId);

			// Clear existing pair panels
			pairPanels.clear();
			rightPanel.removeAll();

			// Add pair panels for each verse
			for (VerseDTO verse : verses) {
				PairPanel pairPanel = new PairPanel();
				pairPanel.setVerseText(verse.getVerseText());
				pairPanels.add(pairPanel);
				pairPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				rightPanel.add(pairPanel);
			}

			// Revalidate and repaint the frame
			panel.revalidate();
			panel.repaint();
		}
	}

	private void initializeBookFilterBox() {

		bookList = iBLLFacade.fetchBookRecords();
		bookFilterComboBox.removeAll();

		// Populate the book filter combo box
		for (BookDTO book : bookList) {
			bookFilterComboBox.addItem(book.getBookTitle());
		}

	}

	private void populateBookFilterBox() {

		bookList = iBLLFacade.fetchBookRecords();
		bookFilterComboBox.removeAll();

		// Populate the book filter combo box
		for (BookDTO book : bookList) {
			bookFilterComboBox.addItem(book.getBookTitle());
		}

		if (!bookList.isEmpty()) {

			selectedBookTitle = bookList.get(0).getBookTitle();
			populatePoemFilterBox(selectedBookTitle);
		}

	}

	private void populatePoemFilterBox(String bookName) {

		poemComboBox.removeAllItems();

		for (PoemDTO poem : poemList) {

			if (poem.getBookId() == getBookIdFromBookTitle(bookName)) {
				poemComboBox.addItem(poem.getPoemTitle());
			}

		}

		if (poemComboBox.getItemCount() > 0) {
			poemComboBox.setSelectedIndex(0);
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


	private void addPairPanel() {
		PairPanel pairPanel = new PairPanel();
		pairPanels.add(pairPanel);
		pairPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rightPanel.add(pairPanel);
		panel.revalidate();
	}

}

class PairPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField verseField1;
	private JTextField verseField2;

	public PairPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		verseField1 = new JTextField();
		verseField2 = new JTextField();
		add(verseField1);
		add(verseField2);
	}

	public boolean isEmpty() {

		if (verseField1.getText().trim().isEmpty() && verseField2.getText().trim().isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public void setVerseText(String verseText) {
		String[] misrahs = verseText.split(", ");
		if (misrahs.length > 0) {
			verseField1.setText(misrahs[0]);
		}
		if (misrahs.length > 1) {
			verseField2.setText(misrahs[1]);
		}
	}

	public String getVerseText() {
		return verseField1.getText() + ", " + verseField2.getText();
	}
}
