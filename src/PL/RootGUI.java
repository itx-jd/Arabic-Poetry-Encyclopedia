package PL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.EventListener;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import BLL.IBLLFacade;
import TO.RootDTO;

/**
 * This class represents a graphical user interface for RootGUI.
 */

public class RootGUI {
	private JPanel panel;
	private JTable rootTable;
	private DefaultTableModel tableModel;
	private JTextField rootTextField;
	private JButton pushRecordButton;
	private JButton deleteRecordButton;
	private JButton updateButton;

	private IBLLFacade iBLLFacade;
	private RootDTO rootDTO;
	private List<RootDTO> rootList;

	private int selectedRecordIndex = -1;
	/**
     * Retrieves the panel containing the RootDTO records GUI components.
     *
     * @param iBLLFacade The interface to the business logic layer facade.
     * @param rootDTO    The RootDTO object to handle records.
     * @return The constructed JPanel for RootDTO records handling.
     */
	public JPanel getPanel(IBLLFacade iBLLFacade, RootDTO rootDTO) {
		this.iBLLFacade = iBLLFacade;
		this.rootDTO = rootDTO;

		panel = new JPanel(new BorderLayout());

		// Left Panel
		JPanel leftPanel = new JPanel(new BorderLayout());
		panel.add(leftPanel, BorderLayout.CENTER);

		tableModel = new DefaultTableModel();
		rootTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(rootTable);
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

		rightPanel.add(new JLabel("Root Text"), gbc);

		gbc.gridy++;
		rootTextField = new JTextField();
		rightPanel.add(rootTextField, gbc);

		gbc.gridy++;
		pushRecordButton = new JButton("Insert");
		pushRecordButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(pushRecordButton, gbc);

		gbc.gridy++;
		updateButton = new JButton("Update");
		updateButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(updateButton, gbc);

		gbc.gridy++;
		deleteRecordButton = new JButton("Delete");
		deleteRecordButton.setPreferredSize(new Dimension(120, 30));
		rightPanel.add(deleteRecordButton, gbc);

		// Action Listeners
		pushRecordButton.addActionListener(e -> handleInsertRecord());
		updateButton.addActionListener(e -> handleUpdateRecord());
		deleteRecordButton.addActionListener(e -> handleDeleteRecord());

		// Add a ListSelectionListener to the rootTable
		rootTable.getSelectionModel().addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {
				// Get the selected row
				int selectedRow = rootTable.getSelectedRow();

				// Check if a row is selected
				if (selectedRow >= 0) {
					// Retrieve data from the selected row
					Object rootText = rootTable.getValueAt(selectedRow, 0);
					selectedRecordIndex = selectedRow;

					// Set the data to the text fields
					rootTextField.setText(rootText.toString());
				}
			}
		});
		
		rootTable.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				populateRootTable();
				
			}
		});

		// Initialize table and populate data
		createTable();
		populateRootTable();

		return panel;
	}

	private void handleInsertRecord() {
		if (rootTextField.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(panel, "Error: Incomplete Form !");
		} else {

			String rootText = rootTextField.getText();
			rootDTO.setRootText(rootText);

			if (iBLLFacade.addRootRecord(rootDTO)) {

				JOptionPane.showMessageDialog(panel, "Record Inserted Successfully !");

				populateRootTable();

			} else {
				JOptionPane.showMessageDialog(panel, "Failed To Insert Record !");
			}

		}
	}

	private void handleUpdateRecord() {
		if (rootTextField.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(panel, "Error: Incomplete Form");
		} else {

			String rootText = rootTextField.getText();
			rootDTO.setRootText(rootText);

			int rootId = getRootIdByText(rootList.get(selectedRecordIndex).getRootText());
			rootDTO.setRootId(rootId);

			// Update Method

			if (iBLLFacade.updateRootRecord(rootDTO)) {
				JOptionPane.showMessageDialog(panel, "Record Updated Successfully !");
				populateRootTable();
			} else {
				JOptionPane.showMessageDialog(panel, "Failed To Update Record !");
			}

		}
	}

	private void handleDeleteRecord() {
		if (!rootTextField.getText().trim().isEmpty()) {

			int rootId = getRootIdByText(rootList.get(selectedRecordIndex).getRootText());
			rootDTO.setRootId(rootId);

			if (iBLLFacade.deleteRootRecord(rootDTO)) {

				JOptionPane.showMessageDialog(panel, "Record Deleted Successfully !");

				populateRootTable();

			} else {
				JOptionPane.showMessageDialog(panel, "Failed To Delete Record !");
			}

		} else {
			JOptionPane.showMessageDialog(panel, "Error: Incomplete Form !");
		}
	}

	int getRootIdByText(String text) {

		int id = -1;

		for (RootDTO root_dto : rootList) {
			if (root_dto.getRootText().equals(text)) {
				return root_dto.getRootId();
			}
		}

		return id;

	}

	private void createTable() {
		tableModel.addColumn("Root Text");
	}

	private void populateRootTable() {

		rootList = iBLLFacade.fetchRootRecords();

		// Clear the existing table data
		tableModel.setRowCount(0);

		// Populate the table with the fetched book records
		for (RootDTO root : rootList) {
			Object[] rowData = { root.getRootText() };
			tableModel.addRow(rowData);
		}
	}
}
