package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TO.PoemDTO;
import Utility.Logger4J;

public class PoemDAO implements IDALPoem {
	
	private final Logger4J logger = Logger4J.getInstance();

    @Override
    public boolean addPoemRecord(PoemDTO poemDTO) {
    	
        boolean result = false;

        try {
        	
            // Prepare and execute an SQL INSERT statement to add a new poem record to the database
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection()
                    .prepareStatement("INSERT INTO poem (poem_title, book_id) VALUES (?, ?)");
            preparedStatement.setString(1, poemDTO.getPoemTitle());
            preparedStatement.setInt(2, poemDTO.getBookId());
        	
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Poem record added successfully. Title: " + poemDTO.getPoemTitle());
            }
        } catch (SQLException e) {
            logger.error("Error adding poem record: Title: " + poemDTO.getPoemTitle(), e);
            result = false;
        }

        return result;
    }

    @Override
    public boolean updatePoemRecord(PoemDTO poemDTO) {
        boolean result = false;

        try {
            // Prepare and execute an SQL UPDATE statement to update the poem record in the database
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection()
                    .prepareStatement("UPDATE poem SET poem_title = ?, book_id = ? WHERE poem_id = ?");
            preparedStatement.setString(1, poemDTO.getPoemTitle());
            preparedStatement.setInt(2, poemDTO.getBookId());
            preparedStatement.setInt(3, poemDTO.getPoemId());
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Poem record updated successfully. Title: " + poemDTO.getPoemTitle());
            }
        } catch (SQLException e) {
            logger.error("Error updating poem record: Title: " + poemDTO.getPoemTitle(), e);
            result = false;
        }

        return result;
    }

    @Override
    public boolean deletePoemRecord(PoemDTO poemDTO) {
        boolean result = false;

        try {
            // Prepare and execute an SQL DELETE statement to delete the poem record from the database
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection()
                    .prepareStatement("DELETE FROM poem WHERE poem_id = ?");
            preparedStatement.setInt(1, poemDTO.getPoemId());
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                result = true;
                logger.info("Poem record deleted successfully. Title: " + poemDTO.getPoemTitle());
            }
        } catch (SQLException e) {
            logger.error("Error deleting poem record: Title: " + poemDTO.getPoemTitle(), e);
            result = false;
        }

        return result;
    }

    @Override
    public List<PoemDTO> fetchPoemRecords() {
    	
        List<PoemDTO> poemList = new ArrayList<>();

        try {
            // Create a SQL statement to select all poem records from the database
            String sql = "SELECT * FROM poem";
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create PoemDTO objects for each record
            while (resultSet.next()) {
                int poemId = resultSet.getInt("poem_id");
                String poemTitle = resultSet.getString("poem_title");
                int bookId = resultSet.getInt("book_id");

                PoemDTO poemDTO = new PoemDTO(poemId, poemTitle, bookId);
                poemList.add(poemDTO);
            }
        } catch (SQLException e) {
        	logger.error("Error fetching poem records", e);
        }

        return poemList;
    }

    @Override
    public int getPoemId(String name) {
        int poemId = -1;

        try {
            // Create an SQL statement to select the poem ID by its title
            String sql = "SELECT poem_id FROM poem WHERE poem_title = ?";
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                poemId = resultSet.getInt("poem_id");
            }
        } catch (SQLException e) {
            logger.error("Error getting poem ID for title: " + name, e);
        }

        return poemId;
    }

}
