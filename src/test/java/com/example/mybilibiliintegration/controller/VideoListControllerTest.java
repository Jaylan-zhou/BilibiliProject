package com.example.mybilibiliintegration.controller;

import com.example.mybilibiliintegration.model.Video;
import com.example.mybilibiliintegration.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class VideoListControllerTest {

    @Mock
    private VideoService videoService;

    @Mock
    private Model model;

    @InjectMocks
    private VideoListController videoListController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(videoListController).build();
    }

    @Test
    void testListVideos() throws Exception {
        Video video1 = new Video();
        video1.setId("1");
        video1.setTitle("Title 1");
        video1.setDescription("Description 1");
        video1.setUrl("/videos/1.mp4");

        Video video2 = new Video();
        video2.setId("2");
        video2.setTitle("Title 2");
        video2.setDescription("Description 2");
        video2.setUrl("/videos/2.mp4");

        List<Video> videos = Arrays.asList(video1, video2);
        when(videoService.getAllVideos()).thenReturn(videos);

        mockMvc.perform(get("/browse"))
                .andExpect(status().isOk())
                .andExpect(view().name("browse"))
                .andExpect(model().attribute("videos", videos));

        verify(videoService, times(1)).getAllVideos();
        verifyNoMoreInteractions(videoService);
    }

    @Test
    void testListVideosDirectCall() {
        Video video1 = new Video();
        video1.setId("1");
        video1.setTitle("Title 1");
        video1.setDescription("Description 1");
        video1.setUrl("/videos/1.mp4");

        Video video2 = new Video();
        video2.setId("2");
        video2.setTitle("Title 2");
        video2.setDescription("Description 2");
        video2.setUrl("/videos/2.mp4");

        List<Video> videos = Arrays.asList(video1, video2);
        when(videoService.getAllVideos()).thenReturn(videos);

        String viewName = videoListController.listVideos(model);

        assertEquals("browse", viewName);
        verify(videoService, times(1)).getAllVideos();
        verify(model, times(1)).addAttribute("videos", videos);
        verifyNoMoreInteractions(videoService, model);
    }
}



