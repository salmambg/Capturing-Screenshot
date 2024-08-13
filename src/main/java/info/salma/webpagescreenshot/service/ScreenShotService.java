package info.salma.webpagescreenshot.service;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class ScreenShotService {

    @Value("${chrome.driver.resource.path}")
    private String chromeDriverResourcePath;

    public void takeScreenshot(String url, String outputPath)throws IOException {

        File chromeDriverFile = extractChromeDriver();
        System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920x1080");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get(url);
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File outputFile = new File(outputPath);
            FileUtils.copyFile(file, outputFile);
            System.out.println("Screenshot save to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            driver.quit();
        }
    }
    private File extractChromeDriver() throws IOException {
        // Load ChromeDriver from resources
        InputStream inputStream = getClass().getResourceAsStream(chromeDriverResourcePath);
        if (inputStream == null) {
            throw new IOException("ChromeDriver not found in resources!");
        }

        // Create a temporary file for ChromeDriver
        File tempFile = File.createTempFile("chromedriver", ".exe");
        tempFile.deleteOnExit();

        // Copy the ChromeDriver from the resources to the temporary file
        Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return tempFile;
    }
}


