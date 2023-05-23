package com.itheima.cloudlibrary.web;

import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.service.RecordService;
import com.itheima.cloudlibrary.service.UserService;
import com.itheima.cloudlibrary.utils.BaseServlet;
import com.itheima.cloudlibrary.utils.BeanFactory;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户登录和注销
 */
@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {
    /*
   用户登录
    */
    public String login(HttpServletRequest req,HttpServletResponse resp) throws Exception {
        // 接收数据
        Map<String, String[]> map = req.getParameterMap();
        // 封装数据
        User user = new User();
        BeanUtils.populate(user, map);
        // 调用业务层的登录方法
        UserService userService =
                (UserService) BeanFactory.getBean("userService");
        User existUser = userService.login(user);
        if (user.getPassword()==""){
            req.setAttribute("msg", "请输入密码");
            return "/admin/login.jsp";
        }
        // 根据登录结果进行页面跳转
        if (existUser == null) {
            // 登录失败
            req.setAttribute("msg", "用户名或密码错误!");
            return "/admin/login.jsp";
        } else {
            // 登录成功
            req.getSession().setAttribute("USER_SESSION", existUser);
            return "/admin/index.jsp";
        }
    }

    /*
    注销登录
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        // 销毁session
        req.getSession().invalidate();
        // 页面跳转:
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    /**
     * 查询用户列表
     */
    public String searchUser(HttpServletRequest req, HttpServletResponse resp){
        // 接收数据
        Map<String, String[]> map = req.getParameterMap();
        //获取当前页码
        String currPage = req.getParameter("currPage");
        Integer pagenum;
        //如果当前页码为null,则设置为1
        if (currPage == null) {
            pagenum = 1;

        } else {
            pagenum = Integer.parseInt(currPage);
        }
        User user = new User();
        //获取当前登录用户
//        User user = (User) req.getSession().getAttribute("USER_SESSION");
        UserService userService =
                (UserService) BeanFactory.getBean("userService");
        try {
            // 封装搜索框的参数信息
            BeanUtils.populate(user , map);
            PageBean<User> userPageBean= userService.searchUsers(user , pagenum);
            //将查询到的借阅记录信息 进行响应
            req.setAttribute("pageBean", userPageBean);
            //将搜索栏中的信息返回
            req.setAttribute("search", user );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user.jsp";
    }

}
