package project1;

import java.io.*;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MyMain {
    public static void main(String[] args) {
    	try {
    	Scanner input = new Scanner(System.in);
        List<Book> books = Database.loadBooks();
        List<Student> students = Database.loadStudents();
        List<Transaction> transactions = Database.loadTransactions();

        while (true) {
            System.out.println("\nWelcome to the Socorro High School Library System");
            System.out.println("1. Student Mode");
            System.out.println("2. Librarian Mode");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice;
            try {
            	choice = input.nextInt();
            } catch (InputMismatchException e) {
            	choice = 0;
            }
            input.nextLine();

            switch (choice) {
                case 1:
                    Student currentStudent = null;
                    try {
                        System.out.print("Enter your Student ID: ");
                        int student_id = Integer.parseInt(input.nextLine().trim());

                        for (Student student : students) {
                            if (student.getId() == student_id) {
                                currentStudent = student;
                                break;
                            }
                        }

                        if (currentStudent == null) {
                            System.out.println("Invalid Student ID, access denied.");
                            break;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric Student ID.");
                        break;
                    }

                    System.out.println("\nStudent Library System:");
                    while (true) { // Student menu loop
                        System.out.println("1. Search Book");
                        System.out.println("2. Borrow Book");
                        System.out.println("3. Return Book");
                        System.out.println("4. View All Books");
                        System.out.println("5. Back to Main Menu");
                        System.out.print("Choose an option: ");
                        int student_choice = input.nextInt();
                        input.nextLine();

                        switch (student_choice) {
                            case 1:
                                System.out.print("Enter title, author, or category: ");
                                String query = input.nextLine().toLowerCase();
                                for (Book book : books) {
                                    if (book.getTitle().toLowerCase().contains(query)
                                            || book.getAuthor().toLowerCase().contains(query)
                                            || book.getCategory().toLowerCase().contains(query)) {
                                        System.out.println(book);
                                    }
                                }
                                break;
                            case 2:
                                if (currentStudent.getBorrowedBooks().size() >= 3) {
                                    System.out.println("You cannot borrow more than 3 books.");
                                    break;
                                }
                                System.out.print("Enter Book ID to borrow: ");
                                int borrow_id = input.nextInt();
                                input.nextLine();
                                boolean borrowed = false;
                                for (Book book : books) {
                                    if (book.getId() == borrow_id && book.isAvailable()) {
                                        book.setAvailability(false);
                                        int transaction_id = Transaction.getNextBorrowId();
                                        LocalDate current_date = LocalDate.now();
                                        LocalDate return_date = null;
                                        transactions.add(new Transaction(transaction_id,
                                        		currentStudent.getId(), book.getId(),current_date,
                                        		current_date.plusWeeks(2), return_date));
                                        currentStudent.getBorrowedBooks().add(book.getId());
                                        currentStudent.borrowBook(book.getId());
                                        System.out.println("Book borrowed successfully.");
                                        borrowed = true;
                                        break;
                                    }
                                }

                                if (!borrowed) {
                                    System.out.println("Book unavailable or ID invalid.");
                                }
                                break;
                            case 3:
                                System.out.print("Enter Book ID to return: ");
                                int return_id = input.nextInt();
                                input.nextLine();
                                boolean returned = false;

                                for (Book book : books) {
                                    if (book.getId() == return_id && !book.isAvailable()) {
                                        book.setAvailability(true);
                                        final Student studentRef = currentStudent; // Need a final reference for lambda expression
                                        transactions.removeIf(t -> t.getBookId() == return_id
                                        		&& t.getStudentId() == studentRef.getId());
                                        currentStudent.returnBook(book.getId());
                                        System.out.println("Book returned successfully.");
                                        returned = true;
                                        break;
                                    }
                                }

                                if (!returned) {
                                    System.out.println("Invalid book ID or book was not borrowed.");
                                }
                                break;
                            case 4:
                                for (Book book : books) {
                                    System.out.println(book);
                                }
                                break;
                            case 5:
                                System.out.println("Returning to main menu...");
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                        
                        if (student_choice == 5) {
                            break;
                        }
                    }
                    break;

                case 2:
                	Librarian librarian = new Librarian("admin123");
                	if (!librarian.authenticate()) {
                        System.out.println("Incorrect password, access denied.");
                        return;
                    }
                    
                    while (true) {
                    	System.out.println("\nLibrarian Menu:");
                        System.out.println("1. List all books");
                        System.out.println("2. List borrowed books");
                        System.out.println("3. List overdue books");
                        System.out.println("4. Add a book");
                        System.out.println("5. Remove a book");
                        System.out.println("6. Update a book");
                        System.out.println("7. Exit");
                        System.out.print("Choose an option: ");
                        
                        int librarian_choice = input.nextInt();
                        input.nextLine(); 

                        switch (librarian_choice) {
                            case 1:
                            	System.out.println("\nAll Books:");
                                for (Book book : books) {
                                    System.out.println(book);
                                }
                            	break;
                            case 2:
                            	System.out.println("\nBorrowed Books:");
                                for (Transaction transaction : transactions) {
                                    if (transaction.getReturnDate() == null) { // Book not returned
                                        System.out.println("Book ID: " + transaction.getBookId()
                                        + ", Borrowed by Student ID: " + transaction.getStudentId());
                                    }
                                }
                            	break;
                            case 3:
                            	System.out.println("\nOverdue Books:");
                                LocalDate today = LocalDate.now();
                                for (Transaction transaction : transactions) {
                                    if (transaction.getReturnDate() == null
                                    		&& today.isAfter(transaction.getDueDate())) {
                                        System.out.println("Book ID: " + transaction.getBookId() + 
                                        " is overdue. Borrowed by Student ID: " + transaction.getStudentId());
                                    }
                                }
                            	break;
                            case 4:
                            	System.out.print("Enter Book Title: ");
                                String title = input.nextLine();
                                System.out.print("Enter Author: ");
                                String author = input.nextLine();
                                System.out.print("Enter ISBN: ");
                                String isbn = input.nextLine();
                                System.out.print("Enter Category: ");
                                String category = input.nextLine();
                                
                                int newId = books.size() + 101;
                                Book newBook = new Book(newId, title, author, isbn, category, true);
                                books.add(newBook);
                                System.out.println("Book added successfully.");

                                Database.saveBooks(books); // Save updated book list to file
                                break;
                            case 5:
                            	System.out.print("Enter Book ID to remove: ");
                                int bookId = Integer.parseInt(input.nextLine());

                                boolean removed = books.removeIf(book -> book.getId() == bookId);

                                if (removed) {
                                    Database.saveBooks(books);
                                    System.out.println("Book removed successfully.");
                                } else
                                    System.out.println("Book ID not found.");
                                break;
                            case 6:
                            	System.out.print("Enter Book ID to update: ");
                                int updateId = Integer.parseInt(input.nextLine());

                                for (Book book : books) {
                                    if (book.getId() == updateId) {
                                        System.out.println("Updating book: " + book);
                                        System.out.print("Enter new Title (leave blank to keep current): ");
                                        String newTitle = input.nextLine();
                                        if (!newTitle.isEmpty()) book.setTitle(newTitle);

                                        System.out.print("Enter new Author (leave blank to keep current): ");
                                        String newAuthor = input.nextLine();
                                        if (!newAuthor.isEmpty()) book.setAuthor(newAuthor);

                                        System.out.print("Enter new ISBN (leave blank to keep current): ");
                                        String newIsbn = input.nextLine();
                                        if (!newIsbn.isEmpty()) book.setIsbn(newIsbn);

                                        System.out.print("Enter new Category (leave blank to keep current): ");
                                        String newCategory = input.nextLine();
                                        if (!newCategory.isEmpty()) book.setCategory(newCategory);

                                        Database.saveBooks(books);
                                        System.out.println("Book updated successfully.");
                                        return;
                                    }
                                }

                                System.out.println("Book ID not found.");
                                break;
                            case 7:
                            	System.out.println("Exiting librarian menu...");
                            	break;
                            default:
                            	System.out.println("Invalid choice, try again.");
                        }
                        if (librarian_choice == 7) {
                            break;
                        }
                    }

                case 3:
                    System.out.println("Exiting system...");
                    input.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    	}
    	catch (IOException e) { 
    		System.out.println("Error loading database files: " + e.getMessage());
    	}
    }
}
