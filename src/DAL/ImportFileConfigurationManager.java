package DAL;

public class ImportFileConfigurationManager {
	
	public static FileReaderFactory getFactory(String fileType) {
		
		if ("txt".equals(fileType)) {
			
			return new TextFileReaderFactory();
			
		} else if ("json".equals(fileType)) {
			
//			return new JsonFileReaderFactory();
		}
		
		// IF UNSUPPORTED FILE TYPE
		return null; 
	}
}
