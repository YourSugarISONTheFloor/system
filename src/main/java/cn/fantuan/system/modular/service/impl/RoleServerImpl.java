package cn.fantuan.system.modular.service.impl;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.Role;
import cn.fantuan.system.modular.mapper.RoleMapper;
import cn.fantuan.system.modular.service.RoleServer;
import cn.fantuan.system.modular.util.code.ErrorCode;
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

	//添加角色集
	@Override
	public CommonResult add(Role role) {
		boolean save = this.save(role);
		if (save) {
			return new CommonResult(SuccessCode.SUCCESS);
		}
		return new CommonResult(ErrorCode.ADD_ERROR);
	}

	//删除角色
	@Override
	public CommonResult del(Long id) {
		boolean b = this.removeById(id);
		if (b) {
			return new CommonResult(SuccessCode.SUCCESS);
		}
		return new CommonResult(ErrorCode.ADD_ERROR);
	}
}
