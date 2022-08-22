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
    ArrayList<byte[]> output = new ArrayList<>();
    public FS_SRC(String ...  url) throws IOException {
        for (String a: url) {
            try {
                output.add(Files.readAllBytes(Path.of(a)));
                System.out.println("File Gotten");
            }
            catch (IOException e){
                System.out.println("File Not Found");
            }
        }
    }


    public static ArrayList<byte[]> get(String url) throws IOException {
        FS_SRC op = new FS_SRC(url);
        return op.output;
    }



}
