package DAL;

import java.util.ArrayList;
import java.util.List;

import BLL.IBLLBook;
import BLL.IBLLFileReader;
import BLL.IBLLPoem;
import BLL.IBLLRoot;
import BLL.IBLLToken;
import BLL.IBLLVerse;
import TO.BookDTO;
import TO.PoemDTO;
import TO.RootDTO;
import TO.RootInfoDTO;
import TO.TokenDTO;
import TO.VerseDTO;

public class DALFacade implements IDALFacade {

	IDALBook iDALBook;
	IBLLBook iBLLbook;
	BookDTO bookDTO;

	IDALVerse iDALVerse;
	IBLLVerse iBLLVerse;
	VerseDTO verseDTO;

	IDALPoem iDALPoem;
	IBLLPoem iBLLPoem;
	PoemDTO poemDTO;

	IDALRoot iDALRoot;
	IBLLRoot iBLLroot;
	RootDTO rootDTO;
	
	IDALToken iDALToken;
	IBLLToken iBLLToken;
	TokenDTO tokenDTO;

	IDALFileReader iDALFileReader;
	IBLLFileReader iBLLFileReader;

	// For Book

	// Connecting Methods

	@Override
	public void setBLLBook(IBLLBook iBLLbook) {
		this.iBLLbook = iBLLbook;
	}

	@Override
	public void setDALBooK(IDALBook iDALBook) {
		this.iDALBook = iDALBook;

	}

	@Override
	public void setBookDTO(BookDTO bookDTO) {
		this.bookDTO = bookDTO;

	}

	// Operational Methods

	@Override
	public boolean addBookRecord(BookDTO bookDTO) {
		return iDALBook.addBookRecord(bookDTO);

	}

	@Override
	public boolean updateBookRecord(BookDTO bookDTO) {
		return iDALBook.updateBookRecord(bookDTO);
	}

	@Override
	public boolean deleteBookRecord(BookDTO bookDTO) {
		return iDALBook.deleteBookRecord(bookDTO);
	}

	@Override
	public List<BookDTO> fetchBookRecords() {
		return iDALBook.fetchBookRecords();
	}

	// FOR VERSE

	// Connecting Methods

	@Override
	public void setDALVerse(IDALVerse iDALVerse) {
		this.iDALVerse = iDALVerse;

	}

	@Override
	public void setBLLVerse(IBLLVerse iBLLVerse) {
		this.iBLLVerse = iBLLVerse;

	}

	@Override
	public void setVerseDTO(VerseDTO verseDTO) {
		this.verseDTO = verseDTO;

	}

	// Operational Methods

	@Override
	public boolean addVerseRecord(List<VerseDTO> verseList) {
		return iDALVerse.addVerseRecord(verseList);
	}
	
	public List<VerseDTO> getVersesByPoemId(int poemId){
		return iDALVerse.getVersesByPoemId(poemId);
	}
	
	@Override
	public boolean deleteVersesByPoemId(int poemId) {
		return iDALVerse.deleteVersesByPoemId(poemId);
	}

	// For Text File Reading From File

	// Connecting

	@Override
	public void setDALFileReader(IDALFileReader iDALFileReader) {
		this.iDALFileReader = iDALFileReader;
	}

	@Override
	public void setBLLFileReader(IBLLFileReader iBLLFileReader) {
		this.iBLLFileReader = iBLLFileReader;

	}

	@Override
	public void setPoemDTO(PoemDTO poemDTO) {
		this.poemDTO = poemDTO;

	}

	// Operational Methods

	@Override
	public ArrayList<PoemDTO> readingFile(String filePath, int book_id) {
		return iDALFileReader.readingFile(filePath, book_id);
	}

	// For Poem

	// Connecting

	@Override
	public void setDALPoem(IDALPoem iDALPoem) {
		this.iDALPoem = iDALPoem;

	}

	@Override
	public void setBLLPoem(IBLLPoem iBLLPoem) {
		this.iBLLPoem = iBLLPoem;

	}

	// Operational

	@Override
	public boolean addPoemRecord(PoemDTO poemDTO) {
		return iDALPoem.addPoemRecord(poemDTO);
	}

	@Override
	public boolean updatePoemRecord(PoemDTO poemDTO) {
		return iDALPoem.updatePoemRecord(poemDTO);
	}

	@Override
	public boolean deletePoemRecord(PoemDTO poemDTO) {
		return iDALPoem.deletePoemRecord(poemDTO);
	}

	@Override
	public List<PoemDTO> fetchPoemRecords() {
		return iDALPoem.fetchPoemRecords();
	}

	@Override
	public int getPoemId(String name) {
		return iDALPoem.getPoemId(name);
	}

	// Connecting Methods of Roots

	@Override
	public void setBLLRoot(IBLLRoot iBLLroot) {
		this.iBLLroot = iBLLroot;
	}

	@Override
	public void setDALRoot(IDALRoot iDALRoot) {
		this.iDALRoot = iDALRoot;

	}

	@Override
	public void setRootDTO(RootDTO rootDTO) {
		this.rootDTO = rootDTO;

	}

	// Operational Methods of Roots

	@Override
	public boolean addRootRecord(RootDTO rootDTO) {
		return iDALRoot.addRootRecord(rootDTO);

	}

	@Override
	public boolean updateRootRecord(RootDTO rootDTO) {
		return iDALRoot.updateRootRecord(rootDTO);
	}

	@Override
	public boolean deleteRootRecord(RootDTO rootDTO) {
		return iDALRoot.deleteRootRecord(rootDTO);
	}

	@Override
	public List<RootDTO> fetchRootRecords() {
		return iDALRoot.fetchRootRecords();
	}
	
	@Override
	public List<RootDTO> getRootDTOListByVerseId(int verseId) {
		return iDALRoot.getRootDTOListByVerseId(verseId);
	}

	@Override
	public boolean deleteRootVerseRecordsByVerseId(int verseId) {
		return iDALRoot.deleteRootRecord(rootDTO);
	}

	@Override
	public boolean insertRootVerseRecord(int verseId, int rootId, String status) {
		return iDALRoot.insertRootVerseRecord(verseId, rootId, status);
	}
	
	@Override
	public boolean doesRootVerseExist(int rootId, int verseId) {
		return iDALRoot.doesRootVerseExist(rootId, verseId);
	}

	@Override
	public int getRootIdFromRootText(String rootText) {
		return iDALRoot.getRootIdFromRootText(rootText);
	}
	
	@Override
	public boolean deleteRootVerseByVerseID(int verseId) {
		return iDALRoot.deleteRootVerseByVerseID(verseId);
	}
	
	@Override
	public List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText) {
		return iDALRoot.getRootInfoDTOListByRootText(rootText);
	}
	
	
	// For Token
	
	
	// Connecting
	
	
	@Override
	public void setBLLToken(IBLLToken iBLLToken) {
		this.iBLLToken = iBLLToken;
		
	}

	@Override
	public void setDALToken(IDALToken iDALToken) {
		this.iDALToken = iDALToken;
		
	}

	@Override
	public void setTokenDTO(TokenDTO tokenDTO) {
		this.tokenDTO = tokenDTO;
		
	}
	
	// Operational Methods of Tokens

	@Override
	public boolean insertToken(TokenDTO tokenDTO) {
		return iDALToken.insertToken(tokenDTO);
	}

	@Override
	public int getTokenIdFromTokenText(String word) {
		return iDALToken.getTokenIdFromTokenText(word);
	}

	@Override
	public boolean deleteToken(int tokenId) {
		return iDALToken.deleteToken(tokenId);
	}

	@Override
	public boolean updateToken(TokenDTO updatedToken) {
		return iDALToken.updateToken(updatedToken);
	}

	@Override
	public List<TokenDTO> getTokenDTOList() {
		return iDALToken.getTokenDTOList();
	}


	@Override
	public boolean insertTokenIntoVerse(int token_id, int verse_id) {
		return iDALToken.insertTokenIntoVerse(token_id, verse_id);
	}

	@Override
	public boolean doesTokenVerseExist(int token_id, int verse_id) {
		return iDALToken.doesTokenVerseExist(token_id, verse_id);
	}


	@Override
	public List<TokenDTO> getTokenDTOListByVerseId(int verseId) {
		return iDALToken.getTokenDTOListByVerseId(verseId);
	}
	

}
