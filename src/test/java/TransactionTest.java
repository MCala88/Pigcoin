package pigcoin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TransactionTest {

	@Test
	public void constructor_test() {
		Transaction trx = new Transaction();
		assertNotNull(trx);
		
		Wallet wallet1 = new Wallet();
		wallet1.generateKeyPair();
		Wallet wallet2 = new Wallet();
		wallet2.generateKeyPair();
		
		trx = new Transaction("hash_1", "0", wallet1.getAddress(), wallet2.getAddress(), 20, "uep");
			assertNotNull(trx);
			assertTrue(trx.getpKey_sender().equals(wallet1.getAddress()));
			assertTrue(trx.getpKey_recipient().equals(wallet2.getAddress()));
			assertEquals(20, trx.getPigcoin(), 0);
			assertTrue(trx.getMessage().equals("uep"));
				
		
	}
}
