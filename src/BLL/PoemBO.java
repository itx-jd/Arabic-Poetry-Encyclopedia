package BLL;

import java.util.List;
import DAL.IDALFacade;
import TO.PoemDTO;
import Utility.CustomException;

// Business Object for Poems
public class PoemBO implements IBLLPoem{
	
    IDALFacade iDALFacade;

    // Add a poem record
    @Override
    public boolean addPoemRecord(PoemDTO poemDTO) {
    	
    	try {
		    if (iDALFacade.addPoemRecord(poemDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Adding Poem Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    	
    }

    // Update a poem record
    @Override
    public boolean updatePoemRecord(PoemDTO poemDTO) {
    	
    	try {
		    if (iDALFacade.updatePoemRecord(poemDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Updaing Poem Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    	
    }

    // Delete a poem record
    @Override
    public boolean deletePoemRecord(PoemDTO poemDTO) {
    	
    	try {
		    if (iDALFacade.deletePoemRecord(poemDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Deleting Poem Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    	
    }

    // Fetch all poem records
    @Override
    public List<PoemDTO> fetchPoemRecords() {
    	
    	try {
			return this.iDALFacade.fetchPoemRecords();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
    	
    }

    // Get poem ID by name
    @Override
    public int getPoemId(String name) {
    	
    	try {
    		return iDALFacade.getPoemId(name);
		}catch(Exception e) {
			e.printStackTrace();
		}
    	
    	return -1;
    }

}
