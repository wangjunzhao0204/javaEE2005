package com.qf.controller;

import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utills.MailUtils;
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
        // 登录
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
        // 注册
        userService.insertUser(user);
        String email = user.getEmail();
        session.setAttribute("userAccount", email);
        return "success";
    }

    @RequestMapping("validateEmail")
    @ResponseBody
    public String validateEmail(String email) {
        // 邮箱验证
        User user = userService.findByEmail(email);

        if (user != null) {
            return "该邮箱已注册";
        }

        return "success";
    }

    @RequestMapping("forgetPassword")
    public String forgetPassword(String email, Model model) {
        // 忘记密码
        model.addAttribute("email", email);

        return "before/forget_password.jsp";
    }

    @RequestMapping("sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession session) {
        // 发送邮箱
        User user = userService.findByEmail(email);
        if (user != null) {
            String validateCode = MailUtils.getValidateCode(6);
            session.setAttribute("validateCode", validateCode);
            MailUtils.sendMail("1136257395@qq.com", "你好，这是一封测试邮件，无需回复。", "测试邮件随机生成的验证码是：" + validateCode);
            return "success";
        } else {
            return "hasNoUser";
        }
    }

    @RequestMapping("validateEmailCode")
    public String validateEmailCode(String email, String code, HttpSession session, Model model) {
        // 验证码
        String validateCode = (String) session.getAttribute("validateCode");
        if (validateCode.equals(code)) {
            model.addAttribute("email", email);
            return "before/reset_password.jsp";
        } else {
            return "before/forget_password.jsp";
        }
    }

    @RequestMapping("resetPassword")
    public String resetPassword(String email, String password, HttpSession session) {
        // 重置密码
        session.removeAttribute("validateCode");
        User user = userService.findByEmail(email);
        user.setPassword(password);
        userService.updateUser(user);

        return "redirect:/subject/selectAll";
    }

    @RequestMapping("showMyProfile")
    public String showMyProfile(HttpSession session, Model model) {
        // 个人中心
        User user = getUser(session);

        model.addAttribute("user", user);
        return "before/my_profile.jsp";
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session) {
        // 退出
        session.removeAttribute("userAccount");

        return "redirect:/subject/selectAll";
    }

    @RequestMapping("loginOut2")
    public String loginOut2(HttpSession session) {
        // 退出
        session.removeAttribute("userAccount");

        return "redirect:/subject/selectAll";
    }

    @RequestMapping("changeProfile")
    public String changeProfile(HttpSession session, Model model) {
        // 修改资料
        User user = getUser(session);

        model.addAttribute("user", user);
        return "before/change_profile.jsp";
    }

    @RequestMapping("updateUser")
    public String updateUser(User user, Model model) {
        // 修改资料
        userService.updateUser(user);
        model.addAttribute("user", user);
        return "redirect:showMyProfile";
    }

    @RequestMapping("passwordSafe")
    public String passwordSafe() {
        // 密码安全
        return "before/password_safe.jsp";
    }

    @RequestMapping("validatePassword")
    @ResponseBody
    public String validatePassword(String password, HttpSession session) {
        // 验证原密码与新密码
        User user = getUser(session);
        if (password.equals(user.getPassword())) {
            return "success";
        }

        return "密码错误";
    }

    @RequestMapping("updatePassword")
    public String updatePassword(HttpSession session, String newPassword, Model model) {
        // 修改密码
        User user = getUser(session);
        user.setPassword(newPassword);

        userService.updateUser(user);
        model.addAttribute("user", user);
        return "redirect:showMyProfile";
    }

    @RequestMapping("changeAvatar")
    public String changeAvatar(HttpSession session, Model model) {
        // 更换头像
        User user = getUser(session);
        model.addAttribute("user", user);
        return "before/change_avatar.jsp";
    }

    @RequestMapping("upLoadImage")
    public String upLoadImage(MultipartFile image_file, Model model, HttpSession session) {
        // 上传新头像
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

    // 获取session中的用户信息
    private User getUser(HttpSession session) {
        String email = (String) session.getAttribute("userAccount");

        return userService.findByEmail(email);
    }

}
