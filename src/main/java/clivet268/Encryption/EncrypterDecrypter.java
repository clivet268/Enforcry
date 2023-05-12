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


    //TODO secure key saving, no paintext in files, plaintext in command line is so-so but better
    public static void fek() throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();
        String kii = gen2048();

        Path pathout = Paths.get(Univ.ENFORCRYFILESPATH + File.separator + Univ.getRandString(69));
        Files.createDirectory(pathout);
        recursefekc(file, kii, pathout.toString());

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(new String(kii.getBytes()));
    }

    public static void fekl() throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();
        String kii = gen2048();
        Path pathout = Paths.get(file.getParent() + File.separator + Univ.getRandString(69));
        Files.createDirectory(pathout);
        recursefekcl(file, kii, pathout.toString());

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(new String(kii.getBytes()));
    }


    //TODO secure key storage would make using individual keys for files easier and accessing them on different inner file
    // more convenient, should be optional as most things
    public static void recursefekc(File file, String kii, String curpath) throws IOException {
        if (file.isDirectory()) {
            for (File infile : file.listFiles()) {
                System.out.println(curpath);
                Path pathout = Paths.get(curpath + File.separator + file.getName() + "_" + file.hashCode());
                try {
                    Files.createDirectory(pathout);
                } catch (FileAlreadyExistsException ignored) {
                }
                recursefekc(infile, kii, pathout.toString());
            }
        } else {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String s = Base64.getEncoder().encodeToString(bytes);
                String out = encrypt(s, kii);

                byte[] decode = Base64.getDecoder().decode(out.getBytes());
                Path pathout = Paths.get(curpath + File.separator + file.getName());
                System.out.println(pathout);
                Files.createFile(pathout);
                Files.write(pathout, decode);
                //Files.write(Path.of(Univ.enforcrybasepath + "." + file.hashCode() + "key"),kii.getBytes());
            } catch (NullPointerException e) {
                System.out.println("null file");
            }
        }
    }

    public static void recursefekcl(File file, String kii, String curpath) {
        if (file.isDirectory()) {
            for (File infile : file.listFiles()) {
                recursefekcl(infile, kii, curpath + File.separator + file.getName() + "_" + file.hashCode());
            }
        } else {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String s = Base64.getEncoder().encodeToString(bytes);
                String out = encrypt(s, kii);

                byte[] decode = Base64.getDecoder().decode(out.getBytes());
                Path pathout = Paths.get(curpath + file.getName());
                Files.write(pathout, decode);
            } catch (NullPointerException e) {
                System.out.println("null file");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void fek(String kii) throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();

        recursefekc(file, kii, Univ.enforcrybasepath);

        System.out.println("Key written, make sure you get all of it\n");
        System.out.println(new String(kii.getBytes()));
    }


    public static void fek(File file) throws IOException, NoSuchAlgorithmException {
        Path of = Path.of(Univ.enforcrybasepath);
        if (!Files.exists(of)) {
            Files.createDirectory(of);
        }
        String kii = gen2048();
        assert file != null;
        byte[] bytes = Files.readAllBytes(file.toPath());
        String s = Base64.getEncoder().encodeToString(bytes);
        String out = encrypt(s, kii);

        byte[] decode = Base64.getDecoder().decode(out.getBytes());
        Path pathout = Paths.get(Univ.enforcrybasepath + file.hashCode());
        Files.write(pathout, decode);
        System.out.println("Key saved, make sure you get all of it ;)\n_\n\n");
        Files.write(Path.of(Univ.enforcrybasepath + "." + file.hashCode() + "key"),kii.getBytes());
    }

    public static void fek(File file, String p) throws IOException, NoSuchAlgorithmException {
        assert file != null;
        byte[] bytes = Files.readAllBytes(file.toPath());
        String s = Base64.getEncoder().encodeToString(bytes);
        String out = encrypt(s, p);

        assert out != null;
        byte[] decode = Base64.getDecoder().decode(out.getBytes());
        Path pathout = Paths.get(Univ.enforcrybasepath + file.hashCode());
        Files.write(pathout, decode);
    }
    public static void fek(File file, File filein, String p) throws IOException, NoSuchAlgorithmException {
        assert file != null;
        byte[] bytes = Files.readAllBytes(file.toPath());
        String s = Base64.getEncoder().encodeToString(bytes);
        String out = encrypt(s, p);

        assert out != null;
        byte[] decode = Base64.getDecoder().decode(out.getBytes());
        Path pathout = Paths.get(filein.getAbsolutePath() + file.hashCode());
        Files.write(pathout, decode);
    }
    public static File fek(File file, String filein, String p, String of) throws IOException, NoSuchAlgorithmException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        String s = Base64.getEncoder().encodeToString(bytes);
        System.out.println("the s" +s);
        String out = encrypt(s, p);
        System.out.println("THE OUT " + out);

        byte[] decode = Base64.getDecoder().decode(out.getBytes());
        System.out.println("Shaka boom?");
        String pathout = filein + "." + of;
        System.out.println("Shaka boom");
        Path of1 = Path.of(pathout);
        Files.createFile(of1);
        Files.write(of1, decode);
        System.out.println("Sucka boom");
        return of1.toFile();
    }
    public static void fdk(File file, String kii) throws IOException, NoSuchAlgorithmException {
        Path of = Path.of(Univ.enforcrybasepath);
        if(!Files.exists(of)) {
            Files.createDirectory(of);
        }

        assert file != null;

        byte[] bytess = Files.readAllBytes(file.toPath());
        String ss = Base64.getEncoder().encodeToString(bytess);
        String outt = decrypt(ss, kii);

        byte[] decodee = Base64.getDecoder().decode(outt.getBytes());
        Files.write(Paths.get(Univ.enforcrybasepath + Arrays.hashCode(decodee)), decodee);
    }

    public static void fdk(String kii) throws IOException, NoSuchAlgorithmException {
        File file = Univ.filechooser();

        Path of = Path.of(Univ.enforcrybasepath);
        if(!Files.exists(of)) {
            Files.createDirectory(of);
        }

        assert file != null;

        byte[] bytess = Files.readAllBytes(file.toPath());
        String ss = Base64.getEncoder().encodeToString(bytess);
        String outt = decrypt(ss, kii);

        byte[] decodee = Base64.getDecoder().decode(outt.getBytes());
        Files.write(Paths.get(Univ.enforcrybasepath + Arrays.hashCode(decodee)), decodee);

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