package project1;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Librarian {
	private String password;
	private List<Book> books;
    private List<Transaction> transactions;
    private Scanner scanner;
    
    public Librarian(String password) {
        this.password = password;
        this.books = Database.loadBooks();
        this.transactions = Database.loadTransactions();
        this.scanner = new Scanner(System.in);
    }

    public boolean authenticate() {
        System.out.print("Enter librarian password: ");
        String inputPassword = scanner.nextLine();
        return inputPassword.equals(this.password);
    }
    
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Following methods do not directly operate on the librarian, probably should be in main instead
    public void showMenu() {
        if (!authenticate()) {
            System.out.println("Incorrect password! Access denied.");
            return;
        }
        
        while (true) {
            System.out.println("\nLibrarian Menu:");
            System.out.println("1. List all books");
            System.out.println("2. List borrowed books");
            System.out.println("3. List overdue books");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                	listAllBooks();
                	break;
                case 2:
                	listBorrowedBooks();
                	break;
                case 3:
                	listOverdueBooks();
                	break;
                case 4:
                	System.out.println("Exiting librarian menu...");
                	return;
                default:
                	System.out.println("Invalid choice, try again.");
            }
        }
    }
    
    private void listAllBooks() {
        System.out.println("\nAll Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void listBorrowedBooks() {
        System.out.println("\nBorrowed Books:");
        for (Transaction transaction : transactions) {
            if (transaction.getReturnDate() == null) { // Book not returned
                System.out.println("Book ID: " + transaction.getBookId() + ", Borrowed by Student ID: " + transaction.getStudentId());
            }
        }
    }

    private void listOverdueBooks() {
        System.out.println("\nOverdue Books:");
        LocalDate today = LocalDate.now();
        for (Transaction transaction : transactions) {
            if (transaction.getReturnDate() == null && today.isAfter(transaction.getDueDate())) {
                System.out.println("Book ID: " + transaction.getBookId() + " is overdue. Borrowed by Student ID: " + transaction.getStudentId());
            }
        }
    }
}
