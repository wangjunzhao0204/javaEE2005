package com.qf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Speaker;
import com.qf.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("speaker")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    // 主讲人管理
    @RequestMapping("showSpeakerList")
    public String showSpeakerList(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                  Model model) {

        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > getTotalPage()) {
            pageNum = getTotalPage();
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Speaker> list = speakerService.findAllSpeaker();
        PageInfo<Speaker> pageInfo = new PageInfo<>(list);
        model.addAttribute("page", pageInfo);

        return "behind/speakerList.jsp";
    }

    @RequestMapping("addSpeaker")
    public String addSpeaker() {
        // 添加主讲人
        return "behind/addSpeaker.jsp";
    }

    @RequestMapping("edit")
    public String edit(Integer id, Model model) {
        // 修改主讲人信息
        Speaker speaker = speakerService.findById(id);
        model.addAttribute("speaker", speaker);
        return "behind/addSpeaker.jsp";
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Speaker speaker) {
        // 保存信息
        if (speaker.getId() != null) {
            speakerService.updateSpeaker(speaker);
        } else {
            speakerService.addSpeaker(speaker);
        }

        return "redirect:showSpeakerList";
    }

    @RequestMapping("speakerDel")
    @ResponseBody
    public String speakerDel(Integer id) {
        // 删除主讲人
        speakerService.deleteSpeaker(id);
        return "success";
    }

    private Integer getTotalPage() {
        Integer pageSize = 10;
        Integer totalCount = speakerService.getTotalCount();

        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else {
            return totalCount / pageSize + 1;
        }
    }
}
