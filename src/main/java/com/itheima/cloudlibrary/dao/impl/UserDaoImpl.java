package com.itheima.cloudlibrary.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.itheima.cloudlibrary.dao.UserDao;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl  implements UserDao {
    // 用户模块DAO的用户登录的方法:
    public User login(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM user WHERE name = ? AND" +" password = ? AND status = ?";
        User existUser = qr.query(sql, new BeanHandler<User>(User.class),user.getName(), user.getPassword(), 0);
        return existUser;
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM user WHERE username=? status = 0";
        User existUser = queryRunner.query(sql, new BeanHandler<User>(User.class), username);
        return existUser;
    }
    @Override
    public Integer searchUserCount( User user) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM record WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
//        //如果是管理员并且搜索框中指定了查询的借阅人
//        if("ADMIN".equals(user.getRole()) &&
//                !StrUtil.isBlankIfStr(record.getBorrower())){
//            sql.append(" AND borrower LIKE ?");
//            params.add("%"+record.getBorrower()+"%");
//        }
//        //如果不是管理员，就查询当前登录用户的借阅记录
//        if(!"ADMIN".equals(user.getRole())){
//            sql.append(" AND borrower=?");
//            params.add(user.getName());
//        }
//        //如果搜索框中指定了查询的图书名称
//        if(!StrUtil.isBlankIfStr(record.getBookname())){
//            sql.append(" AND bookname LIKE ?");
//            params.add("%"+record.getBookname()+"%");
//        }
        //查询符合条件的借阅记录数量
        Long count = (Long)qr.query(sql.toString(),
                new ScalarHandler(),params.toArray());
        return count.intValue();
    }
    @Override
    public List<User> searchUsersByPage( User user,
                                            Integer begin, Integer pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        StringBuilder sql = new StringBuilder("SELECT * FROM record WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
//        //如果是管理员并且搜索框中指定了查询的借阅人
//        if("ADMIN".equals(user.getRole()) &&
//                !StrUtil.isBlankIfStr(record.getBorrower())){
//            sql.append(" AND borrower LIKE ?");
//            params.add("%"+record.getBorrower()+"%");
//        }
//        //如果不是管理员，就查询当前登录用户的借阅记录
//        if(!"ADMIN".equals(user.getRole()) ){
//            sql.append(" AND borrower=?");
//            params.add(user.getName());
//        }
//        //如果搜索框中指定了查询的图书名称
//        if(!StrUtil.isBlankIfStr(record.getBookname())){
//            sql.append(" AND bookname LIKE ?");
//            params.add("%"+record.getBookname()+"%");
//        }
        sql.append(" ORDER BY id ASC  LIMIT ?,?");
        params.add(begin);
        params.add(pageSize);
        List<User> users = queryRunner.query(sql.toString(),
                new BeanListHandler<User>(User.class),params.toArray());
        return users;
    }


}
