package clivet268.Enforcry.Encryption;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

// Class to create an asymmetric key

/**
 * <a href="https://www.geeksforgeeks.org/asymmetric-encryption-cryptography-in-java/">source</a>
 * Note: source used Public key to decrypt and Private to encrypt? I altered to use the opposite as I think that is right
 *
 * @author <a href="https://auth.geeksforgeeks.org/user/nimma_shravan_kumar_reddy">nimma_shravan_kumar_reddy</a>
 */
public class Asymmetric {

    private static final String RSA
            = "RSA";

    // Generating public and private keys
    // using RSA algorithm.
    public static KeyPair generateRSAKkeyPair()
            throws Exception {
        SecureRandom secureRandom
                = new SecureRandom();

        KeyPairGenerator keyPairGenerator
                = KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(
                2048, secureRandom);

        return keyPairGenerator
                .generateKeyPair();
    }

    // Encryption function which converts
    // the plainText into a cipherText
    // using public Key.
    public static byte[] do_RSAEncryption(byte[] bytesin, PublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(bytesin);
    }

    // Decryption function which converts
    // the ciphertext back to the
    // original plaintext using private key.
    public static byte[] do_RSADecryption(byte[] bytesout, PrivateKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(bytesout);

        return result;
    }

    //TODO wrong
    /*
    public static PublicKey stringToPrivateKey(String strin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64.getDecoder().decode(strin);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

     */
    public static PublicKey byteArrayToPrivateKey(byte[] bin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bin);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // Driver code
    public static void main(String args[]) throws Exception {
        KeyPair keypair = generateRSAKkeyPair();

        System.out.println("Public Key is: " + DatatypeConverter.printHexBinary(keypair.getPublic().getEncoded()));
        System.out.println("Private Key is: " + DatatypeConverter.printHexBinary(keypair.getPrivate().getEncoded()));

        String plainText = "This is the PlainText " + "I want to Encrypt using RSA.";
        byte[] cipherText = do_RSAEncryption(plainText.getBytes(), keypair.getPublic());
        System.out.print("The Encrypted Text is: ");
        System.out.println(DatatypeConverter.printHexBinary(cipherText));

        String decryptedText = new String(do_RSADecryption(cipherText, keypair.getPrivate()));
        System.out.println("The decrypted text is: " + decryptedText);

    }
}