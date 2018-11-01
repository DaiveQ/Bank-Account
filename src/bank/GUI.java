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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GUI {

	// Add null check to Deposit and Withdraw\
	// Also give functionality to Transfer To/From
	
	private JFrame frame;
	CardLayout layout = new CardLayout(0, 0);

	private static List<BankAccount> ba = new ArrayList<BankAccount>();
	private static int index = 0; // index of BankAccount. Shortened for simplicity

	final static String CHOICE_PANEL = "Choice Panel";
	final static String WITHDRAW_PANEL = "Withdraw Panel";
	final static String DEPOSIT_PANEL = "Deposit Panel";
	final static String DISPLAY_PANEL = "Display Panel";
	final static String CHECKPASSWORD_PANEL = "CheckPassword Panel";
	final static String GETPASSWORD_PANEL = "GetPassword Panel";
	final static String EMPTYACCOUNT_PANEL = "EmptyAccount Panel";
	final static String MESSAGES_PANEL = "Messages Panel";
	final static String RESETPASSWORD_PANEL = "ResetPassword Panel";
	final static String RESETPASS_PANEL = "ResetPass Panel";
	final static String GETBALANCE_PANEL = "GetBalance Panel";
	final static String TRANSFERTO_PANEL = "TransferTo Panel";
	final static String TRANSFERFROM_PANEL = "TransferFrom Panel";
	final static String NEWACCOUNT_PANEL = "NewAccount Panel";
	final static String LOGIN_PANEL = "Login Panel";
	private static final double DEFAULT_MONEY_VALUE = 1234.56;

	private JTextField Login_txtUsername;
	private JPasswordField Login_passwordField;
	private JTextField Withdraw_txtAmount;
	private JTextField Deposit_txtAmount;
	private JPasswordField ResetPass_txtCurrentPassword;
	private JPasswordField ResetPass_txtNewPassword;
	private JTextField TransferTo_txtAmount;
	private JTextField TransferTo_txtBankAccount;
	private JTextField TransferTo_txtOtherAccount;
	private JPasswordField TransferFrom_passwordField;
	private JTextField TransferFrom_txtBankAccount;
	private JTextField TransferFrom_txtOtherAccount;
	private JTextField TransferFrom_txtAmount;
	private JTextField CreateAccount_txtFirstName;
	private JTextField CreateAccount_txtLastName;
	private JPasswordField CreateAccount_passwordField;
	private JPasswordField CreateAccount_passwordFieldRepeat;
	private JTextField CreateAccount_txtUsername;
	private JButton Main_btnWithdraw;

	public static void main(String[] args) {
		createTestAccount();
		ba.get(index).withdraw(99999999);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
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
				GUI.index = i;
				return;
			}
		}
	}
	
	private boolean isPasswordsecure(String pass) {
		
		///////////////////////////////////////////////////////
		// TESTING IF STATEMENT. PASSOWRDS STARTING WITH 12345 
		// WILL BE PASSED DELETE ONCE DONE
		///////////////////////////////////////////////////////
		if(pass.length() >= 5) {
			if(pass.substring(0, 5).equals("12345")) return true;
		}
		///////////////////////////////////////////////////////
		
		if(pass.toLowerCase().equals(pass)) return false;
		else if(pass.toUpperCase().equals(pass)) return false;
		else if(pass.length() < 9) return false;
		// else if password has number
		else return true;
	}

	private static int generateAccNum() {
		boolean taken = true;
		int accNum = 0;

		while (taken) {

			String accComb = "";

			Random rand = new Random();
			int x[] = new int[9];
			for (int i = 0; i < 9; i++)
				x[i] = rand.nextInt(9);

			for (int i : x) {
				accComb += String.valueOf(x[i]);
			}
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

	private static boolean createAcc(String username, double amt, String fName, String lName, int acctNum, String password) {
		for (BankAccount element : ba) {
			if (element.getUsername().equals(username))
				return false;
		}
		ba.add(new BankAccount(username, amt, fName, lName, acctNum, password));
		return true;
	}
	
	
	private static void createTestAccount() {
		int accNum = generateAccNum();
		ba.add(new BankAccount("12345", 1000, "123", "45", accNum));
		ba.add(new BankAccount("Ricky", 1000, "Ricky", "Lin", accNum));
	}

	private void initialize() {

		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(layout);

		JPanel LoginJPanel = new JPanel();
		frame.getContentPane().add(LoginJPanel, LOGIN_PANEL);
		GridBagLayout gbl_LoginJPanel = new GridBagLayout();
		gbl_LoginJPanel.columnWidths = new int[] { 75, 74, 107, 0 };
		gbl_LoginJPanel.rowHeights = new int[] { 91, 21, 20, 23, 0, 0, 0 };
		gbl_LoginJPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_LoginJPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		LoginJPanel.setLayout(gbl_LoginJPanel);

		JLabel Login_lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_Login_lblUsername = new GridBagConstraints();
		gbc_Login_lblUsername.fill = GridBagConstraints.BOTH;
		gbc_Login_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_Login_lblUsername.gridx = 1;
		gbc_Login_lblUsername.gridy = 1;
		LoginJPanel.add(Login_lblUsername, gbc_Login_lblUsername);

		Login_txtUsername = new JTextField();
		GridBagConstraints gbc_Login_txtUsername = new GridBagConstraints();
		gbc_Login_txtUsername.anchor = GridBagConstraints.NORTH;
		gbc_Login_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_Login_txtUsername.insets = new Insets(0, 0, 5, 0);
		gbc_Login_txtUsername.gridx = 2;
		gbc_Login_txtUsername.gridy = 1;
		LoginJPanel.add(Login_txtUsername, gbc_Login_txtUsername);
		Login_txtUsername.setColumns(10);

		JLabel Login_lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_Login_lblPassword = new GridBagConstraints();
		gbc_Login_lblPassword.anchor = GridBagConstraints.NORTH;
		gbc_Login_lblPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_Login_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_Login_lblPassword.gridx = 1;
		gbc_Login_lblPassword.gridy = 2;
		LoginJPanel.add(Login_lblPassword, gbc_Login_lblPassword);

		Login_passwordField = new JPasswordField();
		GridBagConstraints gbc_Login_passwordField = new GridBagConstraints();
		gbc_Login_passwordField.anchor = GridBagConstraints.NORTH;
		gbc_Login_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_Login_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_Login_passwordField.gridx = 2;
		gbc_Login_passwordField.gridy = 2;
		LoginJPanel.add(Login_passwordField, gbc_Login_passwordField);

		JButton Login_btnEnter = new JButton("Enter");

		Login_btnEnter.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			
			public void actionPerformed(ActionEvent arg0) {
				setBankAccountIndex(Login_txtUsername.getText());

				if (ba.get(index).checkPassword(Login_passwordField.getText()))
					layout.show(frame.getContentPane(), CHOICE_PANEL);
				else
					JOptionPane.showMessageDialog(null, "Wrong Password or Username. Please try again.", "Login Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		
		Login_txtUsername.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Login_btnEnter.doClick();
				}
			}
			
			public void keyReleased(KeyEvent arg0) {
			}
			
			public void keyTyped(KeyEvent arg0) {
			}
		});

		Login_passwordField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Login_btnEnter.doClick();
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		GridBagConstraints gbc_Login_btnEnter = new GridBagConstraints();
		gbc_Login_btnEnter.insets = new Insets(0, 0, 5, 0);
		gbc_Login_btnEnter.anchor = GridBagConstraints.NORTH;
		gbc_Login_btnEnter.fill = GridBagConstraints.HORIZONTAL;
		gbc_Login_btnEnter.gridx = 2;
		gbc_Login_btnEnter.gridy = 3;
		LoginJPanel.add(Login_btnEnter, gbc_Login_btnEnter);

		JButton Login_btnCreateAccount = new JButton("Create A New Account");
		Login_btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), NEWACCOUNT_PANEL);
			}
		});
		GridBagConstraints gbc_Login_btnCreateAccount = new GridBagConstraints();
		gbc_Login_btnCreateAccount.gridx = 2;
		gbc_Login_btnCreateAccount.gridy = 5;
		LoginJPanel.add(Login_btnCreateAccount, gbc_Login_btnCreateAccount);

		JPanel MainJPanel = new JPanel();
		frame.getContentPane().add(MainJPanel, CHOICE_PANEL);
		GridBagLayout gbl_MainJPanel = new GridBagLayout();
		gbl_MainJPanel.columnWidths = new int[] { 138, 136, 133 };
		gbl_MainJPanel.rowHeights = new int[] { 19, 75, 50, 23, 0, 0, 0 };
		gbl_MainJPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_MainJPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		MainJPanel.setLayout(gbl_MainJPanel);

		Main_btnWithdraw = new JButton("Withdraw");
		Main_btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), WITHDRAW_PANEL);
			}
		});
		GridBagConstraints gbc_Main_btnWithdraw = new GridBagConstraints();
		gbc_Main_btnWithdraw.fill = GridBagConstraints.BOTH;
		gbc_Main_btnWithdraw.insets = new Insets(0, 3, 3, 3);
		gbc_Main_btnWithdraw.gridx = 0;
		gbc_Main_btnWithdraw.gridy = 1;
		MainJPanel.add(Main_btnWithdraw, gbc_Main_btnWithdraw);

		JButton Main_btnBalance = new JButton("Get Balance");
		Main_btnBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), GETBALANCE_PANEL);
			}
		});

		JButton Main_btnDeposit = new JButton("Deposit");
		Main_btnDeposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				layout.show(frame.getContentPane(), DEPOSIT_PANEL);
			}
		});
		GridBagConstraints gbc_Main_btnDeposit = new GridBagConstraints();
		gbc_Main_btnDeposit.fill = GridBagConstraints.BOTH;
		gbc_Main_btnDeposit.insets = new Insets(0, 3, 3, 3);
		gbc_Main_btnDeposit.gridx = 1;
		gbc_Main_btnDeposit.gridy = 1;
		MainJPanel.add(Main_btnDeposit, gbc_Main_btnDeposit);
		GridBagConstraints gbc_Main_btnBalance = new GridBagConstraints();
		gbc_Main_btnBalance.fill = GridBagConstraints.BOTH;
		gbc_Main_btnBalance.insets = new Insets(0, 3, 3, 3);
		gbc_Main_btnBalance.gridx = 2;
		gbc_Main_btnBalance.gridy = 1;
		MainJPanel.add(Main_btnBalance, gbc_Main_btnBalance);

		JButton Main_btnEmptyAccount = new JButton("Empty Account");
		Main_btnEmptyAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), EMPTYACCOUNT_PANEL);
			}
		});
		GridBagConstraints gbc_Main_btnEmptyAccount = new GridBagConstraints();
		gbc_Main_btnEmptyAccount.fill = GridBagConstraints.BOTH;
		gbc_Main_btnEmptyAccount.insets = new Insets(3, 3, 0, 3);
		gbc_Main_btnEmptyAccount.gridx = 1;
		gbc_Main_btnEmptyAccount.gridy = 2;
		MainJPanel.add(Main_btnEmptyAccount, gbc_Main_btnEmptyAccount);

		JButton Main_btnGetMessages = new JButton("Read Messages");
		// Action Listerner added later in code

		JButton Main_btnTransferTo = new JButton("Transfer To");
		Main_btnTransferTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), TRANSFERTO_PANEL);
			}
		});

		JButton Main_btnGetPassword = new JButton("Get Password");
		Main_btnGetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), GETPASSWORD_PANEL);
			}
		});

		JButton Main_btnTransferFrom = new JButton("Transfer From");
		Main_btnTransferFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), TRANSFERFROM_PANEL);
			}
		});
		GridBagConstraints gbc_Main_btnTransferFrom = new GridBagConstraints();
		gbc_Main_btnTransferFrom.fill = GridBagConstraints.HORIZONTAL;
		gbc_Main_btnTransferFrom.insets = new Insets(0, 3, 3, 3);
		gbc_Main_btnTransferFrom.gridx = 0;
		gbc_Main_btnTransferFrom.gridy = 4;
		MainJPanel.add(Main_btnTransferFrom, gbc_Main_btnTransferFrom);
		GridBagConstraints gbc_Main_btnGetPassword = new GridBagConstraints();
		gbc_Main_btnGetPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_Main_btnGetPassword.insets = new Insets(0, 3, 3, 3);
		gbc_Main_btnGetPassword.gridx = 1;
		gbc_Main_btnGetPassword.gridy = 4;
		MainJPanel.add(Main_btnGetPassword, gbc_Main_btnGetPassword);

		JButton Main_btnCheckPassword = new JButton("Check Password");
		Main_btnCheckPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), CHECKPASSWORD_PANEL);
			}
		});
		GridBagConstraints gbc_Main_btnCheckPassword = new GridBagConstraints();
		gbc_Main_btnCheckPassword.anchor = GridBagConstraints.NORTH;
		gbc_Main_btnCheckPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_Main_btnCheckPassword.insets = new Insets(0, 3, 3, 3);
		gbc_Main_btnCheckPassword.gridx = 2;
		gbc_Main_btnCheckPassword.gridy = 4;
		MainJPanel.add(Main_btnCheckPassword, gbc_Main_btnCheckPassword);

		JButton Main_btnResetPassword = new JButton("Reset Password");
		Main_btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), RESETPASSWORD_PANEL);
			}
		});
		GridBagConstraints gbc_Main_btnResetPassword = new GridBagConstraints();
		gbc_Main_btnResetPassword.insets = new Insets(3, 3, 0, 3);
		gbc_Main_btnResetPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_Main_btnResetPassword.gridx = 0;
		gbc_Main_btnResetPassword.gridy = 5;
		MainJPanel.add(Main_btnResetPassword, gbc_Main_btnResetPassword);
		GridBagConstraints gbc_Main_btnTransferTo = new GridBagConstraints();
		gbc_Main_btnTransferTo.insets = new Insets(3, 3, 0, 3);
		gbc_Main_btnTransferTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_Main_btnTransferTo.gridx = 1;
		gbc_Main_btnTransferTo.gridy = 5;
		MainJPanel.add(Main_btnTransferTo, gbc_Main_btnTransferTo);
		GridBagConstraints gbc_Main_btnGetMessages = new GridBagConstraints();
		gbc_Main_btnGetMessages.insets = new Insets(3, 3, 0, 3);
		gbc_Main_btnGetMessages.fill = GridBagConstraints.HORIZONTAL;
		gbc_Main_btnGetMessages.anchor = GridBagConstraints.NORTH;
		gbc_Main_btnGetMessages.gridx = 2;
		gbc_Main_btnGetMessages.gridy = 5;
		MainJPanel.add(Main_btnGetMessages, gbc_Main_btnGetMessages);

		JPanel WithdrawJPanel = new JPanel();
		frame.getContentPane().add(WithdrawJPanel, WITHDRAW_PANEL);
		WithdrawJPanel.setLayout(null);

		Withdraw_txtAmount = new JTextField();
		Withdraw_txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Withdraw_txtAmount.setBounds(142, 112, 150, 37);
		WithdrawJPanel.add(Withdraw_txtAmount);
		Withdraw_txtAmount.setColumns(10);

		JLabel Withdraw_lblAmountPromt = new JLabel("Enter the Amount You Would Like To Withdraw:");
		Withdraw_lblAmountPromt.setHorizontalAlignment(SwingConstants.CENTER);
		Withdraw_lblAmountPromt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Withdraw_lblAmountPromt.setBounds(45, 65, 343, 36);
		WithdrawJPanel.add(Withdraw_lblAmountPromt);

		JButton Withdraw_btnWithdraw = new JButton("Withdraw");
		Withdraw_btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
				ba.get(index).withdraw(Double.valueOf(Withdraw_txtAmount.getText()));
			}
		});
		
		// adds KeyListerner to Enter to click the Withdraw Button
		Withdraw_txtAmount.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Withdraw_btnWithdraw.doClick();
				}
			}
			
			public void keyReleased(KeyEvent arg0) {
			}
			
			public void keyTyped(KeyEvent arg0) {
			}
		});
		Withdraw_btnWithdraw.setBounds(153, 160, 127, 30);
		WithdrawJPanel.add(Withdraw_btnWithdraw);

		JPanel DepositJPanel = new JPanel();
		DepositJPanel.setBackground(new Color(240, 240, 240));
		frame.getContentPane().add(DepositJPanel, DEPOSIT_PANEL);
		DepositJPanel.setLayout(null);

		JLabel Deposit_lblAmountPrompt = new JLabel("Enter The Amount That You Would Deposit:");
		Deposit_lblAmountPrompt.setHorizontalAlignment(SwingConstants.CENTER);
		Deposit_lblAmountPrompt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Deposit_lblAmountPrompt.setBounds(56, 65, 321, 36);
		DepositJPanel.add(Deposit_lblAmountPrompt);

		Deposit_txtAmount = new JTextField();
		Deposit_txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Deposit_txtAmount.setBounds(142, 112, 150, 37);
		DepositJPanel.add(Deposit_txtAmount);
		Deposit_txtAmount.setColumns(10);
		
		JButton Deposit_btnDeposit = new JButton("Deposit");
		Deposit_btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ba.get(index).deposit(Double.valueOf(Deposit_txtAmount.getText()));
				layout.show(frame.getContentPane(), CHOICE_PANEL);
				
			}
		});
		Deposit_btnDeposit.setBounds(153, 160, 127, 30);
		DepositJPanel.add(Deposit_btnDeposit);
		
		// enter button presses deposit
		Deposit_txtAmount.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Deposit_btnDeposit.doClick();
				}
			}
			
			public void keyReleased(KeyEvent arg0) {
			}
			
			public void keyTyped(KeyEvent arg0) {
			}
		});
		
		JPanel AccountDetailJPanel = new JPanel();
		frame.getContentPane().add(AccountDetailJPanel, DISPLAY_PANEL);
		AccountDetailJPanel.setLayout(null);

		JButton AccountDetail_Back = new JButton("Back");
		AccountDetail_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		AccountDetail_Back.setBounds(335, 228, 89, 23);
		AccountDetailJPanel.add(AccountDetail_Back);

		JLabel AccountDetail_lblAccNum = new JLabel("Account Number:");
		AccountDetail_lblAccNum.setHorizontalAlignment(SwingConstants.RIGHT);
		AccountDetail_lblAccNum.setBounds(50, 55, 89, 14);
		AccountDetailJPanel.add(AccountDetail_lblAccNum);

		JLabel AccountDetail_lblBalance = new JLabel("Balance:");
		AccountDetail_lblBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		AccountDetail_lblBalance.setBounds(50, 90, 89, 14);
		AccountDetailJPanel.add(AccountDetail_lblBalance);

		JLabel AccountDetail_lblFirstName = new JLabel("First Name:");
		AccountDetail_lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		AccountDetail_lblFirstName.setBounds(50, 125, 90, 14);
		AccountDetailJPanel.add(AccountDetail_lblFirstName);

		JLabel AccountDetail_lblLastName = new JLabel("Last Name:");
		AccountDetail_lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		AccountDetail_lblLastName.setBounds(50, 160, 89, 14);
		AccountDetailJPanel.add(AccountDetail_lblLastName);

		JLabel AccountDetail_lblAccNumValue = new JLabel();
		AccountDetail_lblAccNumValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		AccountDetail_lblAccNumValue.setBounds(149, 50, 226, 20);
		AccountDetailJPanel.add(AccountDetail_lblAccNumValue);

		JLabel AccountDetail_lblBalanceValue = new JLabel("New label");
		AccountDetail_lblBalanceValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		AccountDetail_lblBalanceValue.setBounds(149, 84, 226, 20);
		AccountDetailJPanel.add(AccountDetail_lblBalanceValue);

		JLabel AccountDetail_lblFirstNameValue = new JLabel("New label");
		AccountDetail_lblFirstNameValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		AccountDetail_lblFirstNameValue.setBounds(149, 119, 226, 20);
		AccountDetailJPanel.add(AccountDetail_lblFirstNameValue);

		JLabel AccountDetail_lblLastNameValue = new JLabel("New label");
		AccountDetail_lblLastNameValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		AccountDetail_lblLastNameValue.setBounds(149, 154, 226, 20);
		AccountDetailJPanel.add(AccountDetail_lblLastNameValue);

		JPanel EmptyAccountJPanel = new JPanel();
		frame.getContentPane().add(EmptyAccountJPanel, EMPTYACCOUNT_PANEL);
		EmptyAccountJPanel.setLayout(null);

		JButton EmptyAccount_btnBack = new JButton("Back");
		EmptyAccount_btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		EmptyAccount_btnBack.setBounds(296, 198, 89, 23);
		EmptyAccountJPanel.add(EmptyAccount_btnBack);

		JLabel lblNewLabel_19 = new JLabel("(Add the Writing from the program here:)");
		lblNewLabel_19.setBounds(94, 75, 264, 23);
		EmptyAccountJPanel.add(lblNewLabel_19);

		JPanel ReadMessagesJPanel = new JPanel();
		frame.getContentPane().add(ReadMessagesJPanel, MESSAGES_PANEL);
		ReadMessagesJPanel.setLayout(new BorderLayout(0, 0));

		JButton ReadMessage_btnBack = new JButton("Back");
		ReadMessage_btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);

			}
		});
		ReadMessage_btnBack.setVerticalAlignment(SwingConstants.BOTTOM);
		ReadMessagesJPanel.add(ReadMessage_btnBack);

		JScrollPane ReadMessage_scrollPane = new JScrollPane();
		ReadMessagesJPanel.add(ReadMessage_scrollPane, BorderLayout.NORTH);

		JTextArea ReadMessage_txtAMessage = new JTextArea(13, 10);
		ReadMessage_txtAMessage.setText("");
		ReadMessage_txtAMessage.setEditable(false);
		ReadMessage_scrollPane.setViewportView(ReadMessage_txtAMessage);
		
		Main_btnGetMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadMessage_txtAMessage.setText(ba.get(index).getMessage());
				layout.show(frame.getContentPane(), MESSAGES_PANEL);
			}
		});
		
		JPanel ResetPassJPanel = new JPanel();
		frame.getContentPane().add(ResetPassJPanel, RESETPASS_PANEL);
		ResetPassJPanel.setLayout(null);

		ResetPass_txtCurrentPassword = new JPasswordField();
		ResetPass_txtCurrentPassword.setBounds(60, 79, 174, 20);
		ResetPassJPanel.add(ResetPass_txtCurrentPassword);

		ResetPass_txtNewPassword = new JPasswordField();
		ResetPass_txtNewPassword.setBounds(60, 120, 174, 20);
		ResetPassJPanel.add(ResetPass_txtNewPassword);

		JLabel ResetPass_lblCurrentPassword = new JLabel("<-Current Password");
		ResetPass_lblCurrentPassword.setBounds(266, 82, 158, 14);
		ResetPassJPanel.add(ResetPass_lblCurrentPassword);

		JLabel ResetPass_lblNewPass = new JLabel("<- New Password");
		ResetPass_lblNewPass.setBounds(266, 123, 158, 14);
		ResetPassJPanel.add(ResetPass_lblNewPass);

		JButton ResetPass_btnEnter = new JButton("Enter");
		ResetPass_btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		ResetPass_btnEnter.setBounds(152, 160, 89, 23);
		ResetPassJPanel.add(ResetPass_btnEnter);

		JPanel GetBalanceJPanel = new JPanel();
		frame.getContentPane().add(GetBalanceJPanel, GETBALANCE_PANEL);
		GetBalanceJPanel.setLayout(null);

		JButton GetBalance_Back = new JButton("Back");
		GetBalance_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		GetBalance_Back.setBounds(335, 228, 89, 23);
		GetBalanceJPanel.add(GetBalance_Back);

		JLabel GetBalance_Balance = new JLabel("Return Balance Here:");
		GetBalance_Balance.setBounds(120, 55, 246, 23);
		GetBalanceJPanel.add(GetBalance_Balance);

		JPanel TransferToJPanel = new JPanel();
		frame.getContentPane().add(TransferToJPanel, TRANSFERTO_PANEL);
		TransferToJPanel.setLayout(null);

		TransferTo_txtAmount = new JTextField();
		TransferTo_txtAmount.setBounds(129, 49, 202, 20);
		TransferToJPanel.add(TransferTo_txtAmount);
		TransferTo_txtAmount.setColumns(10);

		TransferTo_txtBankAccount = new JTextField();
		TransferTo_txtBankAccount.setBounds(129, 120, 202, 20);
		TransferToJPanel.add(TransferTo_txtBankAccount);
		TransferTo_txtBankAccount.setColumns(10);

		TransferTo_txtOtherAccount = new JTextField();
		TransferTo_txtOtherAccount.setBounds(131, 191, 202, 20);
		TransferToJPanel.add(TransferTo_txtOtherAccount);
		TransferTo_txtOtherAccount.setColumns(10);

		JLabel TransferTo_lblAmount = new JLabel("Amount");
		TransferTo_lblAmount.setBounds(53, 51, 66, 17);
		TransferToJPanel.add(TransferTo_lblAmount);

		JLabel TransferTo_lblBankAccount = new JLabel("Bank Account");
		TransferTo_lblBankAccount.setBounds(26, 123, 95, 14);
		TransferToJPanel.add(TransferTo_lblBankAccount);

		JLabel TransferTo_lblOtherAccount = new JLabel("Other Account");
		TransferTo_lblOtherAccount.setBounds(26, 194, 95, 14);
		TransferToJPanel.add(TransferTo_lblOtherAccount);

		JButton TransferTo_btnEnter = new JButton("Enter");
		TransferTo_btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		TransferTo_btnEnter.setBounds(290, 228, 89, 23);
		TransferToJPanel.add(TransferTo_btnEnter);

		JPanel TransferFromJPanel = new JPanel();
		frame.getContentPane().add(TransferFromJPanel, TRANSFERFROM_PANEL);
		TransferFromJPanel.setLayout(null);

		TransferFrom_passwordField = new JPasswordField();
		TransferFrom_passwordField.setBounds(131, 187, 200, 20);
		TransferFromJPanel.add(TransferFrom_passwordField);

		TransferFrom_txtBankAccount = new JTextField();
		TransferFrom_txtBankAccount.setBounds(131, 37, 200, 20);
		TransferFromJPanel.add(TransferFrom_txtBankAccount);
		TransferFrom_txtBankAccount.setColumns(10);

		TransferFrom_txtOtherAccount = new JTextField();
		TransferFrom_txtOtherAccount.setBounds(131, 87, 200, 20);
		TransferFromJPanel.add(TransferFrom_txtOtherAccount);
		TransferFrom_txtOtherAccount.setColumns(10);

		TransferFrom_txtAmount = new JTextField();
		TransferFrom_txtAmount.setBounds(131, 137, 200, 20);
		TransferFromJPanel.add(TransferFrom_txtAmount);
		TransferFrom_txtAmount.setColumns(10);

		JLabel TransferFrom_lblBankAccount = new JLabel("Bank Account:");
		TransferFrom_lblBankAccount.setHorizontalAlignment(SwingConstants.TRAILING);
		TransferFrom_lblBankAccount.setBounds(39, 40, 82, 14);
		TransferFromJPanel.add(TransferFrom_lblBankAccount);

		JLabel TransferFrom_lblOtherAccount = new JLabel("Other Account:");
		TransferFrom_lblOtherAccount.setHorizontalAlignment(SwingConstants.TRAILING);
		TransferFrom_lblOtherAccount.setBounds(39, 90, 82, 14);
		TransferFromJPanel.add(TransferFrom_lblOtherAccount);

		JLabel TransferFrom_lblAmount = new JLabel("Amount:");
		TransferFrom_lblAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		TransferFrom_lblAmount.setBounds(39, 140, 82, 14);
		TransferFromJPanel.add(TransferFrom_lblAmount);

		JLabel TransferFrom_txtPassword = new JLabel("Password:");
		TransferFrom_txtPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		TransferFrom_txtPassword.setBounds(39, 190, 82, 14);
		TransferFromJPanel.add(TransferFrom_txtPassword);

		JButton TransferFrom_btnEnter = new JButton("Enter");
		TransferFrom_btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		TransferFrom_btnEnter.setBounds(296, 228, 89, 23);
		TransferFromJPanel.add(TransferFrom_btnEnter);

		JPanel CreateAccountJPanel = new JPanel();

		frame.getContentPane().add(CreateAccountJPanel, NEWACCOUNT_PANEL);
		CreateAccountJPanel.setLayout(null);

		JLabel CreateAccount_lblFirstName = new JLabel("First Name");
		CreateAccount_lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		CreateAccount_lblFirstName.setBounds(36, 78, 84, 14);
		CreateAccountJPanel.add(CreateAccount_lblFirstName);

		JButton CreateAccount_btnBack = new JButton("Back");
		CreateAccount_btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOGIN_PANEL);
			}
		});
		CreateAccount_btnBack.setBounds(55, 225, 65, 25);
		CreateAccountJPanel.add(CreateAccount_btnBack);

		JButton CreateAccount_btnCreate = new JButton("Create Account");
		CreateAccount_btnCreate.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")

			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
				
				if ((CreateAccount_passwordField.getText().trim().isEmpty()
						|| CreateAccount_passwordFieldRepeat.getText().equals(null)
						|| CreateAccount_txtUsername.getText().trim().isEmpty()
						|| CreateAccount_txtFirstName.getText().trim().isEmpty()
						|| CreateAccount_txtLastName.getText().trim().isEmpty())) {
					
					layout.show(frame.getContentPane(), NEWACCOUNT_PANEL);
					JOptionPane.showMessageDialog(null, "Dude. Put stuff in the text box. Kind of the point", "bruh",
							JOptionPane.ERROR_MESSAGE);
				} else if (CreateAccount_passwordField.getText().equals(CreateAccount_passwordFieldRepeat.getText())) {
					
					String username = CreateAccount_txtUsername.getText();
					String firstName = CreateAccount_txtFirstName.getText();
					String lastName = CreateAccount_txtLastName.getText();
					String password = CreateAccount_passwordField.getText();
					
					if(!isPasswordsecure(password)) {
						layout.show(frame.getContentPane(), NEWACCOUNT_PANEL);
						JOptionPane.showMessageDialog(null, "ERROR: Password did not meet one of the following fields: 1+ capital, 1+ lowercase "
								+ "9+ characters. NOTE: PASSWORDS STARTING WITH 12345 IS A LAZY PASS FOR TESTING");
					}
					
					else if (createAcc(username, DEFAULT_MONEY_VALUE, firstName, lastName, generateAccNum(), password)) {
						
						// Add password argument to BankAccount constructor
						// Update createAcc to accept the parameter
						
						layout.show(frame.getContentPane(), LOGIN_PANEL);
						JOptionPane.showMessageDialog(null, "Account Created Successfuly", "Account Creation Success",
								JOptionPane.PLAIN_MESSAGE);
					} else {
						layout.show(frame.getContentPane(), NEWACCOUNT_PANEL);
						JOptionPane.showMessageDialog(null,
								"Error: Username already in use. Try adding some abbritrary "
										+ "numbers no one likes to the end",
								"Account Creation Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					layout.show(frame.getContentPane(), NEWACCOUNT_PANEL);
					JOptionPane.showMessageDialog(null, "Error: Passwords Don't match. Please try again",
							"Account Creation Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		CreateAccount_btnCreate.setBounds(130, 225, 200, 25);
		CreateAccountJPanel.add(CreateAccount_btnCreate);

		CreateAccount_txtFirstName = new JTextField();
		CreateAccount_txtFirstName.setColumns(10);
		CreateAccount_txtFirstName.setBounds(130, 75, 200, 20);
		CreateAccountJPanel.add(CreateAccount_txtFirstName);

		CreateAccount_txtLastName = new JTextField();
		CreateAccount_txtLastName.setColumns(10);
		CreateAccount_txtLastName.setBounds(130, 110, 200, 20);
		CreateAccountJPanel.add(CreateAccount_txtLastName);

		JLabel CreateAccount_lblLastName = new JLabel("Last Name");
		CreateAccount_lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		CreateAccount_lblLastName.setBounds(46, 113, 74, 14);
		CreateAccountJPanel.add(CreateAccount_lblLastName);

		JLabel CreateAccount_lblPassword = new JLabel("Password");
		CreateAccount_lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		CreateAccount_lblPassword.setBounds(46, 148, 74, 14);
		CreateAccountJPanel.add(CreateAccount_lblPassword);

		JLabel CreateAccount_lblRepeatPassword = new JLabel("Repeat Password");
		CreateAccount_lblRepeatPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		CreateAccount_lblRepeatPassword.setBounds(4, 183, 116, 14);
		CreateAccountJPanel.add(CreateAccount_lblRepeatPassword);

		CreateAccount_passwordField = new JPasswordField();
		CreateAccount_passwordField.setBounds(130, 145, 200, 20);
		CreateAccountJPanel.add(CreateAccount_passwordField);

		CreateAccount_passwordFieldRepeat = new JPasswordField();
		CreateAccount_passwordFieldRepeat.setBounds(130, 180, 200, 20);
		CreateAccountJPanel.add(CreateAccount_passwordFieldRepeat);

		CreateAccount_txtUsername = new JTextField();
		CreateAccount_txtUsername.setColumns(10);
		CreateAccount_txtUsername.setBounds(130, 40, 200, 20);
		CreateAccountJPanel.add(CreateAccount_txtUsername);

		JLabel CreateAccount_lblUsername = new JLabel("Username");
		CreateAccount_lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		CreateAccount_lblUsername.setBounds(46, 43, 74, 14);
		CreateAccountJPanel.add(CreateAccount_lblUsername);
	}
}
