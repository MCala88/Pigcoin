package pigcoin;

import java.security.PublicKey;

public class Transaction {
	private String hash = null;
	private String prev_hash = null;
	private PublicKey pKey_sender = null;
	private PublicKey pKey_recipient = null;
	private String pigcoin = null;
	private String message = null;
	private byte[] Signature;


	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPrev_hash() {
		return prev_hash;
	}

	public void setPrev_hash(String prev_hash) {
		this.prev_hash = prev_hash;
	}

	public String getPigcoin() {
		return pigcoin;
	}

	public void setPigcoin(String pigcoin) {
		this.pigcoin = pigcoin;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getSignature() {
		return Signature;
	}

	public void setSignature(byte[] signature) {
		Signature = signature;
	} 
	
	public PublicKey getpKey_sender() {
		return pKey_sender;
	}

	public void setpKey_sender(PublicKey pKey_sender) {
		this.pKey_sender = pKey_sender;
	}

	public PublicKey getpKey_recipient() {
		return pKey_recipient;
	}

	public void setpKey_recipient(PublicKey pKey_recipient) {
		this.pKey_recipient = pKey_recipient;
	}

	public String toString() {
		return "hash" + hash + "\n" + "prev_hash" + prev_hash + "\n" + "pKey_sender" + pKey_sender + "\n" + "pKey_recipient" + pKey_recipient + "\n" + "pigcoin" + pigcoin + "\n" + "message" + message;
	}


	
	 
	
}
