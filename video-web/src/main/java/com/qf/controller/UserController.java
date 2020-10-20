package com.qf.controller;

import com.qf.pojo.User;
import com.qf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("loginUser")
    @ResponseBody
    public String loginUser(String email, String password, HttpSession session, Model model) {

        User user = userService.findByEmail(email);
        model.addAttribute("email", email);
        if (user != null) {
            if (password.equals(user.getPassword())) {

                session.setAttribute("userAccount", email);
                return "success";
            }
        }

        return "登录失败";
    }

    @RequestMapping("insertUser")
    @ResponseBody
    public String insertUser(User user, HttpSession session) {

        userService.insertUser(user);
        String email = user.getEmail();
        session.setAttribute("userAccount", email);
        return "success";
    }

    @RequestMapping("validateEmail")
    @ResponseBody
    public String validateEmail(String email) {
        User user = userService.findByEmail(email);

        if (user != null) {
            return "该邮箱已注册";
        }

        return "success";
    }

    @RequestMapping("forgetPassword")
    public String forgetPassword(String email, Model model) {
        model.addAttribute("email", email);

        return "before/forget_password.jsp";
    }

    @RequestMapping("sendEmail")
    @ResponseBody
    public String sendEmail(String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            return "success";
        }

        return "hasNoUser";
    }

    @RequestMapping("validateEmailCode")
    public String validateEmailCode() {

        return "before/index.jsp";
    }

    @RequestMapping("showMyProfile")
    public String showMyProfile(HttpSession session, Model model) {
        User user = getUser(session);

        model.addAttribute("user", user);
        return "before/my_profile.jsp";
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute("userAccount");

        return "before/index.jsp";
    }

    @RequestMapping("loginOut2")
    public String loginOut2(HttpSession session) {
        session.removeAttribute("userAccount");

        return "before/index.jsp";
    }

    @RequestMapping("changeProfile")
    public String changeProfile(HttpSession session, Model model) {
        User user = getUser(session);

        model.addAttribute("user", user);
        return "before/change_profile.jsp";
    }

    @RequestMapping("updateUser")
    public String updateUser(User user, Model model) {
        userService.updateUser(user);
        model.addAttribute("user", user);
        return "redirect:showMyProfile";
    }

    @RequestMapping("passwordSafe")
    public String passwordSafe() {

        return "before/password_safe.jsp";
    }

    @RequestMapping("validatePassword")
    @ResponseBody
    public String validatePassword(String password, HttpSession session) {
        User user = getUser(session);
        if (password.equals(user.getPassword())) {
            return "success";
        }

        return "密码错误";
    }

    @RequestMapping("updatePassword")
    public String updatePassword(HttpSession session, String newPassword, Model model) {
        User user = getUser(session);
        user.setPassword(newPassword);

        userService.updateUser(user);
        model.addAttribute("user", user);
        return "redirect:showMyProfile";
    }

    @RequestMapping("changeAvatar")
    public String changeAvatar(HttpSession session, Model model) {
        User user = getUser(session);
        model.addAttribute("user", user);
        return "before/change_avatar.jsp";
    }

    @RequestMapping("upLoadImage")
    public String upLoadImage(MultipartFile image_file, Model model, HttpSession session) {
        User user = getUser(session);
        String userImgUrl = user.getImgUrl();
        String path = "F:\\server\\apache-tomcat-8.5.41\\webapps\\video\\";
        String filename = image_file.getOriginalFilename();
        File file = new File(path + filename);

        if (!file.exists()) {
            try {
                image_file.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (filename != null) {
            user.setImgUrl(filename);
        } else {
            user.setImgUrl(userImgUrl);
        }


        userService.updateUser(user);

        return "redirect:showMyProfile";
    }

    public User getUser(HttpSession session) {
        String email = (String) session.getAttribute("userAccount");
        User user = userService.findByEmail(email);

        return user;
    }

}
