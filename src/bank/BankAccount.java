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

	BankAccount(String username, int accNum, double balance, String fname, String lName, String message) {
		this.username = username;
		this.accountNum = accNum;
		this.balance = balance;
		this.firstName = fname;
		this.lastName = lName;
		this.balance = 0;
		this.message = message;
	}

	private double roundDecimals(double num) {
		num = Math.round(num * 100.0) / 100.0;
		if(num < .5) {
			return -1.0;
		} else {
			return num;
		}
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

	public boolean isLegalAmount(String amount) {
		try {
			if (Double.valueOf(amount) >= 0.5)
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
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

		if (amount <= balance && balance > 0) {
			message += DIVIDER + "Transfer To: " + otherAcc.getAccountNum() + "\n";
			otherAcc.message += DIVIDER + "Transfer From: " + this.accountNum + "\n";
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
		if (pswd != password) {
			return false;
		} else {
			if (balance >= amount && balance > 0) {
				message += DIVIDER + "Transfer From: " + otherAcc.getAccountNum() + "\n";
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
	}

	boolean changePassword(String currentPassword, String newPassword) {
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
