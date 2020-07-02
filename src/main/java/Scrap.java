import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class Scrap {
    public static void main(String[] args) {
        try {
            String username = "mahendra82";
            String apiKey = "q6xlMSm7h24R7V2w3huIZzuqU";
            String originalInput = username + ":" + apiKey;
            String encodedString = "Basic " + Base64.getEncoder().encodeToString(originalInput.getBytes());

            String apiEndPoint = "http://api.scraping-bot.io/scrape/raw-html";
            URL url = new URL(apiEndPoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", encodedString);

            String useChrome = "false";//set to "true" if you want to use headless chrome for javascript rendering
            String premiumProxy = "false";//set to "true" if you want to use premium proxies Unblock Amazon,Google,Rakuten
            String urlToScrape = "https://www.networkworld.com/";

            String param = "{\"url\":\""+urlToScrape+"\","+
                    "\"options\":{"+
                    "\"useChrome\":"+useChrome+","+
                    "\"premiumProxy\":"+premiumProxy+
                    "}"+
                    "}";

            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();
            out.write(param.getBytes());
            out.flush();
            out.close();

            int status = con.getResponseCode();
            System.out.println(status);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            String jsonResponse = content.toString();
            System.out.println(jsonResponse);
            in.close();
            con.disconnect();

        } catch (Exception e) {
            System.out.println("An error occured while scraping:" + e);
        }
    }
}
