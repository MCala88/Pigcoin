package pigcoin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
	

}
