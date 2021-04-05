package cn.fantuan.system.modular.service.impl;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.Dept;
import cn.fantuan.system.modular.mapper.DeptMapper;
import cn.fantuan.system.modular.service.DeptServer;
import cn.fantuan.system.modular.util.code.ErrorCode;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.util.menu.TreeUtil;
import cn.fantuan.system.modular.vo.DeptVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public CommonResult getINDept() {
		List<Dept> deptList = this.list();
		List list = new ArrayList();
		for (Dept e : deptList) {
			Map map = new HashMap();
			map.put("deptId", e.getDeptId());
			if (e.getPid() == 0) {
				map.put("pid", "无上级部门");
			} else {
				Dept byId = this.getById(e.getPid());
				map.put("pid", byId.getDeptName());
			}
			map.put("name", e.getDeptName());
			list.add(map);
		}
		return new CommonResult(SuccessCode.SUCCESS, list);
	}

	//添加部门
	@Override
	public CommonResult add(Dept dept) {
		boolean save = this.save(dept);
		if (save) {
			return new CommonResult(SuccessCode.SUCCESS);
		}
		return new CommonResult(ErrorCode.ADD_ERROR);
	}

	//删除部门
	@Override
	public CommonResult del(Long id) {
		boolean b = this.removeById(id);
		if (b) {
			return new CommonResult(SuccessCode.SUCCESS);
		}
		return new CommonResult(ErrorCode.ADD_ERROR);
	}

	@Override
	public CommonResult dept_all() {
		return new CommonResult(SuccessCode.SUCCESS, this.list());
	}

	@Override
	public Dept getPid(Integer deptId) {
		System.out.println(deptId);
		//获取该部门id的信息，及获取到上级部门id
		return this.getById(deptId);
	}

	@Override
	public Dept getSon(Integer deptId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		//父级id为该部门id，及获取该部门id的下级部门
		queryWrapper.eq("pid", deptId);
		return this.getOne(queryWrapper);
	}
}
