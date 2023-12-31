package BLL;

import java.util.List;
import DAL.IDALFacade;
import TO.RootDTO;
import TO.RootInfoDTO;

// Interface for Root in Business Logic Layer
public interface IBLLRoot {
    
    // Add a root record
    boolean addRootRecord(RootDTO rootDTO);
    
    // Update a root record
    boolean updateRootRecord(RootDTO rootDTO);
    
    // Delete a root record
    boolean deleteRootRecord(RootDTO rootDTO);
    
    // Fetch all root records
    List<RootDTO> fetchRootRecords();
    
    // Get root DTO list by verse ID
    List<RootDTO> getRootDTOListByVerseId(int verseId);
    
    // Delete root-verse records by verse ID
    boolean deleteRootVerseRecordsByVerseId(int verseId);
    
    // Insert root-verse record
    boolean insertRootVerseRecord(int verseId, int rootId, String status);
    
    // Check if root-verse exists
    boolean doesRootVerseExist(int rootId, int verseId);
    
    // Get root ID from root text
    int getRootIdFromRootText(String rootText);
    
    // Delete root-verse by verse ID
    boolean deleteRootVerseByVerseID(int verseId);
    
    // Get root info DTO list by root text
    List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText);
    
    // Attaches the Data Access Layer facade
    void attachFacade(IDALFacade iDALFacade);
}
