package project1;

import java.time.LocalDate;

public class Transaction {
	private int borrow_id;
	private int student_id;
	private int book_id;
	private LocalDate borrow_date;
	private LocalDate due_date;
	private LocalDate return_date;
	private static int next_id;

	public Transaction(int borrow_id, int student_id, int book_id, LocalDate borrow_date, LocalDate due_date, LocalDate return_date) {
        this.borrow_id = borrow_id;
        this.student_id = student_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
    }

    public int getBorrowId() {
    	return borrow_id;
    }
    
    public int getStudentId() { 
    	return student_id;
    }
    public int getBookId() { 
    	return book_id; 
    }
    public LocalDate getBorrowDate() { 
    	return borrow_date; 
    }
    public LocalDate getDueDate() { 
    	return due_date; 
    }
    public LocalDate getReturnDate() { 
    	return return_date; 
    }

    public void markReturned(LocalDate return_date) { 
    	this.return_date = return_date; 
    }

    public boolean isOverdue() {
        return return_date == null && LocalDate.now().isAfter(due_date);
    }

    public static void setNextId(int id) {
        next_id = (id > 310) ? 301 : id; // Wrap around if needed
    }
    
    public static int getNextBorrowId() {
        if (next_id > 310) {
            next_id = 301; // Wrap back to 301 if exceeding 310
        }
        return next_id++;
    }
}
