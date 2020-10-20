package com.qf.controller;

import com.qf.pojo.Course;
import com.qf.pojo.Video;
import com.qf.service.CourseService;
import com.qf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("showVideo")
    public String showVideo(Integer videoId, String subjectName, Model model) {

        Video video = videoService.findByVideoId(videoId);

        model.addAttribute("video", video);
        model.addAttribute("subjectName", subjectName);

        Integer courseId = video.getCourseId();
        Course course = courseService.findCourseById(courseId);

        model.addAttribute("course", course);
        return "before/section.jsp";
    }
}
