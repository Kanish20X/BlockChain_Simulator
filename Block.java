
import java.util.Date;

public final class Block {

    public String hash;
    public String previousHash;
    private String data; // Our message (e.g., "Student A sent 5 tokens to Student B")
    private long timeStamp;
    private int nonce; // The "magic number" used for mining

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    // here we are calculating hash based on teh block contents...
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash
                + Long.toString(timeStamp)
                + Integer.toString(nonce)
                + data
        );
        return calculatedhash;
    }

    // This is the "Proof of Work" (Mining) - refers finding a hash that satisifies certain condition
    public void mineBlock(int difficulty) {
        // Create a string with a number of zeros equal to 'difficulty'
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) { // it return the first n charcters of a hash string compare with  target which has leading zeros...
            nonce++; // Keep changing the nonce until the hash starts with enough zeros
            hash = calculateHash();
        }
        System.out.println("Block Mined successfully!!! Hash: " + hash);
    }

    public void setData(String data) {
        this.data = data;
    }
}
