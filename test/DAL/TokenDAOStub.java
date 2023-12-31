package DAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TO.TokenDTO;
import TO.TokenVerseDTO;

public class TokenDAOStub implements IDALToken {

    private final Map<Integer, TokenDTO> tokenDatabase;
    private final Map<Integer, TokenVerseDTO> tokenVerseDatabase;

    public TokenDAOStub() {
        tokenDatabase = new HashMap<>();
        tokenVerseDatabase = new HashMap<>();
    }

    // Token

    @Override
    public boolean insertToken(TokenDTO tokenDTO) {
    	
        if (tokenDTO == null || !isComplete(tokenDTO)) {
            return false;
        }

        // Assuming tokenId is generated sequentially
        tokenDTO.setId(tokenDatabase.size() + 1);

        tokenDatabase.put(tokenDTO.getId(), tokenDTO);
        return true;
    }

    @Override
    public int getTokenIdFromTokenText(String word) {
    	
        for (TokenDTO tokenDTO : tokenDatabase.values()) {
            if (tokenDTO.getWord().equalsIgnoreCase(word)) {
                return tokenDTO.getId();
            }
        }
        return -1; // Return -1 if not found
    }

    @Override
    public boolean deleteToken(int tokenId) {
        if (!tokenDatabase.containsKey(tokenId)) {
            return false;
        }

        tokenDatabase.remove(tokenId);
        return true;
    }

    @Override
    public boolean updateToken(TokenDTO updatedToken) {
        if (updatedToken == null || !tokenDatabase.containsKey(updatedToken.getId()) || !isComplete(updatedToken)) {
            return false;
        }

        // Update if everything is perfect
        tokenDatabase.put(updatedToken.getId(), updatedToken);
        return true;
    }

    @Override
    public List<TokenDTO> getTokenDTOList() {
        return new ArrayList<>(tokenDatabase.values());
    }

    // Token Verse

    @Override
    public boolean doesTokenVerseExist(int token_id, int verse_id) {
    	
    	if(token_id<0 || verse_id < 0) return false;
    	
        // Check if the tokenVerse relationship exists in the database
        for (TokenVerseDTO tokenVerseDTO : tokenVerseDatabase.values()) {
            if (tokenVerseDTO.getRoot_id() == token_id && tokenVerseDTO.getVerse_id() == verse_id) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean insertTokenIntoVerse(int token_id, int verse_id) {
    	
    	if(token_id<0 || verse_id < 0) return false;
    	
        // Create a new TokenVerseDTO
        TokenVerseDTO tokenVerseDTO = new TokenVerseDTO(tokenVerseDatabase.size() + 1, token_id, verse_id);

        // Add the new relationship to the database
        tokenVerseDatabase.put(tokenVerseDTO.getId(), tokenVerseDTO);
        return true;
    }

    @Override
    public List<TokenDTO> getTokenDTOListByVerseId(int verseId) {
        List<TokenDTO> tokenDTOList = new ArrayList<>();

        // Retrieve tokens associated with the given verse_id
        for (TokenVerseDTO tokenVerseDTO : tokenVerseDatabase.values()) {
            if (tokenVerseDTO.getVerse_id() == verseId) {
                TokenDTO associatedToken = tokenDatabase.get(tokenVerseDTO.getRoot_id());
                if (associatedToken != null) {
                    tokenDTOList.add(associatedToken);
                }
            }
        }

        return tokenDTOList;
    }

    boolean isComplete(TokenDTO tokenDTO) {
        return !tokenDTO.getWord().isEmpty() && !tokenDTO.getTag().isEmpty();
    }
}
