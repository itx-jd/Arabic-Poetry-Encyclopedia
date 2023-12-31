package BLL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAL.DALFacade;
import DAL.IDALFacade;
import DAL.IDALRoot;
import DAL.RootDAOStub;
import TO.RootDTO;
import TO.RootInfoDTO;

class RootBOTest {

    IBLLFacade bllFacade;

    @BeforeEach
    void setUp() {
        IDALRoot iDALRoot = new RootDAOStub();
        IBLLRoot iBLLRoot = new RootBO();

        IDALFacade iDALFacade = new DALFacade();
        bllFacade = new BLLFacade();

        iDALFacade.setDALRoot(iDALRoot);
        iDALFacade.setBLLRoot(iBLLRoot);

        bllFacade.attachFacade(iDALFacade);
    }

    @Test
    void testAddRootRecord() {
        assertFalse(bllFacade.addRootRecord(null)); // Checking by NULL
        // Assuming there's a root in the database with rootId 1
        assertTrue(bllFacade.addRootRecord(new RootDTO("Root2")));
    }

    @Test
    void testUpdateRootRecord() {
        assertFalse(bllFacade.updateRootRecord(null)); // Checking by NULL
        assertFalse(bllFacade.updateRootRecord(new RootDTO("Root1"))); // Checking by missing one parameter
        assertFalse(bllFacade.updateRootRecord(new RootDTO(999, "RootNotInDatabase"))); // Checking by if id not present in db

        // Assuming there's a root in the database with rootId 1
        bllFacade.addRootRecord(new RootDTO(1, "Root1"));
        assertTrue(bllFacade.updateRootRecord(new RootDTO(1, "UpdatedRoot1")));
    }

    @Test
    void testDeleteRootRecord() {
        assertFalse(bllFacade.deleteRootRecord(null)); // Checking by NULL
        assertFalse(bllFacade.deleteRootRecord(new RootDTO("Root1"))); // Checking by missing one parameter
        assertFalse(bllFacade.deleteRootRecord(new RootDTO(999, "RootNotInDatabase"))); // Checking by if id not present in db

        // Assuming there's a root in the database with rootId 1
        bllFacade.addRootRecord(new RootDTO(1, "Root1"));
        assertTrue(bllFacade.deleteRootRecord(new RootDTO(1, "Root1")));
    }

    @Test
    void testFetchRootRecords() {
        List<RootDTO> result = bllFacade.fetchRootRecords();
        assertTrue(result.isEmpty(), "Expected an empty list for an empty database");

        // Assuming there's a root in the database with rootId 1
        bllFacade.addRootRecord(new RootDTO(1, "Root1"));
        result = bllFacade.fetchRootRecords();
        assertEquals(1, result.size(), "Expected one root in the list");
    }

    @Test
    void testGetRootDTOListByVerseId() {
        List<RootDTO> result = bllFacade.getRootDTOListByVerseId(1);
        assertTrue(result.isEmpty(), "Expected an empty list for an empty database");

        // Assuming there's a root in the database with rootId 1
        bllFacade.addRootRecord(new RootDTO(1, "Root1"));
        bllFacade.insertRootVerseRecord(1, 1, "Status");
        result = bllFacade.getRootDTOListByVerseId(1);
        assertEquals(1, result.size(), "Expected one root in the list");
    }

    @Test
    void testDeleteRootVerseRecordsByVerseId() {
    	
        assertFalse(bllFacade.deleteRootVerseRecordsByVerseId(-1));
        assertFalse(bllFacade.deleteRootVerseRecordsByVerseId(-2));
        
        // Assuming there's a root in the database with rootId 1
        bllFacade.addRootRecord(new RootDTO(1, "Root1"));
        bllFacade.insertRootVerseRecord(1, 1, "Status");

    }

    @Test
    void testInsertRootVerseRecord() {
    	
    	assertFalse(bllFacade.insertRootVerseRecord(-1, 1, "Status")); 
        assertTrue(bllFacade.insertRootVerseRecord(1, 1, "Status"));
    }

    @Test
    void testDoesRootVerseExist() {
        assertFalse(bllFacade.doesRootVerseExist(1, 1)); // Checking by rootId and verseId not present in db

        // Assuming there's a root in the database with rootId 1
        bllFacade.addRootRecord(new RootDTO(1, "Root1"));
        bllFacade.insertRootVerseRecord(1, 1, "Status");
        assertTrue(bllFacade.doesRootVerseExist(1, 1));
    }

    @Test
    void testGetRootIdFromRootText() {
        assertEquals(-1, bllFacade.getRootIdFromRootText("Root1")); // Checking by rootText not present in db

        // Assuming there's a root in the database with rootId 1
        bllFacade.addRootRecord(new RootDTO(1, "Root1"));
        assertEquals(1, bllFacade.getRootIdFromRootText("Root1"));
    }

    @Test
    void testDeleteRootVerseByVerseID() {
    	
    	assertFalse(bllFacade.deleteRootVerseByVerseID(-1));
        assertFalse(bllFacade.deleteRootVerseByVerseID(1));
        
        // Assuming there's a root in the database with rootId 1
        bllFacade.addRootRecord(new RootDTO(1, "Root1"));
        bllFacade.insertRootVerseRecord(1, 1, "Status");
        
        assertTrue(bllFacade.deleteRootVerseByVerseID(1));
    }
}
