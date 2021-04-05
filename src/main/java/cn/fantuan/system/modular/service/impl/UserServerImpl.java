package cn.fantuan.system.modular.service.impl;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.Dept;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.mapper.UserMapper;
import cn.fantuan.system.modular.page.LayuiPage;
import cn.fantuan.system.modular.page.LayuiPageInfo;
import cn.fantuan.system.modular.service.DeptServer;
import cn.fantuan.system.modular.service.UserServer;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.CheckCode;
import cn.fantuan.system.modular.util.code.CodeUtil;
import cn.fantuan.system.modular.util.code.ErrorCode;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.util.menu.TreeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServerImpl extends ServiceImpl<UserMapper, User> implements UserServer {
	@Resource
	private UserMapper userMapper;

	@Autowired
	private DeptServer deptServer;

	@Autowired
	private RedisUtil redisUtil;

//	//根据ID获取用户信息
//	@Override
//	public User getById(Long id) {
//
//		return null;
//	}

	@Override
	public LayuiPageInfo getAll(LayuiPage layuiPage, String name, List deptId) {
//		System.out.println("数组为空的时候：" + deptId.isEmpty());
		//获取分页页码
		LayuiPage page = layuiPage.getLayuiPage(layuiPage);
		//获取总记录数
		//count();

		//分页查询的sql语句
		String sql = "limit " + page.getStart() + "," + page.getEnd();
		//判断是电话号码还是邮箱
		QueryWrapper queryWrapper = new QueryWrapper<User>();
		if (StringUtils.isNotEmpty(name)) {
			if (CheckCode.checkEmail(name) == 1) {
				queryWrapper.eq("email", name);
			} else {
				queryWrapper.eq("phone", name);
			}
		} else if (CollectionUtils.isNotEmpty(deptId)) {
			if (!deptId.get(0).equals("0")) {
				queryWrapper.in("deptID", deptId);
			}
		}
		//添加limit语句
		queryWrapper.last(sql);
		List<User> list = userMapper.selectList(queryWrapper);
		//返回的数据
		List rest = new ArrayList();
		for (User user : list) {
			//将实体类转为map
			Map map = JSON.parseObject(JSON.toJSONString(user), Map.class);
			Map<String, Object> deptMap = redisUtil.hmget(RedisConst.dept + user.getId());
			map.put("roleNames", deptMap.get("roleNames"));
			map.put("roleList", deptMap.get("roleList"));
			map.put("deptName", deptMap.get("deptName"));
			map.put("deptId", deptMap.get("deptId"));
			rest.add(map);
		}
		return new LayuiPageInfo(this.count(), rest);
	}

	//更改用户状态
	@Override
	public CommonResult editState(Map map) {
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		Integer status = Boolean.parseBoolean(map.get("status").toString()) == false ? 1 : 0;
		updateWrapper.set("status", status);
		updateWrapper.eq("id", map.get("id"));
		if (update(updateWrapper)) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}

	//更改用户信息
	@Override
	public CommonResult editUser(Map map) {
		System.out.println(map);
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		Object mapId = map.get("id");
		Object mapName = map.get("name");
		Object mapEmail = map.get("email");
		Object mapPhone = map.get("phone");
		Long deptId = Long.valueOf(String.valueOf(map.get("deptId")));
		updateWrapper.eq("id", mapId);
		if (!mapName.equals("")) {
			updateWrapper.set("name", mapName);
		} else {
			updateWrapper.set("name", CodeUtil.getUUID());
		}
		if (!mapEmail.equals("")) {
			updateWrapper.set("email", mapEmail);
		}
		if (!mapPhone.equals("")) {
			updateWrapper.set("phone", mapPhone);
		}
		updateWrapper.set("deptId", deptId);
		Map<String, Object> id = redisUtil.hmget(RedisConst.dept + mapId);
		if (!deptId.equals(id.get("deptId"))) {
			id.put("deptName", map.get("deptName"));
			id.put("deptID", deptId);
			Object roleData = map.get("roleData");
			Map map1 = JSON.parseObject(String.valueOf(roleData));
			id.put("roleList", map1.get("roleList"));
			id.put("roleNames", map1.get("roleNames"));
			redisUtil.hmset(RedisConst.dept + mapId, id);
		}
		if (update(updateWrapper)) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}

	//删除用户
	@Override
	public CommonResult delete(Long id) {
		if (this.removeById(id)) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}

	//用户角色分配
	@Override
	public CommonResult setRole(Map map) {
		Object roleData = map.get("data");
		Map map1 = JSON.parseObject(String.valueOf(roleData));
		Long id = Long.valueOf(String.valueOf(map1.get("id")));
		Map<String, Object> hmget = redisUtil.hmget(RedisConst.role + id);
		hmget.put("role_list", map1.get("role_list"));
		redisUtil.hmset(RedisConst.role + id, hmget);
		return new CommonResult(SuccessCode.SUCCESS);
	}

	//重置密码
	@Override
	public CommonResult reset(Long id) {
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("password", "123456q");
		updateWrapper.eq("id", id);
		if (update(updateWrapper)) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}

	@Override
	public Object getTree(String name, Integer tree) {
		QueryWrapper queryWrapper = new QueryWrapper<User>();
		if (CheckCode.checkEmail(name) == 1) {
			queryWrapper.eq("email", name);
		} else {
			queryWrapper.eq("phone", name);
		}
		//获取当前用户信息
		User one = this.getOne(queryWrapper);
		//获取该用户的部门id
		Integer deptId = one.getDeptId();
		//获取处于该部门的用户
		QueryWrapper query = new QueryWrapper<User>();
		//设置对应的部门id
		Long de = null;
		List list = new ArrayList();
		//1：下级、2：上级
		if (tree == 1) {
			//通过部门id获取详细
			Dept pid = deptServer.getSon(deptId);
			if (pid != null) {
				//获取该部门的上级部门id
				de = pid.getDeptId();
				query.eq("deptId", de);
				list = this.list(query);
			}
		} else {
			//通过部门id获取详细
			Dept pid = deptServer.getPid(deptId);
			if (pid != null) {
				//获取该部门的上级部门id
				de = pid.getPid();
				query.eq("deptId", de);
				list = this.list(query);
			}
		}

		System.out.println(list);
		Map map = new HashMap();
		map.put("name", one.getName());
		map.put("dept", deptServer.getPid(one.getDeptId()).getDeptName());
		map.put("children", new TreeUtil().toDeptTree(list, deptServer.getPid(Integer.valueOf(de.toString())).getDeptName()));
		return map;
	}
}
