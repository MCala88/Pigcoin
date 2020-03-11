package pigcoin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WalletTest {

	@Test
	public void contructor_test() {
		Wallet wallet = new Wallet();
		assertNotNull(wallet);
	}
	
	@Test
	public void generateKeyPair_test() {
		Wallet wallet = new Wallet();
		wallet.generateKeyPair();
		assertNotNull(wallet.getAddress());
		assertNotNull(wallet.getsKey());
	}
	
	@Test
	public void signTransaction_test() {
		Wallet wallet = new Wallet();
		wallet.generateKeyPair();
		byte[] sign = wallet.signTransaction("Para Willy");
		
		BlockChain bChain = new BlockChain();
		assertTrue(bChain.isSignatureValid(wallet.getAddress(), "Para Willy", sign));
	}

	@Test
	public void loadInputTransactions_loadOutputTransactions_test() {
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
		
		wallet2.loadInputTransactions(bChain);
		assertTrue(wallet2.getInputTransactions().size()==1);
		assertTrue(wallet2.getInputTransactions().get(0).getPigcoin()==20);
		
		wallet3.loadInputTransactions(bChain);
		assertTrue(wallet3.getInputTransactions().size()==1);
		assertTrue(wallet3.getInputTransactions().get(0).getPigcoin()==50);
		
		wallet1.loadOutputTransactions(bChain);
		assertTrue(wallet1.getOutputTransactions().size()==2);
		assertTrue(wallet1.getOutputTransactions().get(0).getPigcoin()==20);
		assertTrue(wallet1.getOutputTransactions().get(1).getPigcoin()==50);
	}
	
	@Test
	public void total_pigcoins_input_y_output_test() {
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
		
		wallet1.loadCoins(bChain);
		assertEquals(0, wallet1.getTotalInput(), 0);
		assertEquals(70, wallet1.getTotalOutput(), 0);
		assertEquals(-70, wallet1.getBalance(), 0);
		
		wallet2.loadCoins(bChain);
		assertEquals(20, wallet2.getTotalInput(), 0);
		assertEquals(0, wallet2.getTotalOutput(), 0);
		assertEquals(20, wallet2.getBalance(), 0);
	
	}
	
	@Test
	public void sendCoins_test() {
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
		
        wallet2.loadCoins(bChain);
        assertEquals(20, wallet2.getTotalInput(), 0);
        assertEquals(0, wallet2.getTotalOutput(), 0);
        assertEquals(20, wallet2.getBalance(), 0);
        wallet2.loadInputTransactions(bChain);
        assertTrue(wallet2.getInputTransactions().size() == 1);

        wallet3.loadCoins(bChain);
        assertEquals(50, wallet3.getTotalInput(), 0);
        assertEquals(0, wallet3.getTotalOutput(), 0);
        assertEquals(50, wallet3.getBalance(), 0);
        wallet3.loadInputTransactions(bChain);
        assertTrue(wallet3.getInputTransactions().size() == 1);
			
		wallet2.sendCoins(wallet3.getAddress(), 10.3d, "klk", bChain);
		assertEquals(4, bChain.getBlockChain().size(), 0);
		
		wallet2.loadInputTransactions(bChain);
		assertEquals(2, wallet2.getInputTransactions().size());
		assertEquals(20d, wallet2.getInputTransactions().get(0).getPigcoin(), 0);
	    assertEquals(9.7d, wallet2.getInputTransactions().get(1).getPigcoin(), 0);		
	
	    wallet2.loadOutputTransactions(bChain);
	    assertEquals(2, wallet2.getOutputTransactions().size());
	    assertEquals(10.3d, wallet2.getOutputTransactions().get(0).getPigcoin(), 0);
	    assertEquals(9.7d, wallet2.getOutputTransactions().get(1).getPigcoin(), 0);
	 
	    wallet2.loadCoins(bChain);
	    assertEquals(9.7, wallet2.getBalance(), 0);
        wallet3.loadCoins(bChain);
        assertEquals(60.3, wallet3.getBalance(), 0);     
	}
}
