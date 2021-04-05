package cn.fantuan.system.modular.service;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DeptServer extends IService<Dept> {
	//获取所有的部门
	CommonResult getDeptAdd();

	//获取所有的部门
	CommonResult getDept();

	//获取所有的部门
	CommonResult getINDept();

	CommonResult add(Dept dept);

	CommonResult del(Long id);

	//部门下拉选择框数据
	CommonResult dept_all();

	//获取父级
	Dept getPid(Integer deptId);

	//获取下级id
	Dept getSon(Integer deptId);
	
}
