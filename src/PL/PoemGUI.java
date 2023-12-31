package PL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import BLL.IBLLFacade;
import TO.BookDTO;
import TO.PoemDTO;

/**
 * This class represents a graphical user interface for PoemGUI.
 */


public class PoemGUI {
	private JPanel panel;
	private JTable poemTable;
	private DefaultTableModel tableModel;

	private JTextField poemTitleField;

	private JComboBox<String> bookTitleComboBox;
	private JComboBox<String> filterComboBox;

	private JButton insertRecordButton;
	private JButton updateRecordButton;
	private JButton deleteRecordButton;

	private IBLLFacade iBLLFacade;
	private PoemDTO poemDTO;
	private List<BookDTO> bookList;
	private List<PoemDTO> poemList;
	private int selectedPoemIndex = -1;

	public JPanel getPanel(IBLLFacade iBLLFacade, PoemDTO poemDTO) {
		this.iBLLFacade = iBLLFacade;
		this.poemDTO = poemDTO;

		panel = new JPanel(new BorderLayout());

		// Left Panel
		JPanel leftPanel = new JPanel(new BorderLayout());
		panel.add(leftPanel);

		// Filter Panel
		JPanel filterPanel = new JPanel(new BorderLayout());
		JLabel filterLabel = new JLabel("Filter Books");
		filterPanel.add(filterLabel, BorderLayout.NORTH);
		filterComboBox = new JComboBox<>();
		filterPanel.add(filterComboBox, BorderLayout.CENTER);
		leftPanel.add(filterPanel, BorderLayout.NORTH);

		tableModel = new DefaultTableModel();
		poemTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(poemTable);
		leftPanel.add(scrollPane, BorderLayout.CENTER);

		// Right Panel
		JPanel rightPanel = new JPanel(new GridBagLayout());
		panel.add(rightPanel, BorderLayout.EAST);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);

		rightPanel.add(new JLabel("Poem Title"), gbc);

		gbc.gridy++;
		poemTitleField = new JTextField();
		rightPanel.add(poemTitleField, gbc);

		gbc.gridy++;
		rightPanel.add(new JLabel("Book Title"), gbc);

		gbc.gridy++;
		bookTitleComboBox = new JComboBox<>();
		rightPanel.add(bookTitleComboBox, gbc);

		gbc.gridy++;
		insertRecordButton = new JButton("Insert");
		insertRecordButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(insertRecordButton, gbc);

		gbc.gridy++;
		updateRecordButton = new JButton("Update");
		updateRecordButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(updateRecordButton, gbc);

		gbc.gridy++;
		deleteRecordButton = new JButton("Delete");
		deleteRecordButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(deleteRecordButton, gbc);

		// Action Listeners
		filterComboBox.addActionListener(this::filterPoems);
		insertRecordButton.addActionListener(this::insertRecordAction);
		updateRecordButton.addActionListener(this::updateRecordAction);
		deleteRecordButton.addActionListener(this::deleteRecordAction);

		// Focus Listener For Combo box

		filterComboBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				fetchPoemTable();

			}

			@Override
			public void focusLost(FocusEvent e) {
				// Code to execute when the comboBox loses focus
			}
		});

		// SETTING THE SELECTED POEM DATA
		poemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				int selectedRow = poemTable.getSelectedRow();
				if (selectedRow >= 0) {
					Object poemTitle = poemTable.getValueAt(selectedRow, 0);
					Object bookTitle = poemTable.getValueAt(selectedRow, 1);
					poemTitleField.setText(poemTitle.toString());
					int bookTitleIndex = findBookTitleIndex(bookTitle.toString());
					bookTitleComboBox.setSelectedIndex(bookTitleIndex);
					selectedPoemIndex = selectedRow;
				}

			}
		});

		// Initialize table and populate data
		createTable();
		fetchPoemTable();

		return panel;
	}

	private void insertRecordAction(ActionEvent e) {
		if (isFormIncomplete()) {
			showError("Error: Incomplete Form");
		} else {
			String poemTitle = poemTitleField.getText();
			String selectedBookTitle = bookTitleComboBox.getSelectedItem().toString();
			int bookId = getBookIdFromTitle(selectedBookTitle);
			poemDTO.setPoemTitle(poemTitle);
			poemDTO.setBookId(bookId);

			if (iBLLFacade.addPoemRecord(poemDTO)) {
				showSuccess("Record Inserted Successfully");
				fetchPoemTable();
				populatePoemTable();
				resetFields();
			} else {
				showError("Failed To Insert Record");
			}
		}
	}

	private void updateRecordAction(ActionEvent e) {
		if (isFormIncomplete()) {
			showError("Error: Incomplete Form");
		} else {
			String poemTitle = poemTitleField.getText();
			String selectedBookTitle = bookTitleComboBox.getSelectedItem().toString();
			int bookId = getBookIdFromTitle(selectedBookTitle);
			int poemId = getPoemIDFromPoemTitle(selectedPoemIndex);
			poemDTO.setPoemId(poemId);
			poemDTO.setPoemTitle(poemTitle);
			poemDTO.setBookId(bookId);

			if (iBLLFacade.updatePoemRecord(poemDTO)) {
				showSuccess("Record Update Successfully");
				fetchPoemTable();
				populatePoemTable();
				resetFields();
			} else {
				showError("Failed To Update Record");
			}
		}
	}

	private void deleteRecordAction(ActionEvent e) {
		if (isFormIncomplete()) {
			showError("Error: Incomplete Form");
		} else {
			int poemId = getPoemIDFromPoemTitle(selectedPoemIndex);
			poemDTO.setPoemId(poemId);

			if (iBLLFacade.deletePoemRecord(poemDTO)) {
				showSuccess("Record Delete Successfully");
				fetchPoemTable();
				populatePoemTable();
				resetFields();
			} else {
				showError("Failed To Delete Record");
			}
		}
	}

	private void filterPoems(ActionEvent e) {
		String selectedBookTitle = filterComboBox.getSelectedItem() != null
				? filterComboBox.getSelectedItem().toString()
				: "All";
		populatePoemTable(selectedBookTitle);
	}

	private boolean isFormIncomplete() {
		return poemTitleField.getText().trim().isEmpty() || bookTitleComboBox.getSelectedItem() == null;
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showSuccess(String message) {
		JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	private void resetFields() {
		poemTitleField.setText("");
		bookTitleComboBox.setSelectedIndex(0);
	}

	private int findBookTitleIndex(String bookTitle) {
		for (int i = 0; i < bookTitleComboBox.getItemCount(); i++) {
			if (bookTitleComboBox.getItemAt(i).equals(bookTitle)) {
				return i;
			}
		}
		return -1;
	}

	public String getBookNameFromId(int id) {
		for (BookDTO book : bookList) {
			if (book.getBookId() == id) {
				return book.getBookTitle();
			}
		}
		return null;
	}

	public int getPoemIDFromPoemTitle(int selectedRowIndex) {
		int poemId = -1;
		if (selectedRowIndex >= 0 && selectedRowIndex < poemList.size()) {
			String selectedPoemTitle = poemTable.getValueAt(selectedRowIndex, 0).toString();
			for (PoemDTO poem : poemList) {
				if (poem.getPoemTitle().equals(selectedPoemTitle)) {
					poemId = poem.getPoemId();
					return poemId;
				}
			}
		}
		return poemId;
	}

	public int getBookIdFromTitle(String title) {
		for (BookDTO book : bookList) {
			if (book.getBookTitle().equals(title)) {
				return book.getBookId();
			}
		}
		return -1;
	}

	private void createTable() {
		tableModel.addColumn("Poem Title");
		tableModel.addColumn("Book Title");
	}

	private void fetchPoemTable() {

		bookList = iBLLFacade.fetchBookRecords();
		poemList = iBLLFacade.fetchPoemRecords();

		bookTitleComboBox.removeAllItems();
		filterComboBox.removeAllItems();

		// Add "All" option to filterComboBox
		filterComboBox.addItem("All");

		for (BookDTO book : bookList) {
			bookTitleComboBox.addItem(book.getBookTitle());
			filterComboBox.addItem(book.getBookTitle());
		}

		if (!bookList.isEmpty()) {
			bookTitleComboBox.setSelectedIndex(0);
			filterComboBox.setSelectedIndex(0);
		}

	}

	private void populatePoemTable(String selectedBookTitle) {

		tableModel.setRowCount(0);

		for (PoemDTO poem : poemList) {

			if (selectedBookTitle.equals("All") || selectedBookTitle.equals(getBookNameFromId(poem.getBookId()))) {
				Object[] rowData = { poem.getPoemTitle(), getBookNameFromId(poem.getBookId()) };
				tableModel.addRow(rowData);
			}

		}
	}

	private void populatePoemTable() {

		tableModel.setRowCount(0);

		for (PoemDTO poem : poemList) {

			Object[] rowData = { poem.getPoemTitle(), getBookNameFromId(poem.getBookId()) };
			tableModel.addRow(rowData);

		}
	}

}
