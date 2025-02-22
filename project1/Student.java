package project1;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private int id;
	private String name;
	private int grade;
	private String email;
	private List<Integer> borrowed_books;
	
	public Student(int id, String name, int grade, String email) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.email = email;
        this.borrowed_books = new ArrayList<>();
    }
	
	public int getId() {
		return id;
	}
	
    public String getName() {
    	return name;
    }
    
    public int getGrade() {
    	return grade;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public List<Integer> getBorrowedBooks() {
    	return borrowed_books;
    }

    public void borrowBook(int bookId) { 
        if (borrowed_books.size() < 3) 
        	borrowed_books.add(bookId);
    }

    public void returnBook(int bookId) {
    	borrowed_books.remove(Integer.valueOf(bookId));
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + grade + ", " + email + ", Borrowed: " + borrowed_books.size();
    }
}
