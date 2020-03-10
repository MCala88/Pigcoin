package pigcoin;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public boolean isSignatureValid(PublicKey pKey_sender, String message, byte[] signedTransaction) {
    	return GenSig.verify(pKey_sender, message, signedTransaction);
    }
    
    public boolean isConsumedCoinValid(Map<String, Double> consumedCoins) {
    	for (String hash :consumedCoins.keySet()) {
    		for (Transaction transaction : blockChain) {
    			if (hash.equals(transaction.getPrev_hash())) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    /* Codigo no original */
    public void createTransaction(PublicKey pKey_sender, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
    
    	PublicKey address_recipient = pKey_recipient;
        Integer lastBlock = 0;

        for (String prev_hash: consumedCoins.keySet()) {
            if (prev_hash.startsWith("CA_")) {
                pKey_recipient = pKey_sender;
            }
            
            lastBlock = blockChain.size() + 1;
            Transaction transaction = new Transaction("hash_" + lastBlock.toString(), prev_hash, pKey_sender,
                    pKey_recipient, consumedCoins.get(prev_hash), message);
            getBlockChain().add(transaction);
            
            pKey_recipient = address_recipient;
        }
    }
    
    public void processTransactions(PublicKey pKey_sender, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
    
    	if (isSignatureValid(pKey_recipient, message, signedTransaction) && isConsumedCoinValid(consumedCoins)) {
    		createTransaction(pKey_recipient, pKey_recipient, consumedCoins, message, signedTransaction);
    	}
    }
}