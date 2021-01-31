package cn.fantuan.system;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.SuccessCode;
import lombok.SneakyThrows;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SystemApplicationTests {
	@Autowired
	private RedisUtil redisUtil;

	@Test
	void contextLoads() {
		System.out.println(redisUtil);
		String token = "";
		//获取token的剩余时间
		long expire = redisUtil.getExpire(token);
		System.out.println("expire:" + expire);
	}

	@Test
	void Test() {
		System.out.println(new CommonResult(SuccessCode.SUCCESS_REGISTER));
	}

	@SneakyThrows
	@Test
	void Test1() {
		// 1.得到枚举类对象
		Class<?> clz = SuccessCode.class;
		System.out.println(clz);
		// 2.得到所有枚举常量
		Object[] objects = clz.getEnumConstants();
		Method getCode = clz.getMethod("getCode");
		Method getName = clz.getMethod("getMessage");
		for (Object obj : objects) {
			// 3.调用对应方法，得到枚举常量中字段的值
			System.out.println("code=" + getCode.invoke(obj) + "; name=" + getName.invoke(obj));
		}

	}

	//测试MD5加密算法
	@Test
	public void testMD() {
		//声明测试的密码字符串
		String password = "word";
		//使用MD5加密
		Md5Hash md5Hash = new Md5Hash(password);
		System.out.println("一次加密的结果：" + md5Hash.toHex());
		//带盐加密
		Md5Hash md5Hash1 = new Md5Hash(password, "你好");
		System.out.println("带盐加密的结果：" + md5Hash1.toHex());
		//带盐迭代加密
		Md5Hash md5Hash2 = new Md5Hash(password, "你好", 2);
		System.out.println("带盐迭代加密的结果：" + md5Hash2.toHex());
	}

	//测试向缓存中添加dept:用户ID，对应的数据
	@Test
	public void getRandomString() {
		Map<String, Object> map = new HashMap<>();
		map.put("deptID", 1);
		map.put("deptName", "长老会");
		List list = new ArrayList();
		list.add(1L);
		list.add(2L);
		list.add(3L);
		map.put("roleList", list);
		List list1 = new ArrayList();
		list1.add("超级管理员");
		list1.add("总经理");
		list1.add("项目经理");
		map.put("roleNames", list1);
		redisUtil.hmset(RedisConst.dept + 1, map);

	}

	@Test
	public void testRedis() {
		Map<String, Object> map = redisUtil.hmget(RedisConst.dept + 1);
		System.out.println("map:" + map);
		System.out.println(map.get("aaa"));
	}

}
