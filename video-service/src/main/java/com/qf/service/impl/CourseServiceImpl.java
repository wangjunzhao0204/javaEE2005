package com.qf.service.impl;

import com.qf.dao.CourseMapper;
import com.qf.pojo.Course;
import com.qf.pojo.CourseExample;
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

    @Override
    public void updateCourse(Course course) {
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andIdEqualTo(course.getId());
        courseMapper.updateByExampleSelective(course, courseExample);

    }

    @Override
    public void addCourse(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Course findById(Integer id) {
        return courseMapper.selectByPrimaryKey(id);
    }
}
