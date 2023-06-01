package clivet268.Enforcry.Operations;

import clivet268.Enforcry.Encryption.Asymmetric;
import clivet268.Enforcry.SecureLine.ClientConnection;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static clivet268.Enforcry.Enforcry.*;

public class AMOE extends Operation {

    static {
        try {
            sessionKeyStore = Asymmetric.generateRSAKkeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String ip = "";
    private static final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

    @Override
    public void run() {
        CommandSource c = new CommandSource();
        c.init();
    }

    //TODO handle timeout
    private static class CommandSource extends ClientConnection {
        public void init() {
            System.out.println("Source's IP");
            ip = s.next();
            try {
                connect(ip, 28770, -1);
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Enter interval times");
            String timein = s.next();
            ArrayList<String> t = new ArrayList<>();
            while (timein.length() > 0) {
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
            long delay = duration.getSeconds() - 1;

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(new Get(),
                    0,
                    delay,
                    TimeUnit.SECONDS);
        }

        public static void stuff() {
            try {
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


        public static void sendComms(String tosend) {
            try {
                out.writeIntE(999);
                System.out.println(in.readIntE());
                out.writeUTFE(tosend);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static class Get implements Runnable {

        @Override
        public void run() {
            CommandSource.stuff();
        }
    }

    @Override
    public String infoForOp() {
        return "Sets up a process in the background logging information to show this device's \n" + "location through ip and relays it to the designated ip on a select interval.";
    }
}
