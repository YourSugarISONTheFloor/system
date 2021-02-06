package cn.fantuan.system.modular.service;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DeptServer extends IService<Dept> {
	//获取所有的部门
	CommonResult getDeptAdd();

	//获取所有的部门
	CommonResult getDept();
}
