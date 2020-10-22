package com.qf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Course;
import com.qf.pojo.Subject;
import com.qf.service.CourseService;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


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

    // 课程管理
    @RequestMapping("showCourseList")
    public String showCourseList(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                 Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = courseService.findAllCourse();

        PageInfo<Course> pageInfo = new PageInfo<>(list);
        model.addAttribute("page", pageInfo);

        return "behind/courseList.jsp";
    }

    @RequestMapping("addCourse")
    public String addCourse(Model model) {
        // 添加课程
        List<Subject> subjectList = subjectService.findAllSubject();
        model.addAttribute("subjectList", subjectList);
        return "behind/addCourse.jsp";
    }

    @RequestMapping("edit")
    public String edit(Integer id, Model model) {
        // 修改课程
        Course course = courseService.findById(id);
        model.addAttribute("course", course);

        List<Subject> subjectList = subjectService.findAllSubject();
        model.addAttribute("subjectList", subjectList);
        return "behind/addCourse.jsp";
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Course course) {
        // 保存
        if (course.getId() != null) {
            courseService.updateCourse(course);
        } else {
            courseService.addCourse(course);
        }

        return "redirect:showCourseList";
    }

    @RequestMapping("courseDel")
    @ResponseBody
    public String courseDel(Integer id) {
        // 删除课程
        courseService.deleteCourse(id);
        return "success";
    }
}
