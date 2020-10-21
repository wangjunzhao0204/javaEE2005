package com.qf.service;

import com.qf.pojo.Course;

import java.util.List;

public interface CourseService {
    Course findCourseById(Integer courseId);

    List<Course> findAllCourse();
}
