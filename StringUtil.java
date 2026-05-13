
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
// we have imported MessageDigest class from security to convert our input string to hash.

public class StringUtil {

    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Applies sha256 to our input
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]); // it is basically used for converting binary string inti a hexstring with positive values...
                if (hex.length() == 1) { // we ensure that hash code nvr has a single character it follows by 0..
                    hexString.append('0');
                }
                hexString.append(hex); // we are final string appended in to the hexString varaible
            }
            return hexString.toString(); // converting hexString format to a readble String...
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // catch block to raise and handle the exception...
        }
    }
}
