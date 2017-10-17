package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionChecker {

    private static final String GITHUB_API = "https://api.github.com/repos/";
    private static final String GITHUB_REPO = "bowsr/DDSE/releases/latest";

    public static String latest;

    // Returns true if latest version is newer.
    public static boolean compare(String current) throws Exception {
        latest = getVersion();
        int current_major = Integer.parseInt(current.substring((current.startsWith("v") ? 1 : 0), current.indexOf('.'))),
            current_minor = Integer.parseInt(current.substring(current.indexOf('.') + 1)),
            latest_major  = Integer.parseInt(latest.substring((latest.startsWith("v") ? 1 : 0), latest.indexOf('.'))),
            latest_minor  = Integer.parseInt(latest.substring(latest.indexOf('.') + 1));

        System.out.println("Current -> major: " + current_major + " | minor: " + current_minor);
        System.out.println("Latest  -> major: " + latest_major + " | minor: " + latest_minor);

        if(current_major < latest_major) {
            return true;
        }else if(current_major == latest_major) {
            return (current_minor < latest_minor);
        }
        return false;
    }

    private static String getVersion() throws Exception {
        URL url = new URL(GITHUB_API + GITHUB_REPO);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        System.out.println("Version Check Response: " + responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = br.readLine();

        int index = line.indexOf("tag_name") + 10;

        String str = line.substring(index);
        str = str.substring(1, str.indexOf(',') - 1);

        return str;
    }

}
