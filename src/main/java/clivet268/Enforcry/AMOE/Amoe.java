package clivet268.Enforcry.AMOE;

import clivet268.Enforcry.SecureLine.ClientConnection;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static clivet268.Enforcry.Enforcry.getUsername;
import static clivet268.Enforcry.Enforcry.s;

public class Amoe extends ClientConnection {


    private static final String ip = "";
    private static final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

    @Override
    public void postConnect() {


        System.out.println("Enter interval times");
        String timein = s.next();
        ArrayList<String> t = new ArrayList<>();
        while (timein.length() > 0) {
            if (!timein.contains(" ")) {
                t.add(timein);
                break;
            }
            t.add(timein.substring(0, timein.indexOf(" ")));
            timein = timein.substring(timein.indexOf(" ") + 1);
        }
        ZonedDateTime nextRun = now;
        for (String s : t) {
            switch (s.charAt(0)) {
                case ('d'): {
                    nextRun = nextRun.plusDays(Long.parseLong(s.substring(1)));
                }
                case ('h'): {
                    nextRun = nextRun.plusHours(Long.parseLong(s.substring(1)));
                }
                case ('m'): {
                    nextRun = nextRun.plusMinutes(Long.parseLong(s.substring(1)));
                }
                case ('s'): {
                    nextRun = nextRun.plusSeconds(Long.parseLong(s.substring(1)));
                }
            }
        }


        Duration duration = Duration.between(now, nextRun);
        long delay = duration.getSeconds();
        Sender sender = new Sender();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(sender,
                0,
                delay,
                TimeUnit.SECONDS);
    }

    public static void stuff() {
        try {
            //debug only
            out.writeUTFE("short");
            URL url = new URL("https://ipinfo.io/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String lines = "";
            for (String line; (line = reader.readLine()) != null; ) {
                lines = lines + line;
            }

            JSONObject j = new JSONObject(lines);
            j.remove("readme");
            j.put("date", now);
            j.put("username", getUsername());
            j.put("systemname", System.getProperty("user.name"));
            j.put("localmachinehostname", java.net.InetAddress.getLocalHost());
            sendComms(j.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static class Sender implements Runnable {

        @Override
        public void run() {
            System.out.println("working");
            stuff();
        }
    }

    public static void sendComms(String tosend) {
        try {
            System.out.println(tosend);
            out.writeIntE(999);
            System.out.println(in.readIntE());
            out.writeUTFE(tosend);
            System.out.println("wrote");
        } catch (Exception e) {
            System.out.println("???");
            e.printStackTrace();
            System.out.println(e);
            e.getCause();
            System.out.println(e.getClass());
            throw new RuntimeException(e);
        }
    }
}
