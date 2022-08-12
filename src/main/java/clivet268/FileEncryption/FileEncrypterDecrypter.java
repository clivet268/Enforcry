package clivet268.FileEncryption;

import clivet268.Util.Univ;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class FileEncrypterDecrypter{

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
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            //TODO CBC
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
        }
        return null;
    }


    public static void fek() throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();
        Path of = Path.of(Univ.enforceybasepath);
        if(!Files.exists(of)) {
            Files.createDirectory(of);
        }
        String kii = gen2048();
        assert file != null;
        byte[] bytes = Files.readAllBytes(file.toPath());
        String s = Base64.getEncoder().encodeToString(bytes);
        String out = encrypt(s, kii);

        byte[] decode = Base64.getDecoder().decode(out.getBytes());
        Path pathout = Paths.get(Univ.enforceybasepath + file.hashCode());
        Files.write(pathout, decode);
        System.out.println("Key saved, make sure you get all of it\n_\n\n");
        Files.write(Path.of(Univ.enforceybasepath + "." + file.hashCode() + "key"),kii.getBytes());
    }

    public static void fek(File file) throws IOException, NoSuchAlgorithmException {
        Path of = Path.of(Univ.enforceybasepath);
        if(!Files.exists(of)) {
            Files.createDirectory(of);
        }
        String kii = gen2048();
        assert file != null;
        byte[] bytes = Files.readAllBytes(file.toPath());
        String s = Base64.getEncoder().encodeToString(bytes);
        String out = encrypt(s, kii);

        byte[] decode = Base64.getDecoder().decode(out.getBytes());
        Path pathout = Paths.get(Univ.enforceybasepath + file.hashCode());
        Files.write(pathout, decode);
        System.out.println("Key saved, make sure you get all of it\n_\n\n");
        Files.write(Path.of(Univ.enforceybasepath + "." + file.hashCode() + "key"),kii.getBytes());
    }

    public static void fdk(File file, String kii) throws IOException, NoSuchAlgorithmException {
        Path of = Path.of(Univ.enforceybasepath);
        if(!Files.exists(of)) {
            Files.createDirectory(of);
        }

        assert file != null;

        byte[] bytess = Files.readAllBytes(file.toPath());
        String ss = Base64.getEncoder().encodeToString(bytess);
        String outt = decrypt(ss, kii);

        byte[] decodee = Base64.getDecoder().decode(outt.getBytes());
        Files.write(Paths.get(Univ.enforceybasepath + Arrays.hashCode(decodee)), decodee);
    }

    public static void fdk(String kii) throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();

        Path of = Path.of(Univ.enforceybasepath);
        if(!Files.exists(of)) {
            Files.createDirectory(of);
        }

        assert file != null;

        byte[] bytess = Files.readAllBytes(file.toPath());
        String ss = Base64.getEncoder().encodeToString(bytess);
        String outt = decrypt(ss, kii);

        byte[] decodee = Base64.getDecoder().decode(outt.getBytes());
        Files.write(Paths.get(Univ.enforceybasepath + Arrays.hashCode(decodee)), decodee);

    }



}