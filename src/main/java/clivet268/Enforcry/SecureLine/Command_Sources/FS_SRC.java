package clivet268.Enforcry.SecureLine.Command_Sources;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.TestOnly;

import java.io.IOException;
import java.util.ArrayList;

import static clivet268.Enforcry.Util.Univ.USERFILESPATH;
import static clivet268.Enforcry.Util.Univ.ndaryDirs;

//TODO need ot be static? not static? idk lmao

//TODO int, string and byte predefined outputs (type defined here) so that more than just byte arrays can be send for easy
// command info etc
public class FS_SRC {

    //TODO check for pemission level(in system and with a specific EFC access level test)

    /**
     * Gets the selected file from the EFC file root or the selected ndary root and sends it
     *
     * @param basebathbum the ndary root path for this transfer
     * @param url         the url fromt that selected basee path
     * @throws IOException
     */
    public static ArrayList<Pair<Integer, String>> run(int basebathbum, String url) {
        ArrayList<Pair<Integer, String>> output = new ArrayList<>();
        String path = "";
        if (basebathbum == 0) {
            path = USERFILESPATH + url;
            System.out.println(path);
        } else {
            try {
                path = ndaryDirs.get(basebathbum);
            } catch (Exception ignored) {
            }
        }
        if (!path.equals("")) {
            output.add(Pair.of(2, path));
            output.add(Pair.of(1, ("File " + url + " Gotten")));
            System.out.println("File " + url + " Gotten");
        } else {
            output.add(Pair.of(1, ("Base path num not valid")));
            System.out.println("Base path num not valid");
        }
        return output;
    }

    public static ArrayList<Pair<Integer, String>> get(String url) {
        return run(0, url);
    }


    public static ArrayList<Pair<Integer, String>> get(int bbb, String url) throws IOException {
        return run(bbb, url);
    }

    @TestOnly
    public static void main(String[] args) throws IOException {
        //test
        run(12419, "picture.ico");
        run(0, "picture.ico");
    }

}
