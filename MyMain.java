package project1;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyMain {

	public static void main(String[] args) {
		// TODO might want to put all file i/o in another class, also need to go thru students and transactions
		File book_list = new File("Books_List.csv");
		File student_list = new File("Students_List.csv");
		File transaction_list = new File("Borrow_Records.csv");
		
		List<Book> books = new ArrayList<>();
		try (Scanner scanner = new Scanner(book_list)) {
            String line;
            // TODO need to skip first line
            while ((line = scanner.nextLine()) != null) {
                String[] data = line.split(",");
                books.add(new Book(Integer.parseInt(data[0]), data[1],
                		data[2], data[3], data[4], Boolean.parseBoolean(data[5])));
            }
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
			e.printStackTrace();
		}
		
		while (true) {
			Scanner input = new Scanner(System.in);
            System.out.println("Library System:");
            System.out.println("1. Search Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. View All Books");
            System.out.println("5. Enter Librarian Password");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();
            input.nextLine(); 
            
            switch (choice) {
                case 1: 
                	System.out.print("Enter title, author, or category: ");
        	        String query = input.nextLine().toLowerCase();
        	        for (Book book : books) { // might be better to have this as a function in Book.java, likewise with the other cases
        	            if (book.getTitle().toLowerCase().contains(query)
        	            		|| book.getAuthor().toLowerCase().contains(query)
        	            		|| book.getCategory().toLowerCase().contains(query))
        	                System.out.println(book);
        	        }
                	break;
                case 2:
                	System.out.print("Enter Book ID to borrow: ");
        	        int borrow_id = input.nextInt();
        	        for (Book book : books) {
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
        	        for (Book book : books) {
        	            if (book.getId() == return_id && !book.isAvailable()) {
        	                book.setAvailability(true);
        	                System.out.println("Book returned successfully.");
        	                break; // test
        	            }
        	        }
        	        System.out.println("Invalid book ID.");
                	break;
                case 4: 
                	for (Book book : books) {
        	            System.out.println(book);
        	        }
                	break;
                case 5:
                	String password = input.nextLine();
                	Librarian x = new Librarian(password); // TODO: need incorrect password case too
                	x.showMenu(); // move to main?
                	break;
                case 6: 
                	System.out.println("Exiting..."); 
                default: 
                	System.out.println("Invalid choice!");
            }
            
            input.close();
		}
	}
}
