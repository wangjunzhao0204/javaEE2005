package com.qf.service.impl;

import com.qf.dao.CourseMapper;
import com.qf.pojo.Course;
import com.qf.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course findCourseById(Integer courseId) {
        return courseMapper.findCourseById(courseId);
    }
}
