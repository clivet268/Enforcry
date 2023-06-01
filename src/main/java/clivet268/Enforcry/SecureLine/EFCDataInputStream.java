package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Encryption.Asymmetric;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.PrivateKey;
import java.security.PublicKey;

import static clivet268.Enforcry.Util.Univ.*;


public class EFCDataInputStream {
    private static DataInputStream din = null;
    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    /**
     * @param in    passthrough for the socket's data input stream
     * @param pkin  the private key should be the key of the current user (ex. on client should be client's private key)
     * @param pukin the public key should be the key of the communicating user (ex. on client should be server's private key)
     */
    public EFCDataInputStream(DataInputStream in, PrivateKey pkin, PublicKey pukin) {
        din = in;
        privateKey = pkin;
        publicKey = pukin;
    }
/*
    public final void readFullyE(byte b[]) throws IOException {
        readFullyE(b, 0, b.length);
    }

    public final void readFullyE(byte b[], int off, int len) throws IOException {
        Objects.checkFromIndexSize(off, len, b.length);
        int n = 0;
        while (n < len) {
            int count = din.read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }

 */

    public byte[] read(byte b[], int off, int len) throws Exception {
        int codelen = din.readInt();
        byte[] codine = new byte[codelen];
        din.readFully(codine, 0, codelen);
        String ininfo = new String(Asymmetric.do_RSADecryption(codine, privateKey));
        int type = Integer.parseInt(ininfo.substring(0, ininfo.indexOf(":")));
        int msgleng = Integer.parseInt(ininfo.substring(ininfo.indexOf(":") + 1));
        byte[] bytesin = new byte[msgleng];
        System.out.println(type);
        din.readFully(bytesin);
        return Asymmetric.do_RSADecryption(bytesin, privateKey);
    }

    public String readFile() throws Exception {
        String filename = readUTFE();
        filename = USERFILESPATH + filename + getrandname();
        Path of1 = Path.of(filename + ".temp");
        OutputStream os = Files.newOutputStream(of1, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        int codelen = readLength();
        while (codelen != -999) {
            byte[] codine = new byte[codelen];
            din.readFully(codine, 0, codelen);
            String ininfo = new String(Asymmetric.do_RSADecryption(codine, privateKey));
            //int type = Integer.parseInt(ininfo.substring(0, ininfo.indexOf(":")));
            int msgleng = Integer.parseInt(ininfo.substring(ininfo.indexOf(":") + 1));
            byte[] bytesin = new byte[msgleng];
            din.readFully(bytesin);
            os.write(Asymmetric.do_RSADecryption(bytesin, privateKey));

            codelen = readLength();
        }
        os.close();
        File f = new File(of1.toString());
        f.renameTo(new File(filename));
        return f.getPath();
    }


    public String readUTFE() throws Exception {
        int codelen = readLength();
        byte[] codine = new byte[codelen];
        din.readFully(codine, 0, codelen);
        String ininfo = new String(Asymmetric.do_RSADecryption(codine, privateKey));
        int type = Integer.parseInt(ininfo.substring(0, ininfo.indexOf(":")));
        int msgleng = Integer.parseInt(ininfo.substring(ininfo.indexOf(":") + 1));
        byte[] stringn = new byte[msgleng];
        //System.out.println(type);
        din.readFully(stringn);
        return new String(Asymmetric.do_RSADecryption(stringn, privateKey));
        //TODO hash confirm?
    }

    public int readIntE() throws Exception {
        int codelen = readLength();
        byte[] codine = new byte[codelen];
        din.readFully(codine, 0, codelen);
        String ininfo = new String(Asymmetric.do_RSADecryption(codine, privateKey));
        int msgleng = Integer.parseInt(ininfo.substring(ininfo.indexOf(":") + 1));
        byte[] stringn = new byte[msgleng];
        din.readFully(stringn);
        String strout = new String(Asymmetric.do_RSADecryption(stringn, privateKey));
        return Integer.parseInt(strout);

        //TODO hash confirm?
    }

    public int readLength() throws Exception {
        String length = din.readUTF();
        try {
            return Integer.parseInt(length.substring(LENGTHVERIFIER.length()));
        } catch (NumberFormatException e) {
            throw new InvalidLengthIndicatorException();
        }
    }

    public void close() throws IOException {
        din.close();
    }
}
