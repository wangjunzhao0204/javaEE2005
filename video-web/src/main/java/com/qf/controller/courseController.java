package com.qf.controller;

import com.qf.pojo.Subject;
import com.qf.service.CourseService;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("course/{id}")
    public String findSubjectById(@PathVariable(name = "id") Integer id, Model model) {

        Subject subject = subjectService.findSubjectById(id);

        model.addAttribute("subject", subject);
        return "before/course.jsp";
    }
}
