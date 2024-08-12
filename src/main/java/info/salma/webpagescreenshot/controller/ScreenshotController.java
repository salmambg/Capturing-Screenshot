package info.salma.webpagescreenshot.controller;

import info.salma.webpagescreenshot.service.ScreenShotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/screenshot")
public class ScreenshotController {

    @Autowired
    ScreenShotService screenshotService;

    @GetMapping
    public String captureScreenshot(@RequestParam String url, @RequestParam String outputPath) {
        try {
            screenshotService.takeScreenshot(url, outputPath);
            return "Screenshot saved successfully to: " + outputPath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to capture screenshot: " + e.getMessage();
        }
    }
}
