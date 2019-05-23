package bank;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BAHttpURLConnection {
	protected final int CONNECTIONERROR = 0;
	protected final int FAILED = 1;
	protected final int FAILEDSECURITY = 2;
	protected final int BADVALUE = 3;
	protected final int SUCCESS = 4;
	protected final int UNEXPECTEDERROR = 5;

	// Stores PHP response
	StringBuffer response = null;
	String strResponse = "";
	String url = "http://iplaygamesv2.000webhostapp.com/php/";

	// HTTP GET request
	private int sendGet(String url, String params) throws Exception {
		URL obj = new URL(url);
		java.net.HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Send post request
		con.setRequestMethod("GET");
		/*
		 * con.setDoOutput(true); DataOutputStream wr = new
		 * DataOutputStream(con.getOutputStream()); wr.writeBytes(params); wr.flush();
		 */// wr.close();

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return responseCode;

	}

	// HTTP POST request

	private int sendPost(String url, String params) throws Exception {
		URL obj = new URL(url);
		java.net.HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Send post request
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		this.strResponse = response.toString().trim();

		return responseCode;
	}

	public int login(String username, String password, BankAccount ba) {

		BAHttpURLConnection http = new BAHttpURLConnection();

		try {
			http.sendPost(url + "login.php?", "user=" + username + "&pass=" + password);
			System.out.println("Login Response: " + http.strResponse);
			if (http.strResponse != null) {
				if (http.strResponse.contains("login successful")) {
					this.response = http.response;
					return SUCCESS;
				} else if (http.strResponse.contains("login failed"))
					return FAILED;

			}

			return UNEXPECTEDERROR;
		}

		catch (Exception e) {
			return CONNECTIONERROR;
		}

	}

	public double getBalance(String username) throws Exception {

		BAHttpURLConnection http = new BAHttpURLConnection();

		try {

			// temporarily a post request
			http.sendPost(url + "getBalance.php?", "user=" + username);
			System.out.println(http.strResponse);
			Double balDouble = StringToDouble(http.strResponse);

			return balDouble;
			// Double.valueOf(http.strResponse);
			// can't convert a null

		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}

	}

	public String StringBufferToString(StringBuffer valStringBuffer) {
		String valString = valStringBuffer.toString();
		return valString;
	}

	public Double StringToDouble(String valString) {
		Double valDouble = Double.valueOf(valString);
		return valDouble;
	}

	protected BankAccount parseInitialAccountData() {
		String data[] = String.valueOf(response).trim().substring(37).split(",");
		String username = data[0];
		int accNum = Integer.valueOf(data[1]);
		double balance = Double.valueOf(data[2]);
		String fName = data[3];
		String lName = data[4];
		String message = data[5];

		BankAccount ba = new BankAccount(username, accNum, balance, fName, lName, message);

		return ba;
	}

	public int deposit(String username, double amount) {

		BAHttpURLConnection http = new BAHttpURLConnection();

		try {
			http.sendPost(url + "deposit.php?", "user=" + username + "&amt=" + amount);
			System.out.println("Deposit Response: " + http.strResponse);

			if (http.strResponse != null) {
				;
				if (http.strResponse.contains("deposit successful"))
					return SUCCESS;

				else if (http.strResponse.contains("deposit failed"))
					return FAILED;

				else
					return UNEXPECTEDERROR;

			} else
				return UNEXPECTEDERROR;

		} catch (Exception e) {
			return CONNECTIONERROR;

		}

	}

	public int withdraw(String username, double amount) {

		BAHttpURLConnection http = new BAHttpURLConnection();

		try {
			http.sendPost(url + "withdraw.php?", "user=" + username + "&amt=" + amount);
			System.out.println("Withdraw Response: " + http.strResponse);

			if (http.response != null) {
				if (http.strResponse.contains("withdraw successful"))
					return SUCCESS;

				else if (http.strResponse.contains("withdraw failed"))
					return FAILED;
				else if (http.strResponse.contains("bad value"))
					return BADVALUE;
	
				else
					return UNEXPECTEDERROR;

			} else
				return UNEXPECTEDERROR;

		} catch (Exception e) {
			return CONNECTIONERROR;

		}

	}

	public int createAcc(String username, String password, String fname, String lname) {

		BAHttpURLConnection http = new BAHttpURLConnection();

		try {
			fname = fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase();
			lname = lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase();

			http.sendPost(url + "createAcc.php?",
					"user=" + username + "&pass=" + password + "&fname=" + fname + "&lname=" + lname);
			System.out.println("Create Account Response: " + http.response);

			if (http.response != null) {

				this.strResponse = http.response.toString().trim();
				if (http.strResponse.equals("user created")) {

					return SUCCESS;

				} else if (http.strResponse.equals("username clash")) {
					return FAILED;

				} else if (http.strResponse.equals("bad password")) {
					return FAILEDSECURITY;

				}
			}

			return UNEXPECTEDERROR;

		} catch (Exception e) {

			return CONNECTIONERROR;
		}
	}

	public int transferTo(String username, String oUsername, Double amt) {

		BAHttpURLConnection http = new BAHttpURLConnection();

		try {
			http.sendPost(url + "transferTo.php?", "user=" + username + "&oUser=" + oUsername + "&amt=" + amt);

			if (http.response != null) {
				System.out.println("Transfer To Response: " + http.response);
				// if(strResponse.equals(""))
			}

			this.strResponse = http.response.toString().trim();
			if (http.strResponse.equals("transfer successful")) {

				return SUCCESS;

			} else if (http.strResponse.equals("no account")) {
				return FAILED;

			} else if (http.strResponse.equals("bad value")) {
				return BADVALUE;

			}

		}

		catch (Exception e) {

		}

		return 0;

	}

	public String getMessage(String username) {

		BAHttpURLConnection http = new BAHttpURLConnection();

		try {
			http.sendPost(url + "getMessage.php?", "user=" + username);

			if (http.response != null) {
				// System.out.println("Transfer To Response: " + http.strResponse);
				String message = formatMessage(http.strResponse);
				// System.out.println(message);
				return message;
			}
		}

		catch (Exception e) {
			return null;

		}
		return null;
	}

	private String formatMessage(String response) {
		String[] messageList = String.valueOf(response).trim().split(",");
		System.out.print(messageList.length);
		String message = "";

		for (String a : messageList) {
			message += (a + "\n");
		}
		return message;

	}
}
