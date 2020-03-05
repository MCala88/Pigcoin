package pigcoin;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
	private final List<Transaction> blockChain= new ArrayList<>();

	public List<Transaction> getBlockChain() {
		return this.blockChain;
	}
	
	public void addOrigin(Transaction transaccion) {
		blockChain.add(transaccion);
		
	}

}
