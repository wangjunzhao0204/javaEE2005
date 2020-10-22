package com.qf.service;

import com.qf.pojo.QueryVo;
import com.qf.pojo.Video;

import java.util.List;

public interface VideoService {
    Video findByVideoId(Integer videoId);

    Integer getTotalCount();

    List<Video> findAllVideo(QueryVo queryVo);

    void updateVideo(Video video);

    void addVideo(Video video);

    void deleteVideo(Integer id);
}
