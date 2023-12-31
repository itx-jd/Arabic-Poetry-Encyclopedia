package TO;

public class TokenVerseDTO {
	
	int id;
	int root_id;
	int verse_id;
	
	
	
	public TokenVerseDTO() {
		super();
	}

	public TokenVerseDTO(int id, int root_id, int verse_id) {
		super();
		this.id = id;
		this.root_id = root_id;
		this.verse_id = verse_id;
	}
	
	public TokenVerseDTO(int id, int root_id) {
		super();
		this.id = id;
		this.root_id = root_id;
	}	

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getRoot_id() {
		return root_id;
	}
	public void setRoot_id(int root_id) {
		this.root_id = root_id;
	}
	public int getVerse_id() {
		return verse_id;
	}
	public void setVerse_id(int verse_id) {
		this.verse_id = verse_id;
	}
	
	

}
