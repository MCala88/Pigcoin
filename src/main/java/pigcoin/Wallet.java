package pigcoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {
	private PublicKey address = null;
	private PrivateKey sKey = null;
	private double total_input = 0d;
	private double total_output = 0d;
	private double balance = 0d;
	private double inputTransactions = 0d;
	private double outputTransactions = 0d;
	
	public PublicKey getAddress() {
		return address;
	}
	public void setAddress(PublicKey address) {
		this.address = address;
	}
	public PrivateKey getsKey() {
		return sKey;
	}
	public void setsKey(PrivateKey sKey) {
		this.sKey = sKey;
	}
	public double getTotal_input() {
		return total_input;
	}
	public void setTotal_input(double total_input) {
		this.total_input = total_input;
	}
	public double getTotal_output() {
		return total_output;
	}
	public void setTotal_output(double total_output) {
		this.total_output = total_output;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getInputTransactions() {
		return inputTransactions;
	}
	public void setInputTransactions(double inputTransactions) {
		this.inputTransactions = inputTransactions;
	}
	public double getOutputTransactions() {
		return outputTransactions;
	}
	public void setOutputTransactions(double outputTransactions) {
		this.outputTransactions = outputTransactions;
	}
	
	public void generateKeyPair() {
		KeyPair pair = GenSig.generateKeyPair();
		this.setAddress(pair.getPublic());
		this.setsKey(pair.getPrivate());
	}
	
	
	public String toString() {
		return "wallet: " + "\n" + "Total input: " + total_input + "Total output: " + total_output + "\n" + "Balances: " + balance;
	}
		
}
