package clivet268.SecureLine.Command_Sources;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
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
        try {
            Document doc = Jsoup.connect(url).userAgent("EFCMozilla/5.0").get();
            System.out.println("Getting from - " + doc.title());
            Elements images = doc.select("img");
            for (Element el : images) {
                String imageUrl = el.absUrl("src");
                if (!trydecodeb64(imageUrl)) {
                    try {
                        Connection.Response resultImageResponse = Jsoup.connect(imageUrl).ignoreContentType(true).execute();
                        output.add(resultImageResponse.bodyAsBytes());
                    } catch (IllegalArgumentException ex) {
                        //System.out.println(ex);
                        System.out.println("Malformed, skipping " + imageUrl);
                    }
                    catch (HttpStatusException h){
                        System.out.println(h.getLocalizedMessage());
                    }
                }
            }
        }
        catch (UnsupportedMimeTypeException unsupportedMimeTypeException){
            System.out.println(unsupportedMimeTypeException.getLocalizedMessage());
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


    public static ArrayList<byte[]> get(String url) throws IOException {
        AISURL_SRC op = new AISURL_SRC(url);
        System.out.println("Images Gotten");
        return op.output;
    }



    public static void main(String[] args) throws IOException {
        AISURL_SRC test = new AISURL_SRC("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/f804ff9a-ea34-4f02-8fdf-b17202d8c84b/dd237f0-77964a3c-d72a-4431-a8ef-fe360d069eb7.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcL2Y4MDRmZjlhLWVhMzQtNGYwMi04ZmRmLWIxNzIwMmQ4Yzg0YlwvZGQyMzdmMC03Nzk2NGEzYy1kNzJhLTQ0MzEtYThlZi1mZTM2MGQwNjllYjcucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.Q6PFu4ohS8OWed-Y8iAoF3-FxQ1BmX9fvpk41YbFKLA");
        test.output.forEach(bytes -> {
            try {
                Random ran = new Random();
                String nam = "";
                String contents = "";
                for(int i = 0; i < 20; i++) {
                    nam += ran.nextInt(9);
                }
                for (byte aByte : bytes) {
                    contents = contents + (aByte + "|");
                }
                System.out.println();
                Files.write(Path.of(System.getProperty("user.dir") + File.separator + "test" + File.separator + nam), bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }



}
