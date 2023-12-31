package BLL;

import java.util.List;
import TO.PoemDTO;

// Interface for Poem in Business Logic Layer
public interface IBLLPoem {

    // Add a poem record
    public boolean addPoemRecord(PoemDTO poemDTO);

    // Update a poem record
    public boolean updatePoemRecord(PoemDTO poemDTO);

    // Delete a poem record
    public boolean deletePoemRecord(PoemDTO poemDTO);

    // Fetch all poem records
    public List<PoemDTO> fetchPoemRecords();

    // Get poem ID by name
    public int getPoemId(String name);
}
