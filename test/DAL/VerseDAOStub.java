package DAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TO.VerseDTO;

public class VerseDAOStub implements IDALVerse {

    private final Map<Integer, VerseDTO> database;

    public VerseDAOStub() {
        database = new HashMap<>();
    }

    @Override
    public boolean addVerseRecord(List<VerseDTO> verseList) {
        if (verseList == null || verseList.isEmpty()) {
            return false;
        }

        for (VerseDTO verse : verseList) {
            int verseId = database.size() + 1; // Assigning the next available ID
            verse.setVerseId(verseId);
            database.put(verseId, verse);
        }

        return true;
    }

    @Override
    public List<VerseDTO> getVersesByPoemId(int poemId) {
        List<VerseDTO> verses = new ArrayList<>();
        for (VerseDTO verse : database.values()) {
            if (verse.getPoemId() == poemId) {
                verses.add(verse);
            }
        }
        return verses;
    }

    @Override
    public boolean deleteVersesByPoemId(int poemId) {
    	
        List<Integer> versesToRemove = new ArrayList<>();

        for (Map.Entry<Integer, VerseDTO> entry : database.entrySet()) {
            if (entry.getValue().getPoemId() == poemId) {
                versesToRemove.add(entry.getKey());
            }
        }

        for (int verseId : versesToRemove) {
            database.remove(verseId);
        }

        return !versesToRemove.isEmpty();
    }
}
