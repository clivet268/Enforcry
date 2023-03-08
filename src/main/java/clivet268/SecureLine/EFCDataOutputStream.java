package clivet268.SecureLine;

import clivet268.Encryption.Asymmetric;

import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;


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
        din.writeInt(codeOut.length);
        din.write(codeOut);
        din.write(bytesout, 0, bytesout.length);
    }


    public final void writeUTFE(String str) throws Exception {
        byte[] stringOut = Asymmetric.do_RSAEncryption(str.getBytes(), publicKey);

        byte[] codeOut = Asymmetric.do_RSAEncryption((102 + ":" + stringOut.length).getBytes(), publicKey);
        din.writeInt(codeOut.length);
        din.write(codeOut);
        din.write(stringOut);
        flush();
    }

    public final void writeIntE(int intin) throws Exception {
        byte[] stringOut = Asymmetric.do_RSAEncryption((intin + "").getBytes(), publicKey);
        byte[] codeOut = Asymmetric.do_RSAEncryption((101 + ":" + stringOut.length).getBytes(), publicKey);
        din.writeInt(codeOut.length);
        din.write(codeOut);
        din.write(stringOut);
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
