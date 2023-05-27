package clivet268.Enforcry.Util;

import clivet268.Enforcry.Encryption.EncrypterDecrypter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class DriveKeyWrite {

    //TODO pattern breaks?
    //TODO having a drive contained key may not be the best approach for a few reasons, as well as this brute force approach
    // another layer of complexity is needed.
    // First of all, there can be software level intercepts that not only read the contents of the drive as a whole but could possibly tell the location
    // on the drive being read from or the values of the key themselves. Secondly the issue of getting the key from memory once it is formed is a real
    // issue, unfortunately more so than I thought. This means that the whole environment of enforcry is not secure if a key can be so easily accessed and
    // you do not have such low level control of the system you are working on. Ideally systems with enforcry will also have lower level protections but
    // that falls under a greater, possibly future version of enforcry but is currently outside of the scope of enforcry.
    // source that raised concern: https://www.feistyduck.com/library/openssl-cookbook/online/openssl-command-line/key-generation.html
    // Using a passphrase with a key is optional, but strongly recommended. Protected keys can be safely stored, transported, and backed up.
    // On the other hand, such keys are inconvenient, because they can’t be used without their passphrases. For example, you might be asked
    // to enter the passphrase every time you wish to restart your web server. For most, this is either too inconvenient or has unacceptable
    // availability implications. In addition, using protected keys in production does not actually increase the security much, if at all.
    // This is because, once activated, private keys are kept unprotected in program memory; an attacker who can get to the server can get
    // the keys from there with just a little more effort. Thus, passphrases should be viewed only as a mechanism for protecting private
    // keys when they are not installed on production systems. In other words, it’s all right to keep passphrases on production systems,
    // next to the keys. If you need better security in production, you should invest in a hardware solution.
    // This raises concerns about the whole concept of enforcry as there are screen monitoring, io monitoring and memory monitoring programs
    // already in existence.
    // As of now the scope of enforcry expands and this java based environment is not enough.
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        int[] keyyspots = new int[8];
        ArrayList<String> mun = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            mun.add(EncrypterDecrypter.genX(2));
        }
        for (int ii = 0; ii <= 8; ii++) {
            String s = EncrypterDecrypter.genX(2);
            int rr = r.nextInt(10000);
            keyyspots[ii] = rr;
            mun.set(rr, s);
        }


        System.out.println("Select base device path");
        File keyfile = Univ.filechooser();
        System.out.println("Keyspots written, make sure you get all of them");
        for (int i : keyyspots) {
            System.out.print(i + " ");
        }
        System.out.println();
        //TODO ensure file is written and written well
        Files.createFile(Path.of(keyfile.toPath() + File.separator + "key"));
    }
}
