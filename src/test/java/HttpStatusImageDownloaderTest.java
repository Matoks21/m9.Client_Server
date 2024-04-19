import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class HttpStatusImageDownloaderTest {

    @Test
    void testDownloadStatusImage() {
        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();

        try {
            int statusCode = 200;
            downloader.downloadStatusImage(statusCode);
            Path imagePath = Path.of("downloadImage/image_" + statusCode + ".jpg");
            assertTrue(Files.exists(imagePath), "Image file should exist");
        } catch (Exception e) {
            fail("Exception should not be thrown for status code 200");
        }

        try {
            int statusCode = 999;
            downloader.downloadStatusImage(statusCode);
            fail("Exception should be thrown for non-existent status code");
        } catch (Exception e) {
            assertEquals("Image not found for status code 999", e.getMessage());
        }
    }

    @Test
    void testInvalidStatusCode() {
        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();

        try {
            int statusCode = -1;
            downloader.downloadStatusImage(statusCode);
            fail("Exception should be thrown for negative status code");
        } catch (Exception e) {
            assertEquals("Image not found for status code -1", e.getMessage());
        }

        try {
            int statusCode = 1000;
            downloader.downloadStatusImage(statusCode);
            fail("Exception should be thrown for non-existent status code");
        } catch (Exception e) {
            assertEquals("Image not found for status code 1000", e.getMessage());
        }
    }

}