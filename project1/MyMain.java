package project1;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyMain {

	public static void main(String[] args) {
		while (true) {
			Scanner input = new Scanner(System.in);
			List<Book> books = Database.loadBooks();
			List<Student> students = Database.loadStudents();
			List<Transaction> transactions = Database.loadTransactions();
			
			System.out.println("Welcome to the Socorro High School Library System");
            System.out.println("1. Student Mode");
            System.out.println("2. Librarian Mode");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
            	case 1:
	                System.out.print("Enter your Student ID: ");
	                int student_id = input.nextInt();
	                input.nextLine();
	                
	                boolean valid_id = false;
	                for (Student student : students) {
	                	if (student.getId() == student_id) {
	                			valid_id = true;
	                			// save student
	                	}
	                }
	                if (valid_id) {
	                    // everything else...
	                	System.out.println("Student Library System:");
	                    System.out.println("1. Search Book");
	                    System.out.println("2. Borrow Book");
	                    System.out.println("3. Return Book");
	                    System.out.println("4. View All Books");
	                    System.out.println("5. Enter Librarian Password");
	                    System.out.println("6. Exit");
	                    System.out.print("Choose an option: ");
	                    int student_choice = input.nextInt();
	                    input.nextLine(); 
	                    
	                    switch (student_choice) {
	                        case 1: 
	                        	System.out.print("Enter title, author, or category: ");
	                	        String query = input.nextLine().toLowerCase();
	                	        for (Book book : Database.loadBooks()) { // might be better to have this as a function in Book.java, likewise with the other cases
	                	            if (book.getTitle().toLowerCase().contains(query)
	                	            		|| book.getAuthor().toLowerCase().contains(query)
	                	            		|| book.getCategory().toLowerCase().contains(query))
	                	                System.out.println(book);
	                	        }
	                        	break;
	                        case 2:
	                        	System.out.print("Enter Book ID to borrow: ");
	                	        int borrow_id = input.nextInt();
	                	        for (Book book : Database.loadBooks()) {
	                	            if (book.getId() == borrow_id && book.isAvailable()) {
	                	                book.setAvailability(false);
	                	                System.out.println("Book borrowed successfully.");
	                	                break; // test
	                	            }
	                	        }
	                	        System.out.println("Book unavailable.");
	                        	break;
	                        case 3: 
	                        	System.out.print("Enter Book ID to return: ");
	                	        int return_id = input.nextInt();
	                	        for (Book book : Database.loadBooks()) {
	                	            if (book.getId() == return_id && !book.isAvailable()) {
	                	                book.setAvailability(true);
	                	                System.out.println("Book returned successfully.");
	                	                break; // test
	                	            }
	                	        }
	                	        System.out.println("Invalid book ID.");
	                        	break;
	                        case 4: 
	                        	for (Book book : Database.loadBooks()) {
	                	            System.out.println(book);
	                	        }
	                        	break;
	                        case 5:
	                        	String password = input.nextLine();
	                        	// TODO
	                        	break;
	                        case 6: 
	                        	System.out.println("Exiting..."); 
	                        default: 
	                        	System.out.println("Invalid choice.");
	                    }
	                }
	                else {
	                    System.out.println("Invalid Student ID, access denied.");
	                }
	                break;
            	case 2:
	                Librarian librarian = new Librarian("admin123"); // Set password here
	                librarian.showMenu();
	                break;
	           	default:
	                System.out.println("Invalid choice, exiting..."); // TODO probably change to while loop
            }
            
            input.close();
		}
	}
}
