package PL;
/**
 * This class represents a graphical user interface for managing books, allowing insertion, deletion, and updating of book records.
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BLL.IBLLFacade;
import TO.BookDTO;
import TO.PoemDTO;

/**
 * This class represents a graphical user interface for BookGUI.
 */

public class BookGUI {
    private JPanel panel;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField serialNoField;
    private JTextField bookTitleField;
    private JTextField authorNameField;
    private JComboBox<Integer> authorYearComboBox;
    private JButton insertButton;
    private JButton deleteRecordButton;
    private JButton updateButton;
    private JButton poemButton;

    private IBLLFacade iBLLFacade;
    private BookDTO bookDTO;
    List<BookDTO> bookList;
    
    /**
     * Retrieves the panel containing the book management GUI components.
     *
     * @param iBLLFacade The interface to the business logic layer facade.
     * @param bookDTO    The Data Transfer Object representing a book.
     * @return The constructed JPanel for book management.
     */
   
	public JPanel getPanel(IBLLFacade iBLLFacade, BookDTO bookDTO) {
    	
		this.iBLLFacade = iBLLFacade;
		this.bookDTO = bookDTO;

		panel = new JPanel(new BorderLayout());

		 // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        panel.add(leftPanel);

        tableModel = new DefaultTableModel();
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
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

        rightPanel.add(new JLabel("Books Serial No"), gbc);

        gbc.gridy++;
        serialNoField = new JTextField();
        rightPanel.add(serialNoField, gbc);

        gbc.gridy++;
        rightPanel.add(new JLabel("Books Title"), gbc);

        gbc.gridy++;
        bookTitleField = new JTextField();
        rightPanel.add(bookTitleField, gbc);

        gbc.gridy++;
        rightPanel.add(new JLabel("Author Name"), gbc);

        gbc.gridy++;
        authorNameField = new JTextField();
        rightPanel.add(authorNameField, gbc);

        gbc.gridy++;
        rightPanel.add(new JLabel("Author Year Of Death"), gbc);

        gbc.gridy++;
        authorYearComboBox = new JComboBox<>(generateYearRange());
        rightPanel.add(authorYearComboBox, gbc);

        gbc.gridy++;
        insertButton = new JButton("Insert");
        insertButton.setPreferredSize(new Dimension(120, 30));
        rightPanel.add(insertButton, gbc);

        gbc.gridy++;
        updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(120, 30));
        rightPanel.add(updateButton, gbc);

        gbc.gridy++;
        deleteRecordButton = new JButton("Delete");
        deleteRecordButton.setPreferredSize(new Dimension(120, 30));
        rightPanel.add(deleteRecordButton, gbc);
        
        gbc.gridy++;
        poemButton = new JButton("View Poems");
        poemButton.setPreferredSize(new Dimension(120, 30));
        rightPanel.add(poemButton, gbc);
 

        // Action Listeners
        insertButton.addActionListener(this::insertRecordAction);
        updateButton.addActionListener(this::updateRecordAction);
        deleteRecordButton.addActionListener(this::deleteRecordAction);
        poemButton.addActionListener(this::viewPoemTitlesAction);

        // Add a ListSelectionListener to the bookTable
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Object serialNo = bookTable.getValueAt(selectedRow, 0);
                    Object bookTitle = bookTable.getValueAt(selectedRow, 1);
                    Object authorName = bookTable.getValueAt(selectedRow, 2);
                    Object authorYear = bookTable.getValueAt(selectedRow, 3);

                    serialNoField.setText(serialNo.toString());
                    bookTitleField.setText(bookTitle.toString());
                    authorNameField.setText(authorName.toString());
                    authorYearComboBox.setSelectedItem(Integer.parseInt(authorYear.toString()));
                }
            }
        });

        createTable();
        populateBookTable();
        return panel;
    }

    private void insertRecordAction(ActionEvent e) {
    	
        if (isFormIncomplete()) {
            showError("Error: Incomplete Form");
        } else {
        	
        	try {
        		
        		int serialNo = Integer.parseInt(serialNoField.getText());
        		
        		String bookTitle = bookTitleField.getText();
                String authorName = authorNameField.getText();
                int authorYear = (Integer) authorYearComboBox.getSelectedItem();

                bookDTO.setSerialNo(serialNo);
                bookDTO.setBookTitle(bookTitle);
                bookDTO.setBookAuthor(authorName);
                bookDTO.setAuthorDeathYear(authorYear);

                if (iBLLFacade.addBookRecord(bookDTO)) {
                    showSuccess("Record Inserted Successfully");
                    populateBookTable();
                    resetFields();
                } else {
                    showError("Failed To Insert Record");
                }
        		
        	}catch(NumberFormatException exception) {
        		showError("Invald Book Serial Formate");
        	}

        	
        		
            
            
        }
    }

    private void updateRecordAction(ActionEvent e) {
        if (isFormIncomplete()) {
            showError("Error: Incomplete Form");
        } else {
            int serialNo = Integer.parseInt(serialNoField.getText());
            String bookTitle = bookTitleField.getText();
            String authorName = authorNameField.getText();
            int authorYear = (Integer) authorYearComboBox.getSelectedItem();

            bookDTO.setSerialNo(serialNo);
            bookDTO.setBookTitle(bookTitle);
            bookDTO.setBookAuthor(authorName);
            bookDTO.setAuthorDeathYear(authorYear);

            if (iBLLFacade.updateBookRecord(bookDTO)) {
                showSuccess("Record Updated Successfully");
                populateBookTable();
                resetFields();
            } else {
                showError("Failed To Update Record");
            }
        }
    }

    private void viewPoemTitlesAction(ActionEvent e) {
    	
        if (!serialNoField.getText().trim().isEmpty()) {
        	
            int serialNo = Integer.parseInt(serialNoField.getText());
            List<PoemDTO> poemDTOList = iBLLFacade.fetchPoemRecords();
            
            Iterator<PoemDTO> iterator = poemDTOList.iterator();
            while (iterator.hasNext()) {
                PoemDTO poemDTO = iterator.next();
                if (poemDTO.getBookId() != getBookIdfromBookSerialNo(serialNo)) {
                    iterator.remove();
                }
            }

            
            displayPoemTitles(poemDTOList);
        }else{
        	 showError("Serial No. Not Found");
        }
    }
    
    public void displayPoemTitles(List<PoemDTO> poemDTOList) {
    	
    	JFrame poemFrame = new JFrame("Poems");
		poemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		poemFrame.setSize(new Dimension(400, 200));
		
		
		
		DefaultTableModel poemTableModel;
		JTable poemTable;
		
		poemTableModel = new DefaultTableModel();
		poemTable = new JTable(poemTableModel);
        JScrollPane scrollPane = new JScrollPane(poemTable);

        poemTableModel.addColumn("Poem Titles");
        
        poemTableModel.setRowCount(0);
        
        for(PoemDTO poemDTO: poemDTOList) {
        	
        	Object[] rowData = { poemDTO.getPoemTitle()};
        	poemTableModel.addRow(rowData);
        	
        }

        
		poemFrame.add(scrollPane);

		poemFrame.setLocationRelativeTo(panel);
		poemFrame.setVisible(true);
    	
    }
    
    public int getBookIdfromBookSerialNo(int serial) {
    	
    	for (BookDTO book : bookList) {
			if (book.getSerialNo() == serial) {
				return book.getBookId();
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

    
    private void deleteRecordAction(ActionEvent e) {
        if (!serialNoField.getText().trim().isEmpty()) {
            int serialNo = Integer.parseInt(serialNoField.getText());
            bookDTO.setSerialNo(serialNo);

            if (iBLLFacade.deleteBookRecord(bookDTO)) {
                showSuccess("Record Deleted Successfully");
                populateBookTable();
                resetFields();
            } else {
                showError("Failed To Delete Record");
            }
        } else {
            showError("Error: Incomplete Form");
        }
    }


    private void createTable() {
        tableModel.addColumn("Books Serial No");
        tableModel.addColumn("Books Title");
        tableModel.addColumn("Author Name");
        tableModel.addColumn("Author Year Of Death");
        
    }

    private void populateBookTable() {
    	
        bookList = iBLLFacade.fetchBookRecords();
        tableModel.setRowCount(0);
        for (BookDTO book : bookList) {
            Object[] rowData = { book.getSerialNo(), book.getBookTitle(), book.getBookAuthor(),
                    book.getAuthorDeathYear() };
            tableModel.addRow(rowData);
        }
    }

    private Integer[] generateYearRange() {
        int startYear = 1800; // start year of combo box
        int endYear = 2023; //   end year of combo box
        int size = endYear - startYear + 1;
        Integer[] years = new Integer[size];
        for (int i = 0; i < size; i++) {
            years[i] = startYear + i;
        }
        return years;
    }

    private boolean isFormIncomplete() {
        return serialNoField.getText().trim().isEmpty() || bookTitleField.getText().trim().isEmpty()
                || authorNameField.getText().trim().isEmpty() || authorYearComboBox.getSelectedItem() == null;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetFields() {
        serialNoField.setText("");
        bookTitleField.setText("");
        authorNameField.setText("");
        authorYearComboBox.setSelectedIndex(0);
    }
}
