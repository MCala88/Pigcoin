package pigcoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
	private PublicKey address = null;
	private PrivateKey sKey = null;
	private double total_input = 0d;
	private double total_output = 0d;
	private double balance = 0d;
	private List<Transaction> inputTransactions = new ArrayList<>();
	private List<Transaction> outputTransactions = new ArrayList<>();
	private List<Transaction> loadCoins = new ArrayList<>();
	
	public PublicKey getAddress() {
		return address;
	}
	public void setAddress(PublicKey address) {
		this.address = address;
	}
	public PrivateKey getsKey() {
		return sKey;
	}
	public void setSK(PrivateKey sKey) {
		this.sKey = sKey;
	}
	public double getTotalInput() {
		return total_input;
	}
	public void setTotalInput(double total_input) {
		this.total_input = total_input;
	}
	public double getTotalOutput() {
		return total_output;
	}
	public void setTotalOutput(double total_output) {
		this.total_output = total_output;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public List<Transaction> getInputTransactions() {
		return inputTransactions;
	}
	public void setInputTransactions(List<Transaction> inputTransactions) {
		this.inputTransactions = inputTransactions;
	}
	public List<Transaction> getOutputTransactions() {
		return outputTransactions;
	}
	public void setOutputTransactions(List<Transaction> outputTransactions) {
		this.outputTransactions = outputTransactions;
	}
	
	
	public void generateKeyPair() {
		KeyPair pair = GenSig.generateKeyPair();
		this.setAddress(pair.getPublic());
		this.setSK(pair.getPrivate());
	}
	
	public void loadInputTransactions(BlockChain bChain) {
		bChain.getBlockChain().stream().filter(transaction -> transaction.getpKey_recipient().equals(getAddress())).forEachOrdered(transaction->{inputTransactions.add(transaction);});	
	}
	
	public void loadOutputTransactions(BlockChain bChain) {
		bChain.getBlockChain().stream().filter(transaction -> transaction.getpKey_sender().equals(getAddress())).forEachOrdered(transaction->{outputTransactions.add(transaction);});	
	}
	
	public byte[] signTransaction(String message)  {
		return GenSig.sign(getsKey(), message);
	}
	
	public String toString() {
		return " wallet: " + "\n" + "Total input: " + total_input + "Total output: " + total_output + "\n" + "Balances: " + balance + "\n";
	}
	
    public void updateBalance() {
		this.balance = this.getTotalInput() - this.getTotalOutput();
    }
	
	public void loadCoins(BlockChain bChain) {
		double[] pigcoins = {0d, 0d};
		pigcoins = bChain.loadWallet(getAddress());
		setTotalInput(pigcoins[0]);
		setTotalOutput(pigcoins[1]);
		updateBalance();
		
	}
		
		
	public void sendCoins(PublicKey address2, double d, String string, BlockChain bChain) {
		// TODO Auto-generated method stub
		
	}
	public Map<String, Double> collectCoins(Double pigcoins) {
		Map<String, Double> collectCoins = new LinkedHashMap<>();
		return null;
	}
		
}
