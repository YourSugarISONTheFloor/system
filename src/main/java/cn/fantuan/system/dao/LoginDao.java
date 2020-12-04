package cn.fantuan.system.dao;

import cn.fantuan.system.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginDao {
    User getUser(@Param("username") String username);
}