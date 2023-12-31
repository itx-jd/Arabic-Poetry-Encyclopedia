package BLL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAL.DALFacade;
import DAL.IDALFacade;
import DAL.IDALVerse;
import DAL.VerseDAOStub;
import TO.VerseDTO;

class VerseBOTest {

    IBLLFacade bllFacade;

    @BeforeEach
    void setUp() {
        // Create an instance of the stub DAO for testing
        IDALVerse iDALVerse = new VerseDAOStub();
        IBLLVerse iBLLVerse = new VerseBO();

        IDALFacade iDALFacade = new DALFacade();
        bllFacade = new BLLFacade();

        iDALFacade.setDALVerse(iDALVerse);
        iDALFacade.setBLLVerse(iBLLVerse);

        bllFacade.attachFacade(iDALFacade);
    }

    @Test
    void testAddVerseRecord() {
        assertFalse(bllFacade.addVerseRecord(null));
        assertFalse(bllFacade.addVerseRecord(new ArrayList<VerseDTO>()));
        

        List<VerseDTO> verseList = Arrays.asList(
                new VerseDTO("Verse1", 1),
                new VerseDTO("Verse2", 1),
                new VerseDTO("Verse3", 2)
        );

        assertTrue(bllFacade.addVerseRecord(verseList));
    }

    @Test
    void testGetVersesByPoemId() {
        // Assuming there are verses in the database with poemId 1
        List<VerseDTO> result = bllFacade.getVersesByPoemId(1);
        assertTrue(result.isEmpty(), "Expected an empty list for an empty database");

        // Adding verses to the database
        List<VerseDTO> verseList = Arrays.asList(
                new VerseDTO("Verse1", 1),
                new VerseDTO("Verse2", 1),
                new VerseDTO("Verse3", 2)
        );
        bllFacade.addVerseRecord(verseList);

        result = bllFacade.getVersesByPoemId(1);
        assertEquals(2, result.size(), "Expected two verses in the list for poemId 1");
    }

    @Test
    void testDeleteVersesByPoemId() {
        assertFalse(bllFacade.deleteVersesByPoemId(1)); // Deleting from an empty database

        // Adding verses to the database
        List<VerseDTO> verseList = Arrays.asList(
                new VerseDTO("Verse1", 1),
                new VerseDTO("Verse2", 1),
                new VerseDTO("Verse3", 2)
        );
        bllFacade.addVerseRecord(verseList);

        assertTrue(bllFacade.deleteVersesByPoemId(1));
        assertFalse(bllFacade.deleteVersesByPoemId(999)); // Deleting with a non-existing poemId
    }
}
