package BLL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAL.BookDAO;
import DAL.BookDAOStub;
import DAL.DALFacade;
import DAL.IDALBook;
import DAL.IDALFacade;
import TO.BookDTO;

class BookBOTest {

	IBLLFacade bllFacade;
     
  

    @BeforeEach
    void setUp() {
        // Create an instance of the stub DAO for testing
    	IDALBook iDALBook = new BookDAOStub();
		IBLLBook ibook = new BookBO();
		
		IDALFacade iDALFacade = new DALFacade();
		bllFacade = new BLLFacade();
		
		iDALFacade.setDALBooK(iDALBook);
		iDALFacade.setBLLBook(ibook);
		
		bllFacade.attachFacade(iDALFacade);
    }

    @Test
    void testAddBookRecord() {
//    	assertThrowsExactly(null, bllFacade.addBookRecord(null));
//        assertFalse();
        assertFalse(bllFacade.addBookRecord(new BookDTO("Book1", "Author1", 1999)));
        assertTrue(bllFacade.addBookRecord(new BookDTO(123, "Book1", "Author1", 1999)));
    }

    @Test
    void testUpdateBookRecord() {

        assertFalse(bllFacade.updateBookRecord(null));
        assertFalse(bllFacade.updateBookRecord(new BookDTO("Book1", "Author1", 1999)));
        assertFalse(bllFacade.updateBookRecord(new BookDTO(999, "BookNotInDatabase", "Author", 2000)));
        
        // Assuming there's a book in the database with serialNo 123
        bllFacade.addBookRecord(new BookDTO(123, "Book1", "Author1", 1999));
        assertTrue(bllFacade.updateBookRecord(new BookDTO(123, "UpdatedBookTitle", "UpdatedAuthor", 2000)));
    }

    @Test
    void testDeleteBookRecord() {

        assertFalse(bllFacade.deleteBookRecord(null));
        assertFalse(bllFacade.deleteBookRecord(new BookDTO("Book1", "Author1", 1999)));
        assertFalse(bllFacade.deleteBookRecord(new BookDTO(999, "BookNotInDatabase", "Author", 2000)));
        
        // Assuming there's a book in the database with serialNo 123
        bllFacade.addBookRecord(new BookDTO(123, "Book1", "Author1", 1999));
        assertTrue(bllFacade.deleteBookRecord(new BookDTO(123, "Book1", "Author1", 1999)));
    }

    @Test
    void testFetchBookRecords() {
    	List<BookDTO> result = bllFacade.fetchBookRecords();
    	assertTrue(result.isEmpty(), "Expected an empty list for an empty database");
    	
    	bllFacade.addBookRecord(new BookDTO(123, "Book1", "Author1", 1999));
    	result = bllFacade.fetchBookRecords();
    	assertEquals(1, result.size(), "Expected one books in the list");
    	
    }
}
