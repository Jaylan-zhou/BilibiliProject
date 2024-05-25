package com.example.mybilibiliintegration.controller;

import com.example.mybilibiliintegration.model.Video;
import com.example.mybilibiliintegration.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VideoListController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/browse")
    public String listVideos(Model model) {
        List<Video> videos = videoService.getAllVideos();
        model.addAttribute("videos", videos);
        return "browse";
    }
}


