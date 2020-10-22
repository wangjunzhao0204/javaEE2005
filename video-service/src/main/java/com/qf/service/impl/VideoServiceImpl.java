package com.qf.service.impl;

import com.qf.dao.VideoMapper;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Video;
import com.qf.pojo.VideoExample;
import com.qf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Video findByVideoId(Integer videoId) {
        return videoMapper.findByVideoId(videoId);
    }

    @Override
    public Integer getTotalCount() {
        return videoMapper.getTotalCount();
    }

    @Override
    public List<Video> findAllVideo(QueryVo queryVo) {
        return videoMapper.findAllVideo(queryVo);
    }

    @Override
    public void updateVideo(Video video) {
        VideoExample videoExample = new VideoExample();
        VideoExample.Criteria criteria = videoExample.createCriteria();

        criteria.andIdEqualTo(video.getId());

        videoMapper.updateByExampleSelective(video, videoExample);
    }

    @Override
    public void addVideo(Video video) {
        videoMapper.insert(video);
    }

    @Override
    public void deleteVideo(Integer id) {
        videoMapper.deleteByPrimaryKey(id);
    }
}
