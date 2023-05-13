package clivet268.Encryption;

import clivet268.Util.Univ;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static clivet268.Enforcry.logger;

public class EncrypterDecrypter {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static String encrypt(final String strToEncrypt, final String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }
        return null;
    }

    public static String gen2048() throws NoSuchAlgorithmException {
        String beegkee = "";
        for(int i = 0; i < 8; i ++) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            beegkee += Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        }
        return beegkee;
    }
    public static String decrypt(final String strToDecrypt, final String secret) {
        try {
            setKey(secret);
            System.out.println(secretKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            //TODO CBC mode
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: \n");
            e.printStackTrace();
        }
        return null;
    }


    public static void fek(File file) throws IOException, NoSuchAlgorithmException {
        String kii = gen2048();

        Path pathout = Paths.get(Univ.ENFORCRYFILESPATH + File.separator + Univ.getRandString(9) + "_=_" + Univ.getRandString(6));
        Files.createDirectory(pathout);
        recursefekc(file, kii, pathout.toString());

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(new String(kii.getBytes()));
    }


    //TODO secure key saving, no paintext in files, plaintext in command line is so-so but better
    public static void fek() throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();
        fek(file);
    }

    public static void fekl() throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();
        String kii = gen2048();
        Path pathout = Paths.get(file.getParent() + File.separator + Univ.getRandString(9) + "_=_" + Univ.getRandString(6));
        Files.createDirectory(pathout);
        recursefekc(file, kii, pathout.toString());

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(new String(kii.getBytes()));
    }


    //TODO secure key storage would make using individual keys for files easier and accessing them on different inner file
    // more convenient, should be optional as most things
    // TODO obfuscate file names? overkill?
    public static void recursefekc(File file, String kii, String curpath) throws IOException {

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
                String s = Base64.getEncoder().encodeToString(bytes);
                String out = encrypt(s, kii);

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

    public static void fekg(String kii) throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();

        recursefekc(file, kii, Univ.ENFORCRYBASEPATH);

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(new String(kii.getBytes()));
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


}