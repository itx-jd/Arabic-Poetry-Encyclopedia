package BLL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAL.DALFacade;
import DAL.IDALFacade;
import DAL.IDALFileReader;
import DAL.TextFileReaderDAOStub;
import TO.PoemDTO;

class TextFileReaderBOTest {

    IBLLFacade bllFacade;

    @BeforeEach
    void setUp() {
        // Create an instance of the stub DAO for testing
        IDALFileReader iDALFileReader = new TextFileReaderDAOStub();
        IBLLFileReader iBLLFileReader = new TextFileReaderBO();

        IDALFacade iDALFacade = new DALFacade();
        bllFacade = new BLLFacade();

        iDALFacade.setDALFileReader(iDALFileReader);
        iDALFacade.setBLLFileReader(iBLLFileReader);

        bllFacade.attachFacade(iDALFacade);
    }

    @Test
    void testReadingFile() {
    	
        String filePath = "sample.txt";
        int bookId = 1;

        // Test with valid filePath and book_id
        ArrayList<PoemDTO> result = bllFacade.readingFile(filePath, bookId);
        assertNotNull(result, "Expected a non-null result for valid parameters");
        assertEquals(0, result.size(), "Expected an empty list for an empty file");

        // Test with invalid filePath
        result = bllFacade.readingFile("", bookId);
        assertNull(result, "Expected a null result for an empty filePath");

        // Test with invalid book_id
        result = bllFacade.readingFile(filePath, -1);
        assertNull(result, "Expected a null result for an invalid book_id");
    }
}
