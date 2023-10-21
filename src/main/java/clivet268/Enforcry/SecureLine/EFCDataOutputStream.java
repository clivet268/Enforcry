package clivet268.Enforcry.SecureLine;


import clivet268.Enforcry.Encryption.EncrypterDecrypter;

import javax.crypto.SecretKey;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static clivet268.Enforcry.Util.Univ.LENGTHVERIFIER;

//TODO USE AES
public class EFCDataOutputStream {
    private static DataOutputStream dout = null;
    private static SecretKey symmetric = null;

    public EFCDataOutputStream(DataOutputStream in, SecretKey s) {
        dout = in;
        symmetric = s;

    }

    public void writeE(byte[] b) throws Exception {
        byte[] bytesout = EncrypterDecrypter.do_AESDecryption(b, symmetric);
        byte[] codeOut = EncrypterDecrypter.do_AESDecryption((103 + ":" + bytesout.length).getBytes(), symmetric);
        writeLen(codeOut.length);
        dout.write(codeOut);
        dout.write(bytesout, 0, bytesout.length);
    }

    //TODO optimize buffer size
    public void writeFile(File file) throws Exception {
        //TODO Ensure byte[] size of greater than 245 bytes for encryption purposes
        writeUTFE(file.getName());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[30000];
        int read = 0;
        while ((read = fis.read(buffer)) > 0) {
            byte[] bytesout = EncrypterDecrypter.do_AESDecryption(buffer, symmetric);
            byte[] codeOut = EncrypterDecrypter.do_AESDecryption((104 + ":" + bytesout.length).getBytes(), symmetric);
            writeLen(codeOut.length);
            dout.write(codeOut);
            dout.write(bytesout, 0, bytesout.length);
        }
        writeLen(-999);
    }


    //TODO very clunky
    public final void writeUTFE(String str) throws Exception {
        System.out.println(str.getBytes().length);

        byte[] codeOut = EncrypterDecrypter.do_AESDecryption((102 + ":" + str.length()).getBytes(), symmetric);
        writeLen(codeOut.length);
        dout.write(codeOut);

        while (str.length() > 0) {
            byte[] stringOut;
            if (str.length() > 245) {
                String tw = str.substring(0, 245);
                stringOut = EncrypterDecrypter.do_AESDecryption(tw.getBytes(), symmetric);
                str = str.substring(245);
            } else {
                System.out.println(str.length());
                stringOut = EncrypterDecrypter.do_AESDecryption(str.getBytes(), symmetric);
                str = "";
            }
            dout.write(stringOut);
        }
        flush();
    }

    public final void writeIntE(int intin) throws Exception {
        byte[] stringOut = EncrypterDecrypter.do_AESDecryption((intin + "").getBytes(), symmetric);
        byte[] codeOut = EncrypterDecrypter.do_AESDecryption((101 + ":" + stringOut.length).getBytes(), symmetric);
        writeLen(codeOut.length);
        dout.write(codeOut);
        dout.write(stringOut);
        flush();
    }

    public final void writeLen(int intin) throws Exception {
        dout.writeUTF(LENGTHVERIFIER + intin);
        flush();
    }

    //TODO build into write int and normal writes (not UTF), neede
    public void flush() throws IOException {
        dout.flush();
    }


    public void close() throws IOException {
        dout.close();
    }


}
