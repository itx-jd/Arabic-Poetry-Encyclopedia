package BLL;

import java.util.List;

import DAL.IDALFacade;
import TO.BookDTO;

/**
 * This class represents Interface for Book Business Logic Layer
 */

public interface IBLLBook {
	
	// Attaches the Data Access Layer facade
	void attachFacade(IDALFacade iDALFacade);
	
	// Adds a book record
	boolean addBookRecord(BookDTO bookDTO);
	
	// Updates a book record
	boolean updateBookRecord(BookDTO bookDTO);
	
	// Deletes a book record
	boolean deleteBookRecord(BookDTO bookDTO);
	
	// Fetches all book records
	public List<BookDTO> fetchBookRecords();
}
