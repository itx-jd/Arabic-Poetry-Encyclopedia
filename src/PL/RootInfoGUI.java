package PL;
/**
 * This class represents a graphical user interface for retrieving and displaying root information.
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import BLL.IBLLFacade;
import TO.PoemDTO;
import TO.RootDTO;
import TO.RootInfoDTO;
import TO.VerseDTO;

/**
 * This class represents a graphical user interface for RootInfoGUI.
 */

public class RootInfoGUI {

	IBLLFacade iBLLFacade;
	private List<RootDTO> rootList;
	private List<RootInfoDTO> rootInfoList;
	private JPanel panel;
	private JTextField rootTextField;
	private DefaultTableModel rootTableModel, verseTableModel;
	private JTable rootTable, verseTable;
	private JLabel selectedRootLabel, selectedRootTextLabel;
	JButton searchButton;
	JPanel leftPanel;
	private List<PairPanel> pairPanels;
	String selectedPoemTitle;

	String selected_root_text;
	/**
     * Retrieves the panel containing the root information GUI components.
     *
     * @param iBLLFacade The interface to the business logic layer facade.
     * @return The constructed JPanel for root information retrieval.
     */
	public JPanel getPanel(IBLLFacade iBLLFacade) {

		this.iBLLFacade = iBLLFacade;

		panel = new JPanel(new BorderLayout());

		// Left Panel
		JPanel leftPanel = new JPanel(new GridBagLayout());
		GridBagConstraints leftGBC = new GridBagConstraints();
		leftGBC.insets = new Insets(5, 5, 5, 5);

		JLabel rootLabel = new JLabel("Root");
		leftGBC.gridx = 0;
		leftGBC.gridy = 0;
		leftPanel.add(rootLabel, leftGBC);

		rootTextField = new JTextField();
		leftGBC.gridy = 1;
		leftGBC.fill = GridBagConstraints.HORIZONTAL;
		leftPanel.add(rootTextField, leftGBC);

		searchButton = new JButton("Search");
		leftGBC.gridy = 2;
		leftGBC.fill = GridBagConstraints.NONE;
		leftPanel.add(searchButton, leftGBC);

		rootTableModel = new DefaultTableModel();
		rootTable = new JTable(rootTableModel);
		JScrollPane rootTableScrollPane = new JScrollPane(rootTable);
		leftGBC.gridy = 3;
		leftGBC.gridheight = 3;
		leftGBC.fill = GridBagConstraints.BOTH;
		leftPanel.add(rootTableScrollPane, leftGBC);

		// Right Panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		selectedRootLabel = new JLabel("Selected Root: ");
		selectedRootLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		selectedRootLabel.setFont(selectedRootLabel.getFont().deriveFont(Font.PLAIN, 16f));
		rightPanel.add(selectedRootLabel);

		selectedRootTextLabel = new JLabel();
		rightPanel.add(selectedRootTextLabel);

		verseTableModel = new DefaultTableModel();
		verseTable = new JTable(verseTableModel);
		JScrollPane verseTableScrollPane = new JScrollPane(verseTable);
		rightPanel.add(verseTableScrollPane);

		// Split Pane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setResizeWeight(0.5);
		panel.add(splitPane, BorderLayout.CENTER);

		createTable();

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

		// Add a ListSelectionListener to the rootTable
		rootTable.getSelectionModel().addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {
				// Get the selected row
				int selectedRow = rootTable.getSelectedRow();

				// Check if a row is selected
				if (selectedRow >= 0) {
					// Retrieve data from the selected row
					Object rootText = rootTable.getValueAt(selectedRow, 0);
					populateVerseTable(rootText.toString());

				}
			}
		});

		// Add a ListSelectionListener to the rootTable
		verseTable.getSelectionModel().addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {
				// Get the selected row
				int selectedRow = verseTable.getSelectedRow();
				int selectedColumn = verseTable.getSelectedColumn();

				if (selectedRow >= 0 && selectedColumn == 4) {

					selectedPoemTitle = rootInfoList.get(selectedRow).getPoemTitle();
					displayCompletePoem();
					
					
				} else if(selectedRow >= 0){

					String verse_text = rootInfoList.get(selectedRow).getMisrah1().toString()+" "+rootInfoList.get(selectedRow).getMisrah2().toString();
					String[] tokens = verse_text.split("\\s+");
					
					ArrayList<String> tokenOfVerse = new ArrayList<>();
					
					
					
					for(String token : tokens) {
						
						String potentialRoot = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance()
								.processToken(token).getAllRootString();
						
						potentialRoot = potentialRoot.replaceAll("[:-]", "");
						
						
						if(potentialRoot.equals(selected_root_text)) {
							
							String my_token = token+" | "+getPOSTagForToken(token);
							
							if(!tokenOfVerse.contains(my_token)) {
								tokenOfVerse.add(my_token);
							}
								
							
						}
						
						
					}

//					 Display each token in separate JTextArea
					displayTokens(tokenOfVerse, "Tokens");
				}

			}
		});

		// Add ActionListener to the JButton
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				populateRootTable();

				if (isRootExist(rootTextField.getText().toString().trim())) {

					selected_root_text = rootTextField.getText().toString().trim();

					populateVerseTable(rootTextField.getText().toString().trim());

				} else {
					JOptionPane.showMessageDialog(panel, "Root Not Found", "Caution", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		populateRootTable();

		return panel;
	}

	private void displayTokens(ArrayList<String> tokenOfVerse, String title) {

	    JFrame tokenFrame = new JFrame(title);
	    tokenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    tokenFrame.setSize(new Dimension(400, 200));

	    JTextArea tokenTextArea = new JTextArea();
	    tokenTextArea.setEditable(false);

	    // Create a font with increased size
	    Font largerFont = tokenTextArea.getFont().deriveFont(Font.PLAIN, 16f);
	    tokenTextArea.setFont(largerFont);

	    for (String token : tokenOfVerse) {
	        tokenTextArea.append(token + "\n");
	    }

	    JScrollPane tokenScrollPane = new JScrollPane(tokenTextArea);
	    tokenFrame.add(tokenScrollPane);

	    tokenFrame.setLocationRelativeTo(panel);
	    tokenFrame.setVisible(true);
	}


	public void createTable() {

		rootTableModel.addColumn("Root Text");

		verseTableModel.addColumn("Book Title");
		verseTableModel.addColumn("Poem Title");
		verseTableModel.addColumn("Misrah 1");
		verseTableModel.addColumn("Misrah 2");
		verseTableModel.addColumn("Status");
	}

	private void populateRootTable() {

		rootList = iBLLFacade.fetchRootRecords();

		// Clear the existing table data
		rootTableModel.setRowCount(0);

		// Populate the table with the fetched book records
		for (RootDTO root : rootList) {
			Object[] rowData = { root.getRootText() };
			rootTableModel.addRow(rowData);
		}
	}

	private void populateVerseTable(String rootText) {

		rootInfoList = iBLLFacade.getRootInfoDTOListByRootText(rootText.toString());

		selectedRootLabel.setText("Selected Root: " + rootText.toString());

		// Clear the existing table data
		verseTableModel.setRowCount(0);
		
		selected_root_text = rootText.toString();

		// Populate the table with the fetched book records
		for (RootInfoDTO infoDTO : rootInfoList) {
			Object[] rowData = { infoDTO.getBookTitle(), infoDTO.getPoemTitle(), infoDTO.getMisrah1(),
					infoDTO.getMisrah2(), infoDTO.getStatus() };
			verseTableModel.addRow(rowData);
		}

	}

	boolean isRootExist(String root_text) {

		boolean isFound = false;
		rootList = iBLLFacade.fetchRootRecords();

		for (RootDTO rootDTO : rootList) {

			if (rootDTO.getRootText().equals(root_text)) {
				isFound = true;
			}

		}

		return isFound;

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

	public void displayCompletePoem() {

		JFrame poemFrame = new JFrame(selectedPoemTitle);
		poemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		poemFrame.setSize(new Dimension(400, 400));
		
		JPanel panel = new JPanel(new BorderLayout());
		
		pairPanels = new ArrayList<>();
		JScrollPane rightScrollPane = new JScrollPane(panel);
		
		rightScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		fetchVersesAndSetToPanel(panel);

		poemFrame.add(rightScrollPane);

		poemFrame.setLocationRelativeTo(panel);
		poemFrame.setVisible(true);

	}

	
	private void fetchVersesAndSetToPanel(JPanel mainPanel) {
	    int poemId = iBLLFacade.getPoemId(selectedPoemTitle);
	    List<VerseDTO> verses = iBLLFacade.getVersesByPoemId(poemId);

	    // Create a new container panel for PairPanels
	    JPanel versesContainer = new JPanel();
	    versesContainer.setLayout(new BoxLayout(versesContainer, BoxLayout.Y_AXIS));

	    // Add pair panels for each verse to the container
	    for (VerseDTO verse : verses) {
	        PairPanel pairPanel = new PairPanel();
	        pairPanel.setVerseText(verse.getVerseText());
	        pairPanels.add(pairPanel);
	        pairPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        versesContainer.add(pairPanel);
	    }

	    // Clear existing pair panels
	    pairPanels.clear();

	    // Remove all components from the mainPanel
	    mainPanel.removeAll();

	    // Add the container with PairPanels to the mainPanel
	    mainPanel.add(versesContainer);

	    // Revalidate and repaint the frame
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}


}
