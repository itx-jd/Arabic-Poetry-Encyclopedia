package TO;

import java.util.ArrayList;

/**
 * This class represents a Data Transfer Object for Poem.
 */

public class PoemDTO {
    private int poemId;
    private String poemTitle;
    private int bookId = -1;
    
    private ArrayList<VerseDTO> poemVerses;

    public PoemDTO() {
    	poemId = -1;
    	poemTitle = "";
    	bookId = -1;
    	poemVerses = new ArrayList<VerseDTO>();    	
    }
    
    public PoemDTO(String poemTitle, int bookId) {
        this.poemTitle = poemTitle;
        this.bookId = bookId;
        poemVerses = new ArrayList<VerseDTO>(); 
    }

    public PoemDTO(int poemId, String poemTitle, int bookId) {
        this.poemId = poemId;
        this.poemTitle = poemTitle;
        this.bookId = bookId;
        poemVerses = new ArrayList<VerseDTO>(); 
    }
    
    public void setVerseListPoemId(int id) {
    	
    	for(VerseDTO verseDTO: poemVerses) {
    		verseDTO.setPoemId(id);
    	}
    	
    }

    public int getPoemId() {
        return poemId;
    }

    public void setPoemId(int poemId) {
        this.poemId = poemId;
    }

    public String getPoemTitle() {
        return poemTitle;
    }

    public void setPoemTitle(String poemTitle) {
        this.poemTitle = poemTitle;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    ////
    
    public ArrayList<VerseDTO> getPoemVerses() {
        return poemVerses;
    }
    
    
    public void addPoemVerses(VerseDTO verseDTO) {
        poemVerses.add(verseDTO);
    }

}
