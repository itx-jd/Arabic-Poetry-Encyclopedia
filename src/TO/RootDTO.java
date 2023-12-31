package TO;

/**
 * This class represents a Data Transfer Object for Root.
 */

public class RootDTO {
    private int rootId;
    private String rootText;
    private String status;

    public RootDTO() {
        // Default constructor
    }
    
    public RootDTO(String rootText) {
        this.rootText = rootText;
    }

    public RootDTO(int rootId, String rootText) {
        this.rootId = rootId;
        this.rootText = rootText;
    }

    public RootDTO(int rootId, String rootText, String status) {
        this.rootId = rootId;
        this.rootText = rootText;
        this.status = status;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public String getRootText() {
        return rootText;
    }

    public void setRootText(String rootText) {
        this.rootText = rootText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public void setVerseId(int selected_verse_id) {
		// TODO Auto-generated method stub
		
	}
}
