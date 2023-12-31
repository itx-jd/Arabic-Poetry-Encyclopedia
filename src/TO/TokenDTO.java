package TO;

/**
 * This class represents a Data Transfer Object for Token.
 */

public class TokenDTO {
	
	int id = -1;
	String word = "";
	String tag = "";
	
	public TokenDTO(String word, String tag) {
		super();
		this.word = word;
		this.tag = tag;
	}
	
	public TokenDTO(String word) {
		super();
		this.word = word;
	}
	
	public TokenDTO() {
		super();
	}
	
	public TokenDTO(int id, String word) {
		super();
		this.id = id;
		this.word = word;
	}
	
	public TokenDTO(int id, String word, String tag) {
		super();
		this.id = id;
		this.word = word;
		this.tag = tag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}	
}
