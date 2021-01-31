package cn.fantuan.system.modular.service.impl;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.mapper.UserMapper;
import cn.fantuan.system.modular.page.LayuiPage;
import cn.fantuan.system.modular.page.LayuiPageInfo;
import cn.fantuan.system.modular.service.UserServer;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.ErrorCode;
import cn.fantuan.system.modular.util.code.SuccessCode;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServerImpl extends ServiceImpl<UserMapper, User> implements UserServer {
	@Resource
	private UserMapper userMapper;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public LayuiPageInfo getAll(LayuiPage layuiPage) {
		//获取分页页码
		LayuiPage page = layuiPage.getLayuiPage(layuiPage);
		//获取总记录数
		//count();
		//分页查询的sql语句
		String sql = "limit " + page.getStart() + "," + page.getEnd();
		List<User> list = userMapper.selectList(new QueryWrapper<User>().last(sql));
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
		System.out.println(map.get("status"));
		Integer status = Boolean.parseBoolean(map.get("status").toString()) == false ? 1 : 0;
		updateWrapper.set("status", status);
		updateWrapper.eq("id", map.get("id"));
		if (update(updateWrapper)) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}
}
