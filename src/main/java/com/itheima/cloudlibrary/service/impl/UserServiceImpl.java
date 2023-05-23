package com.itheima.cloudlibrary.service.impl;

import com.itheima.cloudlibrary.dao.RecordDao;
import com.itheima.cloudlibrary.dao.UserDao;
import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.service.UserService;
import com.itheima.cloudlibrary.utils.BeanFactory;


import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *用户接口实现类
 */
public class UserServiceImpl  implements UserService {
    //通过User的用户账号和用户密码查询用户信息
    @Override
    public User login(User user) throws SQLException {
        UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
        return userDao.login(user);
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
        return userDao.findByUsername(username);
    }

    /**
     * 查询用户记录
     *  user 用户记录的查询条件
     *  user 当前的登录用户
     *  pageNum 当前页码
     *   每页显示数量
     */
    @Override
    public PageBean<User> searchUsers (User user, Integer pagenum) throws SQLException {
        PageBean<User> pageBean = new PageBean<User>();
        // 设置当前页码
        pageBean.setCurrPage(pagenum);
        // 设置每页显示记录数:
        Integer pageSize = 5;
        pageBean.setPageSize(pageSize);
        UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
        //查询符合条件的借阅记录数量
        Integer totalCount = userDao.searchUserCount(user);
        pageBean.setTotalCount(totalCount);
        // 设置符合条件的总页数:
        Double tc = totalCount.doubleValue();
        Double num = Math.ceil(tc / pageSize);
        pageBean.setTotalPage(num.intValue());
        Integer begin = (pagenum - 1)*pageSize;
        //查询符合条件的数据
        List<User> list = userDao.searchUsersByPage(user,begin,pageSize);
        pageBean.setList(list);
        return pageBean;
    }
}
