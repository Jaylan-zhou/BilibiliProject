package com.example.mybilibiliintegration.controller;

import com.example.mybilibiliintegration.model.Video;
import com.example.mybilibiliintegration.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/videos/upload")
    public String handleVideoUpload(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description) {
        try {
            videoService.saveVideo(file, title, description);
        } catch (IOException e) {
            e.printStackTrace();
            return "uploadError";
        }
        return "redirect:/";
    }

    @GetMapping("/videos")
    public List<Video> listVideos() {
        return videoService.getAllVideos();
    }
}


