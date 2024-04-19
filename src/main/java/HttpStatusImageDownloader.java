import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class HttpStatusImageDownloader {
    public void downloadStatusImage(int code) throws Exception {
        String imageUrl = new HttpStatusChecker().getStatusImage(code);

        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (InputStream inputStream = connection.getInputStream()) {
                Path outputPath = Path.of("downloadImage/image_" + code + ".jpg");
                Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image saved as: " + outputPath.toAbsolutePath());
            }
        } else {
            throw new Exception("Image not found for status code " + code);
        }
    }

    public static void main(String[] args) throws Exception {
        HttpStatusImageDownloader httpStatusImageDownloader=new HttpStatusImageDownloader();
        httpStatusImageDownloader.downloadStatusImage(202);
    }
}