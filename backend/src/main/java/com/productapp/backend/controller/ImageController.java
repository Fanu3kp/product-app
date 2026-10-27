package com.productapp.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/uploads")
public class ImageController {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<byte[]> serveImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            if (Files.exists(filePath)) {
                byte[] image = Files.readAllBytes(filePath);
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "image/jpeg";
                }
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=86400")
                    .body(image);
            }
        } catch (IOException e) {
            // fall through to placeholder
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("image/svg+xml"))
            .header(HttpHeaders.CACHE_CONTROL, "max-age=86400")
            .body(getPlaceholderSvg(filename).getBytes());
    }

    private String getPlaceholderSvg(String filename) {
        String name = filename.replace(".jpg", "").replace("-", " ");
        return """
            <svg xmlns="http://www.w3.org/2000/svg" width="300" height="300" viewBox="0 0 300 300">
              <rect width="300" height="300" fill="%23f0f0f0"/>
              <text x="150" y="150" font-family="Arial" font-size="16" fill="%23666" text-anchor="middle" dominant-baseline="middle">%s</text>
              <text x="150" y="170" font-family="Arial" font-size="14" fill="%23999" text-anchor="middle">Image not available</text>
            </svg>
            """.formatted(name);
    }
}
