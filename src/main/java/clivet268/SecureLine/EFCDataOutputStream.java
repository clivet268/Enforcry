package clivet268.SecureLine;

import clivet268.Encryption.Asymmetric;

import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import static clivet268.Enforcry.logger;

public class EFCDataOutputStream {
    private static DataOutputStream din = null;
    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    public EFCDataOutputStream(DataOutputStream out, PrivateKey pkin, PublicKey pukin) {
        din = out;
        privateKey = pkin;
        publicKey = pukin;
    }

    public void writeE(byte b[]) throws Exception {
        byte[] bytesout = Asymmetric.do_RSAEncryption(b, publicKey);
        byte[] codeOut = Asymmetric.do_RSAEncryption((103 + ":" + bytesout.length).getBytes(), publicKey);
        din.writeInt(codeOut.length);
        logger.log(103 + ":" + bytesout.length);
        logger.log(Arrays.toString((103 + ":" + bytesout.length).getBytes()));
        logger.log(Arrays.toString(codeOut));
        din.write(codeOut);
        logger.log(Arrays.toString(bytesout));
        din.write(bytesout, 0, bytesout.length);
    }


    public final void writeUTFE(String str) throws Exception {
        byte[] stringOut = Asymmetric.do_RSAEncryption(str.getBytes(), publicKey);
        /*
        //TODO needed? already encrypted but the idea of this is small numbers in the hundreds could clash since are so small
        // investigate this, maybe not needed
        int verypseudorandompos = publicKey.getEncoded()[Enforcry.username.hashCode() % 127];
        byte[] randmess = new byte[verypseudorandompos * (Enforcry.username.hashCode() % 10 + 1)];
        new Random().nextBytes(randmess);
        int postoplace = (int) (((double)Enforcry.username.hashCode() % 10) * 10.791 + (randmess.length/2)) % randmess.length;

        List tot = new ArrayList(List.of(Arrays.copyOfRange(randmess, 0, postoplace)));
         */

        byte[] codeOut = Asymmetric.do_RSAEncryption((102 + ":" + stringOut.length).getBytes(), publicKey);
        din.writeInt(codeOut.length);
        logger.log(102 + ":" + stringOut.length);
        logger.log(Arrays.toString((102 + ":" + stringOut.length).getBytes()));
        logger.log(Arrays.toString(codeOut));
        din.write(codeOut);
        logger.log(Arrays.toString(stringOut));
        din.write(stringOut);
    }

    public final void writeIntE(int intin) throws Exception {
        byte[] stringOut = Asymmetric.do_RSAEncryption((intin + "").getBytes(), publicKey);
        byte[] codeOut = Asymmetric.do_RSAEncryption((101 + ":" + stringOut.length).getBytes(), publicKey);
        din.writeInt(codeOut.length);
        logger.log(102 + ":" + stringOut.length);
        logger.log(Arrays.toString((101 + ":" + stringOut.length).getBytes()));
        logger.log(Arrays.toString(codeOut));
        din.write(codeOut);
        logger.log(Arrays.toString(stringOut));
        din.write(stringOut);
        //flush();
    }

    //TODO build into write int and normal writes (not UTF), neede
    public void flush() throws IOException {
        din.flush();
    }


    public void close() throws IOException {
        din.close();
    }


}
