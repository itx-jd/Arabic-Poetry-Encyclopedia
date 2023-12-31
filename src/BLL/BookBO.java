package BLL;

import java.util.List;

import DAL.IDALFacade;
import TO.BookDTO;
import Utility.CustomException;

/**
 * This class represents Business Object for Books
 */

public class BookBO implements IBLLBook {

	IDALFacade iDALFacade;

	// Attaches the Data Access Layer facade
	@Override
	public void attachFacade(IDALFacade iDALFacade) {
		this.iDALFacade = iDALFacade;
	}

	// Adds a book record
	@Override
	public boolean addBookRecord(BookDTO bookDTO) {
		try {
		    if (this.iDALFacade.addBookRecord(bookDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Adding Book Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
	}

	// Updates a book record
	@Override
	public boolean updateBookRecord(BookDTO bookDTO) {
		
		try {
		    if (this.iDALFacade.updateBookRecord(bookDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Adding Book Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
	}

	// Deletes a book record
	@Override
	public boolean deleteBookRecord(BookDTO bookDTO) {
		
		try {
		    if (this.iDALFacade.deleteBookRecord(bookDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Adding Book Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
		
	}
	
	// Fetches all book records
	@Override
	public List<BookDTO> fetchBookRecords() {
		try {
			return this.iDALFacade.fetchBookRecords();
		}catch(Exception e) {
			e.printStackTrace();;	
		}
		return null;
		
	}
}
