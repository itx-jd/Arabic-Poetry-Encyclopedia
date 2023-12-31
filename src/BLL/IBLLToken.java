package BLL;

import java.util.List;
import DAL.IDALFacade;
import TO.TokenDTO;

// Interface for Token in Business Logic Layer
public interface IBLLToken {
    
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
    
    // Attaches the Data Access Layer facade
    void attachFacade(IDALFacade iDALFacade);
}
