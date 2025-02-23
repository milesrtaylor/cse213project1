package project1;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
	private static String book_file = "Books_List.csv";
    private static String student_file = "Students_List.csv";
    private static String transaction_file = "Borrow_Records.csv";
    
    public static List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(book_file))) {
        	br.readLine(); // Skip the first line (header)
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                boolean availability = data[5].trim().equalsIgnoreCase("Available"); // maps "Available" to true and "Borrowed" to false
                books.add(new Book(Integer.parseInt(data[0]), data[1], data[2],
                		data[3], data[4], availability));
            }
        }
        return books;
    }
    
    public static List<Student> loadStudents() throws IOException {
    	List<Student> students = new ArrayList<>();
    	try (BufferedReader br = new BufferedReader(new FileReader(student_file))) {
    		br.readLine(); // Skip the first line (header)
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                students.add(new Student(Integer.parseInt(data[0]), data[1],
                		Integer.parseInt(data[2]), data[3]));
            }
        }
        return students;
    }
    
    public static List<Transaction> loadTransactions() throws IOException {
    	List<Transaction> transactions = new ArrayList<>();
    	int max_id = 300;
    	try (BufferedReader br = new BufferedReader(new FileReader(transaction_file))) {
    		br.readLine(); // Skip the first line (header)
    		String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Check if return date exists (to prevent IndexOutOfBoundsException)
                LocalDate returnDate = (data.length > 5 && !data[5].trim().equalsIgnoreCase("NULL")
                		&& !data[5].trim().isEmpty()) 
                                        ? LocalDate.parse(data[5].trim()) 
                                        : null; // Set returnDate to null if missing
                transactions.add(new Transaction(Integer.parseInt(data[0]),
                		Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                		LocalDate.parse(data[3]), LocalDate.parse(data[4]),
                		returnDate));
                if (Integer.parseInt(data[0]) > max_id) { // Track highest transaction ID
                    max_id = Integer.parseInt(data[0]);
                }
            }
    	}
    	Transaction.setNextId(max_id + 1); // Update next available ID
    	return transactions;
    }
    
    public static void saveBooks(List<Book> books) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(book_file))) {
            for (Book book : books) {
                bw.write(book.toString());
                bw.newLine();
            }
        }
    }
}
