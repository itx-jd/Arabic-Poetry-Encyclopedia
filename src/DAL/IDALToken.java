/*
 * Data Access Layer Interface for Token Operations
 */
package DAL;

import java.util.List;

import TO.TokenDTO;

public interface IDALToken {
	
    /*
     * Inserts a TokenDTO record into the data store.
     * @param tokenDTO The TokenDTO object to be inserted
     * @return True if insertion is successful, false otherwise
     */
	public boolean insertToken(TokenDTO tokenDTO);
	
    /*
     * Retrieves the ID of a token based on its text.
     * @param word The text of the token to search for
     * @return The ID of the token if found, otherwise 0
     */
	public int getTokenIdFromTokenText(String word);
	
    /*
     * Deletes a TokenDTO record from the data store.
     * @param tokenId The ID of the token to be deleted
     * @return True if deletion is successful, false otherwise
     */
	public boolean deleteToken(int tokenId);
	
    /*
     * Updates an existing TokenDTO record in the data store.
     * @param updatedToken The updated TokenDTO object
     * @return True if update is successful, false otherwise
     */
	public boolean updateToken(TokenDTO updatedToken);
	
    /*
     * Retrieves all available TokenDTO records from the data store.
     * @return A list of TokenDTO objects
     */
	public List<TokenDTO> getTokenDTOList();
	
    /*
     * Checks if a TokenDTO record associated with a token ID and verse ID exists.
     * @param token_id The ID of the token
     * @param verse_id The ID of the verse
     * @return True if the record exists, false otherwise
     */
	public boolean doesTokenVerseExist(int token_id, int verse_id);
	
    /*
     * Inserts a TokenDTO record associated with a token ID and verse ID.
     * @param token_id The ID of the token
     * @param verse_id The ID of the verse
     * @return True if insertion is successful, false otherwise
     */
	public boolean insertTokenIntoVerse(int token_id, int verse_id);
	
    /*
     * Retrieves a list of TokenDTO records based on a verse ID.
     * @param verseId The ID of the verse
     * @return A list of TokenDTO objects associated with the verse ID
     */
	public List<TokenDTO> getTokenDTOListByVerseId(int verseId);
}
