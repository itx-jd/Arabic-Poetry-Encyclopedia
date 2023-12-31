package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TO.VerseDTO;
import Utility.Logger4J;

public class VerseDAO implements IDALVerse {

    private final Logger4J logger = Logger4J.getInstance();

    /**
     * Adds a list of VerseDTO records to the database.
     *
     * @param verseList The list of VerseDTO objects to be added.
     * @return True if the verse records are added successfully, false otherwise.
     */
    @Override
    public boolean addVerseRecord(List<VerseDTO> verseList) {
        boolean result = false;

        try {
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(
                    "INSERT INTO verse (misrah_first, misrah_second, poem_id) VALUES (?, ?, ?)");

            for (VerseDTO verse : verseList) {
                // Split verseText into misrah_first and misrah_second
                String[] misrahs = verse.getVerseText().split(",", 2);

                // Set misrah_first and misrah_second based on the split result
                preparedStatement.setString(1, misrahs[0].trim());
                if (misrahs.length > 1) {
                    preparedStatement.setString(2, misrahs[1].trim());
                } else {
                    // Set misrah_second as an empty string if not present
                    preparedStatement.setString(2, "");
                }
                preparedStatement.setInt(3, verse.getPoemId());

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        } catch (SQLException e) {
            logger.error("Error adding verse records", e);
            result = false;
        }

        return result;
    }
    /**
     * Deletes all verses associated with a specific poem from the database.
     *
     * @param poemId The ID of the poem for which verses are to be deleted.
     * @return True if verses are deleted successfully, false otherwise.
     */
    public boolean deleteVersesByPoemId(int poemId) {
        boolean result = false;

        try {
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(
                    "DELETE FROM verse WHERE poem_id = ?");
            preparedStatement.setInt(1, poemId);

            int rowsDeleted = preparedStatement.executeUpdate();

            result = rowsDeleted > 0;
            if (result) {
                logger.info("Verses deleted successfully for Poem ID: " + poemId);
            } else {
                logger.warn("No verses found for deletion. Poem ID: " + poemId);
            }
        } catch (SQLException e) {
            logger.error("Error deleting verses for Poem ID: " + poemId, e);
            result = false;
        }

        return result;
    }
    /**
     * Retrieves all verses associated with a specific poem from the database.
     *
     * @param poemId The ID of the poem for which verses are to be fetched.
     * @return List of VerseDTO objects associated with the specified poem.
     */
    public List<VerseDTO> getVersesByPoemId(int poemId) {
        List<VerseDTO> verseList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM verse WHERE poem_id = ?");
            preparedStatement.setInt(1, poemId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int verseId = resultSet.getInt("verse_id");
                String misrahFirst = resultSet.getString("misrah_first");
                String misrahSecond = resultSet.getString("misrah_second");

                // Create a VerseDTO object for each row in the result set
                VerseDTO verse = new VerseDTO(verseId, misrahFirst + ", " + misrahSecond, poemId);
                verseList.add(verse);
            }

        } catch (SQLException e) {
            logger.error("Error fetching verses for Poem ID: " + poemId, e);
        }

        return verseList;
    }
}
