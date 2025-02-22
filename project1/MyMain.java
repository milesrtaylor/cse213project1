package project1;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MyMain {
    public static void main(String[] args) {
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
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter your Student ID: ");
                    int student_id = input.nextInt();
                    input.nextLine();

                    Student currentStudent = null;
                    for (Student student : students) {
                        if (student.getId() == student_id) {
                            currentStudent = student;
                            break;
                        }
                    }

                    if (currentStudent != null) {
                        System.out.println("\nStudent Library System:");
                        while (true) { // Student menu loop
                            System.out.println("1. Search Book");
                            System.out.println("2. Borrow Book");
                            System.out.println("3. Return Book");
                            System.out.println("4. View All Books");
                            System.out.println("5. Enter Librarian Password");
                            System.out.println("6. Back to Main Menu");
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
                                            transactions.removeIf(t -> t.getBookId() == return_id
                                            		&& t.getStudentId() == currentStudent.getId());
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
                                    System.out.print("Enter librarian password: ");
                                    String password = input.nextLine();
                                    Librarian librarian = new Librarian("admin123");
                                    if (librarian.checkPassword(password)) {
                                        librarian.showMenu();
                                    } else {
                                        System.out.println("Incorrect password.");
                                    }
                                    break;
                                case 6:
                                    System.out.println("Returning to main menu...");
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }

                            if (student_choice == 6) {
                                break;
                            }
                        }
                    } else {
                        System.out.println("Invalid Student ID, access denied.");
                    }
                    break;

                case 2:
                    System.out.print("Enter librarian password: ");
                    String librarianPassword = input.nextLine();
                    Librarian librarian = new Librarian("admin123");
                    if (librarian.checkPassword(librarianPassword)) {
                        librarian.showMenu();
                    } else {
                        System.out.println("Incorrect password.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting system...");
                    input.close();
                    return; // Exit the program

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
