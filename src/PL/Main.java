package PL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BLL.BLLFacade;
import BLL.BookBO;
import BLL.IBLLBook;
import BLL.IBLLFacade;
import BLL.IBLLFileReader;
import BLL.IBLLPoem;
import BLL.IBLLRoot;
import BLL.IBLLToken;
import BLL.IBLLVerse;
import BLL.PoemBO;
import BLL.RootBO;
import BLL.TextFileReaderBO;
import BLL.TokenBO;
import BLL.VerseBO;
import DAL.BookDAO;
import DAL.ImportFileConfigurationManager;
import DAL.DALFacade;
import DAL.FileReaderFactory;
import DAL.IDALBook;
import DAL.IDALFacade;
import DAL.IDALFileReader;
import DAL.IDALPoem;
import DAL.IDALRoot;
import DAL.IDALToken;
import DAL.IDALVerse;
import DAL.PoemDAO;
import DAL.RootDAO;
import DAL.TokenDAO;
import DAL.VerseDAO;
import TO.BookDTO;
import TO.PoemDTO;
import TO.RootDTO;
import TO.TokenDTO;
import TO.VerseDTO;

/**
 * This class represents a graphical user interface for Main Screen.
 */

public class Main extends JFrame {

	/**
	 * JTabbedPane will display the main tab panel
	 */
	
	JTabbedPane tabbedPane;
	
	/**
	 * This method is for main screen
	 */

	public Main() {

		setTitle("Arabic Poetry Encyclopedia");
		setSize(980, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabbedPane = new JTabbedPane();

		// Create tabs for each button
		tabbedPane.addTab("Roots", root());
		tabbedPane.addTab("Poems", poem());
		tabbedPane.addTab("Books", book());
		tabbedPane.addTab("Verses", verse());
		tabbedPane.addTab("Assign Root", assignRoot());
		tabbedPane.addTab("Import Poem", importPoem());
		tabbedPane.addTab("Tokenize Verse", tokenizeVerse());
		tabbedPane.addTab("Root Info", singleRoot());
		tabbedPane.addTab("Auto Root Verse", autoRootVerse());

		add(tabbedPane);
		setLocationRelativeTo(null);
		setVisible(true);

		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				int selectedIndex = tabbedPane.getSelectedIndex();

				// Call the appropriate function based on the selected index
				switch (selectedIndex) {
				case 0:
					// Roots tab selected
					root();
					break;
				case 1:
					// Poems tab selected
					poem();
					break;
				case 2:
					// Books tab selected
					book();
					break;
				case 3:
					// Verses tab selected
					verse();
					break;
				case 4:
					// Assign Root tab selected
					assignRoot();
					break;
				case 5:
					// Import Poem tab selected
					importPoem();
					break;
				case 6:
					// Tokenize Verse tab selected
					tokenizeVerse();
					break;
				case 7:
					// Root Info tab selected
					singleRoot();
					break;
				case 8:
					// Root Info tab selected
					autoRootVerse();
					break;
				// Add more cases for additional tabs as needed
				}
			}
		});

	}

	JPanel assignRoot() {

		IDALBook iDALBook = new BookDAO();
		IBLLBook iBLLBook = new BookBO();

		IDALRoot iDALRoot = new RootDAO();
		IBLLRoot iBLLRoot = new RootBO();

		IDALVerse iDALVerse = new VerseDAO();
		IBLLVerse iVerse = new VerseBO();

		IDALPoem iDALPoem = new PoemDAO();
		IBLLPoem iBLLPoem = new PoemBO();

		IDALToken iDALToken = new TokenDAO();
		IBLLToken iBLLtoken = new TokenBO();

		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();

		iDALFacade.setDALBooK(iDALBook);
		iDALFacade.setBLLBook(iBLLBook);

		iDALFacade.setDALRoot(iDALRoot);
		iDALFacade.setBLLRoot(iBLLRoot);

		iDALFacade.setDALVerse(iDALVerse);
		iDALFacade.setBLLVerse(iVerse);

		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);

		iDALFacade.setDALToken(iDALToken);
		iDALFacade.setBLLToken(iBLLtoken);

		bllFacade.attachFacade(iDALFacade);

		AssignRootGUI assignRootGUI = new AssignRootGUI();
		return assignRootGUI.getPanel(bllFacade);

	}

	JPanel tokenizeVerse() {

		IDALBook iDALBook = new BookDAO();
		IBLLBook iBLLBook = new BookBO();

		IDALVerse iDALVerse = new VerseDAO();
		IBLLVerse iVerse = new VerseBO();

		IDALPoem iDALPoem = new PoemDAO();
		IBLLPoem iBLLPoem = new PoemBO();

		IDALToken iDALToken = new TokenDAO();
		IBLLToken iBLLtoken = new TokenBO();

		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();

		TokenDTO tokenDTO = new TokenDTO();
		iDALFacade.setTokenDTO(tokenDTO);

		iDALFacade.setDALBooK(iDALBook);
		iDALFacade.setBLLBook(iBLLBook);

		iDALFacade.setDALVerse(iDALVerse);
		iDALFacade.setBLLVerse(iVerse);

		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);

		iDALFacade.setDALToken(iDALToken);
		iDALFacade.setBLLToken(iBLLtoken);

		bllFacade.attachFacade(iDALFacade);

		TokenizeVerseGUI verseGUI = new TokenizeVerseGUI();

		return verseGUI.getPanel(bllFacade, tokenDTO);

	}

	JPanel importPoem() {

		String fileType = "txt";
		FileReaderFactory fileReaderFactory = ImportFileConfigurationManager.getFactory(fileType);

		IDALBook iDALBook = new BookDAO();
		IBLLBook ibook = new BookBO();

		IDALPoem iDALPoem = new PoemDAO();
		IBLLPoem iBLLPoem = new PoemBO();

		IDALVerse iDALVerse = new VerseDAO();
		IBLLVerse iVerse = new VerseBO();

		IBLLFileReader iBLLFileReader = new TextFileReaderBO();
		IDALFileReader iDALFileReader = fileReaderFactory.createTextFileReader();

		PoemDTO poemDTO = new PoemDTO();

		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();

		iDALFacade.setPoemDTO(poemDTO);

		iDALFacade.setDALBooK(iDALBook);
		iDALFacade.setBLLBook(ibook);

		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);

		iDALFacade.setDALFileReader(iDALFileReader);
		iDALFacade.setBLLFileReader(iBLLFileReader);

		iDALFacade.setDALVerse(iDALVerse);
		iDALFacade.setBLLVerse(iVerse);

		bllFacade.attachFacade(iDALFacade);

		ArrayList<PoemDTO> poemDTOList = new ArrayList<>();

		ImportPoemGUI importPoemGUI = new ImportPoemGUI();

		return importPoemGUI.getPanel(bllFacade, poemDTOList);
	}

	JPanel book() {

		IDALBook iDALBook = new BookDAO();
		IBLLBook ibook = new BookBO();
		
		IDALPoem iDALPoem = new PoemDAO();
		IBLLPoem iBLLPoem = new PoemBO();

		BookDTO bookDTO = new BookDTO();

		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();

		iDALFacade.setBookDTO(bookDTO);

		iDALFacade.setDALBooK(iDALBook);
		iDALFacade.setBLLBook(ibook);
		
		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);

		bllFacade.attachFacade(iDALFacade);

		BookGUI bookGUI = new BookGUI();
		return bookGUI.getPanel(bllFacade, bookDTO);
	}

	
	JPanel root() {

		IDALRoot iDALRoot = new RootDAO();
		IBLLRoot iBLLRoot = new RootBO();

		RootDTO rootDTO = new RootDTO();

		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();

		iDALFacade.setRootDTO(rootDTO);

		iDALFacade.setDALRoot(iDALRoot);
		iDALFacade.setBLLRoot(iBLLRoot);

		bllFacade.attachFacade(iDALFacade);

		RootGUI rootGUI = new RootGUI();

		return rootGUI.getPanel(bllFacade, rootDTO);

	}

	JPanel poem() {

		IDALBook iDALBook = new BookDAO();
		IBLLBook iBLLBook = new BookBO();

		IDALPoem iDALPoem = new PoemDAO();
		IBLLPoem iBLLPoem = new PoemBO();

		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();

		PoemDTO poemDTO = new PoemDTO();

		iDALFacade.setPoemDTO(poemDTO);

		iDALFacade.setDALBooK(iDALBook);
		iDALFacade.setBLLBook(iBLLBook);

		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);

		bllFacade.attachFacade(iDALFacade);

		PoemGUI poemGUI = new PoemGUI();
		return poemGUI.getPanel(bllFacade, poemDTO);

	}

	JPanel verse() {

		IDALBook iDALBook = new BookDAO();
		IBLLBook iBLLBook = new BookBO();

		IDALVerse iDALVerse = new VerseDAO();
		IBLLVerse iVerse = new VerseBO();

		IDALPoem iDALPoem = new PoemDAO();
		IBLLPoem iBLLPoem = new PoemBO();

		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();

		VerseDTO verseDTO = new VerseDTO();
		iDALFacade.setVerseDTO(verseDTO);

		iDALFacade.setDALBooK(iDALBook);
		iDALFacade.setBLLBook(iBLLBook);

		iDALFacade.setDALVerse(iDALVerse);
		iDALFacade.setBLLVerse(iVerse);

		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);

		bllFacade.attachFacade(iDALFacade);

		List<VerseDTO> verseDTOList = new ArrayList<>();

		VerseGUI verseGUI = new VerseGUI();
		return verseGUI.getPanel(bllFacade, verseDTOList);

	}

	JPanel singleRoot() {
		
		IDALRoot iDALRoot = new RootDAO();
		IBLLRoot iBLLRoot = new RootBO();
		
		IDALPoem iDALPoem = new PoemDAO();
		IBLLPoem iBLLPoem = new PoemBO();
		
		IDALVerse iDALVerse = new VerseDAO();
		IBLLVerse iVerse = new VerseBO();

		RootDTO rootDTO = new RootDTO();

		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();

		iDALFacade.setRootDTO(rootDTO);

		iDALFacade.setDALRoot(iDALRoot);
		iDALFacade.setBLLRoot(iBLLRoot);
		
		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);
		
		iDALFacade.setDALVerse(iDALVerse);
		iDALFacade.setBLLVerse(iVerse);

		bllFacade.attachFacade(iDALFacade);

		RootGUI rootGUI = new RootGUI();
		
		RootInfoGUI singleRootGUI = new RootInfoGUI();
		return singleRootGUI.getPanel(bllFacade);
	}
	
	JPanel autoRootVerse() {
		
		IDALBook iDALBook = new BookDAO();
		IBLLBook iBLLBook = new BookBO();
		
		IDALPoem iDALPoem = new PoemDAO();
		IBLLPoem iBLLPoem = new PoemBO();
		
		IDALVerse iDALVerse = new VerseDAO();
		IBLLVerse iVerse = new VerseBO();
		
		IDALRoot iDALRoot = new RootDAO();
		IBLLRoot iBLLRoot = new RootBO();
		
		IDALToken iDALToken = new TokenDAO();
		IBLLToken iBLLtoken = new TokenBO();
		
		IDALFacade iDALFacade = new DALFacade();
		IBLLFacade bllFacade = new BLLFacade();
		
		iDALFacade.setDALBooK(iDALBook);
		iDALFacade.setBLLBook(iBLLBook);
		
		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);
		
		iDALFacade.setDALVerse(iDALVerse);
		iDALFacade.setBLLVerse(iVerse);
		
		iDALFacade.setDALRoot(iDALRoot);
		iDALFacade.setBLLRoot(iBLLRoot);
		
		iDALFacade.setDALToken(iDALToken);
		iDALFacade.setBLLToken(iBLLtoken);
		
		bllFacade.attachFacade(iDALFacade);
		
		AutoRootVerseGUI autoRootVerseGUI = new AutoRootVerseGUI();
		return autoRootVerseGUI.getPanel(bllFacade);
	}
}
