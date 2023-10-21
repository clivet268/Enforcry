package clivet268.Enforcry.Encryption;

import clivet268.Enforcry.Util.Univ;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static clivet268.Enforcry.Encryption.ChaCha20Poly1305.genChaCha20;
import static clivet268.Enforcry.Enforcry.logger;

/**
 * <a href="https://www.geeksforgeeks.org/asymmetric-encryption-cryptography-in-java/">source</a>
 * Note: source used Public key to decrypt and Private to encrypt? I altered to use the opposite as I think that is right
 *
 * @author <a href="https://auth.geeksforgeeks.org/user/nimma_shravan_kumar_reddy">nimma_shravan_kumar_reddy</a>
 */
public class EncrypterDecrypter {

    private static final String AES = "AES";
    private static final String AESDEFAULT = "AES/ECB/PKCS5PADDING";
    private static final String RSA = "RSA";

    //TODO handle exceptions VERY carefully
    // Generating public and private keys
    // using RSA algorithm.
    public static KeyPair generateRSAKkeyPair() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(4096, secureRandom);

            return keyPairGenerator.generateKeyPair();
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Encryption function which converts
     * the plainText into a cipherText
     * using public Key.
     */
    public static byte[] do_RSAEncryption(byte[] bytesin, PublicKey key) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(bytesin);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Decryption function which converts
     * the ciphertext back to the
     * original plaintext using private key.
     */
    //TODO no null case
    public static byte[] do_RSADecryption(byte[] bytesout, PrivateKey key) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);

            cipher.init(Cipher.DECRYPT_MODE, key);

            return cipher.doFinal(bytesout);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static byte[] do_AESEncryption(byte[] bytesin, SecretKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(AESDEFAULT);

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(bytesin);
        } catch (Exception ignored) {
            return null;
        }
    }


    // Decryption function which converts
    // the ciphertext back to the
    // original plaintext using key.
    public static byte[] do_AESDecryption(byte[] bytesout, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(AES);

            cipher.init(Cipher.DECRYPT_MODE, key);

            return cipher.doFinal(bytesout);
        } catch (Exception ignored) {
            return null;
        }
    }


    //TODO string to private key, aka password

    public static PublicKey byteArrayToKey(byte[] bin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //TODO test only
        System.out.println(new String(bin));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bin);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static SecretKey stringToSecretKey(final String myKey) {
        return new SecretKeySpec(myKey.getBytes(StandardCharsets.UTF_8), "AES");
    }

    //TODO make
    /*
    public static PublicKey stringToPublicKey(final String myKey) {
        return new SecretKeySpec(myKey.getBytes(StandardCharsets.UTF_8), "AES");
    }

     */


    //TODO make it AES and RSA specific
    public static String genX(int x) throws NoSuchAlgorithmException {
        String beegkee = "";
        for (int i = 0; i < x; i++) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            beegkee += Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        }
        return beegkee;
    }

    public static String decrypt(final String strToDecrypt, final String secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            //TODO CBC mode or just more full padding if this padding isnt sufficient
            cipher.init(Cipher.DECRYPT_MODE, stringToSecretKey(secret));
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: \n");
            e.printStackTrace();
        }
        return "";
    }


    public static void fek(File file) throws Exception {
        SecretKey kii = genChaCha20();
        Path pathout = Paths.get(Univ.USERFILESPATH + File.separator + Univ.getRandString(9) + "_=_" + Univ.getRandString(6));
        Files.createDirectory(pathout);
        recursefekc(file, kii, pathout.toString());

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(kii.toString());
    }


    //TODO be very careful throwing exceptions
    //TODO secure key saving, no paintext in files, plaintext in command line is so-so but better
    public static void fek() {
        File file = Univ.filechooser();
        try {
            fek(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void fekl() throws Exception {
        File file = Univ.filechooser();
        SecretKey kii = genChaCha20();
        Path pathout = Paths.get(file.getParent() + File.separator + Univ.getRandString(9) + "_=_" + Univ.getRandString(6));
        Files.createDirectory(pathout);
        recursefekc(file, kii, pathout.toString());

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(new String(kii.toString().getBytes()));
    }


    //TODO secure key storage would make using individual keys for files easier and accessing them on different inner file
    // more convenient, should be optional as most things
    // TODO obfuscate file names? overkill?
    public static void recursefekc(File file, SecretKey kii, String curpath) throws IOException {

        try {
            if (file.isDirectory()) {
                System.out.println(curpath);
                Path pathout = Paths.get(curpath + File.separator + file.getName() + "_=_" + file.hashCode());
                try {
                    Files.createDirectory(pathout);
                } catch (FileAlreadyExistsException ignored) {
                }
                for (File infile : file.listFiles()) {
                    recursefekc(infile, kii, pathout.toString());
                }
            } else {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String out = new String(do_AESEncryption(bytes, kii));

                byte[] decode = Base64.getDecoder().decode(out.getBytes());
                Path pathout = Paths.get(curpath + File.separator + file.getName() + "_=_" + file.hashCode());
                System.out.println(pathout);
                Files.createFile(pathout);
                Files.write(pathout, decode);
                //Files.write(Path.of(Univ.enforcrybasepath + "." + file.hashCode() + "key"),kii.getBytes());
            }

        } catch (NullPointerException e) {
            System.out.println("null file");
        }
    }

    public static void fekg(SecretKey kii) throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();

        recursefekc(file, kii, Univ.ENFORCRYBASEPATH);

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(kii.toString());
    }

    //Decryption

    public static void fdk(File file, String kii) throws IOException, NoSuchAlgorithmException {
        recursedek(file, kii, Univ.ENFORCRYBASEPATH);
    }

    public static void fdk(String kii) throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();
        fdk(file, kii);

    }

    public static void fdkl(String kii) throws IOException {
        File file = Univ.filechooser();
        Path pathout = Path.of(file.getParent());
        logger.log(pathout.toString());
        recursedek(file, kii, pathout.toString());
    }

    public static void recursedek(File file, String kii, String curpath) throws IOException {
        try {
            if (file.isDirectory()) {
                logger.log(file.getPath());
                logger.log(curpath);
                Path pathout = Paths.get(curpath + File.separator + file.getName().substring(0, file.getName().lastIndexOf("_=_")));
                try {
                    Files.createDirectory(pathout);
                } catch (FileAlreadyExistsException ignored) {
                }
                for (File infile : file.listFiles()) {
                    logger.log(file.getName());
                    logger.log(file.getAbsolutePath());
                    recursedek(infile, kii, pathout.toString());
                }
            } else {
                byte[] bytess = Files.readAllBytes(file.toPath());
                String ss = Base64.getEncoder().encodeToString(bytess);
                String outt = decrypt(ss, kii);

                byte[] decodee = Base64.getDecoder().decode(outt.getBytes());
                Path pathout = Paths.get(curpath + File.separator + file.getName().substring(0, file.getName().lastIndexOf("_=_")));
                logger.log(pathout.toString());
                Files.createFile(pathout);
                Files.write(pathout, decodee);
            }
        } catch (NullPointerException e) {
            System.out.println("null file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Non-command FEKing and DEKing
    public static byte[] fdktostream(File file, String kii) throws IOException, NoSuchAlgorithmException {
        byte[] bytess = Files.readAllBytes(file.toPath());
        System.out.println();
        String ss = Base64.getEncoder().encodeToString(bytess);
        System.out.println("SSe" + ss);
        String outt = decrypt(ss, kii);
        System.out.println(outt);

        return outt.getBytes();
    }


    public static String genNonce(String strin) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception ignored) {

        }
        byte[] encodedhash = digest.digest(strin.getBytes(StandardCharsets.UTF_8));
        return new String(encodedhash);
    }

    public static void main(String[] args) throws Exception {
        String s = genChaCha20().toString();
        System.out.println(s);

    }
}