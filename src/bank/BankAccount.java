package bank;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BankAccount {


    // basic variables
	private String firstName;
	private String lastName;
	private String password;
	private String message = "";
	private String username;
	private int accountNum;
	private double balance;
	
	// a (probably a bad) way to align new line text with the 
	// time stamp when viewing message
	private final String DIVIDER = "\n\t     ";
	
	// date format for use with messages for timestamps
	SimpleDateFormat datef = new SimpleDateFormat("dd/MM/YYYY HH:mm");
	
	BankAccount(String uName, double amt, String fName, String lName, int acctNum) {
		username = uName;
		balance = amt;
		firstName = fName;
		lastName = lName;
		password = setPassword();
	}
    
	BankAccount(String uName, String fname, String lName, int accNum) {
		username = uName;
		accountNum = accNum;
		firstName = fname;
		lastName = lName;
		balance = 0;

	}
    
	private String getTime() {
		// change to server side time once server is made
		
		Date date = new Date();
		return datef.format(date) + ": ";
	}

	public String getUsername() {
		return this.username;
	}

	public int getAccountNum() {
		return accountNum;
	}

	private String setPassword() {
		String pswd = "";
        
        // sets beginning to the first 3 characters of firstName
		for (int i = 0; i < 3; i++)
			pswd += firstName.charAt(i);

        // sets end to the last 2 characters of the firstName
		for (int i = 2; i > 0; i--)
			pswd += lastName.charAt(lastName.length() - i);
		return pswd;
	}

	public String getMessage() {
		return message;
	}
    
    // deposits
	boolean deposit(double amount) {
		if (amount > 0) {
			message += getTime() + "A deposit of " + amount + "$ has been made.\n";
			return true;
		} else {
			message += getTime() + "Depost unsucsessful - invalid amount\n";
			return false;
		}

	}

	boolean withdraw(double amount) {
		if (amount >= balance) {
			message += getTime() + "A bank overdraft fee of 25$ has been added to your account." + DIVIDER +  "We thank you for your business.\n";
			return false;
		}

		else {
			message += getTime() + "Withdrawel of " + amount + "$ has beem made.\n";
			return true;
		}
	}

	double getBalance() {
		return balance;
	}
    
    // displays account information
	void display() {
		// shouldn't be printed out
		System.out.println("User account num: " + getAccountNum() + "\nUser full name: " + firstName + " " + lastName
				+ "\nUser account balance: " + balance);
	}
    
    // transfers money from one BankAccount to another
	boolean transferTo(double amount, BankAccount otherAcc) {
		if (amount >= balance && balance > 0) {
			message += getTime() + "Transfer of $" + amount + " to account " + otherAcct + " has been complete\n";
			otherAcc.balance += amount;
			this.balance -= amount;
			return true;

		}
		else {
			message += getTime() + "Overdraft during transfer occured. Transaction not processed. "
					+ "Please contact us for more information. A fee of $25 was applied\n";
			return false;
		}
	}

	boolean transferFrom(double amount, double otherAcct, String pswd) {
		boolean check = checkPassword(pswd);
		if (balance >= amount) {
			message += getTime() + "Transfer of $" + amount + " from account " + accountNum + " complete.\n";
			return true;
		}

		else if (amount > balance) {
			message += getTime() + "Transfer was unsucsessful - an overdraft fee of $25 was applied\n";
			return false;
		}

		else if (pswd != password) {
			message += getTime() + "Transfer was unsucsessful - please check password and try again\n";
			return false;
		}

		return false;
	}
    
    // should be change password as it changes the password to whatever the user inputs
    // as long as the uesr inputed original password is correct
	boolean resetPassword(String currentPassword, String newPassword) {
		if (currentPassword == password) {
			password = newPassword;
			message += getTime() + "Password succesfully changed\n";
			return true;
		}

		else {
			message += getTime() + "Password reset unsucsessful - please try again\n";
			return false;
		}
	}

	boolean checkPassword(String pswd) {
		if (pswd.equals(password))
			return true;
		return false;

	}
    
    // empties account balance
	void emptyAccount() {
		message += getTime() + "So much for \"leaving something for a rainy day\"\n";
		balance = 0;
	}

}
