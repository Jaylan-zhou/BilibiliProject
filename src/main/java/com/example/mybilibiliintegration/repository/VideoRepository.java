package com.example.mybilibiliintegration.repository;

import com.example.mybilibiliintegration.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, String> {
}

