package cn.fantuan.system.modular.mapper;

import cn.fantuan.system.modular.entities.outside.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper extends BaseMapper<User> {
}