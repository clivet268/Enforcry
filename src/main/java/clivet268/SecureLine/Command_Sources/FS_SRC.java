package clivet268.SecureLine.Command_Sources;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;

public class FS_SRC {

    //TODO check for pemission level(in system and with a specific EFC access level test)

    /**
     * Gets the selected file from the EFC file root or the selected ndary root
     *
     * @param basebathbum the ndary root path for this transfer
     * @param url         the url fromt that selected basee path
     * @throws IOException
     */
    public static ArrayList<Pair<Integer, byte[]>> run(int basebathbum, String url) {
        ArrayList<Pair<Integer, byte[]>> output = new ArrayList<>();
        try {
            String path = "";
            if (basebathbum == 0) {
                path = ENFORCRYFILESPATH + url;
                System.out.println(path);
            } else {
                try {
                    path = ndaryDirs.get(basebathbum);
                } catch (Exception ignored) {
                }
            }
            if (!path.equals("")) {
                output.add(Pair.of(2, Files.readAllBytes(Path.of(path))));
                output.add(Pair.of(1, ("File " + url + " Gotten").getBytes()));
                System.out.println("File " + url + " Gotten");
            } else {
                output.add(Pair.of(1, ("Base path num not valid").getBytes()));
                System.out.println("Base path num not valid");
            }
        } catch (IOException e) {
            e.printStackTrace();
            output.add(Pair.of(1, ("File " + url + " Not Found").getBytes()));
            System.out.println("File " + url + " Not Found");
        }
    }


    public static ArrayList<Pair<Integer, byte[]>> get(int bbb, String url) throws IOException {
        return run(bbb, url);
    }



}
