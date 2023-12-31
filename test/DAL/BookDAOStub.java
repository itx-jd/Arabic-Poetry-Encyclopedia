package DAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TO.BookDTO;

public class BookDAOStub implements IDALBook {

	private Map<Integer, BookDTO> database;

	public BookDAOStub() {

		database = new HashMap<>();
	}

	@Override
	public boolean addBookRecord(BookDTO bookDTO) {

		if (bookDTO == null) {
			return false;

		}

		if (isComplete(bookDTO)) {
			database.put(database.size() + 1, bookDTO);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean updateBookRecord(BookDTO bookDTO) {

		// For null
		if (bookDTO == null)
			return false;

		// For Record Not Found
		if (getKeyBySerial(bookDTO) == -1)
			return false;
		
		// Update if everything perfect

		if (isComplete(bookDTO)) {
			database.put(getKeyBySerial(bookDTO), bookDTO);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean deleteBookRecord(BookDTO bookDTO) {

		// For null
		if (bookDTO == null)
			return false;

		// For Record Not Found
		if (getKeyBySerial(bookDTO) == -1)
			return false;

		// delete if everything perfect

		if (isComplete(bookDTO)) {
			database.remove(getKeyBySerial(bookDTO));
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<BookDTO> fetchBookRecords() {
	    List<BookDTO> bookList = new ArrayList<>(database.values());
	    return bookList;
	}

	// Helping Methods
	
	boolean isComplete(BookDTO bookDTO) {

		if (!bookDTO.getBookTitle().isEmpty() && !bookDTO.getBookAuthor().isEmpty() 
				&& bookDTO.getSerialNo() != -1 && bookDTO.getAuthorDeathYear() != -1) {
			return true;
		} else {
			return false;
		}

	}

	int getKeyBySerial(BookDTO bookDTO) {

		for (Map.Entry<Integer, BookDTO> entry : database.entrySet()) {
			
			if (entry.getValue().getSerialNo() == bookDTO.getSerialNo()) {
				return entry.getKey();
			}
		}

		// If the serialNo is not found in the database, return a default value (you can
		// choose -1 or any other meaningful default).
		return -1;
	}
}
