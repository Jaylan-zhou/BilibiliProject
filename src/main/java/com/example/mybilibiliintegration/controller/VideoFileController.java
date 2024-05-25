package com.example.mybilibiliintegration.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@RestController
public class VideoFileController {

    private static final Logger logger = Logger.getLogger(VideoFileController.class.getName());

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/videos/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                logger.info("Serving file: " + file.toString());
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                logger.warning("File not found: " + file.toString());
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            logger.severe("Malformed URL: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            logger.severe("IOException: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}

