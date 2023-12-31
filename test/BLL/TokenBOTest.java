package BLL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAL.DALFacade;
import DAL.IDALFacade;
import DAL.IDALToken;
import DAL.TokenDAOStub;
import TO.TokenDTO;

class TokenBOTest {

	IBLLFacade bllFacade;

	@BeforeEach
	void setUp() {
		// Create an instance of the stub DAO for testing
		IDALToken iDALToken = new TokenDAOStub();
		IBLLToken iBLLToken = new TokenBO();

		IDALFacade iDALFacade = new DALFacade();
		bllFacade = new BLLFacade();

		iDALFacade.setDALToken(iDALToken);
		iDALFacade.setBLLToken(iBLLToken);

		bllFacade.attachFacade(iDALFacade);
	}

	@Test
	public void testInsertToken() {

		assertFalse(bllFacade.insertToken(null));

		TokenDTO validToken = new TokenDTO("TestRootText", "TestTokenTag");
		assertTrue(bllFacade.insertToken(validToken));

		TokenDTO invalidToken = new TokenDTO("TestRootText");
		assertFalse(bllFacade.insertToken(invalidToken));
	}

	@Test
	public void testGetTokenIdFromTokenText() {
		// Assuming there's a token in the database with tokenText "TestToken"
		bllFacade.insertToken(new TokenDTO("TestToken", "TestTokenTag"));
		assertEquals(1, bllFacade.getTokenIdFromTokenText("TestToken"));
		assertEquals(-1, bllFacade.getTokenIdFromTokenText("TestToken1"));
	}

	@Test
	public void testDeleteToken() {

		bllFacade.insertToken(new TokenDTO("Token1", "Tag1"));
		// Assuming there's a token in the database with tokenId 1
		assertTrue(bllFacade.deleteToken(1));

		// Test case: Deleting a non-existent token
		assertFalse(bllFacade.deleteToken(999));
	}

	@Test
	public void testUpdateToken() {

		assertFalse(bllFacade.updateToken(null));
		
		assertFalse(bllFacade.updateToken(new TokenDTO(1, "Token")));
		assertFalse(bllFacade.updateToken(new TokenDTO(99, "TestToken", "TestTag")));
		
		bllFacade.insertToken(new TokenDTO("Token1", "Tag1"));
		assertTrue(bllFacade.updateToken(new TokenDTO(1, "TestToken", "TestTag")));

		
	}

	@Test
	public void testGetTokenDTOList() {
		
		List<TokenDTO> tokenDTOList = bllFacade.getTokenDTOList();
		
		assertTrue(tokenDTOList.isEmpty(), "Expected an empty list for an empty database");
		
		bllFacade.insertToken(new TokenDTO("Token1", "Tag1"));
		tokenDTOList = bllFacade.getTokenDTOList();

		assertEquals(1, tokenDTOList.size(), "Expected one Token in the list");

	}

	@Test
	public void testInsertTokenIntoVerse() {
		
		assertFalse(bllFacade.insertTokenIntoVerse(1,-1));
		assertTrue(bllFacade.insertTokenIntoVerse(1, 1));
		
	}

	@Test
	public void testDoesTokenVerseExist() {

		bllFacade.insertTokenIntoVerse(1,1);
		
		assertFalse(bllFacade.doesTokenVerseExist(999, 999));
		assertFalse(bllFacade.doesTokenVerseExist(10, -1));
		assertFalse(bllFacade.doesTokenVerseExist(-1, 1));
		
		assertTrue(bllFacade.doesTokenVerseExist(1, 1));

		
	}

	@Test
	public void testGetTokenDTOListByVerseId() {
		// Assuming there's a verse in the database with verseId 1
		
		List<TokenDTO> tokenDTOList = bllFacade.getTokenDTOListByVerseId(1);
		assertTrue(tokenDTOList.isEmpty(), "Expected an empty list for an empty database");
		
		bllFacade.insertToken(new TokenDTO("TestToken", "TestTokenTag"));
		bllFacade.insertTokenIntoVerse(1,1);
		
		tokenDTOList = bllFacade.getTokenDTOListByVerseId(1);
		
		assertEquals(1, tokenDTOList.size(), "Expected one Token in the list");
		
		

	}
}
