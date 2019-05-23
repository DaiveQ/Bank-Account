package bank;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
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

public class GUI {

	// BEGIN USING KEY BINDINGS INSTEAD OF KEY LISTENER

	// Also give functionality to Transfer To/From
	private JFrame frame;
	CardLayout layout = new CardLayout(0, 0);

	private BankAccount ba;
	private DecimalFormat df = new DecimalFormat("#0.00");

	static final String CHOICE_PANEL = "Choice Panel";
	static final String WITHDRAW_PANEL = "Withdraw Panel";
	static final String DEPOSIT_PANEL = "Deposit Panel";
	static final String ACCOUNTDETAIL_PANEL = "AccountDetail Panel";
	static final String GETPASSWORD_PANEL = "GetPassword Panel";
	static final String EMPTYACCOUNT_PANEL = "EmptyAccount Panel";
	static final String MESSAGES_PANEL = "Messages Panel";
	static final String RESETPASSWORD_PANEL = "ResetPassword Panel";
	static final String GETBALANCE_PANEL = "GetBalance Panel";
	static final String TRANSFERTO_PANEL = "TransferTo Panel";
	static final String TRANSFERFROM_PANEL = "TransferFrom Panel";
	static final String REGISTER_PANEL = "Register Panel";
	static final String LOGIN_PANEL = "Login Panel";

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
	private JPasswordField changePasswordFieldRepeat;
	private JPasswordField changePasswordFieldNew;
	private JPasswordField changePasswordField;

	BAHttpURLConnection http = new BAHttpURLConnection();

	public static void main(String[] args) {
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

	private void createErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void createSuccessMessage(String message) {
		JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.PLAIN_MESSAGE);
	}

	private void initialize() {

		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/res/favicon.png")));
		frame.setTitle("Bank Account");
		frame.setAlwaysOnTop(false);
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
				if (loginTxtUsername.getText().trim().isEmpty() || loginPassword.getText().trim().isEmpty()) {
					createErrorMessage("Please fill out all fields");
					loginPassword.setText("");
					return;
				}

				BAHttpURLConnection http = new BAHttpURLConnection();
				int response = http.login(loginTxtUsername.getText(), loginPassword.getText(), ba);

				if (response == http.CONNECTIONERROR) {
					createErrorMessage("Connection Error");
				}

				else if (response == http.UNEXPECTEDERROR) {
					createErrorMessage("Unexpected error occured");
				}

				else if (response == http.FAILED) {
					createErrorMessage("Username or password incorrect");
				}

				else if (response == http.SUCCESS) {
					ba = http.parseInitialAccountData();
					layout.show(frame.getContentPane(), CHOICE_PANEL);
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

			@Override
			public void keyReleased(KeyEvent arg0) {
				// Only keyPressed needed
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// Only keyPressed needed
			}

		});

		loginPassword.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBtnEnter.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// Only keyPressed needed
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// Only keyPressed needed
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

				loginTxtUsername.setText("");
				loginPassword.setText("");

				layout.show(frame.getContentPane(), REGISTER_PANEL);
			}
		});

		GridBagConstraints gbc_loginBtnRegister = new GridBagConstraints();
		gbc_loginBtnRegister.gridx = 2;
		gbc_loginBtnRegister.gridy = 5;
		loginJPanel.add(loginBtnRegister, gbc_loginBtnRegister);

		JPanel withdrawJPanel = new JPanel();
		frame.getContentPane().add(withdrawJPanel, WITHDRAW_PANEL);
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
				BAHttpURLConnection http = new BAHttpURLConnection();

				if (ba.isLegalAmount(withdrawTxtAmount.getText())) {
					if (withdrawTxtAmount.getText().trim().isEmpty())
						createErrorMessage("Please fill in all fields");
					else {

						int response = http.withdraw(ba.getUsername(), Double.valueOf(withdrawTxtAmount.getText()));

						if (response == http.FAILED)
							createErrorMessage("Withdraw failed");
						else if (response == http.BADVALUE)
							createErrorMessage("Bad Value. Please Check Balance");
						else if (response == http.CONNECTIONERROR)
							createErrorMessage("Connection failed");

						else if (response == http.SUCCESS)
							createSuccessMessage("Withdraw successful");

						else
							createErrorMessage("Unexpected error");
					}

					withdrawTxtAmount.setText("");
					layout.show(frame.getContentPane(), CHOICE_PANEL);
				} else
					createErrorMessage("Bad value entered");
			}
		});

		withdrawTxtAmount.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					withdrawBtnWithdraw.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// Only keyPressed needed
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// Only keyPressed needed
			}
		});

		withdrawBtnWithdraw.setBounds(153, 160, 127, 30);
		withdrawJPanel.add(withdrawBtnWithdraw);

		JButton withdrawBtnBack = new JButton("Back");

		withdrawBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});

		withdrawBtnBack.setBounds(335, 227, 89, 23);
		withdrawJPanel.add(withdrawBtnBack);

		JPanel depositJPanel = new JPanel();
		depositJPanel.setBackground(new Color(240, 240, 240));
		frame.getContentPane().add(depositJPanel, DEPOSIT_PANEL);
		depositJPanel.setLayout(null);

		depositTxtAmount = new JTextField();
		depositTxtAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		depositTxtAmount.setBounds(142, 112, 150, 37);
		depositJPanel.add(depositTxtAmount);
		depositTxtAmount.setColumns(10);

		JButton depositBtnDeposit = new JButton("Deposit");
		depositBtnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BAHttpURLConnection http = new BAHttpURLConnection();

				if (ba.isLegalAmount(depositTxtAmount.getText())) {
					if (depositTxtAmount.getText().trim().isEmpty())
						createErrorMessage("Please fill in all fields");
					else {

						int response = http.deposit(ba.getUsername(), Double.valueOf(depositTxtAmount.getText()));

						if (response == http.FAILED)
							createErrorMessage("Deposit failed");

						else if (response == http.CONNECTIONERROR)
							createErrorMessage("Connection failed");

						else if (response == http.SUCCESS)
							createSuccessMessage("Deposit successful");

						else
							createErrorMessage("Unexpected error");
					}

					depositTxtAmount.setText("");
					layout.show(frame.getContentPane(), CHOICE_PANEL);
				} else
					createErrorMessage("Bad value entered");
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
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		depositBtnBack.setBounds(335, 227, 89, 23);
		depositJPanel.add(depositBtnBack);

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
		frame.getContentPane().add(accDetailJPanel, ACCOUNTDETAIL_PANEL);
		accDetailJPanel.setLayout(null);

		JButton accDetailBack = new JButton("Back");
		accDetailBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
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
		frame.getContentPane().add(emptyJPanel, EMPTYACCOUNT_PANEL);
		emptyJPanel.setLayout(null);

		JButton emptyBtnBack = new JButton("Back");
		emptyBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
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

				int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to empty your account?",
						"Are you sure?", JOptionPane.YES_NO_OPTION);

				if (response == JOptionPane.YES_OPTION) {

					// remember to change the it
					ba.emptyAccount();
					emptyLblMessage.setText("So much for \"leaving something for a rainy day\"...");
					JOptionPane.showMessageDialog(frame, "Shame on you!", "SHAME", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame,
							"Operation has been canceled. You still pressed it in the first place so SHAME.", "SHAME",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		emptyBtnEmptyAcc.setFont(new Font("Tahoma", Font.PLAIN, 35));
		emptyBtnEmptyAcc.setBounds(10, 11, 414, 205);
		emptyJPanel.add(emptyBtnEmptyAcc);

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
		messageTxtAMessage.setText("MESSAGE SET IN CODE");
		messageTxtAMessage.setEditable(false);
		messageScrollPane.setViewportView(messageTxtAMessage);

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

		JLabel getBalanceLblBalance = new JLabel("BALANCE SET IN CODE");
		getBalanceLblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		getBalanceLblBalance.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		getBalanceLblBalance.setBounds(10, 11, 414, 207);
		getBalance.add(getBalanceLblBalance);

		JPanel transToJPanel = new JPanel();
		frame.getContentPane().add(transToJPanel, TRANSFERTO_PANEL);
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
			public void actionPerformed(ActionEvent arg0) {
				// fix this

				if (transToTxtOtherAccount.getText().trim().isEmpty() || transToTxtAmount.getText().trim().isEmpty()) {
					createErrorMessage("Please fill out all fields");
					loginPassword.setText("");
					return;
				}

				BAHttpURLConnection http = new BAHttpURLConnection();
				int response = http.transferTo(ba.getUsername(), transToTxtOtherAccount.getText(),
						Double.valueOf(transToTxtAmount.getText()));
				if (response == http.CONNECTIONERROR) {
					createErrorMessage("Connection Error");
				}

				else if (response == http.UNEXPECTEDERROR) {
					createErrorMessage("Unexpected error occured");
				}

				else if (response == http.FAILED) {
					createErrorMessage("Transfer Failed");
				}

				else if (response == http.BADVALUE) {
					createErrorMessage("Bad Value Please Check Balance");
				}

				else if (response == http.SUCCESS) {
					createSuccessMessage("Transfer Successful");
					layout.show(frame.getContentPane(), CHOICE_PANEL);
				}
				loginPassword.setText("");
			}

		});

		// Fix whatever the hell I did here later because I have know idea what I did
		// here

		/*
		 * transToBtnEnter.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { if
		 * (transToTxtAmount.getText().trim().isEmpty() ||
		 * transToTxtOtherAccount.getText().trim().isEmpty()) {
		 * JOptionPane.showMessageDialog(frame, "Please Fill All Fields", "Failed",
		 * JOptionPane.ERROR_MESSAGE); } else if
		 * (setOtherAccountIndex(Integer.valueOf(transToTxtOtherAccount.getText()))) {
		 * if (ba.transferTo(Double.valueOf(transToTxtAmount.getText()),
		 * OLDBA.get(otherIndex))) { JOptionPane.showMessageDialog(frame,
		 * "Transfer Success", "Success", JOptionPane.PLAIN_MESSAGE);
		 * layout.show(frame.getContentPane(), CHOICE_PANEL); } else {
		 * JOptionPane.showMessageDialog(frame, "Transfer Failed", "Failed",
		 * JOptionPane.ERROR_MESSAGE); layout.show(frame.getContentPane(),
		 * CHOICE_PANEL); } } else { JOptionPane.showMessageDialog(frame,
		 * "Transfer Failed - Other Account Doesn't Exist", "Failed",
		 * JOptionPane.ERROR_MESSAGE); layout.show(frame.getContentPane(),
		 * CHOICE_PANEL); }
		 * 
		 * transToTxtAmount.setText(""); transToTxtOtherAccount.setText(""); } });
		 */

		transToBtnEnter.setBounds(166, 227, 128, 23);
		transToJPanel.add(transToBtnEnter);

		JButton transToBtnBack = new JButton("Back");
		transToBtnBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});
		transToBtnBack.setBounds(335, 227, 89, 23);
		transToJPanel.add(transToBtnBack);

		JPanel transFromJPanel = new JPanel();
		frame.getContentPane().add(transFromJPanel, TRANSFERFROM_PANEL);
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

		JLabel transFromLblPassword = new JLabel("Password:");
		transFromLblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		transFromLblPassword.setBounds(39, 173, 82, 14);
		transFromJPanel.add(transFromLblPassword);

		JButton transFromBtnEnter = new JButton("Enter");

		// See transTo, but just whatever I did, I don't wanna deal with it

		/*
		 * transFromBtnEnter.addActionListener(new ActionListener() {
		 * 
		 * @SuppressWarnings("deprecation") public void actionPerformed(ActionEvent e) {
		 * if (transFromTxtAmount.getText().trim().isEmpty() ||
		 * transFromTxtOtherAccount.getText().trim().isEmpty() ||
		 * transFromPassword.getText().trim().isEmpty()) {
		 * JOptionPane.showMessageDialog(frame, "Please Fill All Fields", "Failed",
		 * JOptionPane.ERROR_MESSAGE); } else if
		 * (setOtherAccountIndex(Integer.valueOf(transFromTxtOtherAccount.getText()))) {
		 * if (ba.transferFrom(Double.valueOf(transFromTxtAmount.getText()),
		 * OLDBA.get(otherIndex), transFromPassword.getText())) {
		 * JOptionPane.showMessageDialog(frame, "Transfer Success", "Success",
		 * JOptionPane.PLAIN_MESSAGE); layout.show(frame.getContentPane(),
		 * CHOICE_PANEL); } else { JOptionPane.showMessageDialog(frame,
		 * "Transfer Failed", "Failed", JOptionPane.ERROR_MESSAGE);
		 * layout.show(frame.getContentPane(), CHOICE_PANEL); } } else {
		 * JOptionPane.showMessageDialog(frame,
		 * "Transfer Failed - Other Account Doesn't Exist", "Failed",
		 * JOptionPane.ERROR_MESSAGE); layout.show(frame.getContentPane(),
		 * CHOICE_PANEL); }
		 * 
		 * transFromTxtOtherAccount.setText(""); transFromTxtAmount.setText("");
		 * transFromPassword.setText(""); }
		 * 
		 * });
		 */

		transFromBtnEnter.setBounds(166, 227, 128, 23);
		transFromJPanel.add(transFromBtnEnter);

		JButton transFromBtnBack = new JButton("Back");

		transFromBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);
			}
		});

		transFromBtnBack.setBounds(335, 227, 89, 23);
		transFromJPanel.add(transFromBtnBack);

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

					JOptionPane.showMessageDialog(frame, "Please fill out all fields", "ERROR",
							JOptionPane.ERROR_MESSAGE);

				} else if (regPassword.getText().equals(regPasswordRepeat.getText())) {
					BAHttpURLConnection http = new BAHttpURLConnection();
					String username = regTxtUsername.getText();
					String firstName = regTxtFirstName.getText();
					String lastName = regTxtLastName.getText();
					String password = regPassword.getText();

					int response = http.createAcc(username, password, firstName, lastName);

					if (response == http.CONNECTIONERROR)
						createErrorMessage("Connection Error");

					else if (response == http.FAILED)
						createErrorMessage("Username in use. Try adding arbitrary numbers no one likes to the end");

					else if (response == http.FAILEDSECURITY)
						createErrorMessage("Password must be 3+ characters long");

					else if (response == http.SUCCESS) {
						createSuccessMessage("Account successfully created");

						regTxtUsername.setText("");
						regTxtFirstName.setText("");
						regTxtLastName.setText("");

						layout.show(frame.getContentPane(), LOGIN_PANEL);

					} else
						createErrorMessage("Unexpected error has occured");

					/*
					 * else if (createAcc(username, 0, firstName, lastName, generateAccNum(),
					 * password)) {
					 * 
					 * // Add password argument to BankAccount constructor // Update createAcc to
					 * accept the parameter
					 * 
					 * layout.show(frame.getContentPane(), LOGIN_PANEL);
					 * JOptionPane.showMessageDialog(frame, "Account Created Successfuly",
					 * "SUCCESS", JOptionPane.PLAIN_MESSAGE);
					 * 
					 * // add pass clears } else { layout.show(frame.getContentPane(),
					 * REGISTER_PANEL); JOptionPane.showMessageDialog(frame,
					 * "Error: Username already in use. Try adding some abbritrary " +
					 * "numbers that no one likes to the end", "ERROR", JOptionPane.ERROR_MESSAGE);
					 * 
					 * // add pass clears } } else { layout.show(frame.getContentPane(),
					 * REGISTER_PANEL); JOptionPane.showMessageDialog(frame,
					 * "Error: Passwords Don't match. Please try again", "ERROR",
					 * JOptionPane.ERROR_MESSAGE);
					 */
				} else
					createErrorMessage("Passwords do not match");

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

		JPanel mainJPanel = new JPanel();
		frame.getContentPane().add(mainJPanel, CHOICE_PANEL);
		GridBagLayout gbl_mainJPanel = new GridBagLayout();
		gbl_mainJPanel.columnWidths = new int[] { 138, 136, 133 };
		gbl_mainJPanel.rowHeights = new int[] { 19, 75, 50, 23, 0, 0, 0, 0 };
		gbl_mainJPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_mainJPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainJPanel.setLayout(gbl_mainJPanel);

		JButton mainBtnWithdraw = new JButton("Withdraw");

		mainBtnWithdraw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				withdrawTxtAmount.setText("");
				layout.show(frame.getContentPane(), WITHDRAW_PANEL);
			}
		});

		GridBagConstraints gbc_mainBtnWithdraw = new GridBagConstraints();
		gbc_mainBtnWithdraw.fill = GridBagConstraints.BOTH;
		gbc_mainBtnWithdraw.insets = new Insets(0, 3, 5, 5);
		gbc_mainBtnWithdraw.gridx = 0;
		gbc_mainBtnWithdraw.gridy = 1;
		mainJPanel.add(mainBtnWithdraw, gbc_mainBtnWithdraw);

		JButton mainBtnBalance = new JButton("Get Balance");

		mainBtnBalance.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					getBalanceLblBalance.setText("Balance: $" + df.format(http.getBalance(ba.getUsername())));

				} catch (Exception execp) {
					getBalanceLblBalance.setText("Balance Could Not Be Found");
					execp.printStackTrace();
				}

				layout.show(frame.getContentPane(), GETBALANCE_PANEL);
			}
		});

		JButton mainBtnDeposit = new JButton("Deposit");
		mainBtnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				depositTxtAmount.setText("");
				layout.show(frame.getContentPane(), DEPOSIT_PANEL);
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

		JButton mainBtnEmpty = new JButton("Empty Account");
		mainBtnEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emptyLblMessage.setText("");
				layout.show(frame.getContentPane(), EMPTYACCOUNT_PANEL);
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
				messageTxtAMessage.setText(http.getMessage(ba.getUsername()));
				layout.show(frame.getContentPane(), MESSAGES_PANEL);

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
		gbc_mainBtnTransFrom.insets = new Insets(0, 3, 5, 5);
		gbc_mainBtnTransFrom.gridx = 0;
		gbc_mainBtnTransFrom.gridy = 4;
		mainJPanel.add(mainBtnTransFrom, gbc_mainBtnTransFrom);

		JButton mainBtnAccDetail = new JButton("Account Info");
		mainBtnAccDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				accDetailLblAccNumValue.setText(String.valueOf(ba.getAccountNum()));
				try {
					accDetailLblBalanceValue.setText('$' + df.format(http.getBalance(ba.getUsername())));
				} catch (Exception e1) {
					accDetailLblBalanceValue.setText("ERROR");
				}
				accDetailLblFirstNameValue.setText(ba.getFirstName());
				accDetailLblLastNameValue.setText(ba.getLastName());
				layout.show(frame.getContentPane(), ACCOUNTDETAIL_PANEL);
			}
		});

		JButton mainBtnTransTo = new JButton("Transfer To");
		mainBtnTransTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transToTxtAmount.setText("");
				transToTxtOtherAccount.setText("");

				layout.show(frame.getContentPane(), TRANSFERTO_PANEL);
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

		JButton mainBtnChangePassword = new JButton("Change Password");
		mainBtnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePasswordFieldRepeat.setText("");
				changePasswordFieldNew.setText("");
				changePasswordField.setText("");
				layout.show(frame.getContentPane(), RESETPASSWORD_PANEL);
			}
		});

		GridBagConstraints gbc_mainBtnChangePassword = new GridBagConstraints();
		gbc_mainBtnChangePassword.insets = new Insets(3, 3, 5, 5);
		gbc_mainBtnChangePassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnChangePassword.gridx = 0;
		gbc_mainBtnChangePassword.gridy = 5;
		mainJPanel.add(mainBtnChangePassword, gbc_mainBtnChangePassword);

		// displays the get password panel when the button is pressed on the main panel
		JButton mainBtnGetPassword = new JButton("Get Password");
		mainBtnGetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(frame.getContentPane(), GETPASSWORD_PANEL);
			}
		});

		GridBagConstraints gbc_mainBtnGetPassword = new GridBagConstraints();
		gbc_mainBtnGetPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnGetPassword.insets = new Insets(3, 3, 5, 5);
		gbc_mainBtnGetPassword.gridx = 1;
		gbc_mainBtnGetPassword.gridy = 5;
		mainJPanel.add(mainBtnGetPassword, gbc_mainBtnGetPassword);

		GridBagConstraints gbc_mainBtnGetMessages = new GridBagConstraints();
		gbc_mainBtnGetMessages.insets = new Insets(3, 3, 5, 3);
		gbc_mainBtnGetMessages.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainBtnGetMessages.anchor = GridBagConstraints.NORTH;
		gbc_mainBtnGetMessages.gridx = 2;
		gbc_mainBtnGetMessages.gridy = 5;
		mainJPanel.add(mainBtnGetMessages, gbc_mainBtnGetMessages);

		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginTxtUsername.setText("");
				loginPassword.setText("");
				layout.show(frame.getContentPane(), LOGIN_PANEL);
			}
		});
		btnSignOut.setHorizontalAlignment(SwingConstants.RIGHT);

		GridBagConstraints gbc_btnSignOut = new GridBagConstraints();
		gbc_btnSignOut.anchor = GridBagConstraints.EAST;
		gbc_btnSignOut.insets = new Insets(0, 0, 10, 0);
		gbc_btnSignOut.gridx = 2;
		gbc_btnSignOut.gridy = 6;
		mainJPanel.add(btnSignOut, gbc_btnSignOut);

		JPanel changePassword = new JPanel();
		frame.getContentPane().add(changePassword, "ResetPassword Panel");
		changePassword.setLayout(null);

		changePasswordFieldRepeat = new JPasswordField();
		changePasswordFieldRepeat.setBounds(140, 125, 200, 20);
		changePassword.add(changePasswordFieldRepeat);

		changePasswordFieldNew = new JPasswordField();
		changePasswordFieldNew.setBounds(140, 94, 200, 20);
		changePassword.add(changePasswordFieldNew);

		changePasswordField = new JPasswordField();
		changePasswordField.setBounds(140, 63, 200, 20);
		changePassword.add(changePasswordField);

		JLabel changeLblCurrentPassword = new JLabel("Current Password:");
		changeLblCurrentPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		changeLblCurrentPassword.setBounds(-15, 66, 145, 14);
		changePassword.add(changeLblCurrentPassword);

		JLabel changeLblNewPassword = new JLabel("New Pasword:");
		changeLblNewPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		changeLblNewPassword.setBounds(20, 97, 110, 14);
		changePassword.add(changeLblNewPassword);

		JLabel changePassLblRepeatPassword = new JLabel("Repeat Password:");
		changePassLblRepeatPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		changePassLblRepeatPassword.setBounds(20, 128, 110, 14);
		changePassword.add(changePassLblRepeatPassword);

		JButton changeBtnEnter = new JButton("Enter");
		changeBtnEnter.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {

				if (changePasswordField.getText().trim().isEmpty() || changePasswordFieldNew.getText().trim().isEmpty()
						|| changePasswordFieldRepeat.getText().trim().isEmpty()) {
					createErrorMessage("Please fill in all fields");
				} else {
					if (changePasswordFieldNew.getText().equals(changePasswordFieldRepeat.getText())) {
						if (ba.changePassword(changePasswordField.getText(), changePasswordFieldNew.getText())) {
							createSuccessMessage("Passwords successfully changed");
							layout.show(frame.getContentPane(), CHOICE_PANEL);
						} else
							createErrorMessage("Current password is incorrect");

					} else {
						createErrorMessage("Passwords do not match");
					}
					changePasswordFieldRepeat.setText("");
					changePasswordFieldNew.setText("");
					changePasswordField.setText("");

				}
			}
		});
		changeBtnEnter.setFont(new Font("Tahoma", Font.BOLD, 11));
		changeBtnEnter.setBounds(251, 156, 89, 23);
		changePassword.add(changeBtnEnter);

		JButton changeBtnBack = new JButton("Back");
		changeBtnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		changeBtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(frame.getContentPane(), CHOICE_PANEL);

			}
		});
		changeBtnBack.setBounds(251, 184, 89, 23);
		changePassword.add(changeBtnBack);

	}
}
