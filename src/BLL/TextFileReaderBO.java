/*
 * Business Object for Text File Reader implementing IBLLFileReader
 */
package BLL;

import java.util.ArrayList;
import DAL.IDALFacade;
import TO.PoemDTO;

public class TextFileReaderBO implements IBLLFileReader{

    IDALFacade iDALFacade;
    
    // Attaches the Data Access Layer facade
    @Override
    public void attachFacade(IDALFacade iDALFacade) {
        this.iDALFacade = iDALFacade;
    }

    // Reads a file and retrieves poems
    @Override
    public ArrayList<PoemDTO> readingFile(String filePath,int book_id) {
    	
    	try {
    		return this.iDALFacade.readingFile(filePath, book_id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
    	
        
    }
}
