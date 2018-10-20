package bank;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BankAccount {

	private String firstName;
	private String lastName;
	private String password;
	private String message;
	private String username;
	private int accountNum;
	private double balance;
	
	SimpleDateFormat datef = new SimpleDateFormat("dd/MM/YYYY HH:mm a");
	
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
	
	
	private String getTimeStamp() {
		Date date = new Date();
		return datef.format(date) + ": ";
		
		//make it so it's used when a message is made
	}
	
	public String getUsername() {
		return this.username;
	}
	

	public int getAccountNum() {
		return accountNum;
	}

	private String setPassword() {
		String pswd = "";

		int i = 0;
		while (i < 3) {
			pswd += firstName.charAt(i);
			i++;
		}

		i = 1;
		while (i < 3) {
			pswd += firstName.charAt(firstName.length() - i);
			i++;
		}

		return pswd;
	}

	void setMessage(String mess) {
		message += mess;
	}

	public String getMessage() {
		return message;
	}

	boolean deposit(double amount) {
		if (amount > 0) {
			message += "A deposit of " + amount + "$ has been made.";
			return true;
		} else {
			message += "Depost unsucsessful - invalid amount\n";
			return false;
		}

	}

	boolean withdraw(double amount) {
		if (amount >= balance) {
			message += "A bank overdraft fee of 25$ has been added to your account. We thank you for your business.\n";
			return false;
		}

		else {
			message += "Withdrawel of " + amount + "$ has beem made.\n";
			return true;
		}
	}

	double getBalance() {
		return balance;
	}

	void display() {
		// shouldn't be printed out
		System.out.println("User account num: " + getAccountNum() + "\nUser full name: " + firstName + " " 
			+ lastName + "\nUser account balance: " + balance);
	}

	boolean transferTo(double amount, double otherAcct) {
		if (amount >= balance) {
			message += "Transfer of $" + amount + " to account " + otherAcct + " has been complete\n";
			otherAcct += amount;
			balance -= amount;
			return true;

		}

		else {
			message += "Overdraft during transfer occured. Transaction not processed. "
					+ "Please contact us for more information A fee of $25 was applied\n";
			return false;
		}
	}

	boolean transferFrom(double amount, double otherAcct, String pswd) {
		boolean check = checkPassword(pswd);
		if (balance >= amount && check == true) {
			message += "Transfer of $" + amount + " from account " + accountNum + " complete.\n";
			return true;
		}

		else if (amount > balance) {
			message += "Transfer was unsucsessful - an overdraft fee of $25 was applied\n";
			return false;
		}

		else if (pswd != password) {
			message += "Transfer was unsucsessful - please check password and try again\n";
			return false;
		}

		return false;
	}

	boolean resetPassword(String currentPassword, String newPassword) {
		if (currentPassword == password) {
			password = newPassword;
			message += "Password succesfully changed\n";
			return true;
		}

		else {
			message += "Password reset unsucsessful - please try again\n";
			return false;
		}
	}

	boolean checkPassword(String pswd) {
		if (pswd.equals(password))
			return true;
		return false;

	}

	void emptyAccount() {
		message += "So much for \"leaving something for a rainy day\"\n";
		balance = 0;
	}

}
