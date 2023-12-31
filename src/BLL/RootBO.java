package BLL;

import java.util.List;
import DAL.IDALFacade;
import TO.RootDTO;
import TO.RootInfoDTO;
import Utility.CustomException;

// Business Object for Roots
public class RootBO implements IBLLRoot{
    
    IDALFacade iDALFacade;
    
    // Attaches the Data Access Layer facade
    @Override
    public void attachFacade(IDALFacade iDALFacade) {
        this.iDALFacade = iDALFacade;
    }
    
    // Add a root record
    @Override
    public boolean addRootRecord(RootDTO rootDTO) {
    	
    	try {
		    if (iDALFacade.addRootRecord(rootDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Adding Root Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    }

    // Update a root record
    @Override
    public boolean updateRootRecord(RootDTO rootDTO) {
    	
    	try {
		    if (this.iDALFacade.updateRootRecord(rootDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Updaing Root Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    }

    // Delete a root record
    @Override
    public boolean deleteRootRecord(RootDTO rootDTO) {
    	
    	try {
		    if (this.iDALFacade.updateRootRecord(rootDTO)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Deleting Root Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    }

    // Fetch all root records
    @Override
    public List<RootDTO> fetchRootRecords() {
    	
    	try {
			return this.iDALFacade.fetchRootRecords();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
   
    }

    // Get root DTO list by verse ID
    @Override
    public List<RootDTO> getRootDTOListByVerseId(int verseId) {
    	
    	try {
			return this.iDALFacade.getRootDTOListByVerseId(verseId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
    }

    // Delete root-verse records by verse ID
    @Override
    public boolean deleteRootVerseRecordsByVerseId(int verseId) {
    	
    	try {
		    if (this.iDALFacade.deleteRootVerseRecordsByVerseId(verseId)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Deleting RootVerse Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    	
    }

    // Insert root-verse record
    @Override
    public boolean insertRootVerseRecord(int verseId, int rootId, String status) {
    	
    	try {
		    if (this.iDALFacade.insertRootVerseRecord(verseId, rootId, status)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Inserting RootVerse Record");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    	
    }

    // Check if root-verse exists
    @Override
    public boolean doesRootVerseExist(int rootId, int verseId) {
    	
    	try {
		    if (this.iDALFacade.doesRootVerseExist(rootId, verseId)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For Does RootVerse Record Exist");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    	
    }

    // Get root ID from root text
    @Override
    public int getRootIdFromRootText(String rootText) {
    	
    	try {
    		return this.iDALFacade.getRootIdFromRootText(rootText);
		}catch(Exception e) {
			e.printStackTrace();
		}
    	
    	return -1;
    	
        
    }
    
    // Delete root-verse by verse ID
    @Override
    public boolean deleteRootVerseByVerseID(int verseId) {
    	
    	try {
		    if (this.iDALFacade.deleteRootVerseByVerseID(verseId)) {
		        return true;
		    } else {
		        throw new CustomException("Invalid Input For delete Root Verse By VerseID");
		    }
		} catch (CustomException customException) {
		    customException.printErrorMessage();
		    return false;
		} 
    	
    }

    // Get root info DTO list by root text
    @Override
    public List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText) {
    	
    	try {
    		return this.iDALFacade.getRootInfoDTOListByRootText(rootText);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
    	
        
    }
}
