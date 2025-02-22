package project1;

import java.time.LocalDate;

public class Transaction {
	private int borrow_id;
	private int student_id;
	private int book_id;
	private LocalDate borrow_date;
	private LocalDate due_date;
	private LocalDate return_date;

	public Transaction(int borrow_id, int student_id, int book_id, LocalDate borrow_date) {
        this.borrow_id = borrow_id;
        this.student_id = student_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
        this.due_date = borrow_date.plusWeeks(2);
        this.return_date = null;
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

    public void markReturned(LocalDate returnDate) { 
    	this.return_date = returnDate; 
    }

    public boolean isOverdue() {
        return return_date == null && LocalDate.now().isAfter(due_date);
    }

    @Override
    public String toString() {
        return borrow_id + ", " + student_id + ", " + book_id + ", " + borrow_date + ", " + due_date + ", " + (return_date == null ? "Not Returned" : return_date);
    }
}
