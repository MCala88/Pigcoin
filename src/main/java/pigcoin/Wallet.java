package pigcoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Wallet {
	private PublicKey address = null;
	private PrivateKey sKey = null;
	private double total_input = 0d;
	private double total_output = 0d;
	private double balance = 0d;
	private List<Transaction> inputTransactions = new ArrayList<>();
	private List<Transaction> outputTransactions = new ArrayList<>();
	
	PublicKey getAddress() {
		return address;
	}
	void setAddress(PublicKey address) {
		this.address = address;
	}
	PrivateKey getsKey() {
		return sKey;
	}
	void setSK(PrivateKey sKey) {
		this.sKey = sKey;
	}
	double getTotalInput() {
		return total_input;
	}
	private void setTotalInput(double total_input) {
		this.total_input = total_input;
	}
	double getTotalOutput() {
		return total_output;
	}
	private void setTotalOutput(double total_output) {
		this.total_output = total_output;
	}
	double getBalance() {
		return balance;
	}
	void setBalance(double balance) {
		this.balance = balance;
	}
	List<Transaction> getInputTransactions() {
		return inputTransactions;
	}
	private void setInputTransactions(List<Transaction> inputTransactions) {
		this.inputTransactions = inputTransactions;
	}
	List<Transaction> getOutputTransactions() {
		return outputTransactions;
	}
	private void setOutputTransactions(List<Transaction> outputTransactions) {
		this.outputTransactions = outputTransactions;
	}
	
	
	void generateKeyPair() {
		KeyPair pair = GenSig.generateKeyPair();
		this.setAddress(pair.getPublic());
		this.setSK(pair.getPrivate());
	}
	
	
	byte[] signTransaction(String message)  {
		return GenSig.sign(getsKey(), message);
	}

	
	void loadInputTransactions(BlockChain bChain) {
		setInputTransactions(bChain.loadInputTransaction(getAddress()));
	}
	
	void loadOutputTransactions(BlockChain bChain) {
		setOutputTransactions(bChain.loadOutputTransaction(getAddress()));	
	}
	
    void updateBalance() {
		this.balance = this.getTotalInput() - this.getTotalOutput();
    }
	
	void loadCoins(BlockChain bChain) {
		double[] pigcoins = {0d, 0d};
		pigcoins = bChain.loadWallet(getAddress());
		setTotalInput(pigcoins[0]);
		setTotalOutput(pigcoins[1]);
		updateBalance();
		
	}
		
		
	void sendCoins(PublicKey pKey_recipient, double coins, String message, BlockChain bChain) {
		Map<String, Double> consumedCoins = new LinkedHashMap<>();
		
		consumedCoins = collectCoins(coins);
		
		if (consumedCoins != null) {
			bChain.processTransactions(getAddress(), pKey_recipient, consumedCoins, message, signTransaction(message));
		}
	}
	/* Metodo mayoritariamente no original */
	Map<String, Double> collectCoins(Double pigcoins) {
		Map<String, Double> collectedCoins = new LinkedHashMap<>();
		
        if (getInputTransactions() == null||pigcoins > getBalance()) {
      
            return null;
        }
			
	
		Double coinsGuardadas = 0d;
		
		Set<String> consumedCoins = new HashSet<>();
		  
		 if (getOutputTransactions() != null) {
			for (Transaction transaction : getOutputTransactions()) {
				consumedCoins.add(transaction.getPrev_hash());
			}
		} 
	        for (Transaction transaction : getInputTransactions()) {

	            if (consumedCoins.contains(transaction.getHash())) {
	                continue;
	            }

	            if (transaction.getPigcoin() == pigcoins) {
	                collectedCoins.put(transaction.getHash(), transaction.getPigcoin());
	                consumedCoins.add(transaction.getHash());
	                break;
	            } else if (transaction.getPigcoin() > pigcoins) {
	                collectedCoins.put(transaction.getHash(), pigcoins);
	                collectedCoins.put("CA_" + transaction.getHash(), transaction.getPigcoin() - pigcoins);
	                consumedCoins.add(transaction.getHash());
	                break;
	            } else {
	                collectedCoins.put(transaction.getHash(), transaction.getPigcoin());
	                coinsGuardadas = transaction.getPigcoin();
	                pigcoins = pigcoins - coinsGuardadas;
	                consumedCoins.add(transaction.getHash());
	            }

	        }
	        // getInputTransactions().removeAll(consumedCoins);
	        return collectedCoins;
	}
	
	@Override
	public String toString() {
		return "\n" + "wallet: " + getAddress().hashCode() + "\n" + "Total input: " + getTotalInput() + "\n" + "Total output: " + getTotalOutput() + "\n" + "Balances: " + getBalance() + "\n";
	}
		
}
