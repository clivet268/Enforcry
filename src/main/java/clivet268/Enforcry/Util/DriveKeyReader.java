package clivet268.Enforcry.Util;

import clivet268.Enforcry.Encryption.EncrypterDecrypter;
import clivet268.Enforcry.Enforcry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DriveKeyReader {
    //TODO make a keysave with redundancy since these drives can be finicky and are cheap
    // #1 redundancy, dont wanna lose a key, #2 speed, could fill whole sectors bu there are better algorithms
    // #3 physical seperation, ties into redundancy but is sort of an extra qualifier that would be nice as i have seen random offsets if
    // the data overwrites stuff near the drive info and filesystem, etc. This also means redundancy should be sector AND offset secure
    // there are some things that can be done like counting until first byte but there may be other unforseen offset errors

    public static String readkey() throws IOException, NoSuchAlgorithmException {
        Scanner s = new Scanner(System.in);
        File keyfile = Univ.filechooser();
        System.out.println("Enter keyspots");
        String r = s.nextLine();
        String kii = "";
        String string = new String(Files.readAllBytes(keyfile.toPath()));
        while (r.length() > 0) {
            int index = r.indexOf(' ');
            int begindex = Integer.parseInt(r.substring(0, index)) * 512;
            kii += string.substring(begindex, begindex + 512);
            r = r.substring(index, r.length() - 1);
        }
        verifyKey(kii);
        return kii;
    }

    public static void decryptUserPath() throws IOException, NoSuchAlgorithmException {
        String kii = readkey();
        EncrypterDecrypter.fdk(Univ.USERBASEPATH);
    }

    public static void verifyDrive() throws FileNotFoundException {
        String driveno = "1";
        File diskRoot = new File("\\\\.\\PhysicalDrive" + driveno);
        RandomAccessFile diskAccess = new RandomAccessFile(diskRoot, "r");
    }

    //TODO detect malicious devices? and verify the key isnt malicious
    public static void verifyKey(String kii) throws IOException {
        if (EncrypterDecrypter.encrypt(Enforcry.getUsername(), kii).equals(Enforcry.getUsername())) {
            ;
        }
    }
}
