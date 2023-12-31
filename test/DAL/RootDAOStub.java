package DAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TO.RootDTO;
import TO.RootInfoDTO;
import TO.RootVerseDTO;

public class RootDAOStub implements IDALRoot {

	private final Map<Integer, RootDTO> rootDatabase;
	private final Map<Integer, RootVerseDTO> rootVerseDatabase;
	private final Map<Integer, RootInfoDTO> rootInfoDatabase;

	public RootDAOStub() {
		rootDatabase = new HashMap<>();
		rootVerseDatabase = new HashMap<>();
		rootInfoDatabase = new HashMap<>();
	}

	@Override
	public boolean addRootRecord(RootDTO rootDTO) {

		if (rootDTO == null || !isComplete(rootDTO)) {
			return false;
		}

		rootDTO.setRootId(rootDatabase.size() + 1);

		rootDatabase.put(rootDTO.getRootId(), rootDTO);
		return true;
	}

	@Override
	public boolean updateRootRecord(RootDTO rootDTO) {
		if (rootDTO == null || !rootDatabase.containsKey(rootDTO.getRootId()) || !isComplete(rootDTO)) {
			return false;
		}

		// Update if everything is perfect
		rootDatabase.put(rootDTO.getRootId(), rootDTO);
		return true;
	}

	@Override
	public boolean deleteRootRecord(RootDTO rootDTO) {
		if (rootDTO == null || !rootDatabase.containsKey(rootDTO.getRootId())) {
			return false;
		}

		rootDatabase.remove(rootDTO.getRootId());
		return true;
	}

	@Override
	public List<RootDTO> fetchRootRecords() {
		return new ArrayList<>(rootDatabase.values());
	}

	@Override
	public List<RootDTO> getRootDTOListByVerseId(int verseId) {
		
		List<RootDTO> rootDTOList = new ArrayList<>();

		// Retrieve roots associated with the given verseId
		for (RootVerseDTO rootVerseDTO : rootVerseDatabase.values()) {
			if (rootVerseDTO.getVerse_id() == verseId) {
				RootDTO associatedRoot = rootDatabase.get(rootVerseDTO.getRoot_id());
				if (associatedRoot != null) {
					rootDTOList.add(associatedRoot);
				}
			}
		}

		return rootDTOList;
	}

	@Override
	public boolean insertRootVerseRecord(int verseId, int rootId, String status) {


		if (verseId < 0 || rootId < 0)
			return false;

		// Create a new RootVerseDTO
		RootVerseDTO rootVerseDTO = new RootVerseDTO(rootVerseDatabase.size() + 1, rootId, verseId);

		// Add the new relationship to the database
		rootVerseDatabase.put(rootVerseDTO.getId(), rootVerseDTO);
		return true;
	}

	@Override
	public boolean doesRootVerseExist(int rootId, int verseId) {
		// Check if the rootVerse relationship exists in the database
		for (RootVerseDTO rootVerseDTO : rootVerseDatabase.values()) {
			if (rootVerseDTO.getRoot_id() == rootId && rootVerseDTO.getVerse_id() == verseId) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getRootIdFromRootText(String rootText) {
		for (RootDTO rootDTO : rootDatabase.values()) {
			if (rootDTO.getRootText().equalsIgnoreCase(rootText)) {
				return rootDTO.getRootId();
			}
		}
		return -1; // Return -1 if not found
	}

	@Override
	public boolean deleteRootVerseByVerseID(int verseId) {

		if (verseId < 0)
			return false;

		// Doesn't contain any entry with verse_id

		if (!rootVerseDatabase.entrySet().stream().anyMatch(entry -> entry.getValue().getVerse_id() == verseId))
			return false;

		// Delete all root-verse relationships associated with the given verseId
		rootVerseDatabase.entrySet().removeIf(entry -> entry.getValue().getVerse_id() == verseId);

		return true;
	}

	@Override
	public List<RootInfoDTO> getRootInfoDTOListByRootText(String rootText) {
		
		// Uncomment below two line To Test If any elements contains
		
		RootInfoDTO rootInfoDTO1 = new RootInfoDTO("Book1","Poem1","Misrah 1","Misrah 2 ","Automatic");
		insertRootInfo(rootInfoDTO1);
		
		List<RootInfoDTO> rootInfoDTOList = new ArrayList<>();

		// Retrieve root information associated with the given rootText
		for (RootInfoDTO rootInfoDTO : rootInfoDatabase.values()) {
			if (rootInfoDTO.getBookTitle().equalsIgnoreCase(rootText)) {
				rootInfoDTOList.add(rootInfoDTO);
			}
		}

		return rootInfoDTOList;
	}

	boolean isComplete(RootDTO rootDTO) {
		return !rootDTO.getRootText().isEmpty();
	}

	@Override
	public boolean deleteRootVerseRecordsByVerseId(int verseId) {

		if (verseId < 0)
			return false;

		// Doesn't contain any entry with verse_id

		if (!rootVerseDatabase.entrySet().stream().anyMatch(entry -> entry.getValue().getVerse_id() == verseId)) {
			return false;
		}

		// Delete all root-verse relationships associated with the given verseId
		rootVerseDatabase.entrySet().removeIf(entry -> entry.getValue().getVerse_id() == verseId);

		return true;
	}
	
	public boolean insertRootInfo(RootInfoDTO rootInfoDTO) {

		int id = rootInfoDatabase.size()+1;

	    // Add the RootInfoDTO to the database
	    rootInfoDatabase.put(id, rootInfoDTO);

	    return true;
	}

}
