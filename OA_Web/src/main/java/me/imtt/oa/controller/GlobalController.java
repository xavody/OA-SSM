package me.imtt.oa.controller;

import me.imtt.oa.biz.GlobalBiz;
import me.imtt.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller("globalController")
public class GlobalController {
    @Resource
    private GlobalBiz globalBiz;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/loginTo")
    public String loginTo(HttpSession session, @RequestParam String sn, @RequestParam String password) {
        Employee employee = globalBiz.login(sn, password);
        if (employee == null) {
            //登陆失败
            return "redirect:login";
        } else {
            //登陆成功,跳转到个人信息页面
            session.setAttribute("employee", employee);
            return "redirect:self";
        }
    }

    /**
     * 跳转到个人信息页面
     */
    @RequestMapping("/self")
    public String self() {
        return "self";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("employee", null);
        return "redirect:login";
    }

    @RequestMapping("/change_password")
    public String changePassword() {
        return "change_password";
    }

    @RequestMapping("/change_password_to")
    public String changePasswordTo(HttpSession session,
                                   @RequestParam String oldPassword,
                                   @RequestParam String newPassword1,
                                   @RequestParam String newPassword2) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getPassword().equals(oldPassword)) {
            if (newPassword1.equals(newPassword2)) {
                employee.setPassword(newPassword1);
                globalBiz.changePassword(employee);

                //修改密码成功
                return "redirect:self";
            }
        }

        //修改密码失败
        return "redirect:change_password";
    }
}
