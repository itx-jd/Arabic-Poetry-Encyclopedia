package DAL;

import java.util.List;

import TO.VerseDTO;

public interface IDALVerse {

	public boolean addVerseRecord(List<VerseDTO> verseList);
	public List<VerseDTO> getVersesByPoemId(int poemId);
	public boolean deleteVersesByPoemId(int poemId);
	
}
