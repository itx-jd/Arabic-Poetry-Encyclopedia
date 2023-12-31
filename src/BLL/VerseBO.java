/*
 * Business Object for Verse implementing IBLLVerse
 */
package BLL;

import java.util.List;
import DAL.IDALFacade;
import TO.VerseDTO;
import Utility.CustomException;

public class VerseBO implements IBLLVerse{
    
    IDALFacade iDALFacade;

    // Attaches the Data Access Layer facade
    @Override
    public void attachFacade(IDALFacade iDALFacade) {
        this.iDALFacade = iDALFacade;
    }

    // Add a verse record
    @Override
    public boolean addVerseRecord(List<VerseDTO> verseList) {
    	
    	try {
		    if (this.iDALFacade.addVerseRecord(verseList)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For add Verse Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    	
    }

    // Get verses by poem ID
    @Override
    public List<VerseDTO> getVersesByPoemId(int poemId) {
        return this.iDALFacade.getVersesByPoemId(poemId);
    }

    // Delete verses by poem ID
    @Override
    public boolean deleteVersesByPoemId(int poemId) {
    	
    	try {
		    if (this.iDALFacade.deleteVersesByPoemId(poemId)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For delete Verses By PoemId");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 

    }

}
