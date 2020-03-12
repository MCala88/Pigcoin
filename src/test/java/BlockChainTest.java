package pigcoin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class BlockChainTest {
	
	@Test
	public void constructor() {
		BlockChain bChain = new BlockChain();
		assertNotNull(bChain);
	}
	
	@Test
	public void addOrigin_test() {
		Wallet wallet1 = new Wallet();
		wallet1.generateKeyPair();
		Wallet wallet2 = new Wallet();
		wallet2.generateKeyPair();
		Wallet wallet3 = new Wallet();
		wallet3.generateKeyPair();
		 
		
		BlockChain bChain = new BlockChain();
		assertEquals(0, bChain.getBlockChain().size());
		
		Transaction trx = new Transaction("hash_1", "0", wallet1.getAddress(), wallet2.getAddress(), 20, "uep");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_2", "1", wallet1.getAddress(), wallet3.getAddress(), 50, "com va");
		bChain.addOrigin(trx);
		

		assertEquals(2, bChain.getBlockChain().size());
		assertTrue(bChain.getBlockChain().get(0).getPigcoin()==20);
		assertTrue(bChain.getBlockChain().get(1).getPigcoin()==50);
	}
	
	@Test
	public void summerize_test() {
		Wallet wallet1 = new Wallet();
		wallet1.generateKeyPair();
		Wallet wallet2 = new Wallet();
		wallet2.generateKeyPair();
		Wallet wallet3 = new Wallet();
		wallet3.generateKeyPair();
		 
		
		BlockChain bChain = new BlockChain();
		Transaction trx = new Transaction("hash_1", "0", wallet1.getAddress(), wallet2.getAddress(), 20, "uep");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_2", "1", wallet1.getAddress(), wallet3.getAddress(), 50, "com va");
		bChain.addOrigin(trx);
	
	
		bChain.summarize();
		assertEquals(2, bChain.getBlockChain().size());
		assertTrue(bChain.getBlockChain().get(0).getHash()=="hash_1");
		assertTrue(bChain.getBlockChain().get(0).getPrev_hash()=="0");
		assertTrue(bChain.getBlockChain().get(0).getMessage()=="uep");
		assertNotNull(wallet1.getAddress());
		assertNotNull(wallet2.getAddress());
	
	}
		
	@Test
	public void loadWallet_test() {
		Wallet wallet1 = new Wallet();
		wallet1.generateKeyPair();
		Wallet wallet2 = new Wallet();
		wallet2.generateKeyPair();
		Wallet wallet3 = new Wallet();
		wallet3.generateKeyPair();
		
		
		BlockChain bChain = new BlockChain();
		Transaction trx = new Transaction("hash_1", "0", wallet1.getAddress(), wallet2.getAddress(), 20, "uep");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_2", "1", wallet1.getAddress(), wallet3.getAddress(), 50, "com va");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_3", "2", wallet2.getAddress(), wallet3.getAddress(), 15.5d, "montoro");
		bChain.addOrigin(trx);
		
		
		double[] pigcoins = bChain.loadWallet(wallet2.getAddress());
		assertEquals(20, pigcoins[0], 0);
		assertEquals(15.5, pigcoins[1], 0);
			
	}
	
	@Test
	public void loadInputTransaction_y_loadOutputTransaction_test() {
		Wallet wallet1 = new Wallet();
		wallet1.generateKeyPair();
		Wallet wallet2 = new Wallet();
		wallet2.generateKeyPair();
		Wallet wallet3 = new Wallet();
		wallet3.generateKeyPair();
		
		
		BlockChain bChain = new BlockChain();
		Transaction trx = new Transaction("hash_1", "0", wallet1.getAddress(), wallet2.getAddress(), 20, "uep");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_2", "1", wallet1.getAddress(), wallet3.getAddress(), 50, "com va");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_3", "2", wallet2.getAddress(), wallet3.getAddress(), 15.5d, "montoro");
		bChain.addOrigin(trx);
		
		
		List<Transaction> inputTransactions = bChain.loadInputTransaction(wallet2.getAddress());
		assertNotNull(inputTransactions);
		assertTrue(inputTransactions.size()==1);
		assertTrue(inputTransactions.get(0).getPigcoin()==20);
		assertTrue(inputTransactions.get(0).getMessage()=="uep");
		
		inputTransactions = bChain.loadInputTransaction(wallet1.getAddress());
		assertNotNull(inputTransactions);
		assertTrue(inputTransactions.size()==0);
		
		
		List<Transaction> outputTransactions = bChain.loadOutputTransaction(wallet1.getAddress());
		assertNotNull(outputTransactions);
		assertTrue(outputTransactions.size()==2);
		assertTrue(outputTransactions.get(0).getPigcoin()==20);
		assertTrue(outputTransactions.get(0).getMessage()=="uep");
		assertTrue(outputTransactions.get(1).getPigcoin()==50);
		
		outputTransactions = bChain.loadOutputTransaction(wallet2.getAddress());
		assertNotNull(outputTransactions);
		assertTrue(outputTransactions.size()==1);
		assertTrue(outputTransactions.get(0).getPigcoin()==15.5);
	
	}
	
	@Test
	public void isConsumedCoinValid_test() {
		Wallet wallet1 = new Wallet();
		wallet1.generateKeyPair();
		Wallet wallet2 = new Wallet();
		wallet2.generateKeyPair();
		Wallet wallet3 = new Wallet();
		wallet3.generateKeyPair();
		
		
		BlockChain bChain = new BlockChain();
		Transaction trx = new Transaction("hash_1", "0", wallet1.getAddress(), wallet2.getAddress(), 20, "uep");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_2", "1", wallet1.getAddress(), wallet3.getAddress(), 50, "com va");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_3", "2", wallet2.getAddress(), wallet3.getAddress(), 15.5d, "montoro");
		bChain.addOrigin(trx);
		trx = new Transaction("hash_4", "hash_4", wallet2.getAddress(), wallet3.getAddress(), 15.5d, "montoro");
		bChain.addOrigin(trx);
		
		Map<String, Double> consumedCoins = new LinkedHashMap<>();
		consumedCoins.put("hash_1", 20d);
		assertNotNull(consumedCoins);
		assertTrue(bChain.isConsumedCoinValid(consumedCoins));
		consumedCoins.clear();
		consumedCoins.put("hash_4", 40d);
		assertFalse(bChain.isConsumedCoinValid(consumedCoins));
	}
	
	
	//Caso test no original
	@Test
	public void createTransaction_test() {
	Wallet wallet1 = new Wallet();
	wallet1.generateKeyPair();
	Wallet wallet2 = new Wallet();
	wallet2.generateKeyPair();
	Wallet wallet3 = new Wallet();
	wallet3.generateKeyPair();
	
	
	BlockChain bChain = new BlockChain();
	Transaction trx = new Transaction("hash_1", "0", wallet1.getAddress(), wallet2.getAddress(), 20, "uep");
	bChain.addOrigin(trx);
	trx = new Transaction("hash_2", "1", wallet1.getAddress(), wallet3.getAddress(), 50, "com va");
	bChain.addOrigin(trx);
	trx = new Transaction("hash_3", "2", wallet2.getAddress(), wallet3.getAddress(), 15.5d, "montoro");
	bChain.addOrigin(trx);
	trx = new Transaction("hash_4", "hash_4", wallet2.getAddress(), wallet3.getAddress(), 15.5d, "montoro");
	bChain.addOrigin(trx);
	
	
	Map<String, Double> consumedCoins = new LinkedHashMap<>();
	consumedCoins.put("hash_1", 20d);
	consumedCoins.put("CA_hash_2", 9.8d);
    assertTrue(bChain.isConsumedCoinValid(consumedCoins));
    
    int previousBlockChainSize = bChain.getBlockChain().size();
	bChain.createTransaction(wallet1.getAddress(), wallet3.getAddress(), consumedCoins, "Pa ti", wallet1.signTransaction("Pa ti"));
	 assertEquals(previousBlockChainSize + consumedCoins.size(), bChain.getBlockChain().size(), 0);
     assertEquals("hash_4", bChain.getBlockChain().get(3).getHash());
     assertEquals(9.8, bChain.getBlockChain().get(5).getPigcoin(), 0);
     bChain.summarize(3);
	}
}
