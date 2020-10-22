package com.qf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Course;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Speaker;
import com.qf.pojo.Video;
import com.qf.service.CourseService;
import com.qf.service.SpeakerService;
import com.qf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SpeakerService speakerService;

    @RequestMapping("showVideo")
    public String showVideo(Integer videoId, String subjectName, Model model) {
        // 展示视频信息
        Video video = videoService.findByVideoId(videoId);

        model.addAttribute("video", video);
        model.addAttribute("subjectName", subjectName);

        Integer courseId = video.getCourseId();
        Course course = courseService.findCourseById(courseId);

        model.addAttribute("course", course);
        return "before/section.jsp";
    }

    // 视频管理
    @RequestMapping("list")
    public String videoList(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                            QueryVo queryVo,
                            Model model) {

        model.addAttribute("queryVo", queryVo);

        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > getTotalPage()) {
            pageNum = getTotalPage();
        }

        PageHelper.startPage(pageNum, pageSize);

        List<Video> list = videoService.findAllVideo(queryVo);
        PageInfo<Video> pageInfo = new PageInfo<>(list);
        model.addAttribute("page", pageInfo);

        List<Speaker> speakerList = speakerService.findAllSpeaker();
        model.addAttribute("speakerList", speakerList);

        List<Course> courseList = courseService.findAllCourse();
        model.addAttribute("courseList", courseList);

        return "behind/videoList.jsp";
    }

    @RequestMapping("addVideo")
    public String addVideo(Model model) {
        // 添加视频
        List<Speaker> speakerList = speakerService.findAllSpeaker();
        model.addAttribute("speakerList", speakerList);

        List<Course> courseList = courseService.findAllCourse();
        model.addAttribute("courseList", courseList);

        return "behind/addVideo.jsp";
    }

    @RequestMapping("edit")
    public String edit(Integer id, Model model) {
        // 修改视频
        Video video = videoService.findByVideoId(id);
        model.addAttribute("video", video);

        List<Speaker> speakerList = speakerService.findAllSpeaker();
        model.addAttribute("speakerList", speakerList);

        List<Course> courseList = courseService.findAllCourse();
        model.addAttribute("courseList", courseList);
        return "behind/addVideo.jsp";
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Video video) {
        // 保存
        if (video.getId() != null) {
            videoService.updateVideo(video);
        } else {
            videoService.addVideo(video);
        }
        return "redirect:list";
    }

    @RequestMapping("videoDel")
    @ResponseBody
    public String videoDel(Integer id) {
        // 删除视频
        videoService.deleteVideo(id);

        return "success";
    }

    @RequestMapping("delBatchVideos")
    public String delBatchVideos(Integer[] ids) {
        // 批量删除
        for (Integer id : ids) {
            videoService.deleteVideo(id);
        }

        return "redirect:list";
    }


    private Integer getTotalPage() {
        Integer pageSize = 10;
        Integer totalCount = videoService.getTotalCount();

        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else {
            return totalCount / pageSize + 1;
        }
    }
}
