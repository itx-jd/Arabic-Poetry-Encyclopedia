package BLL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAL.DALFacade;
import DAL.IDALFacade;
import DAL.IDALPoem;
import DAL.PoemDAO;
import DAL.PoemDAOStub;
import TO.PoemDTO;

class PoemBOTest {

	IBLLFacade bllFacade;

	@BeforeEach
	void setUp() {

		IDALPoem iDALPoem = new PoemDAOStub();
		IBLLPoem iBLLPoem = new PoemBO();

		IDALFacade iDALFacade = new DALFacade();
		bllFacade = new BLLFacade();

		iDALFacade.setDALPoem(iDALPoem);
		iDALFacade.setBLLPoem(iBLLPoem);

		bllFacade.attachFacade(iDALFacade);

	}

	@Test
	void testAddPoemRecord() {

		// Checking by NULL
		assertFalse(bllFacade.addPoemRecord(null));
		
		// Checking by missing one parameter

		PoemDTO poemDTO = new PoemDTO();
		poemDTO.setPoemTitle("Poem3");

		assertFalse(bllFacade.addPoemRecord(poemDTO));
		
		// checking true condition if everything work perfect

		// Assuming there's a poem in the database with poemId 1
		assertTrue(bllFacade.addPoemRecord(new PoemDTO("Poem2", 1)));
	}

	@Test
	void testUpdatePoemRecord() {
		assertFalse(bllFacade.updatePoemRecord(null)); // Checking by NULL
		assertFalse(bllFacade.updatePoemRecord(new PoemDTO("Poem1", 1))); // Checking by missing one parameter
		// Checking by if id not present in db
		assertFalse(bllFacade.updatePoemRecord(new PoemDTO(999, "PoemNotInDatabase", 1))); 
		

		// Assuming there's a poem in the database with poemId 1
		bllFacade.addPoemRecord(new PoemDTO(1, "Poem1", 1)); 
		// checking true condition if everything work perfect
		assertTrue(bllFacade.updatePoemRecord(new PoemDTO(1, "UpdatedPoemTitle", 1))); 
	}

	@Test
	void testDeletePoemRecord() {
		assertFalse(bllFacade.deletePoemRecord(null)); // Checking by NULL
		assertFalse(bllFacade.deletePoemRecord(new PoemDTO("Poem1", 1))); // Checking by missing one parameter
		// Checking by if id not present in db
		assertFalse(bllFacade.deletePoemRecord(new PoemDTO(999, "PoemNotInDatabase", 1)));

		// checking true condition if everything work perfect
		bllFacade.addPoemRecord(new PoemDTO(1, "Poem1", 1));
		assertTrue(bllFacade.deletePoemRecord(new PoemDTO(1, "Poem1", 1)));
	}

	@Test
	void testFetchPoemRecords() {
		List<PoemDTO> result = bllFacade.fetchPoemRecords();
		assertTrue(result.isEmpty(), "Expected an empty list for an empty database");

		// Assuming there's a poem in the database with poemId 1
		bllFacade.addPoemRecord(new PoemDTO(1, "Poem1", 1));
		result = bllFacade.fetchPoemRecords();
		assertEquals(1, result.size(), "Expected one poem in the list");
	}

	@Test
	void testGetPoemId() {
		// Assuming there's a poem in the database with poemId 1
		bllFacade.addPoemRecord(new PoemDTO(1, "Poem1", 1));

		int poemId = bllFacade.getPoemId("Poem1");
		assertEquals(1, poemId, "Expected poemId 1 for Poem1");
	}
}
