package com.itheima.cloudlibrary.dao;


import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户操作接口
 */
public interface UserDao {
    //用户登录
    User login(User user) throws SQLException;

    User findByUsername(String username) throws SQLException;
    //查询用户数量
    Integer searchUserCount( User user) throws SQLException;
    //查询符合条件的用户数据
    List<User> searchUsersByPage( User user,
                                     Integer begin, Integer pageSize) throws SQLException;
}
