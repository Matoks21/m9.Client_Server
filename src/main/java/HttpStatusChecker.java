import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStatusChecker {
    public String getStatusImage(int code) throws Exception {
        String url = "https://http.cat/" + code + ".jpg";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("url = " + url);
            return url;
        } else {
            throw new Exception("Image not found for status code " + code);
        }
    }

    public static void main(String[] args) throws Exception {
        HttpStatusChecker httpStatusChecker=new HttpStatusChecker();
        httpStatusChecker.getStatusImage(200);
        httpStatusChecker.getStatusImage(300);

    }
}