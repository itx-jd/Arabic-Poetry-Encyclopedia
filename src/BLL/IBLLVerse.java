package BLL;

import java.util.List;
import DAL.IDALFacade;
import TO.VerseDTO;

// Interface for Verse in Business Logic Layer
public interface IBLLVerse {
    
    // Attaches the Data Access Layer facade
    void attachFacade(IDALFacade iDALFacade);
    
    // Add a verse record
    public boolean addVerseRecord(List<VerseDTO> verseList);
    
    // Get verses by poem ID
    public List<VerseDTO> getVersesByPoemId(int poemId);
    
    // Delete verses by poem ID
    public boolean deleteVersesByPoemId(int poemId);
}
