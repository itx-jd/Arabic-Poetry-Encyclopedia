/*
 * Data Access Layer Interface for Poem Operations
 */
package DAL;

import java.util.List;

import TO.BookDTO;
import TO.PoemDTO;

public interface IDALPoem {

    /*
     * Adds a new PoemDTO record to the data store.
     * @param poemDTO The PoemDTO object to be added
     * @return True if addition is successful, false otherwise
     */
	public boolean addPoemRecord(PoemDTO poemDTO);

    /*
     * Updates an existing PoemDTO record in the data store.
     * @param poemDTO The PoemDTO object to be updated
     * @return True if update is successful, false otherwise
     */
	 public boolean updatePoemRecord(PoemDTO poemDTO);

    /*
     * Deletes a PoemDTO record from the data store.
     * @param poemDTO The PoemDTO object to be deleted
     * @return True if deletion is successful, false otherwise
     */
	 public boolean deletePoemRecord(PoemDTO poemDTO);

    /*
     * Retrieves all available PoemDTO records from the data store.
     * @return A list of PoemDTO objects
     */
	 public List<PoemDTO> fetchPoemRecords();

    /*
     * Retrieves the ID of a poem based on its name.
     * @param name The name of the poem to search for
     * @return The ID of the poem if found, otherwise 0
     */
	 public int getPoemId(String name);
	
}
