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

    public void borrowBook(int book_id) { 
        if (borrowed_books.size() < 3) 
        	borrowed_books.add(book_id);
    }

    public void returnBook(int book_id) {
    	borrowed_books.remove(Integer.valueOf(book_id));
    }
}
