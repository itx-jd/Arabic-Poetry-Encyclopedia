/*
 * Data Access Layer Facade Interface
 */
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

public interface IDALFacade extends IDALBook, IDALVerse, IDALFileReader, IDALPoem, IDALToken {

    /*
     * Book methods
     */
	public boolean addBookRecord(BookDTO bookDTO);
	public boolean updateBookRecord(BookDTO bookDTO);
	public boolean deleteBookRecord(BookDTO bookDTO);
	public List<BookDTO> fetchBookRecords();
	public void setDALBooK(IDALBook iDALBook);
	public void setBLLBook(IBLLBook iBLLbook);
	public void setBookDTO(BookDTO bookDTO);

    /*
     * Root methods
     */
	public boolean addRootRecord(RootDTO rootDTO);
	public boolean updateRootRecord(RootDTO rootDTO);
	public boolean deleteRootRecord(RootDTO rootDTO);
	public List<RootDTO> fetchRootRecords();
	public List<RootDTO> getRootDTOListByVerseId(int verseId);
	public boolean deleteRootVerseRecordsByVerseId(int verseId);
	public boolean insertRootVerseRecord(int verseId, int rootId, String status);
	public boolean doesRootVerseExist(int rootId, int verseId);
	public int getRootIdFromRootText(String rootText);
	public void setBLLRoot(IBLLRoot iroot);
	public void setDALRoot(IDALRoot iDALRoot);
	public void setRootDTO(RootDTO rootDTO);
	public boolean deleteRootVerseByVerseID(int verseId);
	public List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText);

    /*
     * Verse methods
     */
	public boolean addVerseRecord(List<VerseDTO> verseList);
	public void setDALVerse(IDALVerse iDALVerse);
	public void setBLLVerse(IBLLVerse iBLLVerse);
	public void setVerseDTO(VerseDTO verseDTO);
	public List<VerseDTO> getVersesByPoemId(int poemId);
	public boolean deleteVersesByPoemId(int poemId);

    /*
     * Poem methods
     */
	public boolean addPoemRecord(PoemDTO poemDTO);
	public boolean updatePoemRecord(PoemDTO poemDTO);
	public boolean deletePoemRecord(PoemDTO poemDTO);
	public List<PoemDTO> fetchPoemRecords();
	public int getPoemId(String name);
	public void setDALPoem(IDALPoem iDALPoem);
	public void setBLLPoem(IBLLPoem iBLLPoem);
	public void setPoemDTO(PoemDTO poemDTO);

    /*
     * File reading methods
     */
	public ArrayList<PoemDTO> readingFile(String filePath, int book_id);
	public void setDALFileReader(IDALFileReader iDALFileReader);
	public void setBLLFileReader(IBLLFileReader iBLLFileReader);

    /*
     * Token methods
     */
	public boolean insertToken(TokenDTO tokenDTO);
	public int getTokenIdFromTokenText(String word);
	public boolean deleteToken(int tokenId);
	public boolean updateToken(TokenDTO updatedToken);
	public List<TokenDTO> getTokenDTOList();
	public boolean doesTokenVerseExist(int token_id, int verse_id);
	public boolean insertTokenIntoVerse(int token_id, int verse_id);
	public List<TokenDTO> getTokenDTOListByVerseId(int verseId);
	public void setBLLToken(IBLLToken iBLLToken);
	public void setDALToken(IDALToken iDALToken);
	public void setTokenDTO(TokenDTO tokkenDTO);
}
