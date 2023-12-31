package DAL;

public class TextFileReaderFactory extends FileReaderFactory {

	@Override
	public
	IDALFileReader createTextFileReader() {
		return new TextFileReaderDAO();
	}

	@Override
	public
	IDALFileReader createJsonFileReader() {
		// WE WILL IMPLEMENT THIS ONLY IN JSONFileReaderFactory CLASS
		return null;
	}

}
