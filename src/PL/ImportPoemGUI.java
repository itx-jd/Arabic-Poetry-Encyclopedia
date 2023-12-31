package PL;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import BLL.IBLLFacade;
import TO.BookDTO;
import TO.PoemDTO;
import TO.VerseDTO;


/**
 * This class represents a graphical user interface for Import Poem sGUI.
 */

public class ImportPoemGUI {
	JPanel panel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel gridPanel;
	private JLabel idLabel;
	private JButton selectFileButton;
	private JButton uploadPoemButton;
	private JComboBox<String> bookTitlesComboBox;
	private JProgressBar progressBar;
	private JTextArea textArea;
	private JFileChooser fileChooser;
	String file_content = "";
	String selectedFile;
	int book_id = -1;

	IBLLFacade iBLLFacade;
	ArrayList<PoemDTO> poemsArrayList;

	List<BookDTO> bookList;
	List<String> bookTitlesList;

	public JPanel getPanel(IBLLFacade iBLLFacade, ArrayList<PoemDTO> poemsArrayList) {

		this.iBLLFacade = iBLLFacade;
		this.poemsArrayList = poemsArrayList;
		
		bookTitlesList = new ArrayList<>();
		bookList = iBLLFacade.fetchBookRecords();

		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());

		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		gridPanel = new JPanel(new GridLayout(3, 3));
		idLabel = new JLabel("Book Title:");

		
		bookTitlesComboBox = new JComboBox<>();
		populateBookTitlesList();
		
		bookTitlesComboBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				populateBookTitlesList();

			}

			@Override
			public void focusLost(FocusEvent e) {
				// Code to execute when the comboBox loses focus
			}
		});

		selectFileButton = new JButton("Select Poem File");
		uploadPoemButton = new JButton("Upload Poem");

		progressBar = new JProgressBar();
		textArea = new JTextArea();
		textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		fileChooser.setFileFilter(filter);

		selectFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser.showOpenDialog(panel);

				if (returnValue == JFileChooser.APPROVE_OPTION) {

					selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
					// Start the loading bar while processing the file
					progressBar.setIndeterminate(true);

					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

						boolean success;

						@Override
						protected Void doInBackground() throws Exception {
							// Process the file and update the UI
							success = processTextAreaContent(selectedFile);

							return null;
						}

						@Override
						protected void done() {
							// Stop the loading bar

							if (success) {
								JOptionPane.showMessageDialog(panel, "File Read Successfully");

							} else {
								JOptionPane.showMessageDialog(panel, "Failed To Read File");

							}

							textArea.setText(file_content);
							progressBar.setIndeterminate(false);

						}
					};

					worker.execute();
				}
			}
		});

		uploadPoemButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				

				if (bookTitlesComboBox.getSelectedItem() != null) {

					// Start the loading bar while processing the file
					progressBar.setIndeterminate(true);

					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws Exception {
							

							book_id = getSelectedBookId(bookTitlesComboBox.getSelectedItem().toString());
							
							ArrayList<PoemDTO> poemsArrayList = iBLLFacade.readingFile(selectedFile, book_id);

							// Upload Unique Poems

							ArrayList<String> unique_poem_titles = new ArrayList<String>();
							unique_poem_titles.clear();
							
							
							for (PoemDTO poem : poemsArrayList) {

								if (!unique_poem_titles.contains(poem.getPoemTitle())) {
									unique_poem_titles.add(poem.getPoemTitle());
									iBLLFacade.addPoemRecord(new PoemDTO(poem.getPoemTitle(), book_id));
									
								}
							}

							// UPLOADING VERSE OF POEM INTO DATABASE

							for (PoemDTO poem : poemsArrayList) {
								poem.setPoemId(iBLLFacade.getPoemId(poem.getPoemTitle()));
								poem.setVerseListPoemId(poem.getPoemId());
								iBLLFacade.addVerseRecord(poem.getPoemVerses());

							}

							return null;
						}

						@Override
						protected void done() {
							// Stop the loading bar
							progressBar.setIndeterminate(false);
							JOptionPane.showMessageDialog(panel, "Upload Successfully");
						}
					};

					worker.execute();

				} else {
					JOptionPane.showMessageDialog(panel, "Error: No Book Selected");
				}
			}
		});
		
		gridPanel.add(idLabel);
		gridPanel.add(bookTitlesComboBox);
		gridPanel.add(selectFileButton);
		gridPanel.add(uploadPoemButton);

		leftPanel.add(gridPanel, BorderLayout.NORTH);
		leftPanel.add(progressBar, BorderLayout.SOUTH);

		panel.add(leftPanel, BorderLayout.WEST);
		panel.add(rightPanel, BorderLayout.CENTER);

		rightPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
		return panel;
	}

	private int getSelectedBookId(String selectedTitle) {

		for (BookDTO book : bookList) {
			if (book.getBookTitle().equals(selectedTitle)) {
				return book.getBookId();
			}
		}

		return -1;
	}

	private void populateBookTitlesList() {
		
		bookList = iBLLFacade.fetchBookRecords();

		bookTitlesComboBox.removeAllItems();
		
		for (BookDTO book : bookList) {
			bookTitlesComboBox.addItem(book.getBookTitle());
		}
		
		if (bookTitlesComboBox.getItemCount() > 0) {
			bookTitlesComboBox.setSelectedIndex(0);
		}

	}

	// DISPLAYING JTEXT AREA CONTENT

	private boolean processTextAreaContent(String selectedFile) {

		poemsArrayList = iBLLFacade.readingFile(selectedFile, book_id);
		file_content = "";

		if (poemsArrayList.size() > 0) {

			for (PoemDTO poemDTO : poemsArrayList) {

				String poem_title = poemDTO.getPoemTitle();

				file_content += "\n\n" + poem_title + "\n\n";

				for (VerseDTO versseDTO : poemDTO.getPoemVerses()) {
					file_content += versseDTO.getVerseText() + "\n";

				}

			}

			return true;

		} else {
			return false;
		}

	}
}
