package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TO.BookDTO;
import Utility.Logger4J;

public class BookDAO implements IDALBook {

	Logger4J logger = Logger4J.getInstance();

	@Override
	public boolean addBookRecord(BookDTO bookDTO) {
		boolean result = false;

		try {
			PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(
					"INSERT INTO book (serial_no, book_title, book_author, author_death_year) VALUES (?, ?, ?, ?)");
			preparedStatement.setInt(1, bookDTO.getSerialNo());
			preparedStatement.setString(2, bookDTO.getBookTitle());
			preparedStatement.setString(3, bookDTO.getBookAuthor());
			preparedStatement.setInt(4, bookDTO.getAuthorDeathYear());

			preparedStatement.executeUpdate();
			result = true;
			logger.info("Book record added successfully: Serial:" + bookDTO.getSerialNo());
			
		} catch (SQLException e) {
			logger.error("Error adding book record: Serial:" + bookDTO.getSerialNo(), e);
			result = false;
		}

		return result;
	}

	public boolean updateBookRecord(BookDTO bookDTO) {

		boolean result = false;
		try {
			// Prepare and execute an SQL UPDATE statement to update the book record in the
			// database
			PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(
					"UPDATE book SET book_title = ?, book_author = ?, author_death_year = ? WHERE serial_no = ?");
			preparedStatement.setString(1, bookDTO.getBookTitle());
			preparedStatement.setString(2, bookDTO.getBookAuthor());
			preparedStatement.setInt(3, bookDTO.getAuthorDeathYear());
			preparedStatement.setInt(4, bookDTO.getSerialNo());
			int rowsUpdated = preparedStatement.executeUpdate();

			if (rowsUpdated > 0) {
				result = true;
				logger.info("Book record updated successfully: Serial:" + bookDTO.getSerialNo());
			} else {
				logger.warn("No rows updated. Book record not found or not modified: Serial:" + bookDTO.getSerialNo());
			}
		} catch (SQLException e) {
			logger.error("Error updating book record: Serial:" + bookDTO.getSerialNo(), e);
			result = false;
		}
		return result;
	}

	public boolean deleteBookRecord(BookDTO bookDTO) {

		boolean result = false;
		try {
			// Prepare and execute an SQL DELETE statement to delete the book record from
			// the database
			PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection()
					.prepareStatement("DELETE FROM book WHERE serial_no = ?");
			preparedStatement.setInt(1, bookDTO.getSerialNo());
			int rowsDeleted = preparedStatement.executeUpdate();

			if (rowsDeleted > 0) {
				result = true;
				logger.info("Book record deleted successfully: Serial:" + bookDTO.getSerialNo());
			} else {
				logger.warn("No rows deleted. Book record not found: Serial:" + bookDTO.getSerialNo());
			}
		} catch (SQLException e) {
			logger.error("Error deleting book record: Serial:" + bookDTO.getSerialNo(), e);
			result = false;
		}
		return result;
	}

	public List<BookDTO> fetchBookRecords() {

		List<BookDTO> bookList = new ArrayList<>();

		try {
			// Create a SQL statement to select all book records from the database
			String sql = "SELECT * FROM book";
			PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			// Iterate through the result set and create BookDTO objects for each record
			while (resultSet.next()) {
				int book_id = resultSet.getInt("book_id");
				int serialNo = resultSet.getInt("serial_no");
				String bookTitle = resultSet.getString("book_title");
				String bookAuthor = resultSet.getString("book_author");
				int authorDeathYear = resultSet.getInt("author_death_year");

				BookDTO bookDTO = new BookDTO(book_id, serialNo, bookTitle, bookAuthor, authorDeathYear);
				bookList.add(bookDTO);
			}

		} catch (SQLException e) {
			logger.error("Error fetching book records.", e);
		}

		return bookList;
	}
}
