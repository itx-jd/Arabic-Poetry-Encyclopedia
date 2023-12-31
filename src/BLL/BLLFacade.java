package BLL;

import java.util.ArrayList;
import java.util.List;

import DAL.IDALFacade;
import DAL.IDALRoot;
import TO.BookDTO;
import TO.PoemDTO;
import TO.RootDTO;
import TO.RootInfoDTO;
import TO.TokenDTO;
import TO.VerseDTO;

/**
 * This class represents Business Layer Facade.
 */

public class BLLFacade implements IBLLFacade {

    IDALFacade iDALFacade;

    IBLLBook iBLLbook;
    IBLLVerse iBLLVerse;
    IBLLPoem iBLLPoem;
    IBLLRoot iBLLroot;
    IBLLToken iBLLToken;
    IBLLFileReader iBLLFileReader;

    // Universal

    @Override
    public void attachFacade(IDALFacade iDALFacade) {
        this.iDALFacade = iDALFacade;
    }

    // For Book

    // Connecting Methods

    // Set the Book Business Logic Layer
    @Override
    public void setBLLBook(IBLLBook iBLLbook) {
        this.iBLLbook = iBLLbook;
    }

    // Operational Methods

    // Add a book record
    @Override
    public boolean addBookRecord(BookDTO bookDTO) {
        return iDALFacade.addBookRecord(bookDTO);
    }

    // Update a book record
    @Override
    public boolean updateBookRecord(BookDTO bookDTO) {
        return iDALFacade.updateBookRecord(bookDTO);
    }

    // Delete a book record
    @Override
    public boolean deleteBookRecord(BookDTO bookDTO) {
        return iDALFacade.deleteBookRecord(bookDTO);
    }

    // Fetch all book records
    @Override
    public List<BookDTO> fetchBookRecords() {
        return iDALFacade.fetchBookRecords();
    }

    // For Verse

    // Connecting Methods

    // Set the Verse Business Logic Layer
    @Override
    public void setBLLVerse(IBLLVerse iBLLVerse) {
        this.iBLLVerse = iBLLVerse;
    }

    // Operational Methods

    // Add a verse record
    @Override
    public boolean addVerseRecord(List<VerseDTO> verseList) {
        return iDALFacade.addVerseRecord(verseList);
    }

    // Get verses by poem ID
    public List<VerseDTO> getVersesByPoemId(int poemId) {
        return iDALFacade.getVersesByPoemId(poemId);
    }

    // Delete verses by poem ID
    @Override
    public boolean deleteVersesByPoemId(int poemId) {
        return iDALFacade.deleteVersesByPoemId(poemId);
    }

    // For File Reading

    // Connecting Methods

    // Set the File Reader Business Logic Layer
    @Override
    public void setBLLFileReader(IBLLFileReader iBLLFileReader) {
        this.iBLLFileReader = iBLLFileReader;
    }

    // Operational Methods

    // Read a file and retrieve poems
    @Override
    public ArrayList<PoemDTO> readingFile(String filePath, int book_id) {
        return iDALFacade.readingFile(filePath, book_id);
    }

    // For Poem

    // Connecting Methods

    // Set the Poem Business Logic Layer
    @Override
    public void setBLLPoem(IBLLPoem iBLLPoem) {
        this.iBLLPoem = iBLLPoem;
    }

    // Operational Methods

    // Add a poem record
    @Override
    public boolean addPoemRecord(PoemDTO poemDTO) {
        return iDALFacade.addPoemRecord(poemDTO);
    }

    // Update a poem record
    @Override
    public boolean updatePoemRecord(PoemDTO poemDTO) {
        return iDALFacade.updatePoemRecord(poemDTO);
    }

    // Delete a poem record
    @Override
    public boolean deletePoemRecord(PoemDTO poemDTO) {
        return iDALFacade.deletePoemRecord(poemDTO);
    }

    // Fetch all poem records
    @Override
    public List<PoemDTO> fetchPoemRecords() {
        return iDALFacade.fetchPoemRecords();
    }

    // Get poem ID by name
    @Override
    public int getPoemId(String name) {
        return iDALFacade.getPoemId(name);
    }

    // Roots

    // Connecting Methods of Root

    // Set the Root Business Logic Layer
    @Override
    public void setBLLRoot(IBLLRoot iBLLroot) {
        this.iBLLroot = iBLLroot;
    }

    // Operational Methods of Root

    // Add a root record
    @Override
    public boolean addRootRecord(RootDTO rootDTO) {
        return iDALFacade.addRootRecord(rootDTO);
    }

    // Update a root record
    @Override
    public boolean updateRootRecord(RootDTO rootDTO) {
        return iDALFacade.updateRootRecord(rootDTO);
    }

    // Delete a root record
    @Override
    public boolean deleteRootRecord(RootDTO rootDTO) {
        return iDALFacade.deleteRootRecord(rootDTO);
    }

    // Fetch all root records
    @Override
    public List<RootDTO> fetchRootRecords() {
        return iDALFacade.fetchRootRecords();
    }

    // Get root DTO list by verse ID
    @Override
    public List<RootDTO> getRootDTOListByVerseId(int verseId) {
        return iDALFacade.getRootDTOListByVerseId(verseId);
    }

    // Delete root-verse records by verse ID
    @Override
    public boolean deleteRootVerseRecordsByVerseId(int verseId) {
        return iDALFacade.deleteRootVerseRecordsByVerseId(verseId);
    }

    // Insert root-verse record
    @Override
    public boolean insertRootVerseRecord(int verseId, int rootId, String status) {
        return iDALFacade.insertRootVerseRecord(verseId, rootId, status);
    }

    // Get root info DTO list by root text
    @Override
    public List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText) {
        return iDALFacade.getRootInfoDTOListByRootText(rootText);
    }

    // Token

    // Connecting

    // Set the Token Business Logic Layer
    @Override
    public void setBLLToken(IBLLToken iBLLToken) {
        this.iBLLToken = iBLLToken;
    }

    // Operational

    // Insert a token
    @Override
    public boolean insertToken(TokenDTO tokenDTO) {
        return iDALFacade.insertToken(tokenDTO);
    }

    // Get token ID from token text
    @Override
    public int getTokenIdFromTokenText(String word) {
        return iDALFacade.getTokenIdFromTokenText(word);
    }

    // Delete a token
    @Override
    public boolean deleteToken(int tokenId) {
        return iDALFacade.deleteToken(tokenId);
    }

    // Update a token
    @Override
    public boolean updateToken(TokenDTO updatedToken) {
        return iDALFacade.updateToken(updatedToken);
    }

    // Get token DTO list
    @Override
    public List<TokenDTO> getTokenDTOList() {
        return iDALFacade.getTokenDTOList();
    }

    // Insert token into verse
    @Override
    public boolean insertTokenIntoVerse(int token_id, int verse_id) {
        return iDALFacade.insertTokenIntoVerse(token_id, verse_id);
    }

    // Check if token-verse exists
    @Override
    public boolean doesTokenVerseExist(int token_id, int verse_id) {
        return iDALFacade.doesTokenVerseExist(token_id, verse_id);
    }

    // Get token DTO list by verse ID
    @Override
    public List<TokenDTO> getTokenDTOListByVerseId(int verseId) {
        return iDALFacade.getTokenDTOListByVerseId(verseId);
    }

    // Check if root-verse exists
    @Override
    public boolean doesRootVerseExist(int rootId, int verseId) {
        return iDALFacade.doesRootVerseExist(rootId, verseId);
    }

    // Get root ID from root text
    @Override
    public int getRootIdFromRootText(String rootText) {
        return iDALFacade.getRootIdFromRootText(rootText);
    }

    // Delete root-verse by verse ID
    @Override
    public boolean deleteRootVerseByVerseID(int verseId) {
        return iDALFacade.deleteRootVerseByVerseID(verseId);
    }
}
