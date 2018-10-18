package bank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Window.Type;

public class GUI {

	private JFrame frame;
	CardLayout layout = new CardLayout(0, 0);

	private static List<BankAccount> ba = new ArrayList<BankAccount>();
	
	final static String MAIN_PANEL = "Main Panel";
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
	final static String WRONGPASSWORD_PANEL = "WrongPass Panel";
	final static String NEWACCOUNT_PANEL = "NewAccount Panel";

	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JPasswordField passwordField_3;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JPasswordField passwordField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_9;
	private JTextField textField_8;
	private JPasswordField passwordField_5;
	private JPasswordField passwordField_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		createTestAccount();

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
	
	
	private int findBankAccountIndex(String username) {
		// this would be so much easier if we just made a username thingy instead of f/m/l name combo.
		// too lazy to do it right now though so tommorow. Also need to add a check if it's unique
	}
	
	private static int generateAccNum() {

		// add a check if already used I guess?

		Random rand = new Random();

		int x[] = new int[9];
		for (int i = 0; i < 9; i++) {
			x[i] = rand.nextInt(9);
		}

		String accComb = "";
		for (int i : x) {
			accComb = accComb + String.valueOf(x[i]);
		}
		return Integer.valueOf(accComb);
	}

	private boolean createAcc(double amt, String fName, String mName, String lName, int acctNum) {
		try {
			ba.add(new BankAccount(amt, fName, mName, lName, acctNum));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// for testing purposes
	private static void createTestAccount() {
		ba.add(new BankAccount(1000, "Ricky", "Loves", "Lin", generateAccNum()));
	}

	private void initialize() {

		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(layout);

		JPanel loginJPanel = new JPanel();
		frame.getContentPane().add(loginJPanel, MAIN_PANEL);
		GridBagLayout gbl_loginJPanel = new GridBagLayout();
		gbl_loginJPanel.columnWidths = new int[] { 75, 74, 107, 0 };
		gbl_loginJPanel.rowHeights = new int[] { 91, 21, 20, 23, 0, 0, 0 };
		gbl_loginJPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_loginJPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		loginJPanel.setLayout(gbl_loginJPanel);

		JLabel lblNewLabel = new JLabel("Username:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		loginJPanel.add(lblNewLabel, gbc_lblNewLabel);

		txtUsername = new JTextField();
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.anchor = GridBagConstraints.NORTH;
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 0);
		gbc_txtUsername.gridx = 2;
		gbc_txtUsername.gridy = 1;
		loginJPanel.add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Password:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		loginJPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.NORTH;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		loginJPanel.add(passwordField, gbc_passwordField);

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (ba.get(this.accIndex).checkPassword(passwordField.getText())) {
					layout.show(frame.getContentPane(), CHOICE_PANEL);

				} else {
					layout.show(frame.getContentPane(), WRONGPASSWORD_PANEL);
					
				}
			}
		});
		GridBagConstraints gbc_btnEnter = new GridBagConstraints();
		gbc_btnEnter.insets = new Insets(0, 0, 5, 0);
		gbc_btnEnter.anchor = GridBagConstraints.NORTH;
		gbc_btnEnter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnter.gridx = 2;
		gbc_btnEnter.gridy = 3;
		loginJPanel.add(btnEnter, gbc_btnEnter);

		JButton NewAccountBtn = new JButton("Create A New Account");
		NewAccountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), NEWACCOUNT_PANEL);
			}
		});
		GridBagConstraints gbc_NewAccountBtn = new GridBagConstraints();
		gbc_NewAccountBtn.gridx = 2;
		gbc_NewAccountBtn.gridy = 5;
		loginJPanel.add(NewAccountBtn, gbc_NewAccountBtn);

		JPanel MainJPanel = new JPanel();
		frame.getContentPane().add(MainJPanel, CHOICE_PANEL);
		GridBagLayout gbl_MainJPanel = new GridBagLayout();
		gbl_MainJPanel.columnWidths = new int[] { 138, 136, 133 };
		gbl_MainJPanel.rowHeights = new int[] { 19, 75, 23, 23, 23, 0, 0, 0 };
		gbl_MainJPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_MainJPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		MainJPanel.setLayout(gbl_MainJPanel);

		JButton withdraw = new JButton("Withdraw");
		withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), WITHDRAW_PANEL);
			}
		});
		GridBagConstraints gbc_withdraw = new GridBagConstraints();
		gbc_withdraw.fill = GridBagConstraints.BOTH;
		gbc_withdraw.insets = new Insets(0, 0, 5, 5);
		gbc_withdraw.gridx = 0;
		gbc_withdraw.gridy = 1;
		MainJPanel.add(withdraw, gbc_withdraw);

		JButton getBalance = new JButton("Get Balance");
		getBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), GETBALANCE_PANEL);
			}
		});

		JButton deposit = new JButton("Deposit");
		deposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				layout.show(frame.getContentPane(), DEPOSIT_PANEL);
			}
		});
		GridBagConstraints gbc_deposit = new GridBagConstraints();
		gbc_deposit.fill = GridBagConstraints.BOTH;
		gbc_deposit.insets = new Insets(0, 0, 5, 5);
		gbc_deposit.gridx = 1;
		gbc_deposit.gridy = 1;
		MainJPanel.add(deposit, gbc_deposit);
		GridBagConstraints gbc_getBalance = new GridBagConstraints();
		gbc_getBalance.fill = GridBagConstraints.BOTH;
		gbc_getBalance.insets = new Insets(0, 0, 5, 0);
		gbc_getBalance.gridx = 2;
		gbc_getBalance.gridy = 1;
		MainJPanel.add(getBalance, gbc_getBalance);

		JButton display = new JButton("Display");
		display.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), DISPLAY_PANEL);
			}
		});

		JButton btnEmptyAccount = new JButton("Empty Account");
		btnEmptyAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), EMPTYACCOUNT_PANEL);
			}
		});
		GridBagConstraints gbc_btnEmptyAccount = new GridBagConstraints();
		gbc_btnEmptyAccount.fill = GridBagConstraints.BOTH;
		gbc_btnEmptyAccount.insets = new Insets(0, 0, 5, 5);
		gbc_btnEmptyAccount.gridheight = 2;
		gbc_btnEmptyAccount.gridx = 1;
		gbc_btnEmptyAccount.gridy = 2;
		MainJPanel.add(btnEmptyAccount, gbc_btnEmptyAccount);
		GridBagConstraints gbc_display = new GridBagConstraints();
		gbc_display.anchor = GridBagConstraints.NORTH;
		gbc_display.fill = GridBagConstraints.HORIZONTAL;
		gbc_display.insets = new Insets(0, 0, 5, 0);
		gbc_display.gridx = 2;
		gbc_display.gridy = 2;
		MainJPanel.add(display, gbc_display);

		JButton btnNewButton_4 = new JButton("Read Messages");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MESSAGES_PANEL);
			}
		});

		JButton btnNewButton = new JButton("Transfer To");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), TRANSFERTO_PANEL);
			}
		});

		JButton btnGetPassword = new JButton("Get Password");
		btnGetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), GETPASSWORD_PANEL);
			}
		});

		JButton btnNewButton_1 = new JButton("     Transfer From    ");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), TRANSFERFROM_PANEL);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 5;
		MainJPanel.add(btnNewButton_1, gbc_btnNewButton_1);
		GridBagConstraints gbc_btnGetPassword = new GridBagConstraints();
		gbc_btnGetPassword.anchor = GridBagConstraints.NORTH;
		gbc_btnGetPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGetPassword.insets = new Insets(0, 0, 5, 5);
		gbc_btnGetPassword.gridx = 1;
		gbc_btnGetPassword.gridy = 5;
		MainJPanel.add(btnGetPassword, gbc_btnGetPassword);

		JButton btnNewButton_3 = new JButton("Check Password");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), CHECKPASSWORD_PANEL);
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_3.gridx = 2;
		gbc_btnNewButton_3.gridy = 5;
		MainJPanel.add(btnNewButton_3, gbc_btnNewButton_3);

		JButton btnNewButton_2 = new JButton("Reset Password");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), RESETPASSWORD_PANEL);
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 6;
		MainJPanel.add(btnNewButton_2, gbc_btnNewButton_2);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 6;
		MainJPanel.add(btnNewButton, gbc_btnNewButton);
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_4.gridx = 2;
		gbc_btnNewButton_4.gridy = 6;
		MainJPanel.add(btnNewButton_4, gbc_btnNewButton_4);

		JPanel Withdraw = new JPanel();
		frame.getContentPane().add(Withdraw, WITHDRAW_PANEL);
		Withdraw.setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(142, 112, 150, 37);
		Withdraw.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Enter the Amount You Would Like To Withdraw:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(45, 65, 343, 36);
		Withdraw.add(lblNewLabel_2);

		JButton btnNewButton_5 = new JButton("Enter");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		btnNewButton_5.setBounds(153, 160, 127, 30);
		Withdraw.add(btnNewButton_5);

		JPanel Deposit = new JPanel();
		Deposit.setBackground(new Color(240, 240, 240));
		frame.getContentPane().add(Deposit, DEPOSIT_PANEL);
		Deposit.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Enter The Amount That You Would Deposit:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(56, 65, 321, 36);
		Deposit.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setBounds(142, 112, 150, 37);
		Deposit.add(textField_1);
		textField_1.setColumns(10);
		//
		JButton depositBtn = new JButton("Enter");
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		depositBtn.setBounds(153, 160, 127, 30);
		Deposit.add(depositBtn);

		JPanel Display = new JPanel();
		frame.getContentPane().add(Display, DISPLAY_PANEL);
		Display.setLayout(null);

		JButton btnNewButton_8 = new JButton("Back");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		btnNewButton_8.setBounds(335, 228, 89, 23);
		Display.add(btnNewButton_8);

		JLabel lblNewLabel_13 = new JLabel("Account Number:");
		lblNewLabel_13.setBounds(78, 56, 167, 14);
		Display.add(lblNewLabel_13);

		JLabel lblNewLabel_14 = new JLabel("Balance:");
		lblNewLabel_14.setBounds(78, 81, 146, 14);
		Display.add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("First Name:");
		lblNewLabel_15.setBounds(77, 106, 181, 14);
		Display.add(lblNewLabel_15);

		JLabel lblNewLabel_16 = new JLabel("Middle Name:");
		lblNewLabel_16.setBounds(77, 131, 147, 14);
		Display.add(lblNewLabel_16);

		JLabel lblNewLabel_17 = new JLabel("Last Name:");
		lblNewLabel_17.setBounds(80, 157, 144, 14);
		Display.add(lblNewLabel_17);

		JPanel CheckPassword = new JPanel();
		frame.getContentPane().add(CheckPassword, CHECKPASSWORD_PANEL);
		CheckPassword.setLayout(null);

		JButton btnNewButton_9 = new JButton("back");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		btnNewButton_9.setBounds(310, 206, 89, 23);
		CheckPassword.add(btnNewButton_9);

		JLabel lblNewLabel_18 = new JLabel("Check Password Here:");
		lblNewLabel_18.setBounds(54, 67, 224, 14);
		CheckPassword.add(lblNewLabel_18);

		JPanel GetPassword = new JPanel();
		frame.getContentPane().add(GetPassword, GETPASSWORD_PANEL);
		GetPassword.setLayout(null);

		JButton btnNewButton_10 = new JButton("Back");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		btnNewButton_10.setBounds(309, 203, 89, 23);
		GetPassword.add(btnNewButton_10);

		JLabel label = new JLabel("(Add the code here)  ");
		label.setBounds(47, 101, 224, 14);
		GetPassword.add(label);

		JPanel EmptyAccount = new JPanel();
		frame.getContentPane().add(EmptyAccount, EMPTYACCOUNT_PANEL);
		EmptyAccount.setLayout(null);

		JButton btnNewButton_11 = new JButton("Enter");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		btnNewButton_11.setBounds(296, 198, 89, 23);
		EmptyAccount.add(btnNewButton_11);

		JLabel lblNewLabel_12 = new JLabel("");
		lblNewLabel_12.setBounds(71, 50, 269, 14);
		EmptyAccount.add(lblNewLabel_12);

		JLabel lblNewLabel_19 = new JLabel("(Add the Writing from the program here:)");
		lblNewLabel_19.setBounds(121, 75, 264, 4);
		EmptyAccount.add(lblNewLabel_19);

		JPanel ReadMessages = new JPanel();
		frame.getContentPane().add(ReadMessages, MESSAGES_PANEL);
		ReadMessages.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 240);
		ReadMessages.add(scrollPane);

		JTextArea textArea = new JTextArea(10, 10);
		scrollPane.setViewportView(textArea);

		JButton btnEnter_1 = new JButton("Enter");
		btnEnter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		scrollPane.setColumnHeaderView(btnEnter_1);

		JPanel ResetPasswordMain = new JPanel();
		frame.getContentPane().add(ResetPasswordMain, RESETPASSWORD_PANEL);
		ResetPasswordMain.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Please enter your current password: ");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(43, 43, 246, 38);
		ResetPasswordMain.add(lblNewLabel_4);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(42, 78, 156, 20);
		ResetPasswordMain.add(passwordField_1);

		JButton btnNewButton_7 = new JButton("Enter");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), RESETPASS_PANEL);
			}
		});
		btnNewButton_7.setBounds(105, 109, 89, 23);
		ResetPasswordMain.add(btnNewButton_7);

		JPanel ResetPass = new JPanel();
		frame.getContentPane().add(ResetPass, RESETPASS_PANEL);
		ResetPass.setLayout(null);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(60, 79, 174, 20);
		ResetPass.add(passwordField_2);

		passwordField_3 = new JPasswordField();
		passwordField_3.setBounds(60, 120, 174, 20);
		ResetPass.add(passwordField_3);

		JLabel label_1 = new JLabel("<-Current Password");
		label_1.setBounds(266, 82, 158, 14);
		ResetPass.add(label_1);

		JLabel label_2 = new JLabel("<- New Password");
		label_2.setBounds(266, 123, 158, 14);
		ResetPass.add(label_2);

		JButton btnNewButton_12 = new JButton("Enter");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		btnNewButton_12.setBounds(152, 160, 89, 23);
		ResetPass.add(btnNewButton_12);

		JPanel GetBalance = new JPanel();
		frame.getContentPane().add(GetBalance, GETBALANCE_PANEL);
		GetBalance.setLayout(null);

		JButton btnNewButton_13 = new JButton("Back");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		btnNewButton_13.setBounds(335, 228, 89, 23);
		GetBalance.add(btnNewButton_13);

		JLabel lblNewLabel_20 = new JLabel("Return Balance Here:");
		lblNewLabel_20.setBounds(120, 55, 246, 23);
		GetBalance.add(lblNewLabel_20);

		JPanel TransferTo = new JPanel();
		frame.getContentPane().add(TransferTo, TRANSFERTO_PANEL);
		TransferTo.setLayout(null);

		textField_2 = new JTextField();
		textField_2.setBounds(129, 49, 202, 20);
		TransferTo.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(129, 120, 202, 20);
		TransferTo.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(131, 191, 202, 20);
		TransferTo.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Amount");
		lblNewLabel_5.setBounds(53, 51, 66, 17);
		TransferTo.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Bank Account");
		lblNewLabel_6.setBounds(26, 123, 95, 14);
		TransferTo.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Other Account");
		lblNewLabel_7.setBounds(26, 194, 95, 14);
		TransferTo.add(lblNewLabel_7);

		JButton btnNewButton_14 = new JButton("Enter");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		btnNewButton_14.setBounds(290, 228, 89, 23);
		TransferTo.add(btnNewButton_14);

		JPanel TransferFrom = new JPanel();
		frame.getContentPane().add(TransferFrom, TRANSFERFROM_PANEL);
		TransferFrom.setLayout(null);

		passwordField_4 = new JPasswordField();
		passwordField_4.setBounds(131, 187, 200, 20);
		TransferFrom.add(passwordField_4);

		textField_5 = new JTextField();
		textField_5.setBounds(131, 37, 200, 20);
		TransferFrom.add(textField_5);
		textField_5.setColumns(10);

		textField_6 = new JTextField();
		textField_6.setBounds(131, 87, 200, 20);
		TransferFrom.add(textField_6);
		textField_6.setColumns(10);

		textField_7 = new JTextField();
		textField_7.setBounds(131, 137, 200, 20);
		TransferFrom.add(textField_7);
		textField_7.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Bank Account:");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_8.setBounds(39, 40, 82, 14);
		TransferFrom.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("Other Account:");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_9.setBounds(39, 90, 82, 14);
		TransferFrom.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("Amount:");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_10.setBounds(39, 140, 82, 14);
		TransferFrom.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Password:");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_11.setBounds(39, 190, 82, 14);
		TransferFrom.add(lblNewLabel_11);

		JButton btnNewButton_15 = new JButton("Enter");
		btnNewButton_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}

		});
		btnNewButton_15.setBounds(296, 228, 89, 23);
		TransferFrom.add(btnNewButton_15);

		JPanel WrongPass = new JPanel();
		frame.getContentPane().add(WrongPass, WRONGPASSWORD_PANEL);
		WrongPass.setLayout(null);

		JLabel lblWrongPasswordPlease = new JLabel("Wrong Password Please Try Again");
		lblWrongPasswordPlease.setBounds(116, 118, 207, 42);
		WrongPass.add(lblWrongPasswordPlease);

		JButton btnBack_1 = new JButton("Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().add(loginJPanel, MAIN_PANEL);
			}
		});
		btnBack_1.setBounds(175, 171, 89, 23);
		WrongPass.add(btnBack_1);

		JPanel NewAccount = new JPanel();

		frame.getContentPane().add(NewAccount, NEWACCOUNT_PANEL);
		NewAccount.setLayout(null);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setBounds(36, 33, 84, 14);
		NewAccount.add(lblFirstName);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), MAIN_PANEL);
			}
		});
		btnBack.setBounds(128, 227, 65, 23);
		NewAccount.add(btnBack);

		JButton btnCreate = new JButton("Create Account");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
				
			}
		});
		btnCreate.setBounds(203, 227, 148, 23);
		NewAccount.add(btnCreate);

		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(130, 30, 200, 20);
		NewAccount.add(textField_9);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(130, 80, 200, 20);
		NewAccount.add(textField_8);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLastName.setBounds(46, 83, 74, 14);
		NewAccount.add(lblLastName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(46, 133, 74, 14);
		NewAccount.add(lblPassword);

		JLabel lblRepeatPassword = new JLabel("Repeat Password");
		lblRepeatPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepeatPassword.setBounds(4, 183, 116, 14);
		NewAccount.add(lblRepeatPassword);

		passwordField_5 = new JPasswordField();
		passwordField_5.setBounds(130, 130, 200, 20);
		NewAccount.add(passwordField_5);

		passwordField_6 = new JPasswordField();
		passwordField_6.setBounds(130, 180, 200, 20);
		NewAccount.add(passwordField_6);
	}

}
