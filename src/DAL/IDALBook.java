/*
 * Data Access Layer Interface for Book Operations
 */
package DAL;

import java.util.List;
import TO.BookDTO;

public interface IDALBook {
	
    /*
     * Adds a book record to the data storage
     * @param bookDTO The BookDTO object to be added
     * @return boolean Returns true if the addition is successful, otherwise false
     */
	 public boolean addBookRecord(BookDTO bookDTO);
	 
    /*
     * Updates a book record in the data storage
     * @param bookDTO The BookDTO object containing updated information
     * @return boolean Returns true if the update is successful, otherwise false
     */
	 public boolean updateBookRecord(BookDTO bookDTO);
	 
    /*
     * Deletes a book record from the data storage
     * @param bookDTO The BookDTO object to be deleted
     * @return boolean Returns true if the deletion is successful, otherwise false
     */
	 public boolean deleteBookRecord(BookDTO bookDTO);
	 
    /*
     * Fetches all book records from the data storage
     * @return List<BookDTO> A list containing all BookDTO objects retrieved
     */
	 public List<BookDTO> fetchBookRecords();
}
