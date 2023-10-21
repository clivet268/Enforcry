package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Encryption.EncrypterDecrypter;

import javax.crypto.SecretKey;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static clivet268.Enforcry.Util.Univ.*;

//TODO asym key to transfer sym key ASAP
public class EFCDataInputStream {
    private static DataInputStream din = null;
    private static SecretKey symmetric = null;

    /**
     * @param in passthrough for the socket's data input stream
     */
    public EFCDataInputStream(DataInputStream in, SecretKey sSk) {
        din = in;
        symmetric = sSk;

    }

    //TODO readfullyE make it if needed

    public byte[] read(byte[] b, int off, int len) throws Exception {
        int codelen = din.readInt();
        byte[] codine = new byte[codelen];
        din.readFully(codine, 0, codelen);
        String ininfo = new String(EncrypterDecrypter.do_AESDecryption(codine, symmetric));
        int type = Integer.parseInt(ininfo.substring(0, ininfo.indexOf(":")));
        int msgleng = Integer.parseInt(ininfo.substring(ininfo.indexOf(":") + 1));
        byte[] bytesin = new byte[msgleng];
        System.out.println(type);
        din.readFully(bytesin);
        return EncrypterDecrypter.do_AESDecryption(bytesin, symmetric);
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
            String ininfo = new String(EncrypterDecrypter.do_AESDecryption(codine, symmetric));
            //int type = Integer.parseInt(ininfo.substring(0, ininfo.indexOf(":")));
            int msgleng = Integer.parseInt(ininfo.substring(ininfo.indexOf(":") + 1));
            byte[] bytesin = new byte[msgleng];
            din.readFully(bytesin);
            os.write(EncrypterDecrypter.do_AESDecryption(bytesin, symmetric));

            codelen = readLength();
        }
        os.close();
        File f = new File(of1.toString());
        f.renameTo(new File(filename));
        return f.getPath();
    }


    //TODO clunky
    public String readUTFE() throws Exception {
        String cummulative = "";
        int codelen = readLength();
        byte[] codine = new byte[codelen];
        din.readFully(codine);
        String ininfo = new String(EncrypterDecrypter.do_AESDecryption(codine, symmetric));
        int type = Integer.parseInt(ininfo.substring(0, ininfo.indexOf(":")));
        int msgleng = Integer.parseInt(ininfo.substring(ininfo.indexOf(":") + 1));
        System.out.println(msgleng);
        System.out.println();
        while (msgleng > 0) {
            byte[] stringn;
            if (msgleng > 245) {
                stringn = new byte[245];
                msgleng -= 245;
            } else {
                stringn = new byte[msgleng];
            }
            //System.out.println(type);
            din.readFully(stringn);
            System.out.println(new String(stringn));
            cummulative += new String(EncrypterDecrypter.do_AESDecryption(stringn, symmetric));
        }
        //TODO hash confirm?
        return cummulative;
    }

    public int readIntE() throws Exception {
        int codelen = readLength();
        byte[] codine = new byte[codelen];
        din.readFully(codine, 0, codelen);
        String ininfo = new String(EncrypterDecrypter.do_AESDecryption(codine, symmetric));
        int msgleng = Integer.parseInt(ininfo.substring(ininfo.indexOf(":") + 1));
        byte[] stringn = new byte[msgleng];
        din.readFully(stringn);
        String strout = new String(EncrypterDecrypter.do_AESDecryption(stringn, symmetric));
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
