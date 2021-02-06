package cn.fantuan.system.modular.service.impl;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.Dept;
import cn.fantuan.system.modular.mapper.DeptMapper;
import cn.fantuan.system.modular.service.DeptServer;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.util.menu.TreeUtil;
import cn.fantuan.system.modular.vo.DeptVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeptServerImpl extends ServiceImpl<DeptMapper, Dept> implements DeptServer {
	public List getDeptDB() {
		//获取所有激活的菜单数组：父ID为0和状态为激活的菜单
		List<Dept> deptList = this.list();
		//child数组
		List<DeptVo> deptVoList = new ArrayList<>();
		for (Dept e : deptList) {
			DeptVo deptVo = new DeptVo();
			deptVo.setId(e.getDeptId());
			deptVo.setPid(e.getPid());
			deptVo.setTitle(e.getDeptName());
			deptVoList.add(deptVo);
		}
		return new TreeUtil().toTree(deptVoList, 0L);
	}

	//获取所有的部门
	@Override
	public CommonResult getDeptAdd() {
		List list = getDeptDB();
		DeptVo deptVo = new DeptVo();
		deptVo.setId(0L);
		deptVo.setPid(0L);
		deptVo.setTitle("查看全部");
		list.add(deptVo);
		return new CommonResult(SuccessCode.SUCCESS, list);
	}

	//获取数据库中的部门
	public CommonResult getDept() {
		return new CommonResult(SuccessCode.SUCCESS, getDeptDB());
	}
}
