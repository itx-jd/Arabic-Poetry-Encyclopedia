package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TO.TokenDTO;
import Utility.Logger4J;

public class TokenDAO implements IDALToken {

    private final Logger4J logger = Logger4J.getInstance();

    // Token
    /**
     * Inserts a token into the database.
     *
     * @param tokenDTO The TokenDTO object containing token details to be inserted.
     * @return True if the token is inserted successfully, false otherwise.
     */
    public boolean insertToken(TokenDTO tokenDTO) {
        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO token (word, tag) VALUES (?, ?)");

            preparedStatement.setString(1, tokenDTO.getWord());
            preparedStatement.setString(2, tokenDTO.getTag());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Token inserted successfully: " + tokenDTO.getWord());
            }

        } catch (SQLException e) {
            logger.error("Error inserting token: " + tokenDTO.getWord(), e);
            result = false;
        }

        return result;
    }
    /**
     * Retrieves the ID of a token from the database based on its word.
     *
     * @param word The word of the token for which the ID is to be retrieved.
     * @return The ID of the token if found, otherwise -1.
     */
    public int getTokenIdFromTokenText(String word) {
        int tokenId = -1;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM token WHERE word = ?");
            preparedStatement.setString(1, word);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                tokenId = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            logger.error("Error getting token ID for word: " + word, e);
            tokenId = -1; // Set to -1 in case of an exception
        }

        return tokenId;
    }
    /**
     * Deletes a token from the database based on its ID.
     *
     * @param tokenId The ID of the token to be deleted.
     * @return True if the token is deleted successfully, false otherwise.
     */
    public boolean deleteToken(int tokenId) {
        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM token WHERE id = ?");
            preparedStatement.setInt(1, tokenId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Token deleted successfully: ID - " + tokenId);
            }

        } catch (SQLException e) {
            logger.error("Error deleting token: ID - " + tokenId, e);
            result = false;
        }

        return result;
    }
    /**
     * Updates a token's details in the database.
     *
     * @param updatedToken The updated TokenDTO object.
     * @return True if the token details are updated successfully, false otherwise.
     */
    public boolean updateToken(TokenDTO updatedToken) {
        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE token SET word = ?, tag = ? WHERE id = ?");
            preparedStatement.setString(1, updatedToken.getWord());
            preparedStatement.setString(2, updatedToken.getTag());
            preparedStatement.setInt(3, updatedToken.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Token updated successfully: ID - " + updatedToken.getId());
            }

        } catch (SQLException e) {
            logger.error("Error updating token: ID - " + updatedToken.getId(), e);
            result = false;
        }

        return result;
    }
    /**
     * Retrieves a list of all tokens from the database.
     *
     * @return List of TokenDTO objects representing all tokens in the database.
     */
    public List<TokenDTO> getTokenDTOList() {

        List<TokenDTO> tokenList = new ArrayList<>();

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tokens");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TokenDTO token = new TokenDTO();
                token.setId(resultSet.getInt("id"));
                token.setWord(resultSet.getString("word"));
                token.setTag(resultSet.getString("tag"));

                tokenList.add(token);
            }

        } catch (SQLException e) {
            logger.error("Error fetching token list", e);
        }

        return tokenList;
    }
    /**
     * Checks if a token-verse relation exists in the database.
     *
     * @param token_id The ID of the token.
     * @param verse_id The ID of the verse.
     * @return True if the relation exists, false otherwise.
     */
    public boolean doesTokenVerseExist(int token_id, int verse_id) {
        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM token_verse WHERE token_id = ? AND verse_id = ?");
            preparedStatement.setInt(1, token_id);
            preparedStatement.setInt(2, verse_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // If the result set is not empty, it means the record already exists
                result = true;
            }

        } catch (SQLException e) {
            logger.error("Error checking if token verse exists: Token ID - " + token_id + ", Verse ID - " + verse_id, e);
            result = false;
        }

        return result;
    }

    // Token To Verse
    /**
     * Inserts a token-verse relation into the database.
     *
     * @param token_id The ID of the token.
     * @param verse_id The ID of the verse.
     * @return True if the relation is inserted successfully, false otherwise.
     */
    public boolean insertTokenIntoVerse(int token_id, int verse_id) {

        boolean result = false;

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO token_verse (token_id, verse_id) VALUES (?, ?)");

            preparedStatement.setInt(1, token_id);
            preparedStatement.setInt(2, verse_id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                result = true;
                logger.info("Token linked to verse successfully: Token ID - " + token_id + ", Verse ID - " + verse_id);
            }

        } catch (SQLException e) {
            logger.error("Error linking token to verse: Token ID - " + token_id + ", Verse ID - " + verse_id, e);
            result = false;
        }

        return result;
    }
    /**
     * Retrieves a list of tokens associated with a specific verse from the database.
     *
     * @param verseId The ID of the verse.
     * @return List of TokenDTO objects associated with the specified verse.
     */
    public List<TokenDTO> getTokenDTOListByVerseId(int verseId) {
        List<TokenDTO> tokenList = new ArrayList<>();

        try {
            Connection connection = MySQLConnectionSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT token.id, token.word, token.tag " +
                            "FROM token_verse " +
                            "JOIN token ON token_verse.token_id = token.id " +
                            "WHERE token_verse.verse_id = ?");
            preparedStatement.setInt(1, verseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int tokenId = resultSet.getInt("id");
                String word = resultSet.getString("word");
                String tag = resultSet.getString("tag");

                TokenDTO tokenDTO = new TokenDTO();
                tokenDTO.setId(tokenId);
                tokenDTO.setWord(word);
                tokenDTO.setTag(tag);

                tokenList.add(tokenDTO);
            }

        } catch (SQLException e) {
            logger.error("Error fetching token list by verse ID: " + verseId, e);
        }

        return tokenList;
    }
}
