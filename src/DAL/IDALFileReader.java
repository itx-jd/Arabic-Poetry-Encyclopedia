package DAL;

import java.util.ArrayList;

import TO.PoemDTO;

public interface IDALFileReader {

	public ArrayList<PoemDTO> readingFile(String filePath,int book_id);
	
}
