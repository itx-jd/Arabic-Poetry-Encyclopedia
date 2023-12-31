package DAL;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import TO.PoemDTO;
import TO.VerseDTO;
import Utility.Logger4J;

public class TextFileReaderDAO implements IDALFileReader {

    private final Logger4J logger = Logger4J.getInstance();

    @Override
    public ArrayList<PoemDTO> readingFile(String filePath, int book_id) {

        ArrayList<PoemDTO> poems = new ArrayList<>();
        PoemDTO currentPoem = null;

        try {

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("_________")) {
                    // Ignore lines until "=========="
                    while ((line = br.readLine()) != null && !line.contains("==========")) {
                        // Skip lines
                    }
                }

                if (line.startsWith("[") && line.endsWith("]")) {
                    // Extract and set the poem title
                    String title = line.substring(1, line.length() - 1);
                    currentPoem = new PoemDTO();

                    // Assigning Book Id

                    currentPoem.setBookId(book_id);

                    // Assigning Poem Title

                    currentPoem.setPoemTitle(title);

                    poems.add(currentPoem);
                }

                if (line.contains("(") && line.contains(")")) {
                    if (currentPoem != null) {
                        String verseLine = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).trim();
                        String[] verseParts = verseLine.split("\\.\\.\\.");

                        String verse = "";

                        if (verseParts.length < 2) {
                            verse = verseParts[0];
                        } else {
                            verse = verseParts[0] + ", " + verseParts[1];
                        }

                        currentPoem.addPoemVerses(new VerseDTO(verse));

                    }
                }
            }

            logger.info("File reading completed successfully.");

        } catch (Exception e) {
            logger.error("Error reading file: " + filePath, e);
        }

        return poems;
    }
}
