package cn.fantuan.system.modular.service.impl;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.Role;
import cn.fantuan.system.modular.mapper.RoleMapper;
import cn.fantuan.system.modular.service.RoleServer;
import cn.fantuan.system.modular.util.code.SuccessCode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServerImpl extends ServiceImpl<RoleMapper, Role> implements RoleServer {
	//获取所有的角色集
	@Override
	public CommonResult getRole() {
		return new CommonResult(SuccessCode.SUCCESS, this.list());
	}
}
