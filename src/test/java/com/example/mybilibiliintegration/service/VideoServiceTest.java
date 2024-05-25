package com.example.mybilibiliintegration.service;

import com.example.mybilibiliintegration.model.Video;
import com.example.mybilibiliintegration.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private VideoService videoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveVideo() throws IOException {
        when(multipartFile.getOriginalFilename()).thenReturn("test.mp4");

        videoService.saveVideo(multipartFile, "Test Title", "Test Description");

        verify(videoRepository, times(1)).save(any(Video.class));
    }

    @Test
    void testGetAllVideos() {
        List<Video> videos = Collections.singletonList(new Video());
        when(videoRepository.findAll()).thenReturn(videos);

        List<Video> result = videoService.getAllVideos();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
