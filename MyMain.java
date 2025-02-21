package project1;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyMain {

	public static void main(String[] args) {
		File book_list = new File("Books_List.csv");
		File student_list = new File("Students_List.csv");
		File transaction_list = new File("Borrow_Records.csv");
		
		List<Book> books = new ArrayList<>();
		try (Scanner scanner = new Scanner(book_list)) {
            String line;
            while ((line = scanner.nextLine()) != null) {
                String[] data = line.split(",");
                books.add(new Book(Integer.parseInt(data[0]), data[1],
                		data[2], data[3], data[4], Boolean.parseBoolean(data[5])));
            }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true) {
			Scanner s1;
            System.out.println("Library System:");
            System.out.println("1. Search Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. View All Books");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = s1.nextInt();
            s1.nextLine(); 
		}
	}
