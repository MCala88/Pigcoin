package pigcoin;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BlockChain {
	private final List<Transaction> blockChain= new ArrayList<>();

	public List<Transaction> getBlockChain() {
		return this.blockChain;
	}
	
	public void addOrigin(Transaction transaccion) {
		blockChain.add(transaccion);
		
	}

	public void summarize() {
		this.blockChain.forEach(transaction->{System.out.println(transaction.toString());});		
	}

	public void summarize(Integer position) {
		System.out.println(blockChain.get(position).toString());		
	}

    public List<Transaction> loadInputTransaction(PublicKey address) {
        List<Transaction> inputTransactions = getBlockChain().stream()
                .filter((transacciones) -> (transacciones.getpKey_recipient().equals(address)))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));
        return inputTransactions;
    }

    public List<Transaction> loadOutputTransaction(PublicKey address) {
        List<Transaction> outputTransactions = getBlockChain().stream()
                .filter((transacciones) -> (transacciones.getpKey_sender().equals(address)))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));
        return outputTransactions;
    }
    
    public double[] loadWallet(PublicKey address) {
    	double pigcoinsIn = 0d;
    	double pigcoinsOut = 0d;
    	
    	for (Transaction transaction : getBlockChain()) {
    		if (address.equals(transaction.getpKey_recipient())) {
    			pigcoinsIn = pigcoinsIn + transaction.getPigcoin();
    		}
    		if (address.equals(transaction.getpKey_sender())) {
    			pigcoinsOut = pigcoinsOut + transaction.getPigcoin();
    		}
    		
    	}
    	double[] pigcoins = {pigcoinsIn, pigcoinsOut};
    	return pigcoins;
    }
		
}