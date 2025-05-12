
public class CrackTest {
    public static void main(String[] args) {
        ColTransCipher cipher = new ColTransCipher("34152", null, true, false);
        String plaintext = "thequickbrownfoxjumpedoverthelazydogs";
        String ciphertext = cipher.encrypt(plaintext);
        System.out.println("Original plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);

        ColTransCipher cracker = new ColTransCipher("12345", null, true, false);
        String cracked = cracker.crack(ciphertext);
        String trimmedCracked = cracked.substring(0, plaintext.length());
        System.out.println("Cracked plaintext: " + trimmedCracked);
        System.out.println("Crack successful? " + trimmedCracked.equals(plaintext));

    }
}
