package com.qf.service.impl;

import com.qf.dao.SubjectMapper;
import com.qf.pojo.Subject;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> findAllSubject() {
        return subjectMapper.selectByExample(null);
    }

    @Override
    public Subject findSubjectById(Integer id) {
        return subjectMapper.findSubjectById(id);
    }
}
