package project1;

public class Book {
	private int id;
	private String title;
	private String author;
	private String isbn;
	private String category;
	private boolean availability;
	
	public Book(int id, String title, String author, String isbn, String category, boolean availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.availability = availability;
    }
	
	public int getId() {
		return id;
	}
	
    public String getTitle() {
    	return title;
    }
    
    public void setTitle(String title) {
    	this.title=title;
    }
    
    public String getAuthor() {
    	return author;
    }
    
    public void setAuthor(String author) {
    	this.author=author;
    }
    
    public String getIsbn() {
    	return isbn;
    }
    
    public void setIsbn(String isbn) {
    	this.isbn=isbn;
    }
    
    public String getCategory() {
    	return category;
    }
    
    public void setCategory(String category) {
    	this.category=category;
    }
    
    public boolean isAvailable() {
    	return availability;
    }
    
    public void setAvailability(boolean availability) {
    	this.availability = availability;
    }
    
    @Override
    public String toString() {
        return id + ", " + title + ", " + author + ", " + isbn + ", " + category + ", " + (availability ? "Available" : "Borrowed");
    }
}
