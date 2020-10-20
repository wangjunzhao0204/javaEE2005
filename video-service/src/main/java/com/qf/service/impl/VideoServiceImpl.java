package com.qf.service.impl;

import com.qf.dao.VideoMapper;
import com.qf.pojo.Video;
import com.qf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Video findByVideoId(Integer videoId) {
        return videoMapper.findByVideoId(videoId);
    }
}
