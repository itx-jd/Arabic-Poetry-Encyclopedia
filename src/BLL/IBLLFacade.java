package BLL;

import java.util.ArrayList;
import java.util.List;

import DAL.IDALFacade;
import TO.BookDTO;
import TO.PoemDTO;
import TO.RootDTO;
import TO.RootInfoDTO;
import TO.TokenDTO;
import TO.VerseDTO;

/**
 * This class represents Interface for Book Business Logic Layer
 */

public interface IBLLFacade extends IBLLBook, IBLLPoem, IBLLRoot, IBLLVerse, IBLLFileReader, IBLLToken {

    // Attaches the Data Access Layer facade
    public void attachFacade(IDALFacade iDALFacade);

    // FOR BOOK

    // Set the Book Business Logic Layer
    public void setBLLBook(IBLLBook iBLLbook);

    // Add a book record
    public boolean addBookRecord(BookDTO bookDTO);

    // Update a book record
    public boolean updateBookRecord(BookDTO bookDTO);

    // Delete a book record
    public boolean deleteBookRecord(BookDTO bookDTO);

    // Fetch all book records
    public List<BookDTO> fetchBookRecords();

    // FOR ROOT

    // Set the Root Business Logic Layer
    public void setBLLRoot(IBLLRoot iBLLroot);

    // Add a root record
    public boolean addRootRecord(RootDTO rootDTO);

    // Update a root record
    public boolean updateRootRecord(RootDTO rootDTO);

    // Delete a root record
    public boolean deleteRootRecord(RootDTO rootDTO);

    // Fetch all root records
    public List<RootDTO> fetchRootRecords();

    // Get root DTO list by verse ID
    public List<RootDTO> getRootDTOListByVerseId(int verseId);

    // Delete root-verse records by verse ID
    public boolean deleteRootVerseRecordsByVerseId(int verseId);

    // Insert root-verse record
    public boolean insertRootVerseRecord(int verseId, int rootId, String status);

    // Delete root-verse by verse ID
    public boolean deleteRootVerseByVerseID(int verseId);

    // Get root info DTO list by root text
    public List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText);

    // FOR VERSE

    // Set the Verse Business Logic Layer
    public void setBLLVerse(IBLLVerse iBLLVerse);

    // Add a verse record
    public boolean addVerseRecord(List<VerseDTO> verseList);

    // Get verses by poem ID
    public List<VerseDTO> getVersesByPoemId(int poemId);

    // Delete verses by poem ID
    public boolean deleteVersesByPoemId(int poemId);

    // FOR POEM

    // Set the Poem Business Logic Layer
    public void setBLLPoem(IBLLPoem iBLLPoem);

    // Add a poem record
    public boolean addPoemRecord(PoemDTO poemDTO);

    // Update a poem record
    public boolean updatePoemRecord(PoemDTO poemDTO);

    // Delete a poem record
    public boolean deletePoemRecord(PoemDTO poemDTO);

    // Fetch all poem records
    public List<PoemDTO> fetchPoemRecords();

    // Get poem ID by name
    public int getPoemId(String name);

    // FOR FILE READING

    // Set the File Reader Business Logic Layer
    public void setBLLFileReader(IBLLFileReader iBLLFileReader);

    // Read a file and retrieve poems
    public ArrayList<PoemDTO> readingFile(String filePath, int book_id);

    // FOR TOKEN

    // Set the Token Business Logic Layer
    public void setBLLToken(IBLLToken iBLLToken);

    // Insert a token
    public boolean insertToken(TokenDTO tokenDTO);

    // Get token ID from token text
    public int getTokenIdFromTokenText(String word);

    // Delete a token
    public boolean deleteToken(int tokenId);

    // Update a token
    public boolean updateToken(TokenDTO updatedToken);

    // Get token DTO list
    public List<TokenDTO> getTokenDTOList();

    // Check if token-verse exists
    public boolean doesTokenVerseExist(int token_id, int verse_id);

    // Insert token into verse
    public boolean insertTokenIntoVerse(int token_id, int verse_id);

    // Get token DTO list by verse ID
    public List<TokenDTO> getTokenDTOListByVerseId(int verseId);

}
