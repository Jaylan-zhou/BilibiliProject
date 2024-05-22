package com.example.mybilibiliintegration.service;

import com.example.mybilibiliintegration.model.Video;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class VideoService {

    private static final Logger logger = Logger.getLogger(VideoService.class.getName());
    private List<Video> videoStorage = new ArrayList<>();

    @Value("${file.upload-dir}")
    private String uploadDir;

    public void saveVideo(MultipartFile file, String title, String description) throws IOException {
        String id = UUID.randomUUID().toString();
        String fileName = id + "_" + file.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;
        String url = "/videos/" + fileName;  // 使用相对路径作为URL

        saveFileToLocal(file, filePath);

        Video video = new Video();
        video.setId(id);
        video.setTitle(title);
        video.setDescription(description);
        video.setUrl(url);
        videoStorage.add(video);

        logger.info("Video saved with URL: " + url);
    }

    private void saveFileToLocal(MultipartFile file, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        file.transferTo(path.toFile());
        logger.info("File saved to: " + filePath);
    }

    public List<Video> getAllVideos() {
        return videoStorage;
    }
}


