import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class HttpImageStatusCliTest {

    private HttpImageStatusCli cli;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        cli = new HttpImageStatusCli(new HttpStatusImageDownloader());
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @ParameterizedTest
    @ValueSource(strings = {"200", "201", "302"})
    void testAskStatus_ValidInput_Success(String input) {

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        cli.askStatus();

        String expectedOutput = "Image saved as: ";
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput), "Output should contain success message");
    }

    @Test
    void testAskStatus_NonNumericInput() {

        String input = "test";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.askStatus();

        String expectedOutput = "Please enter a valid number.";
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput), "Output should contain error message");
    }

    @Test
    void testAskStatus_InvalidInput_NonExistentStatus() {

        String input = "999";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.askStatus();

        String expectedOutput = "Error: Image not found for status code 999";
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput), "Output should contain error message");
    }

    @Test
    void testAskStatus_Exception() throws Exception {
        HttpStatusImageDownloader mockedDownloader = mock(HttpStatusImageDownloader.class);
        cli = new HttpImageStatusCli(mockedDownloader);

        doThrow(new Exception("Image download failed")).when(mockedDownloader).downloadStatusImage(404);

        String input = "404";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.askStatus();

        verify(mockedDownloader).downloadStatusImage(404);
        String expectedOutput = "Error: Image download failed";
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput), "Output should contain error message");
    }


}