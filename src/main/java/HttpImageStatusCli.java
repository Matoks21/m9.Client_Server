import java.util.Scanner;

public class HttpImageStatusCli {

    private final HttpStatusImageDownloader downloader;

    public HttpImageStatusCli(HttpStatusImageDownloader downloader) {
        this.downloader = downloader;
    }

    public void askStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter HTTP status code: ");
        try {
            int statusCode = Integer.parseInt(scanner.nextLine());
            downloader.downloadStatusImage(statusCode);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();
        HttpImageStatusCli httpImageStatusCli = new HttpImageStatusCli(downloader);
        httpImageStatusCli.askStatus();
    }
}