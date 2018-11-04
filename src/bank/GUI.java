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
	private JTextField transToTxtBankAccount;
	private JTextField transToTxtOtherAccount;
	private JPasswordField transFromPassword;
	private JTextField transFromTxtBankAccount;
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

		JPanel loginJPanel = new JPanel();
		frame.getContentPane().add(loginJPanel, LOGIN_PANEL);
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

		loginBtnEnter.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				setBankAccountIndex(loginTxtUsername.getText());
				
				if (ba.get(index).checkPassword(loginPassword.getText())) {
					layout.show(frame.getContentPane(), CHOICE_PANEL);
				} else {
					JOptionPane.showMessageDialog(null, "Wrong Password or Username. Please try again.", "Login Error",
							JOptionPane.ERROR_MESSAGE);
				}
				loginPassword.setText("");
			}
		});
		
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

		JButton loginBtnRegister = new JButton("Create A New Account");
		loginBtnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), REGISTER_PANEL);
			}
		});
		
		GridBagConstraints gbc_loginBtnRegister = new GridBagConstraints();
		gbc_loginBtnRegister.gridx = 2;
		gbc_loginBtnRegister.gridy = 5;
		loginJPanel.add(loginBtnRegister, gbc_loginBtnRegister);

		JPanel mainJPanel = new JPanel();
		frame.getContentPane().add(mainJPanel, CHOICE_PANEL);
		GridBagLayout gbl_mainJPanel = new GridBagLayout();
		gbl_mainJPanel.columnWidths = new int[] { 138, 136, 133 };
		gbl_mainJPanel.rowHeights = new int[] { 19, 75, 50, 23, 0, 0, 0 };
		gbl_mainJPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_mainJPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainJPanel.setLayout(gbl_mainJPanel);

		mainBtnWithdraw = new JButton("Withdraw");
		mainBtnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), WITHDRAW_PANEL);
			}
		});
		
		GridBagConstraints gbc_mainBtnWithdraw = new GridBagConstraints();
		gbc_mainBtnWithdraw.fill = GridBagConstraints.BOTH;
		gbc_mainBtnWithdraw.insets = new Insets(0, 3, 3, 3);
		gbc_mainBtnWithdraw.gridx = 0;
		gbc_mainBtnWithdraw.gridy = 1;
		mainJPanel.add(mainBtnWithdraw, gbc_mainBtnWithdraw);

		JButton mainBtnBalance = new JButton("Get Balance");
		mainBtnBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), GETBALANCE_PANEL);
			}
		});

		JButton mainBtnDeposit = new JButton("Deposit");
		mainBtnDeposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				layout.show(frame.getContentPane(), DEPOSIT_PANEL);
			}
		});
		
		GridBagConstraints gbc_mainBtnDeposit = new GridBagConstraints();
		gbc_mainBtnDeposit.fill = GridBagConstraints.BOTH;
		gbc_mainBtnDeposit.insets = new Insets(0, 3, 3, 3);
		gbc_mainBtnDeposit.gridx = 1;
		gbc_mainBtnDeposit.gridy = 1;
		mainJPanel.add(mainBtnDeposit, gbc_mainBtnDeposit);
		GridBagConstraints gbc_mainBtnBalance = new GridBagConstraints();
		gbc_mainBtnBalance.fill = GridBagConstraints.BOTH;
		gbc_mainBtnBalance.insets = new Insets(0, 3, 3, 3);
		gbc_mainBtnBalance.gridx = 2;
		gbc_mainBtnBalance.gridy = 1;
		mainJPanel.add(mainBtnBalance, gbc_mainBtnBalance);

		JButton mainBtnEmpty = new JButton("Empty Account");
		mainBtnEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), EMPTYACCOUNT_PANEL);
			}
		});
		
		GridBagConstraints gbc_mainBtnEmpty = new GridBagConstraints();
		gbc_mainBtnEmpty.fill = GridBagConstraints.BOTH;
		gbc_mainBtnEmpty.insets = new Insets(3, 3, 0, 3);
		gbc_mainBtnEmpty.gridx = 1;
		gbc_mainBtnEmpty.gridy = 2;
		mainJPanel.add(mainBtnEmpty, gbc_mainBtnEmpty);

		JButton mainBtnGetMessages = new JButton("Read Messages");
		// Action Listener added later in code

		JButton mainBtnTransTo = new JButton("Transfer To");
		mainBtnTransTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), TRANSFERTO_PANEL);
			}
		});

		JButton mainBtnGetPassword = new JButton("Get Password");
		mainBtnGetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), GETPASSWORD_PANEL);
			}
		});

		JButton mainBtnTransFrom = new JButton("Transfer From");
		mainBtnTransFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), TRANSFERFROM_PANEL);
			}
		});
		
		GridBagConstraints gbc_mainBtnTransFrom = new GridBagConstraints();
		gbc_mainBtnTransFrom.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnTransFrom.insets = new Insets(0, 3, 3, 3);
		gbc_mainBtnTransFrom.gridx = 0;
		gbc_mainBtnTransFrom.gridy = 4;
		mainJPanel.add(mainBtnTransFrom, gbc_mainBtnTransFrom);
		GridBagConstraints gbc_mainBtnGetPassword = new GridBagConstraints();
		gbc_mainBtnGetPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnGetPassword.insets = new Insets(0, 3, 3, 3);
		gbc_mainBtnGetPassword.gridx = 1;
		gbc_mainBtnGetPassword.gridy = 4;
		mainJPanel.add(mainBtnGetPassword, gbc_mainBtnGetPassword);

		JButton mainBtnCheckPassword = new JButton("Check Password");
		mainBtnCheckPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), CHECKPASSWORD_PANEL);
			}
		});
		
		GridBagConstraints gbc_mainBtnCheckPassword = new GridBagConstraints();
		gbc_mainBtnCheckPassword.anchor = GridBagConstraints.NORTH;
		gbc_mainBtnCheckPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnCheckPassword.insets = new Insets(0, 3, 3, 3);
		gbc_mainBtnCheckPassword.gridx = 2;
		gbc_mainBtnCheckPassword.gridy = 4;
		mainJPanel.add(mainBtnCheckPassword, gbc_mainBtnCheckPassword);

		JButton mainBtnResetPassword = new JButton("Reset Password");
		mainBtnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), RESETPASSWORD_PANEL);
			}
		});
		
		GridBagConstraints gbc_mainBtnResetPassword = new GridBagConstraints();
		gbc_mainBtnResetPassword.insets = new Insets(3, 3, 0, 3);
		gbc_mainBtnResetPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnResetPassword.gridx = 0;
		gbc_mainBtnResetPassword.gridy = 5;
		mainJPanel.add(mainBtnResetPassword, gbc_mainBtnResetPassword);
		GridBagConstraints gbc_mainBtnTransTo = new GridBagConstraints();
		gbc_mainBtnTransTo.insets = new Insets(3, 3, 0, 3);
		gbc_mainBtnTransTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnTransTo.gridx = 1;
		gbc_mainBtnTransTo.gridy = 5;
		mainJPanel.add(mainBtnTransTo, gbc_mainBtnTransTo);
		GridBagConstraints gbc_mainBtnGetMessages = new GridBagConstraints();
		gbc_mainBtnGetMessages.insets = new Insets(3, 3, 0, 3);
		gbc_mainBtnGetMessages.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnGetMessages.anchor = GridBagConstraints.NORTH;
		gbc_mainBtnGetMessages.gridx = 2;
		gbc_mainBtnGetMessages.gridy = 5;
		mainJPanel.add(mainBtnGetMessages, gbc_mainBtnGetMessages);

		JPanel withdrawJPanel = new JPanel();
		frame.getContentPane().add(withdrawJPanel, WITHDRAW_PANEL);
		withdrawJPanel.setLayout(null);

		withdrawTxtAmount = new JTextField();
		withdrawTxtAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		withdrawTxtAmount.setBounds(142, 112, 150, 37);
		withdrawJPanel.add(withdrawTxtAmount);
		withdrawTxtAmount.setColumns(10);

		JLabel withdrawLblAmountPromt = new JLabel("Enter the Amount You Would Like To Withdraw:");
		withdrawLblAmountPromt.setHorizontalAlignment(SwingConstants.CENTER);
		withdrawLblAmountPromt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		withdrawLblAmountPromt.setBounds(45, 65, 343, 36);
		withdrawJPanel.add(withdrawLblAmountPromt);

		JButton withdrawBtnWithdraw = new JButton("Withdraw");
		withdrawBtnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
				ba.get(index).withdraw(Double.valueOf(withdrawTxtAmount.getText()));
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

		JPanel depositJPanel = new JPanel();
		depositJPanel.setBackground(new Color(240, 240, 240));
		frame.getContentPane().add(depositJPanel, DEPOSIT_PANEL);
		depositJPanel.setLayout(null);

		JLabel depositLblAmountPrompt = new JLabel("Enter The Amount That You Would Deposit:");
		depositLblAmountPrompt.setHorizontalAlignment(SwingConstants.CENTER);
		depositLblAmountPrompt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		depositLblAmountPrompt.setBounds(56, 65, 321, 36);
		depositJPanel.add(depositLblAmountPrompt);

		depositTxtAmount = new JTextField();
		depositTxtAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		depositTxtAmount.setBounds(142, 112, 150, 37);
		depositJPanel.add(depositTxtAmount);
		depositTxtAmount.setColumns(10);
		
		JButton depositBtnDeposit = new JButton("Deposit");
		depositBtnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ba.get(index).deposit(Double.valueOf(depositTxtAmount.getText()));
				layout.show(frame.getContentPane(), CHOICE_PANEL);
				
			}
		});
		
		depositBtnDeposit.setBounds(153, 160, 127, 30);
		depositJPanel.add(depositBtnDeposit);
		
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
		
		JPanel accInfoJPanel = new JPanel();
		frame.getContentPane().add(accInfoJPanel, DISPLAY_PANEL);
		accInfoJPanel.setLayout(null);

		JButton accInfoBack = new JButton("Back");
		accInfoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		
		accInfoBack.setBounds(335, 228, 89, 23);
		accInfoJPanel.add(accInfoBack);

		JLabel accInfoLblAccNum = new JLabel("Account Number:");
		accInfoLblAccNum.setHorizontalAlignment(SwingConstants.RIGHT);
		accInfoLblAccNum.setBounds(50, 55, 89, 14);
		accInfoJPanel.add(accInfoLblAccNum);

		JLabel accInfoLblBalance = new JLabel("Balance:");
		accInfoLblBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		accInfoLblBalance.setBounds(50, 90, 89, 14);
		accInfoJPanel.add(accInfoLblBalance);

		JLabel accInfoLblFirstName = new JLabel("First Name:");
		accInfoLblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		accInfoLblFirstName.setBounds(50, 125, 90, 14);
		accInfoJPanel.add(accInfoLblFirstName);

		JLabel accInfoLblLastName = new JLabel("Last Name:");
		accInfoLblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		accInfoLblLastName.setBounds(50, 160, 89, 14);
		accInfoJPanel.add(accInfoLblLastName);

		JLabel accInfoLblAccNumValue = new JLabel();
		accInfoLblAccNumValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accInfoLblAccNumValue.setBounds(149, 50, 226, 20);
		accInfoJPanel.add(accInfoLblAccNumValue);

		JLabel accInfoLblBalanceValue = new JLabel("New label");
		accInfoLblBalanceValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accInfoLblBalanceValue.setBounds(149, 84, 226, 20);
		accInfoJPanel.add(accInfoLblBalanceValue);

		JLabel accInfoLblFirstNameValue = new JLabel("New label");
		accInfoLblFirstNameValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accInfoLblFirstNameValue.setBounds(149, 119, 226, 20);
		accInfoJPanel.add(accInfoLblFirstNameValue);

		JLabel accInfoLblLastNameValue = new JLabel("New label");
		accInfoLblLastNameValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accInfoLblLastNameValue.setBounds(149, 154, 226, 20);
		accInfoJPanel.add(accInfoLblLastNameValue);

		JPanel emptyJPanel = new JPanel();
		frame.getContentPane().add(emptyJPanel, EMPTYACCOUNT_PANEL);
		emptyJPanel.setLayout(null);

		JButton emptyBtnBack = new JButton("Back");
		emptyBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		
		emptyBtnBack.setBounds(296, 198, 89, 23);
		emptyJPanel.add(emptyBtnBack);

		JLabel emptyLblMessage = new JLabel("(Add the Writing from the program here:)");
		emptyLblMessage.setBounds(94, 75, 264, 23);
		emptyJPanel.add(emptyLblMessage);

		JPanel messageJPanel = new JPanel();
		frame.getContentPane().add(messageJPanel, MESSAGES_PANEL);
		messageJPanel.setLayout(new BorderLayout(0, 0));

		JButton messageBtnBack = new JButton("Back");
		messageBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);

			}
		});
		
		messageBtnBack.setVerticalAlignment(SwingConstants.BOTTOM);
		messageJPanel.add(messageBtnBack);

		JScrollPane messageScrollPane = new JScrollPane();
		messageJPanel.add(messageScrollPane, BorderLayout.NORTH);

		JTextArea messageTxtAMessage = new JTextArea(13, 10);
		messageTxtAMessage.setText("");
		messageTxtAMessage.setEditable(false);
		messageScrollPane.setViewportView(messageTxtAMessage);
		
	    // Makes it so txtAMessage is updated on click
		mainBtnGetMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageTxtAMessage.setText(ba.get(index).getMessage());
				layout.show(frame.getContentPane(), MESSAGES_PANEL);
			}
		});

		JPanel getBalance = new JPanel();
		frame.getContentPane().add(getBalance, GETBALANCE_PANEL);
		getBalance.setLayout(null);

		JButton getBalanceBtnBack = new JButton("Back");
		getBalanceBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		
		getBalanceBtnBack.setBounds(335, 228, 89, 23);
		getBalance.add(getBalanceBtnBack);

		JLabel getBalanceLblBalance = new JLabel("Return Balance Here:");
		getBalanceLblBalance.setBounds(120, 55, 246, 23);
		getBalance.add(getBalanceLblBalance);

		JPanel transToJPanel = new JPanel();
		frame.getContentPane().add(transToJPanel, TRANSFERTO_PANEL);
		transToJPanel.setLayout(null);

		transToTxtAmount = new JTextField();
		transToTxtAmount.setBounds(129, 49, 202, 20);
		transToJPanel.add(transToTxtAmount);
		transToTxtAmount.setColumns(10);

		transToTxtBankAccount = new JTextField();
		transToTxtBankAccount.setBounds(129, 120, 202, 20);
		transToJPanel.add(transToTxtBankAccount);
		transToTxtBankAccount.setColumns(10);

		transToTxtOtherAccount = new JTextField();
		transToTxtOtherAccount.setBounds(131, 191, 202, 20);
		transToJPanel.add(transToTxtOtherAccount);
		transToTxtOtherAccount.setColumns(10);

		JLabel transToLblAmount = new JLabel("Amount");
		transToLblAmount.setBounds(53, 51, 66, 17);
		transToJPanel.add(transToLblAmount);

		JLabel transToLblBankAccount = new JLabel("Bank Account");
		transToLblBankAccount.setBounds(26, 123, 95, 14);
		transToJPanel.add(transToLblBankAccount);

		JLabel transToLblOtherAccount = new JLabel("Other Account");
		transToLblOtherAccount.setBounds(26, 194, 95, 14);
		transToJPanel.add(transToLblOtherAccount);

		JButton transToBtnEnter = new JButton("Enter");
		transToBtnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		
		transToBtnEnter.setBounds(290, 228, 89, 23);
		transToJPanel.add(transToBtnEnter);

		JPanel transFromJPanel = new JPanel();
		frame.getContentPane().add(transFromJPanel, TRANSFERFROM_PANEL);
		transFromJPanel.setLayout(null);

		transFromPassword = new JPasswordField();
		transFromPassword.setBounds(131, 187, 200, 20);
		transFromJPanel.add(transFromPassword);

		transFromTxtBankAccount = new JTextField();
		transFromTxtBankAccount.setBounds(131, 37, 200, 20);
		transFromJPanel.add(transFromTxtBankAccount);
		transFromTxtBankAccount.setColumns(10);

		transFromTxtOtherAccount = new JTextField();
		transFromTxtOtherAccount.setBounds(131, 87, 200, 20);
		transFromJPanel.add(transFromTxtOtherAccount);
		transFromTxtOtherAccount.setColumns(10);

		transFromTxtAmount = new JTextField();
		transFromTxtAmount.setBounds(131, 137, 200, 20);
		transFromJPanel.add(transFromTxtAmount);
		transFromTxtAmount.setColumns(10);

		JLabel transFromLblBankAccount = new JLabel("Bank Account:");
		transFromLblBankAccount.setHorizontalAlignment(SwingConstants.TRAILING);
		transFromLblBankAccount.setBounds(39, 40, 82, 14);
		transFromJPanel.add(transFromLblBankAccount);

		JLabel transFromLblOtherAccount = new JLabel("Other Account:");
		transFromLblOtherAccount.setHorizontalAlignment(SwingConstants.TRAILING);
		transFromLblOtherAccount.setBounds(39, 90, 82, 14);
		transFromJPanel.add(transFromLblOtherAccount);

		JLabel transFromLblAmount = new JLabel("Amount:");
		transFromLblAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		transFromLblAmount.setBounds(39, 140, 82, 14);
		transFromJPanel.add(transFromLblAmount);

		JLabel transFromTxtPassword = new JLabel("Password:");
		transFromTxtPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		transFromTxtPassword.setBounds(39, 190, 82, 14);
		transFromJPanel.add(transFromTxtPassword);

		JButton transFromBtnEnter = new JButton("Enter");
		transFromBtnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		
		transFromBtnEnter.setBounds(296, 228, 89, 23);
		transFromJPanel.add(transFromBtnEnter);

		JPanel registerJPanel = new JPanel();

		frame.getContentPane().add(registerJPanel, REGISTER_PANEL);
		registerJPanel.setLayout(null);

		JLabel regLblFirstName = new JLabel("First Name");
		regLblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		regLblFirstName.setBounds(36, 78, 84, 14);
		registerJPanel.add(regLblFirstName);

		JButton regBtnBack = new JButton("Back");
		regBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), LOGIN_PANEL);
			}
		});
		
		regBtnBack.setBounds(55, 225, 65, 25);
		registerJPanel.add(regBtnBack);

		JButton regBtnCreate = new JButton("Create Account");
		regBtnCreate.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")

			public void actionPerformed(ActionEvent e) {
				
				if ((regPassword.getText().trim().isEmpty()
						|| regPasswordRepeat.getText().equals(null)
						|| regTxtUsername.getText().trim().isEmpty()
						|| regTxtFirstName.getText().trim().isEmpty()
						|| regTxtLastName.getText().trim().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Dude. Put stuff in the text box. Kind of the point", "bruh",
							JOptionPane.ERROR_MESSAGE);
				 
				} else if (regPassword.getText().equals(regPasswordRepeat.getText())) {
					
					String username = regTxtUsername.getText();
					String firstName = regTxtFirstName.getText();
					String lastName = regTxtLastName.getText();
					String password = regPassword.getText();
					
					if(!isPasswordsecure(password)) {
						JOptionPane.showMessageDialog(null, "ERROR: Password did not meet one of the following fields: 1+ capital, 1+ lowercase "
								+ "9+ characters. NOTE: PASSWORDS STARTING WITH 12345 IS A LAZY PASS FOR TESTING");
								}
					
					else if (createAcc(username, DEFAULT_MONEY_VALUE, firstName, lastName, generateAccNum(), password)) {
						
						// Add password argument to BankAccount constructor
						// Update createAcc to accept the parameter
						
						layout.show(frame.getContentPane(), LOGIN_PANEL);
						JOptionPane.showMessageDialog(null, "Account Created Successfuly", "Account Creation Success",
								JOptionPane.PLAIN_MESSAGE);
							
							//add pass clears
					} else {
						layout.show(frame.getContentPane(), REGISTER_PANEL);
						JOptionPane.showMessageDialog(null,
								"Error: Username already in use. Try adding some abbritrary "
										+ "numbers no one likes to the end",
								"Account Creation Error", JOptionPane.ERROR_MESSAGE);
							
							// add pass clears
					}
				} else {
					layout.show(frame.getContentPane(), REGISTER_PANEL);
					JOptionPane.showMessageDialog(null, "Error: Passwords Don't match. Please try again",
							"Account Creation Error", JOptionPane.ERROR_MESSAGE);
				}
				
				regPassword.setText("");
				regPasswordRepeat.setText("");
				
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
		
		JPanel resetPasswordPanel = new JPanel();
		frame.getContentPane().add(resetPasswordPanel, "name_2314993320963");
	}
}
