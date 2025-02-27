package project1;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Librarian {
	private String password;
    private Scanner scanner;
    
    public Librarian(String password) {
	    this.password = password;
	    this.scanner = new Scanner(System.in);
    }

    public boolean authenticate() {
        System.out.print("Enter librarian password: ");
        String inputPassword = scanner.nextLine();
        return inputPassword.equals(this.password);
    }
    
 // Add a new book to the system
    public void addBook(List<Book> books, Scanner input) throws IOException {
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
    }

    // Remove a book from the system
    public void removeBook(List<Book> books, Scanner input) throws IOException {
        System.out.print("Enter Book ID to remove: ");
        int bookId = Integer.parseInt(input.nextLine());

        Iterator<Book> iterator = books.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == bookId) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Book removed successfully.");
            Database.saveBooks(books);
        } else {
            System.out.println("Book ID not found.");
        }
    }

    // Update book details
    public void updateBook(List<Book> books, Scanner input) throws IOException {
        System.out.print("Enter Book ID to update: ");
        int bookId = Integer.parseInt(input.nextLine());

        for (Book book : books) {
            if (book.getId() == bookId) {
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
    }
}
