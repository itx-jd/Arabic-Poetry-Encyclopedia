package TO;

/**
 * This class represents a Data Transfer Object for Verse.
 */

public class VerseDTO {
    private int verseId = 0;
    private String verseText;
    private int poemId;

    public VerseDTO() {
        // Default constructor
    }

    public VerseDTO(int verseId, String verseText, int poemId) {
        this.verseId = verseId;
        this.verseText = verseText;
        this.poemId = poemId;
    }
    
    public VerseDTO(String verseText, int poemId) {
        this.verseText = verseText;
        this.poemId = poemId;
    }
    
    public VerseDTO(String verseText) {
        this.verseText = verseText;
    }
    
    public int getVerseId() {
        return verseId;
    }

    public void setVerseId(int verseId) {
        this.verseId = verseId;
    }

    public String getVerseText() {
        return verseText;
    }

    public void setVerseText(String verseText) {
        this.verseText = verseText;
    }

    public int getPoemId() {
        return poemId;
    }

    public void setPoemId(int poemId) {
        this.poemId = poemId;
    }

}
