package TO;

public class RootInfoDTO {

	int id;
	String bookTitle;
	String poemTitle;
	String misrah1;
	String misrah2;
	String status;
	
	
	
	public RootInfoDTO(int id, String bookTitle, String poemTitle, String misrah1, String misrah2, String status) {
		super();
		this.id = id;
		this.bookTitle = bookTitle;
		this.poemTitle = poemTitle;
		this.misrah1 = misrah1;
		this.misrah2 = misrah2;
		this.status = status;
	}

	public RootInfoDTO(String bookTitle, String poemTitle, String misrah1, String misrah2, String status) {
		super();
		this.bookTitle = bookTitle;
		this.poemTitle = poemTitle;
		this.misrah1 = misrah1;
		this.misrah2 = misrah2;
		this.status = status;
	}
	
	public RootInfoDTO(String bookTitle, String poemTitle, String misrah1, String misrah2) {
		super();
		this.bookTitle = bookTitle;
		this.poemTitle = poemTitle;
		this.misrah1 = misrah1;
		this.misrah2 = misrah2;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RootInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getPoemTitle() {
		return poemTitle;
	}

	public void setPoemTitle(String poemTitle) {
		this.poemTitle = poemTitle;
	}

	public String getMisrah1() {
		return misrah1;
	}

	public void setMisrah1(String misrah1) {
		this.misrah1 = misrah1;
	}

	public String getMisrah2() {
		return misrah2;
	}

	public void setMisrah2(String misrah2) {
		this.misrah2 = misrah2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
