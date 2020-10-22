package com.qf.service.impl;

import com.qf.dao.SpeakerMapper;
import com.qf.pojo.Speaker;
import com.qf.pojo.SpeakerExample;
import com.qf.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    @Autowired
    private SpeakerMapper speakerMapper;

    @Override
    public List<Speaker> findAllSpeaker() {
        return speakerMapper.selectByExample(null);
    }

    @Override
    public Integer getTotalCount() {
        return speakerMapper.getTotalCount();
    }

    @Override
    public Speaker findById(Integer id) {
        return speakerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateSpeaker(Speaker speaker) {
        SpeakerExample speakerExample = new SpeakerExample();
        SpeakerExample.Criteria criteria = speakerExample.createCriteria();
        criteria.andIdEqualTo(speaker.getId());

        speakerMapper.updateByExampleSelective(speaker, speakerExample);
    }

    @Override
    public void addSpeaker(Speaker speaker) {
        speakerMapper.insert(speaker);
    }

    @Override
    public void deleteSpeaker(Integer id) {
        speakerMapper.deleteByPrimaryKey(id);
    }
}
