package DAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TO.PoemDTO;

public class PoemDAOStub implements IDALPoem {

    private final Map<Integer, PoemDTO> database;

    public PoemDAOStub() {
        database = new HashMap<>();
    }

    @Override
    public boolean addPoemRecord(PoemDTO poemDTO) {
    	
        if (poemDTO == null || !isComplete(poemDTO)) {
            return false;
        }

        // Assuming poemId is generated sequentially
        poemDTO.setPoemId(database.size() + 1);

        database.put(poemDTO.getPoemId(), poemDTO);
        return true;
    }

    @Override
    public boolean updatePoemRecord(PoemDTO poemDTO) {
        if (poemDTO == null || !database.containsKey(poemDTO.getPoemId()) || !isComplete(poemDTO)) {
            return false;
        }

        // Update if everything is perfect
        database.put(poemDTO.getPoemId(), poemDTO);
        return true;
    }

    @Override
    public boolean deletePoemRecord(PoemDTO poemDTO) {
        if (poemDTO == null || !database.containsKey(poemDTO.getPoemId())) {
            return false;
        }

        database.remove(poemDTO.getPoemId());
        return true;
    }

    @Override
    public List<PoemDTO> fetchPoemRecords() {
        return new ArrayList<>(database.values());
    }

    @Override
    public int getPoemId(String name) {
        for (PoemDTO poemDTO : database.values()) {
            if (poemDTO.getPoemTitle().equalsIgnoreCase(name)) {
                return poemDTO.getPoemId();
            }
        }
        return -1; // Return -1 if not found
    }

    boolean isComplete(PoemDTO poemDTO) {
        return !poemDTO.getPoemTitle().isEmpty() && poemDTO.getBookId() != -1;
    }
}
