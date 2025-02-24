package project1;

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

}
