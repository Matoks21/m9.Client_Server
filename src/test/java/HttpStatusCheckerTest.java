import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpStatusCheckerTest {

    @Test
    void testGetStatusImage() {
        HttpStatusChecker checker = new HttpStatusChecker();

        try {
            String imageUrl = checker.getStatusImage(200);
            assertNotNull(imageUrl);
            assertTrue(imageUrl.startsWith("https://http.cat/"));
            assertTrue(imageUrl.endsWith(".jpg"));
        } catch (Exception e) {
            fail("Exception should not be thrown for status code 200");
        }

        try {
            checker.getStatusImage(999);
            fail("Exception should be thrown for non-existent status code");
        } catch (Exception e) {
            assertEquals("Image not found for status code 999", e.getMessage());
        }
    }

    @Test
    void testInvalidStatusCode() {
        HttpStatusChecker checker = new HttpStatusChecker();

        try {
            checker.getStatusImage(-1);
            fail("Exception should be thrown for negative status code");
        } catch (Exception e) {
            assertEquals("Image not found for status code -1", e.getMessage());
        }

        try {
            checker.getStatusImage(1000);
            fail("Exception should be thrown for non-existent status code");
        } catch (Exception e) {
            assertEquals("Image not found for status code 1000", e.getMessage());
        }
    }
}