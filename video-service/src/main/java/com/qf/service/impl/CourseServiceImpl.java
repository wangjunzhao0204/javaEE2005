package com.qf.service.impl;

import com.qf.dao.CourseMapper;
import com.qf.pojo.Course;
import com.qf.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course findCourseById(Integer courseId) {
        return courseMapper.findCourseById(courseId);
    }

    @Override
    public List<Course> findAllCourse() {
        return courseMapper.selectByExample(null);
    }
}
