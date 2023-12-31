/*
 * Data Access Layer Interface for Root Operations
 */
package DAL;

import java.util.List;

import TO.RootDTO;
import TO.RootInfoDTO;

public interface IDALRoot {
	
    /*
     * Adds a new RootDTO record to the data store.
     * @param rootDTO The RootDTO object to be added
     * @return True if addition is successful, false otherwise
     */
	public boolean addRootRecord(RootDTO rootDTO);

    /*
     * Updates an existing RootDTO record in the data store.
     * @param rootDTO The RootDTO object to be updated
     * @return True if update is successful, false otherwise
     */
	public boolean updateRootRecord(RootDTO rootDTO);

    /*
     * Deletes a RootDTO record from the data store.
     * @param rootDTO The RootDTO object to be deleted
     * @return True if deletion is successful, false otherwise
     */
	public boolean deleteRootRecord(RootDTO rootDTO);

    /*
     * Retrieves all available RootDTO records from the data store.
     * @return A list of RootDTO objects
     */
	public List<RootDTO> fetchRootRecords();

    /*
     * Retrieves a list of RootDTO records based on a verse ID.
     * @param verseId The ID of the verse to search for
     * @return A list of RootDTO objects associated with the verse ID
     */
	public List<RootDTO> getRootDTOListByVerseId(int verseId);

    /*
     * Deletes RootDTO records associated with a verse ID.
     * @param verseId The ID of the verse whose records need deletion
     * @return True if deletion is successful, false otherwise
     */
	public boolean deleteRootVerseRecordsByVerseId(int verseId);

    /*
     * Inserts a RootDTO record associated with a verse ID and root ID.
     * @param verseId The ID of the verse to associate the record with
     * @param rootId The ID of the root to insert
     * @param status The status of the insertion
     * @return True if insertion is successful, false otherwise
     */
	public boolean insertRootVerseRecord(int verseId, int rootId, String status);

    /*
     * Checks if a RootDTO record associated with a root and verse ID exists.
     * @param rootId The ID of the root
     * @param verseId The ID of the verse
     * @return True if the record exists, false otherwise
     */
	public boolean doesRootVerseExist(int rootId, int verseId);

    /*
     * Retrieves the ID of a root based on its text.
     * @param rootText The text of the root to search for
     * @return The ID of the root if found, otherwise 0
     */
	public int getRootIdFromRootText(String rootText);

    /*
     * Deletes RootDTO records associated with a verse ID.
     * @param verseId The ID of the verse whose records need deletion
     * @return True if deletion is successful, false otherwise
     */
	boolean deleteRootVerseByVerseID(int verseId);

    /*
     * Retrieves a list of RootInfoDTO records based on a root text.
     * @param rootText The text of the root to search for
     * @return A list of RootInfoDTO objects associated with the root text
     */
	public List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText);
}
