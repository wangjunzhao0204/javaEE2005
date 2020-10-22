package com.qf.service;

import com.qf.pojo.Speaker;

import java.util.List;

public interface SpeakerService {
    List<Speaker> findAllSpeaker();

    Integer getTotalCount();

    Speaker findById(Integer id);

    void updateSpeaker(Speaker speaker);

    void addSpeaker(Speaker speaker);

    void deleteSpeaker(Integer id);
}
