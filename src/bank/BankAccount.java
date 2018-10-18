package bank;

public class BankAccount {

	// members:

	private String firstName;
	private String middleName;
	private String lastName;
	private int accountNum;
	private String password;
	private String message;
	private double balance;

	BankAccount(double amt, String fName, String mName, String lName, int acctNum) {
		balance = amt;
		firstName = fName;
		middleName = mName;
		lastName = lName;
		password = setPassword();
	}

	BankAccount(String fname, String lName, int accNum) {
		accountNum = accNum;
		firstName = fname;
		middleName = "";
		lastName = lName;
		balance = 0;

	}

	private int getAccountNum() {
		return accountNum;
	}

	private void setBalance(double setbalance) {
		balance = setbalance;
	}

	private String getFirstName() {
		return firstName;
	}

	private void setFirstName(String fName) {
		firstName = fName;
	}

	private String getMiddleName() {
		return middleName;
	}

	private void setMiddleName(String mName) {
		middleName = mName;
	}

	private String getLastName() {
		return lastName;
	}

	private void setLastName(String lName) {
		lastName = lName;
	}

	private String getPassword() {
		return password;
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

	// methods:
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
		System.out.println("User account numb: " + getAccountNum() + "\nUser full name: " + firstName + " " + middleName
				+ " " + lastName + "\nUser account balance: " + balance);
	}

	boolean transferTo(double amount, double otherAcct) {
		if (amount >= balance) {
			message += "Transfer of $" + amount + " to account " + otherAcct + " has been complete\n";
			otherAcct += amount;
			balance -= amount;
			return true;

		}

		else {
			message += "Overdraft during transfer occured. Transaction not processed. Please contact us for more information A fee of $25 was applied\n";
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

/*
 * public static void main (String [] args) { Scanner sc = new
 * Scanner(System.in); System.out.println("hi");
 * 
 * BankAccount bankAccount = new BankAccount(); bankAccount bank =
 * bankAccount.new bankAccount();
 * 
 * 
 * String fName = bank.getFirstName(); String mName = bank.getMiddleName();
 * String lName = bank.getLastName(); doZAuble balence = bank.getBalance();
 * String password = bank.getPassword();
 * 
 * 
 * System.out.println("Hello " + fName + " " + mName + " " + lName +
 * " your current bank account balence is " + balence);
 * System.out.println(password);
 * 
 * bank.display(); bank.emptyAccount();
 * 
 * double amount = 0; double otherAcct = 54321; String pswd = password ; boolean
 * check = bank.transferFrom(amount, otherAcct, pswd); String mess =
 * bank.getMessage(); System.out.println(mess); System.out.println(check);
 * 
 * 
 * }
 */
