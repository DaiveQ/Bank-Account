package bank;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GUI {

	// Also give functionality to Transfer To/From

	private JFrame frmBankAccount;
	CardLayout layout = new CardLayout(0, 0);

	private static List<BankAccount> ba = new ArrayList<BankAccount>();
	private int index = 0; // index of BankAccount. Shortened for simplicity
	private int otherIndex = 0;

	final static String CHOICE_PANEL = "Choice Panel";
	final static String WITHDRAW_PANEL = "Withdraw Panel";
	final static String DEPOSIT_PANEL = "Deposit Panel";
	final static String ACCOUNTDETAIL_PANEL = "AccountDetail Panel";
	final static String GETPASSWORD_PANEL = "GetPassword Panel";
	final static String EMPTYACCOUNT_PANEL = "EmptyAccount Panel";
	final static String MESSAGES_PANEL = "Messages Panel";
	final static String RESETPASSWORD_PANEL = "ResetPassword Panel";
	final static String GETBALANCE_PANEL = "GetBalance Panel";
	final static String TRANSFERTO_PANEL = "TransferTo Panel";
	final static String TRANSFERFROM_PANEL = "TransferFrom Panel";
	final static String REGISTER_PANEL = "Register Panel";
	final static String LOGIN_PANEL = "Login Panel";
	private static final double DEFAULT_MONEY_VALUE = 1234.56;

	private JTextField loginTxtUsername;
	private JPasswordField loginPassword;
	private JTextField withdrawTxtAmount;
	private JTextField depositTxtAmount;
	private JTextField transToTxtAmount;
	private JTextField transToTxtOtherAccount;
	private JPasswordField transFromPassword;
	private JTextField transFromTxtOtherAccount;
	private JTextField transFromTxtAmount;
	private JTextField regTxtFirstName;
	private JTextField regTxtLastName;
	private JPasswordField regPassword;
	private JPasswordField regPasswordRepeat;
	private JTextField regTxtUsername;
	private JButton mainBtnWithdraw;

	public static void main(String[] args) {
		createTestAccount();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmBankAccount.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public GUI() {
		initialize();
	}

	private void setBankAccountIndex(String username) {
		for (int i = 0; i < ba.size(); i++) {
			if (ba.get(i).getUsername().equals(username)) {
				this.index = i;
				return;
			}
		}
	}

	private boolean setOtherAccountIndex(int accNum) {
		for (int i = 0; i < ba.size(); i++) {
			if (ba.get(i).getAccountNum() == accNum) {
				this.otherIndex = i;
				return true;
			}
		}
		return false;
	}

	private boolean isPasswordSecure(String pass) {

		///////////////////////////////////////////////////////
		// TESTING IF STATEMENT. PASSOWRDS STARTING WITH 12345
		// WILL BE PASSED NO MATTER WHAT. DELETE THIS WHEN DONE
		///////////////////////////////////////////////////////
		if (pass.length() >= 5) {
			if (pass.substring(0, 5).equals("12345"))
				return true;
		} else if (pass.equals("1"))
			;
		///////////////////////////////////////////////////////

		if (pass.toLowerCase().equals(pass))
			return false;
		else if (pass.toUpperCase().equals(pass))
			return false;
		else if (pass.length() < 9)
			return false;
		// else if password has number
		else
			return true;
	}

	private static int generateAccNum() {
		boolean taken = true;
		int accNum = 0;

		while (taken) {

			String accComb = "";

			// generates account number
			Random rand = new Random();
			int x[] = new int[9];
			for (int i = 0; i < 9; i++)
				x[i] = rand.nextInt(9);

			for (int i : x) {
				accComb += String.valueOf(x[i]);
			}

			// checks if the generated number is unique
			if (checkForUniqueAccNum(Integer.valueOf(accComb))) {
				accNum = Integer.valueOf(accComb);
				taken = false;
			}

		}
		return Integer.valueOf(accNum);
	}

	private static boolean checkForUniqueAccNum(int accNum) {
		for (BankAccount element : ba) {
			if (element.getAccountNum() == accNum)
				return false;
		}
		return true;
	}

	private static boolean createAcc(String username, double amt, String fName, String lName, int acctNum,
			String password) {

		// checks if the username is already taken
		for (BankAccount element : ba) {
			if (element.getUsername().equals(username))
				return false;
		}

		// adds account
		ba.add(new BankAccount(username, amt, fName, lName, acctNum, password));
		return true;
	}

	private static void createTestAccount() {
		ba.add(new BankAccount("12345", 123456.78, "123", "45", 1));
		ba.add(new BankAccount("Ricky", 1234.56, "Ricky", "Lin", 2));
		ba.add(new BankAccount("1", 12.34, "John", "Doe", 3, "1"));
	}

	private void initialize() {

		frmBankAccount = new JFrame();
		frmBankAccount.setTitle("Bank Account");
		frmBankAccount.setAlwaysOnTop(true);
		frmBankAccount.setBounds(100, 100, 450, 300);
		frmBankAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBankAccount.getContentPane().setLayout(layout);

		JPanel loginJPanel = new JPanel();
		frmBankAccount.getContentPane().add(loginJPanel, LOGIN_PANEL);
		GridBagLayout gbl_loginJPanel = new GridBagLayout();
		gbl_loginJPanel.columnWidths = new int[] { 75, 74, 107, 0 };
		gbl_loginJPanel.rowHeights = new int[] { 91, 21, 20, 23, 0, 0, 0 };
		gbl_loginJPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_loginJPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		loginJPanel.setLayout(gbl_loginJPanel);

		JLabel loginLblUsername = new JLabel("Username:");
		GridBagConstraints gbc_loginLblUsername = new GridBagConstraints();
		gbc_loginLblUsername.fill = GridBagConstraints.BOTH;
		gbc_loginLblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_loginLblUsername.gridx = 1;
		gbc_loginLblUsername.gridy = 1;
		loginJPanel.add(loginLblUsername, gbc_loginLblUsername);

		loginTxtUsername = new JTextField();
		GridBagConstraints gbc_loginTxtUsername = new GridBagConstraints();
		gbc_loginTxtUsername.anchor = GridBagConstraints.NORTH;
		gbc_loginTxtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginTxtUsername.insets = new Insets(0, 0, 5, 0);
		gbc_loginTxtUsername.gridx = 2;
		gbc_loginTxtUsername.gridy = 1;
		loginJPanel.add(loginTxtUsername, gbc_loginTxtUsername);
		loginTxtUsername.setColumns(10);

		JLabel loginLblPassword = new JLabel("Password:");
		GridBagConstraints gbc_loginLblPassword = new GridBagConstraints();
		gbc_loginLblPassword.anchor = GridBagConstraints.NORTH;
		gbc_loginLblPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginLblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_loginLblPassword.gridx = 1;
		gbc_loginLblPassword.gridy = 2;
		loginJPanel.add(loginLblPassword, gbc_loginLblPassword);

		loginPassword = new JPasswordField();
		GridBagConstraints gbc_loginPassword = new GridBagConstraints();
		gbc_loginPassword.anchor = GridBagConstraints.NORTH;
		gbc_loginPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginPassword.insets = new Insets(0, 0, 5, 0);
		gbc_loginPassword.gridx = 2;
		gbc_loginPassword.gridy = 2;
		loginJPanel.add(loginPassword, gbc_loginPassword);

		JButton loginBtnEnter = new JButton("Enter");

		// checks for username and password. Continues accordingly
		loginBtnEnter.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				setBankAccountIndex(loginTxtUsername.getText());

				if (ba.get(index).checkPassword(loginPassword.getText())) {
					layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
				} else {
					JOptionPane.showMessageDialog(null, "Wrong Password or Username. Please try again.", "Login Error",
							JOptionPane.ERROR_MESSAGE);
				}

				// resets the password field
				loginPassword.setText("");
			}
		});

		// makes the username text field press the enter button when
		// the enter key is pressed
		loginTxtUsername.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBtnEnter.doClick();
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		// makes the username text field click the enter button when the enter key is
		// pressed
		loginPassword.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBtnEnter.doClick();
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		GridBagConstraints gbc_loginBtnEnter = new GridBagConstraints();
		gbc_loginBtnEnter.insets = new Insets(0, 0, 5, 0);
		gbc_loginBtnEnter.anchor = GridBagConstraints.NORTH;
		gbc_loginBtnEnter.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginBtnEnter.gridx = 2;
		gbc_loginBtnEnter.gridy = 3;
		loginJPanel.add(loginBtnEnter, gbc_loginBtnEnter);

		// displays the register panel when the register button is pressed on the login
		// panel
		JButton loginBtnRegister = new JButton("Create A New Account");
		loginBtnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				loginTxtUsername.setText("");
				loginPassword.setText("");

				layout.show(frmBankAccount.getContentPane(), REGISTER_PANEL);
			}
		});

		GridBagConstraints gbc_loginBtnRegister = new GridBagConstraints();
		gbc_loginBtnRegister.gridx = 2;
		gbc_loginBtnRegister.gridy = 5;
		loginJPanel.add(loginBtnRegister, gbc_loginBtnRegister);

		JPanel withdrawJPanel = new JPanel();
		frmBankAccount.getContentPane().add(withdrawJPanel, WITHDRAW_PANEL);
		withdrawJPanel.setLayout(null);

		withdrawTxtAmount = new JTextField();
		withdrawTxtAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		withdrawTxtAmount.setBounds(142, 112, 150, 37);
		withdrawJPanel.add(withdrawTxtAmount);
		withdrawTxtAmount.setColumns(10);

		JLabel withdrawLblPrompt = new JLabel("Enter the Amount You Would Like To Withdraw");
		withdrawLblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
		withdrawLblPrompt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		withdrawLblPrompt.setBounds(45, 65, 343, 36);
		withdrawJPanel.add(withdrawLblPrompt);

		// withdraws moeny when the withdraw button is pressed on the withdraw panel
		JButton withdrawBtnWithdraw = new JButton("Withdraw");
		withdrawBtnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (withdrawTxtAmount.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fill in the blanks", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					if (ba.get(index).withdraw(Double.valueOf(withdrawTxtAmount.getText()))) {
						JOptionPane.showMessageDialog(null, "Withdrawl Success", "Success", JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Withdrawl Failed. Check balance and try again", "Failed",
								JOptionPane.ERROR_MESSAGE);
					}
					withdrawTxtAmount.setText("");
					layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
				}
			}
		});

		// adds KeyListerner to Enter to click the Withdraw Button
		withdrawTxtAmount.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					withdrawBtnWithdraw.doClick();
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		withdrawBtnWithdraw.setBounds(153, 160, 127, 30);
		withdrawJPanel.add(withdrawBtnWithdraw);

		JButton withdrawBtnBack = new JButton("Back");

		withdrawBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
			}
		});

		withdrawBtnBack.setBounds(335, 227, 89, 23);
		withdrawJPanel.add(withdrawBtnBack);

		JPanel depositJPanel = new JPanel();
		depositJPanel.setBackground(new Color(240, 240, 240));
		frmBankAccount.getContentPane().add(depositJPanel, DEPOSIT_PANEL);
		depositJPanel.setLayout(null);

		depositTxtAmount = new JTextField();
		depositTxtAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		depositTxtAmount.setBounds(142, 112, 150, 37);
		depositJPanel.add(depositTxtAmount);
		depositTxtAmount.setColumns(10);

		JButton depositBtnDeposit = new JButton("Deposit");
		depositBtnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (depositBtnDeposit.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fill in the blanks", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					if (ba.get(index).deposit(Double.valueOf(depositTxtAmount.getText()))) {
						JOptionPane.showMessageDialog(null, "Deposit Success", "Success", JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Deposit Failed. Please try again", "Failed",
								JOptionPane.ERROR_MESSAGE);
					}
					depositTxtAmount.setText("");
					layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
				}
			}
		});

		depositBtnDeposit.setBounds(153, 160, 127, 30);
		depositJPanel.add(depositBtnDeposit);

		JLabel depositLblPrompt = new JLabel("Enter the Amount You Would Like To Deposit");
		depositLblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
		depositLblPrompt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		depositLblPrompt.setBounds(45, 65, 343, 36);
		depositJPanel.add(depositLblPrompt);

		JButton depositBtnBack = new JButton("Back");
		depositBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
			}
		});
		depositBtnBack.setBounds(335, 227, 89, 23);
		depositJPanel.add(depositBtnBack);

		// enter button presses deposit
		depositTxtAmount.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					depositBtnDeposit.doClick();
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		JPanel accDetailJPanel = new JPanel();
		frmBankAccount.getContentPane().add(accDetailJPanel, ACCOUNTDETAIL_PANEL);
		accDetailJPanel.setLayout(null);

		JButton accDetailBack = new JButton("Back");
		accDetailBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
			}
		});

		accDetailBack.setBounds(335, 227, 89, 23);
		accDetailJPanel.add(accDetailBack);

		JLabel accIDetailLblAccNum = new JLabel("Account Number:");
		accIDetailLblAccNum.setHorizontalAlignment(SwingConstants.RIGHT);
		accIDetailLblAccNum.setBounds(25, 55, 114, 14);
		accDetailJPanel.add(accIDetailLblAccNum);

		JLabel accDetailLblBalance = new JLabel("Balance:");
		accDetailLblBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		accDetailLblBalance.setBounds(50, 90, 89, 14);
		accDetailJPanel.add(accDetailLblBalance);

		JLabel accDetailLblFirstName = new JLabel("First Name:");
		accDetailLblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		accDetailLblFirstName.setBounds(50, 125, 90, 14);
		accDetailJPanel.add(accDetailLblFirstName);

		JLabel accDetailLblLastName = new JLabel("Last Name:");
		accDetailLblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		accDetailLblLastName.setBounds(50, 160, 89, 14);
		accDetailJPanel.add(accDetailLblLastName);

		JLabel accDetailLblAccNumValue = new JLabel();
		accDetailLblAccNumValue.setText("ACCOUNT NUMBER SET IN CODE");
		accDetailLblAccNumValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accDetailLblAccNumValue.setBounds(149, 50, 226, 20);
		accDetailJPanel.add(accDetailLblAccNumValue);

		JLabel accDetailLblBalanceValue = new JLabel("BALANCE SET IN CODE");
		accDetailLblBalanceValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accDetailLblBalanceValue.setBounds(149, 84, 226, 20);
		accDetailJPanel.add(accDetailLblBalanceValue);

		JLabel accDetailLblFirstNameValue = new JLabel("FIRST NAME SET IN CODE");
		accDetailLblFirstNameValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accDetailLblFirstNameValue.setBounds(149, 119, 226, 20);
		accDetailJPanel.add(accDetailLblFirstNameValue);

		JLabel accDetailLblLastNameValue = new JLabel("LAST NAME SET IN CODE");
		accDetailLblLastNameValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accDetailLblLastNameValue.setBounds(149, 154, 226, 20);
		accDetailJPanel.add(accDetailLblLastNameValue);

		JPanel emptyJPanel = new JPanel();
		frmBankAccount.getContentPane().add(emptyJPanel, EMPTYACCOUNT_PANEL);
		emptyJPanel.setLayout(null);

		JButton emptyBtnBack = new JButton("Back");
		emptyBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
			}
		});

		emptyBtnBack.setBounds(335, 227, 89, 23);
		emptyJPanel.add(emptyBtnBack);

		JLabel emptyLblMessage = new JLabel("CLEARED WHEN OPENED AND SET IN CODE");
		emptyLblMessage.setFont(new Font("Tahoma", Font.PLAIN, 9));
		emptyLblMessage.setBounds(10, 227, 264, 23);
		emptyJPanel.add(emptyLblMessage);

		JButton emptyBtnEmptyAcc = new JButton("EMPTY ACCOUNT");
		emptyBtnEmptyAcc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// confirmation on wheter or not to empty account
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to empty your account?", 
						"Are you sure?", JOptionPane.YES_NO_OPTION);
				
				if(response == JOptionPane.YES_OPTION) {
					
					// empties account, sets the small message and shames user for not being responsible
					ba.get(index).emptyAccount();
					emptyLblMessage.setText("So much for \"leaving something for a rainy day\"...");
					JOptionPane.showMessageDialog(null, "Shame on you!", "SHAME", JOptionPane.PLAIN_MESSAGE);
				} else {
					// cancels operation, but shames the user anyways for pressing it in the first place
					JOptionPane.showMessageDialog(null, "Operation has been canceled. You still pressed it in the first place so shame on you.", 
							"SHAME", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		emptyBtnEmptyAcc.setFont(new Font("Tahoma", Font.PLAIN, 35));
		emptyBtnEmptyAcc.setBounds(10, 11, 414, 205);
		emptyJPanel.add(emptyBtnEmptyAcc);

		JPanel messageJPanel = new JPanel();
		frmBankAccount.getContentPane().add(messageJPanel, MESSAGES_PANEL);
		messageJPanel.setLayout(new BorderLayout(0, 0));

		JButton messageBtnBack = new JButton("Back");
		messageBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);

			}
		});

		messageBtnBack.setVerticalAlignment(SwingConstants.BOTTOM);
		messageJPanel.add(messageBtnBack);

		JScrollPane messageScrollPane = new JScrollPane();
		messageJPanel.add(messageScrollPane, BorderLayout.NORTH);

		JTextArea messageTxtAMessage = new JTextArea(13, 10);
		messageTxtAMessage.setText("MESSAGE SET IN CODE");
		messageTxtAMessage.setEditable(false);
		messageScrollPane.setViewportView(messageTxtAMessage);

		JPanel getBalance = new JPanel();
		frmBankAccount.getContentPane().add(getBalance, GETBALANCE_PANEL);
		getBalance.setLayout(null);

		JButton getBalanceBtnBack = new JButton("Back");
		getBalanceBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
			}
		});

		getBalanceBtnBack.setBounds(335, 228, 89, 23);
		getBalance.add(getBalanceBtnBack);

		JLabel getBalanceLblBalance = new JLabel("BALANCE SET IN CODE");
		getBalanceLblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		getBalanceLblBalance.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		getBalanceLblBalance.setBounds(10, 11, 414, 207);
		getBalance.add(getBalanceLblBalance);

		JPanel transToJPanel = new JPanel();
		frmBankAccount.getContentPane().add(transToJPanel, TRANSFERTO_PANEL);
		transToJPanel.setLayout(null);

		transToTxtAmount = new JTextField();
		transToTxtAmount.setBounds(129, 149, 202, 20);
		transToJPanel.add(transToTxtAmount);
		transToTxtAmount.setColumns(10);

		transToTxtOtherAccount = new JTextField();
		transToTxtOtherAccount.setBounds(129, 75, 202, 20);
		transToJPanel.add(transToTxtOtherAccount);
		transToTxtOtherAccount.setColumns(10);

		JLabel transToLblAmount = new JLabel("Amount:");
		transToLblAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		transToLblAmount.setBounds(52, 151, 66, 17);
		transToJPanel.add(transToLblAmount);

		JLabel transToLblOtherAccount = new JLabel("Other Account:");
		transToLblOtherAccount.setHorizontalAlignment(SwingConstants.TRAILING);
		transToLblOtherAccount.setBounds(25, 78, 95, 14);
		transToJPanel.add(transToLblOtherAccount);

		JButton transToBtnEnter = new JButton("Enter");
		transToBtnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (transToTxtAmount.getText().trim().isEmpty() || transToTxtOtherAccount.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Fill All Fields", "Failed", JOptionPane.ERROR_MESSAGE);
				} else if (setOtherAccountIndex(Integer.valueOf(transToTxtOtherAccount.getText()))) {
					if (ba.get(index).transferTo(Double.valueOf(transToTxtAmount.getText()), ba.get(otherIndex))) {
						JOptionPane.showMessageDialog(null, "Transfer Success", "Success", JOptionPane.PLAIN_MESSAGE);
						layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
					} else {
						JOptionPane.showMessageDialog(null, "Transfer Failed", "Failed", JOptionPane.ERROR_MESSAGE);
						layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Transfer Failed - Other Account Doesn't Exist", "Failed",
							JOptionPane.ERROR_MESSAGE);
					layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
				}

				transToTxtAmount.setText("");
				transToTxtOtherAccount.setText("");
			}
		});

		transToBtnEnter.setBounds(166, 227, 128, 23);
		transToJPanel.add(transToBtnEnter);

		JButton transToBtnBack = new JButton("Back");
		transToBtnBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
			}
		});
		transToBtnBack.setBounds(335, 227, 89, 23);
		transToJPanel.add(transToBtnBack);

		JPanel transFromJPanel = new JPanel();
		frmBankAccount.getContentPane().add(transFromJPanel, TRANSFERFROM_PANEL);
		transFromJPanel.setLayout(null);

		transFromPassword = new JPasswordField();
		transFromPassword.setBounds(131, 170, 200, 20);
		transFromJPanel.add(transFromPassword);

		transFromTxtOtherAccount = new JTextField();
		transFromTxtOtherAccount.setBounds(131, 50, 200, 20);
		transFromJPanel.add(transFromTxtOtherAccount);
		transFromTxtOtherAccount.setColumns(10);

		transFromTxtAmount = new JTextField();
		transFromTxtAmount.setBounds(131, 110, 200, 20);
		transFromJPanel.add(transFromTxtAmount);
		transFromTxtAmount.setColumns(10);

		JLabel transFromLblOtherAccount = new JLabel("Other Account:");
		transFromLblOtherAccount.setHorizontalAlignment(SwingConstants.TRAILING);
		transFromLblOtherAccount.setBounds(21, 51, 100, 14);
		transFromJPanel.add(transFromLblOtherAccount);

		JLabel transFromLblAmount = new JLabel("Amount:");
		transFromLblAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		transFromLblAmount.setBounds(39, 108, 82, 14);
		transFromJPanel.add(transFromLblAmount);

		JLabel transFromTxtPassword = new JLabel("Password:");
		transFromTxtPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		transFromTxtPassword.setBounds(39, 173, 82, 14);
		transFromJPanel.add(transFromTxtPassword);

		JButton transFromBtnEnter = new JButton("Enter");
		transFromBtnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
				transFromTxtOtherAccount.setText("");
				transFromTxtAmount.setText("");
				transFromPassword.setText("");

			}
		});

		transFromBtnEnter.setBounds(166, 227, 128, 23);
		transFromJPanel.add(transFromBtnEnter);

		JButton transFromBtnBack = new JButton("Back");

		transFromBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), CHOICE_PANEL);
			}
		});

		transFromBtnBack.setBounds(335, 227, 89, 23);
		transFromJPanel.add(transFromBtnBack);

		JPanel registerJPanel = new JPanel();

		frmBankAccount.getContentPane().add(registerJPanel, REGISTER_PANEL);
		registerJPanel.setLayout(null);

		JLabel regLblFirstName = new JLabel("First Name");
		regLblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		regLblFirstName.setBounds(36, 78, 84, 14);
		registerJPanel.add(regLblFirstName);

		JButton regBtnBack = new JButton("Back");
		regBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				layout.show(frmBankAccount.getContentPane(), LOGIN_PANEL);
				regTxtUsername.setText("");
				regTxtFirstName.setText("");
				regTxtLastName.setText("");
				regPasswordRepeat.setText("");
				regPassword.setText("");
			}
		});

		regBtnBack.setBounds(359, 225, 65, 25);
		registerJPanel.add(regBtnBack);

		JButton regBtnCreate = new JButton("Create Account");
		regBtnCreate.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")

			public void actionPerformed(ActionEvent e) {
				if ((regPassword.getText().trim().isEmpty() || regPasswordRepeat.getText().isEmpty()
						|| regTxtUsername.getText().trim().isEmpty() || regTxtFirstName.getText().trim().isEmpty()
						|| regTxtLastName.getText().trim().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Dude. Put stuff in the text box. Kind of the point", "bruh",
							JOptionPane.ERROR_MESSAGE);

				} else if (regPassword.getText().equals(regPasswordRepeat.getText())) {

					String username = regTxtUsername.getText();
					String firstName = regTxtFirstName.getText();
					String lastName = regTxtLastName.getText();
					String password = regPassword.getText();

					if (!isPasswordSecure(password)) {
						JOptionPane.showMessageDialog(null,
								"ERROR: Password did not meet one of the following fields: 1+ capital, 1+ lowercase "
										+ "9+ characters. NOTE: PASSWORDS STARTING WITH 12345 IS A LAZY PASS FOR TESTING");
					}

					else if (createAcc(username, DEFAULT_MONEY_VALUE, firstName, lastName, generateAccNum(),
							password)) {

						// Add password argument to BankAccount constructor
						// Update createAcc to accept the parameter

						layout.show(frmBankAccount.getContentPane(), LOGIN_PANEL);
						JOptionPane.showMessageDialog(null, "Account Created Successfuly", "Account Creation Success",
								JOptionPane.PLAIN_MESSAGE);

						// add pass clears
					} else {
						layout.show(frmBankAccount.getContentPane(), REGISTER_PANEL);
						JOptionPane.showMessageDialog(null,
								"Error: Username already in use. Try adding some abbritrary "
										+ "numbers no one likes to the end",
								"Account Creation Error", JOptionPane.ERROR_MESSAGE);

						// add pass clears
					}
				} else {
					layout.show(frmBankAccount.getContentPane(), REGISTER_PANEL);
					JOptionPane.showMessageDialog(null, "Error: Passwords Don't match. Please try again",
							"Account Creation Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		regBtnCreate.setBounds(130, 225, 200, 25);
		registerJPanel.add(regBtnCreate);

		regTxtFirstName = new JTextField();
		regTxtFirstName.setColumns(10);
		regTxtFirstName.setBounds(130, 75, 200, 20);
		registerJPanel.add(regTxtFirstName);

		regTxtLastName = new JTextField();
		regTxtLastName.setColumns(10);
		regTxtLastName.setBounds(130, 110, 200, 20);
		registerJPanel.add(regTxtLastName);

		JLabel regLblLastName = new JLabel("Last Name");
		regLblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		regLblLastName.setBounds(46, 113, 74, 14);
		registerJPanel.add(regLblLastName);

		JLabel regLblPassword = new JLabel("Password");
		regLblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		regLblPassword.setBounds(46, 148, 74, 14);
		registerJPanel.add(regLblPassword);

		JLabel regLblRepeatPassword = new JLabel("Repeat Password");
		regLblRepeatPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		regLblRepeatPassword.setBounds(4, 183, 116, 14);
		registerJPanel.add(regLblRepeatPassword);

		regPassword = new JPasswordField();
		regPassword.setBounds(130, 145, 200, 20);
		registerJPanel.add(regPassword);

		regPasswordRepeat = new JPasswordField();
		regPasswordRepeat.setBounds(130, 180, 200, 20);
		registerJPanel.add(regPasswordRepeat);

		regTxtUsername = new JTextField();
		regTxtUsername.setColumns(10);
		regTxtUsername.setBounds(130, 40, 200, 20);
		registerJPanel.add(regTxtUsername);

		JLabel regLblUsername = new JLabel("Username");
		regLblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		regLblUsername.setBounds(46, 43, 74, 14);
		registerJPanel.add(regLblUsername);

		JPanel mainJPanel = new JPanel();
		frmBankAccount.getContentPane().add(mainJPanel, CHOICE_PANEL);
		GridBagLayout gbl_mainJPanel = new GridBagLayout();
		gbl_mainJPanel.columnWidths = new int[] { 138, 136, 133 };
		gbl_mainJPanel.rowHeights = new int[] { 19, 75, 50, 23, 0, 0, 0 };
		gbl_mainJPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_mainJPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainJPanel.setLayout(gbl_mainJPanel);

		mainBtnWithdraw = new JButton("Withdraw");

		// displays the withdraw panel when the withdraw button is pressed on the main
		// panel
		mainBtnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdrawTxtAmount.setText("");
				layout.show(frmBankAccount.getContentPane(), WITHDRAW_PANEL);
			}
		});

		GridBagConstraints gbc_mainBtnWithdraw = new GridBagConstraints();
		gbc_mainBtnWithdraw.fill = GridBagConstraints.BOTH;
		gbc_mainBtnWithdraw.insets = new Insets(0, 3, 5, 5);
		gbc_mainBtnWithdraw.gridx = 0;
		gbc_mainBtnWithdraw.gridy = 1;
		mainJPanel.add(mainBtnWithdraw, gbc_mainBtnWithdraw);

		// displays the balance (maybe in a popup later) when the button is pressed on
		// the main panel
		JButton mainBtnBalance = new JButton("Get Balance");

		mainBtnBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), GETBALANCE_PANEL);
				getBalanceLblBalance.setText("Balance: " + ba.get(index).getBalance());
			}
		});

		// displays the deposit panel when the button is pressed on the main panel
		JButton mainBtnDeposit = new JButton("Deposit");
		mainBtnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				depositTxtAmount.setText("");
				layout.show(frmBankAccount.getContentPane(), DEPOSIT_PANEL);
			}
		});

		GridBagConstraints gbc_mainBtnDeposit = new GridBagConstraints();
		gbc_mainBtnDeposit.fill = GridBagConstraints.BOTH;
		gbc_mainBtnDeposit.insets = new Insets(0, 3, 5, 5);
		gbc_mainBtnDeposit.gridx = 1;
		gbc_mainBtnDeposit.gridy = 1;
		mainJPanel.add(mainBtnDeposit, gbc_mainBtnDeposit);
		GridBagConstraints gbc_mainBtnBalance = new GridBagConstraints();
		gbc_mainBtnBalance.fill = GridBagConstraints.BOTH;
		gbc_mainBtnBalance.insets = new Insets(0, 3, 5, 3);
		gbc_mainBtnBalance.gridx = 2;
		gbc_mainBtnBalance.gridy = 1;
		mainJPanel.add(mainBtnBalance, gbc_mainBtnBalance);

		// displays the empty account panel when the button is pressed on the main panel
		JButton mainBtnEmpty = new JButton("Empty Account");
		mainBtnEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emptyLblMessage.setText("");
				layout.show(frmBankAccount.getContentPane(), EMPTYACCOUNT_PANEL);
			}
		});

		GridBagConstraints gbc_mainBtnEmpty = new GridBagConstraints();
		gbc_mainBtnEmpty.fill = GridBagConstraints.BOTH;
		gbc_mainBtnEmpty.insets = new Insets(3, 3, 5, 5);
		gbc_mainBtnEmpty.gridx = 1;
		gbc_mainBtnEmpty.gridy = 2;
		mainJPanel.add(mainBtnEmpty, gbc_mainBtnEmpty);

		JButton mainBtnGetMessages = new JButton("Read Messages");
		mainBtnGetMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageTxtAMessage.setText(ba.get(index).getMessage());
				layout.show(frmBankAccount.getContentPane(), MESSAGES_PANEL);
			}
		});

		// displays the transfer from panel when the button is pressed on the main panel
		JButton mainBtnTransFrom = new JButton("Transfer From");
		mainBtnTransFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frmBankAccount.getContentPane(), TRANSFERFROM_PANEL);
			}
		});

		GridBagConstraints gbc_mainBtnTransFrom = new GridBagConstraints();
		gbc_mainBtnTransFrom.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnTransFrom.insets = new Insets(0, 3, 5, 5);
		gbc_mainBtnTransFrom.gridx = 0;
		gbc_mainBtnTransFrom.gridy = 4;
		mainJPanel.add(mainBtnTransFrom, gbc_mainBtnTransFrom);

		JButton mainBtnAccDetail = new JButton("Account Info");
		mainBtnAccDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				accDetailLblAccNumValue.setText(String.valueOf(ba.get(index).getAccountNum()));
				accDetailLblBalanceValue.setText('$' + ba.get(index).getBalance());
				accDetailLblFirstNameValue.setText(ba.get(index).getFirstName());
				accDetailLblLastNameValue.setText(ba.get(index).getLastName());

				layout.show(frmBankAccount.getContentPane(), ACCOUNTDETAIL_PANEL);
			}
		});

		// displays the transfer to panel when the button is pressed on the main panel
		JButton mainBtnTransTo = new JButton("Transfer To");
		mainBtnTransTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), TRANSFERTO_PANEL);
			}
		});
		GridBagConstraints gbc_mainBtnTransTo = new GridBagConstraints();
		gbc_mainBtnTransTo.insets = new Insets(0, 3, 5, 5);
		gbc_mainBtnTransTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnTransTo.gridx = 1;
		gbc_mainBtnTransTo.gridy = 4;
		mainJPanel.add(mainBtnTransTo, gbc_mainBtnTransTo);

		GridBagConstraints gbc_mainBtnAccDetail = new GridBagConstraints();
		gbc_mainBtnAccDetail.anchor = GridBagConstraints.NORTH;
		gbc_mainBtnAccDetail.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnAccDetail.insets = new Insets(0, 3, 5, 3);
		gbc_mainBtnAccDetail.gridx = 2;
		gbc_mainBtnAccDetail.gridy = 4;
		mainJPanel.add(mainBtnAccDetail, gbc_mainBtnAccDetail);

		// displays the reset password panel when the button is pressed on the main
		// panel
		JButton mainBtnResetPassword = new JButton("Reset Password");
		mainBtnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), RESETPASSWORD_PANEL);
			}
		});

		GridBagConstraints gbc_mainBtnResetPassword = new GridBagConstraints();
		gbc_mainBtnResetPassword.insets = new Insets(3, 3, 0, 5);
		gbc_mainBtnResetPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnResetPassword.gridx = 0;
		gbc_mainBtnResetPassword.gridy = 5;
		mainJPanel.add(mainBtnResetPassword, gbc_mainBtnResetPassword);

		// displays the get password panel when the button is pressed on the main panel
		JButton mainBtnGetPassword = new JButton("Get Password");
		mainBtnGetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frmBankAccount.getContentPane(), GETPASSWORD_PANEL);
			}
		});
		GridBagConstraints gbc_mainBtnGetPassword = new GridBagConstraints();
		gbc_mainBtnGetPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnGetPassword.insets = new Insets(3, 3, 0, 5);
		gbc_mainBtnGetPassword.gridx = 1;
		gbc_mainBtnGetPassword.gridy = 5;
		mainJPanel.add(mainBtnGetPassword, gbc_mainBtnGetPassword);
		GridBagConstraints gbc_mainBtnGetMessages = new GridBagConstraints();
		gbc_mainBtnGetMessages.insets = new Insets(3, 3, 0, 3);
		gbc_mainBtnGetMessages.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnGetMessages.anchor = GridBagConstraints.NORTH;
		gbc_mainBtnGetMessages.gridx = 2;
		gbc_mainBtnGetMessages.gridy = 5;
		mainJPanel.add(mainBtnGetMessages, gbc_mainBtnGetMessages);

	}
}
