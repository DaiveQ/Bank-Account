package bank;

import java.text.DecimalFormat;
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

	// a (bad) way to align new line text with the
	// time stamp when viewing message
	private final String DIVIDER = "\n\t     ";

	// date format for use with messages for timestamps
	SimpleDateFormat datef = new SimpleDateFormat("dd/MM/YYYY HH:mm");

	DecimalFormat df = new DecimalFormat("#0. 00");

	BankAccount(String uName, double amt, String fName, String lName, int accNum) {
		this.username = uName;
		this.balance = amt;
		this.firstName = fName;
		this.lastName = lName;
		this.accountNum = accNum;
		this.password = setPassword();
	}

	BankAccount(String uName, double amt, String fName, String lName, int accNum, String password) {
		this.username = uName;
		this.balance = amt;
		this.firstName = fName;
		this.lastName = lName;
		this.accountNum = accNum;
		this.password = password;
	}

	BankAccount(String uName, String fname, String lName, int accNum) {
		this.username = uName;
		this.accountNum = accNum;
		this.firstName = fname;
		this.lastName = lName;
		this.balance = 0;
	}

	private double roundDecimals(double num) {
		return (Math.round(num * 100.0) / 100.0);
	}

	private String getTime() {
		// change to server side time once server is made

		Date date = new Date();
		return datef.format(date) + ": ";
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getUsername() {
		return this.username;
	}

	public int getAccountNum() {
		return accountNum;
	}

	public String getMessage() {
		return message;
	}

	public String getBalance() {
		return df.format(this.balance);
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

	boolean deposit(double amount) {
		amount = roundDecimals(amount);
		if (amount > 0) {
			this.balance += amount;
			message += getTime() + "A deposit of $" + df.format(amount) + " made.\n";
			return true;
		} else {
			message += getTime() + "Deposit failed - Invalid amount\n";
			return false;
		}

	}

	boolean withdraw(double amount) {
		amount = roundDecimals(amount);
		if (amount < 0) {
			message += getTime() + "Withdrawl failed - Invalid amount\n";
			return false;
		} else if (balance - amount < -1000) {
			message += getTime() + "Withdrawl of $" + df.format(amount) + "failed. Overdraft limit reached\n";
			return false;
		} else if (amount > balance) {
			this.balance -= amount + 25.0;
			message += getTime() + "Withdrawl of $" + df.format(amount) + " made. Overdraft fee of $25 added\n";
			return true;
		} else {
			this.balance -= amount;
			message += getTime() + "Withdrawl of $" + df.format(amount) + " made.\n";
			return true;
		}
	}

	// transfers money from user to another account
	boolean transferTo(double amount, BankAccount otherAcc) {
		amount = roundDecimals(amount);

		if (amount >= balance && balance > 0) {
			message += DIVIDER + "Transfer To: " + otherAcc.getAccountNum();
			otherAcc.message += DIVIDER + "Transfer From: " + this.accountNum;
			otherAcc.deposit(amount);
			withdraw(amount);
			return true;

		} else {
			message += getTime() + "Overdraft during transfer occured. Transaction not processed.\n" + DIVIDER
					+ "Please contact us for more information. A fee of $25 was applied\n";
			return false;
		}
	}

	// transfers money from another account to the user
	boolean transferFrom(double amount, BankAccount otherAcc, String pswd) {
		amount = roundDecimals(amount);

		if (balance >= amount && balance > 0) {
			message += DIVIDER + "Transfer From: " + otherAcc.getAccountNum();
			otherAcc.message += DIVIDER + "Transfer To: " + this.accountNum;
			deposit(amount);
			otherAcc.withdraw(amount);
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

	//
	boolean resetPassword(String currentPassword, String newPassword) {
		if (checkPassword(currentPassword)) {
			password = newPassword;
			message += getTime() + "Password succesfully changed\n";
			return true;
		} else {
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
