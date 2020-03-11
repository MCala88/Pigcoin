package pigcoin;

import java.security.PublicKey;

public class Transaction {
	private String hash = null;
	private String prev_hash = null;
	private PublicKey pKey_sender;
	private PublicKey pKey_recipient;
	private double pigcoins = 0d;
	private String message = null;
	private byte[] Signature;

	public Transaction () {}
	
    public Transaction (String hash, String prev_hash, PublicKey senderPK, PublicKey recipientPK, double pigcoins, String message) {
        this.hash = hash;
        this.prev_hash = prev_hash;
        this.pKey_sender = senderPK;
        this.pKey_recipient = recipientPK;
        this.pigcoins = pigcoins;
        this.message = message;
    }
	
	String getHash() {
		return hash;
	}

	String getPrev_hash() {
		return prev_hash;
	}

	double getPigcoin() {
		return pigcoins;
	}

	String getMessage() {
		return message;
	}

	public byte[] getSignature() {
		return Signature;
	}
	
	public PublicKey getpKey_sender() {
		return pKey_sender;
	}

	public PublicKey getpKey_recipient() {
		return pKey_recipient;
	}


	@Override
	public String toString() {
		return "hash: " + hash + "\n" + "prev_hash: " + prev_hash + "\n" + "pKey_sender: " + pKey_sender.hashCode() + "\n" + "pKey_recipient: " + pKey_recipient.hashCode() + "\n" + "pigcoin: " + pigcoins + "\n" + "message: " + message + "\n";
	}


	
	 
	
}
