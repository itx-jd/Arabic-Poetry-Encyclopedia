package DAL;

import java.util.ArrayList;

import TO.PoemDTO;

public class TextFileReaderDAOStub implements IDALFileReader{

	@Override
	public ArrayList<PoemDTO> readingFile(String filePath, int book_id) {
		
		if(filePath.isEmpty() || book_id < 0) {
			return null;
		}else {
			ArrayList<PoemDTO> file = new ArrayList<PoemDTO>();
			return file;
		}
	}

}
