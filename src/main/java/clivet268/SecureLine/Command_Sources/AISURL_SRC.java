package clivet268.SecureLine.Command_Sources;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class AISURL_SRC {
    ArrayList<byte[]> output = new ArrayList<>();
    public AISURL_SRC(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("EFCMozilla/5.0").get();
        Elements images = doc.select("img");
        for (Element el : images) {
            String imageUrl = el.absUrl("src");
            if (!trydecodeb64(imageUrl)) {
                try {
                    Connection.Response resultImageResponse = Jsoup.connect(imageUrl).ignoreContentType(true).execute();
                    output.add(resultImageResponse.bodyAsBytes());
                } catch (IllegalArgumentException ex) {
                    //System.out.println(ex);
                    System.out.println("Malformed, skipping");
                }
            }
        }
    }

    public boolean trydecodeb64(String str){
        if(str.startsWith("data")) {
            str = str.trim().replaceFirst("data[:]image[/]([a-z])+;base64,", "");
            output.add(Base64.getDecoder().decode(str));
            System.out.println("Decoded 64");
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        AISURL_SRC test = new AISURL_SRC("https://www.amazon.com/");
        test.output.forEach(bytes -> {
            try {
                Random ran = new Random();
                String nam = "";
                for(int i = 0; i < 20; i++) {
                    nam += ran.nextInt(9);
                }
                Files.write(Path.of(System.getProperty("user.dir") + File.separator + "test" + File.separator + nam), bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
