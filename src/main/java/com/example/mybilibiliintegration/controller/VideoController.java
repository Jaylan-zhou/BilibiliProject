package com.example.mybilibiliintegration.controller;

import com.example.mybilibiliintegration.model.Video;
import com.example.mybilibiliintegration.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file,
                                              @RequestParam("title") String title,
                                              @RequestParam("description") String description) {
        try {
            videoService.saveVideo(file, title, description);
            return ResponseEntity.ok("Video uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload video");
        }
    }

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }
}
