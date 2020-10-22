package com.qf.controller;

import com.qf.pojo.Admin;
import com.qf.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("toLogin")
    public String toLogin() {
        // 登录
        return "behind/login.jsp";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(String username, String password, HttpSession session) {
        // 管理员登录
        Admin admin = adminService.findByUsername(username);
        if (admin != null) {
            if (password.equals(admin.getPassword())) {
                session.setAttribute("userName", username);
                return "success";
            }
        }

        return "登录失败";
    }

    @RequestMapping("exit")
    public String exit(HttpSession session) {
        // 退出
        session.removeAttribute("userName");

        return "behind/login.jsp";
    }
}
