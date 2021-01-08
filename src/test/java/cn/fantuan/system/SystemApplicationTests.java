package cn.fantuan.system;

import cn.fantuan.system.entities.CommonResult;
import cn.fantuan.system.util.RedisUtil;
import cn.fantuan.system.util.code.SuccessCode;
import lombok.SneakyThrows;
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
		String token="";
		//获取token的剩余时间
		long expire = redisUtil.getExpire(token);
		System.out.println("expire:" + expire);
	}

	@Test
	void Test(){
		System.out.println(new CommonResult(SuccessCode.SUCCESS_REGISTER));
	}

	@SneakyThrows
	@Test
	void Test1(){
		// 1.得到枚举类对象
		Class<?> clz = SuccessCode.class;
		System.out.println(clz);
		// 2.得到所有枚举常量
		Object[] objects = clz.getEnumConstants();
		Method getCode = clz.getMethod("getCode");
		Method getName = clz.getMethod("getMessage");
		for (Object obj : objects){
			// 3.调用对应方法，得到枚举常量中字段的值
			System.out.println("code=" + getCode.invoke(obj) + "; name=" + getName.invoke(obj));
		}

	}

}
