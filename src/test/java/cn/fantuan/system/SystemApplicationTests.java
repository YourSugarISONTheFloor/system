package cn.fantuan.system;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.CodeUtil;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.util.core.ToolUtil;
import lombok.SneakyThrows;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

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

	@Test
	public void getRandomString() {
		ToolUtil toolUtil = new ToolUtil();
		String randomString = CodeUtil.getRandomString(5);
		System.out.println("randomString = " + randomString);
	}

}
