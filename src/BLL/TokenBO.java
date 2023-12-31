/*
 * Business Object for Token implementing IBLLToken
 */
package BLL;

import java.util.List;
import DAL.IDALFacade;
import TO.TokenDTO;
import Utility.CustomException;

public class TokenBO implements IBLLToken{
    
    IDALFacade iDALFacade;

    // Insert a token
    @Override
    public boolean insertToken(TokenDTO tokenDTO) {
    	
    	try {
		    if (this.iDALFacade.insertToken(tokenDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For insert Token");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
  
    }

    // Get token ID from token text
    @Override
    public int getTokenIdFromTokenText(String word) {
    	
    	try {
    		return this.iDALFacade.getTokenIdFromTokenText(word);
		}catch(Exception e) {
			e.printStackTrace();
		}
    	
    	return -1;
    	
        
    }

    // Delete a token
    @Override
    public boolean deleteToken(int tokenId) {
    	
    	try {
		    if (this.iDALFacade.deleteToken(tokenId)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For delete Token");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    }

    // Update a token
    @Override
    public boolean updateToken(TokenDTO updatedToken) {
    	

    	try {
		    if (this.iDALFacade.updateToken(updatedToken)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Update Token");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		}

    }

    // Get token DTO list
    @Override
    public List<TokenDTO> getTokenDTOList() {
    	
    	try {
    		return this.iDALFacade.getTokenDTOList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
    	
        
    }

    // Attaches the Data Access Layer facade
    @Override
    public void attachFacade(IDALFacade iDALFacade) {
        this.iDALFacade = iDALFacade;
        
    }

    // Insert token into verse
    @Override
    public boolean insertTokenIntoVerse(int token_id, int verse_id) {
    	
    	try {
		    if (this.iDALFacade.insertTokenIntoVerse(token_id, verse_id)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For insert Token Into Verse");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    }

    // Check if token-verse exists
    @Override
    public boolean doesTokenVerseExist(int token_id, int verse_id) {
    	
    	try {
		    if (this.iDALFacade.doesTokenVerseExist(token_id, verse_id)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For does Token Verse Exist");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 

    }

    // Get token DTO list by verse ID
    @Override
    public List<TokenDTO> getTokenDTOListByVerseId(int verseId) {
    	
    	try {
    		return this.iDALFacade.getTokenDTOListByVerseId(verseId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
    	
        
    }

}
