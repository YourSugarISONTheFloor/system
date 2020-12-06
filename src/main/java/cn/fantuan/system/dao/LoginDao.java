package cn.fantuan.system.dao;

import cn.fantuan.system.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface LoginDao {
    //根据邮箱获取用户
    User getUserToEmail(@Param("username") String username);

    //根据手机号获取用户信息
    User getUserToPhone(@Param("username") String username);

    //更改用户密码
    Integer updatePassword(@Param("username") String username, @Param("password") String password,@Param("rest") Integer rest);

    //添加用户
    Integer addUser(@Param("username") String username, @Param("password") String password, @Param("name") String name, @Param("rest") Integer rest);
}