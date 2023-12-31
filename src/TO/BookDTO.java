package TO;

/**
 * This class represents a Data Transfer Object for Book.
 */


public class BookDTO {
    private int bookId;
    private int serialNo = -1; // changed on 3rd iteration
    private String bookTitle;
    private String bookAuthor;
    private int authorDeathYear = -1;

    public BookDTO() {
        // Default constructor
    }

    public BookDTO(int bookId, int serialNo, String bookTitle, String bookAuthor, int authorDeathYear) {
        this.bookId = bookId;
        this.serialNo = serialNo;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.authorDeathYear = authorDeathYear;
    }
    
    public BookDTO(int serialNo, String bookTitle, String bookAuthor, int authorDeathYear) {
        this.serialNo = serialNo;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.authorDeathYear = authorDeathYear;
    }
    
    public BookDTO(String bookTitle, String bookAuthor, int authorDeathYear) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.authorDeathYear = authorDeathYear;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getAuthorDeathYear() {
        return authorDeathYear;
    }

    public void setAuthorDeathYear(int authorDeathYear) {
        this.authorDeathYear = authorDeathYear;
    }
}
