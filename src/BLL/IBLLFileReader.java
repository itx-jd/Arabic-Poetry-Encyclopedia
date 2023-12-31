package BLL;

import java.util.ArrayList;
import DAL.IDALFacade;
import TO.PoemDTO;

// Interface for File Reader in Business Logic Layer
public interface IBLLFileReader {

    // Attaches the Data Access Layer facade
    void attachFacade(IDALFacade iDALFacade);
    
    // Reads a file and retrieves poems
    public ArrayList<PoemDTO> readingFile(String filePath, int book_id);
}
