package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Encryption.Asymmetric;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static clivet268.Enforcry.Util.Univ.LENGTHVERIFIER;


public class EFCDataOutputStream {
    private static DataOutputStream din = null;
    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    public EFCDataOutputStream(DataOutputStream out, PrivateKey pkin, PublicKey pukin) {
        din = out;
        privateKey = pkin;
        publicKey = pukin;
    }

    public void writeE(byte[] b) throws Exception {
        //Ensure byte[] size of greater than 245 bytes for encryption purposes
        byte[] bytesout = Asymmetric.do_RSAEncryption(b, publicKey);
        byte[] codeOut = Asymmetric.do_RSAEncryption((103 + ":" + bytesout.length).getBytes(), publicKey);
        writeLen(codeOut.length);
        din.write(codeOut);
        din.write(bytesout, 0, bytesout.length);
    }

    //TODO optimize buffer size
    public void writeFile(File file) throws Exception {
        //TODO Ensure byte[] size of greater than 245 bytes for encryption purposes
        writeUTFE(file.getName());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[30000];
        int read = 0;
        while ((read = fis.read(buffer)) > 0) {
            byte[] bytesout = Asymmetric.do_RSAEncryption(buffer, publicKey);
            byte[] codeOut = Asymmetric.do_RSAEncryption((104 + ":" + bytesout.length).getBytes(), publicKey);
            writeLen(codeOut.length);
            din.write(codeOut);
            din.write(bytesout, 0, bytesout.length);
        }
        writeLen(-999);
    }


    public final void writeUTFE(String str) throws Exception {
        byte[] stringOut = Asymmetric.do_RSAEncryption(str.getBytes(), publicKey);

        byte[] codeOut = Asymmetric.do_RSAEncryption((102 + ":" + stringOut.length).getBytes(), publicKey);
        writeLen(codeOut.length);
        din.write(codeOut);
        din.write(stringOut);
        flush();
    }

    public final void writeIntE(int intin) throws Exception {
        byte[] stringOut = Asymmetric.do_RSAEncryption((intin + "").getBytes(), publicKey);
        byte[] codeOut = Asymmetric.do_RSAEncryption((101 + ":" + stringOut.length).getBytes(), publicKey);
        writeLen(codeOut.length);
        din.write(codeOut);
        din.write(stringOut);
        flush();
    }

    public final void writeLen(int intin) throws Exception {
        din.writeUTF(LENGTHVERIFIER + intin);
        flush();
    }

    //TODO build into write int and normal writes (not UTF), neede
    public void flush() throws IOException {
        din.flush();
    }


    public void close() throws IOException {
        din.close();
    }


}
