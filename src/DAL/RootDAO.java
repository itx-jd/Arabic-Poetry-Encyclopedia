package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TO.RootDTO;
import TO.RootInfoDTO;
import Utility.Logger4J;

public class RootDAO implements IDALRoot {
    
    private final Logger4J logger = Logger4J.getInstance();
    /**
     * Adds a RootDTO record to the database.
     *
     * @param rootDTO The RootDTO object to be added.
     * @return True if the root record is added successfully, false otherwise.
     */
    @Override
    public boolean addRootRecord(RootDTO rootDTO) {

        boolean result = false;

        try {

            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection()
                    .prepareStatement("INSERT INTO root(root_text) VALUES(?)");
            preparedStatement.setString(1, rootDTO.getRootText());
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Root record added successfully. Text: " + rootDTO.getRootText());
            }

        } catch (SQLException e) {
            logger.error("Error adding root record: Text: " + rootDTO.getRootText(), e);
            result = false;
        }

        return result;
    }
    /**
     * Updates a RootDTO record in the database.
     *
     * @param rootDTO The RootDTO object containing updated information.
     * @return True if the root record is updated successfully, false otherwise.
     */
    @Override
    public boolean updateRootRecord(RootDTO rootDTO) {

        boolean result = false;

        try {

            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection()
                    .prepareStatement("UPDATE root SET root_text = ? WHERE root_id = ?");
            preparedStatement.setString(1, rootDTO.getRootText());
            preparedStatement.setInt(2, rootDTO.getRootId());
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Root record updated successfully. Text: " + rootDTO.getRootText());
            }

        } catch (SQLException e) {
            logger.error("Error updating root record: Text: " + rootDTO.getRootText(), e);
            result = false;
        }

        return result;
    }
    /**
     * Deletes a RootDTO record from the database.
     *
     * @param rootDTO The RootDTO object to be deleted.
     * @return True if the root record is deleted successfully, false otherwise.
     */
    @Override
    public boolean deleteRootRecord(RootDTO rootDTO) {

        boolean result = false;

        try {

            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection()
                    .prepareStatement("DELETE FROM root WHERE root_id = ?");
            preparedStatement.setInt(1, rootDTO.getRootId());
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Root record deleted successfully. Text: " + rootDTO.getRootText());
            }

        } catch (SQLException e) {
            logger.error("Error deleting root record: Text: " + rootDTO.getRootText(), e);
            result = false;
        }

        return result;
    }
    /**
     * Fetches all RootDTO records from the database.
     *
     * @return List of RootDTO objects containing all root records.
     */
    @Override
    public List<RootDTO> fetchRootRecords() {

        List<RootDTO> rootList = new ArrayList<>();

        try {
            // Create a SQL statement to select all root records from the database
            String sql = "SELECT * FROM root";
            PreparedStatement preparedStatement = MySQLConnectionSingleton.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create RootDTO objects for each record
            while (resultSet.next()) {
                int rootId = resultSet.getInt("root_id");
                String rootText = resultSet.getString("root_text");

                RootDTO rootDTO = new RootDTO(rootId, rootText);
                rootList.add(rootDTO);
            }

        } catch (SQLException e) {
            logger.error("Error fetching root records", e);
        }

        return rootList;
    }

    // Root Verse
    /**
     * Retrieves RootDTO records associated with a specific verse ID.
     *
     * @param verseId The ID of the verse for which root records are fetched.
     * @return List of RootDTO objects associated with the specified verse ID.
     */
    public List<RootDTO> getRootDTOListByVerseId(int verseId) {
        
        List<RootDTO> rootList = new ArrayList<>();

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT root_verse.root_id, root_verse.verse_id, root_verse.status, root.root_text " +
                            "FROM root_verse " +
                            "JOIN root ON root_verse.root_id = root.root_id " +
                            "WHERE root_verse.verse_id = ?");
            preparedStatement.setInt(1, verseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int rootId = resultSet.getInt("root_id");
                String rootText = resultSet.getString("root_text");
                String status = resultSet.getString("status");

                RootDTO rootDTO = new RootDTO(rootId, rootText, status);
                rootList.add(rootDTO);
            }

        } catch (SQLException e) {
            logger.error("Error fetching root records by verse ID", e);
        }

        return rootList;
    }
    
    /**
     * Deletes all root verse records associated with a specific verse ID.
     *
     * @param verseId The ID of the verse for which root verse records are deleted.
     * @return True if root verse records are deleted successfully, false otherwise.
     */
    public boolean deleteRootVerseRecordsByVerseId(int verseId) {
        
        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            String sql = "DELETE FROM root_verse WHERE verse_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, verseId);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    result = true;
                    logger.info("Root verse records deleted successfully. Verse ID: " + verseId);
                }
            }
        } catch (SQLException e) {
            logger.error("Error deleting root verse records by verse ID: " + verseId, e);
        }

        return result;
    }
    
    /**
     * Inserts a root verse record into the database.
     *
     * @param rootId  The ID of the root.
     * @param verseId The ID of the verse.
     * @param status  The status of the root verse record.
     * @return True if the root verse record is added successfully, false otherwise.
     */
    public boolean insertRootVerseRecord(int rootId,int verseId,String status) {
        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            String sql = "INSERT INTO root_verse (root_id, verse_id, status) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, rootId);
                preparedStatement.setInt(2, verseId);
                preparedStatement.setString(3, status);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    result = true;
                    logger.info("Root verse record added successfully. Root ID: " + rootId + ", Verse ID: " + verseId);
                }
            }
        } catch (SQLException e) {
            logger.error("Error inserting root verse record. Root ID: " + rootId + ", Verse ID: " + verseId, e);
        }

        return result;
    }
    /**
     * Retrieves the root ID from the provided root text.
     *
     * @param rootText The root text for which the ID is retrieved.
     * @return The ID of the root.
     */
    public int getRootIdFromRootText(String rootText) {
        int rootId = -1;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT root_id FROM root WHERE root_text = ?");
            preparedStatement.setString(1, rootText);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                rootId = resultSet.getInt("root_id");
            }

        } catch (SQLException e) {
            logger.error("Error getting root ID from root text: " + rootText, e);
        }

        return rootId;
    }
    /**
     * Checks if a root verse record exists based on the root ID and verse ID.
     *
     * @param rootId  The ID of the root.
     * @param verseId The ID of the verse.
     * @return True if the root verse record exists, false otherwise.
     */
    public boolean doesRootVerseExist(int rootId, int verseId) {
        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM root_verse WHERE root_id = ? AND verse_id = ?");
            preparedStatement.setInt(1, rootId);
            preparedStatement.setInt(2, verseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // If the result set is not empty, it means the record already exists
                result = true;
            }

        } catch (SQLException e) {
            logger.error("Error checking if root verse exists. Root ID: " + rootId + ", Verse ID: " + verseId, e);
        }

        return result;
    }

    /**
     * Deletes all root verse records associated with a specific verse ID.
     *
     * @param verseId The ID of the verse for which root verse records are deleted.
     * @return True if root verse records are deleted successfully, false otherwise.
     */
    public boolean deleteRootVerseByVerseID(int verseId) {
        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            String sql = "DELETE FROM root_verse WHERE verse_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, verseId);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    result = true;
                    logger.info("Root verse records deleted successfully. Verse ID: " + verseId);
                }
            }
        } catch (SQLException e) {
            logger.error("Error deleting root verse records by verse ID: " + verseId, e);
        }

        return result;
    }

    
    // Root Info
    /**
     * Retrieves RootInfoDTO records based on the provided root text.
     *
     * @param rootText The root text for which RootInfoDTO records are retrieved.
     * @return List of RootInfoDTO objects associated with the specified root text.
     */
    public List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText) {
        
        List<RootInfoDTO> rootInfoDTOList = new ArrayList<>();

        try {
            
            Connection connection = MySQLConnectionSingleton.getConnection();
            String sql = "SELECT b.book_title, p.poem_title, v.misrah_first, v.misrah_second, rv.status " +
                    "FROM root r " +
                    "JOIN root_verse rv ON r.root_id = rv.root_id " +
                    "JOIN verse v ON rv.verse_id = v.verse_id " +
                    "JOIN poem p ON v.poem_id = p.poem_id " +
                    "JOIN book b ON p.book_id = b.book_id " +
                    "WHERE r.root_text = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, rootText);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String bookTitle = resultSet.getString("book_title");
                    String poemTitle = resultSet.getString("poem_title");
                    String misrah1 = resultSet.getString("misrah_first");
                    String misrah2 = resultSet.getString("misrah_second");
                    String status = resultSet.getString("status");

                    RootInfoDTO rootInfoDTO = new RootInfoDTO(bookTitle, poemTitle, misrah1, misrah2, status);
                    rootInfoDTOList.add(rootInfoDTO);
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting root info by root text: " + rootText, e);
        }

        return rootInfoDTOList;
    }
}
