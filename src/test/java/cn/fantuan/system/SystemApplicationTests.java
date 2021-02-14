package cn.fantuan.system;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.vo.MenuVo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.SneakyThrows;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

	@Test
	public void printClassMethodMessage() throws IllegalAccessException, InstantiationException {
		// 要获取类的信息 首先要获取类的类类型,传递的是哪个子类的对象 c就是该子类的类类型
		Class c = new MenuVo().getClass();
		// 获取类的名称
		System.out.println("类的名称是:" + c.getName());
		/*
		 * Method类，方法对象 一个成员方法就是一个Method对象
		 * getMethods()方法获取的是所有的public的函数，包括父类继承而来的
		 * getDeclaredMethods()获取的是所有该类自己声明的方法，不问访问权限
		 */
		// c.getDeclaredMethods()
		Method[] ms = c.getMethods();

		Object obj = c.newInstance();
		try {
			List list = new ArrayList();
			list.add("a");
			list.add("b");
			list.add("c");
			System.out.println(c.getMethod("setChild", List.class).invoke(obj, list));
			System.out.println(c.getMethod("getChild").invoke(obj));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < ms.length; i++) {
			// 得到方法的返回值类型的类类型
			Class returnType = ms[i].getReturnType();
			System.out.print(returnType.getName() + " ");
			// 得到方法的名称
			System.out.print(ms[i].getName() + "(");
			// 获取参数类型--->得到的是参数列表的类型的类类型
			Class[] paramTypes = ms[i].getParameterTypes();
			for (Class class1 : paramTypes) {
				System.out.print(class1.getName() + ",");
			}
			System.out.println(")");
		}

	}

	@Test
	public void printFieldMessage() {
		Class c = new MenuVo().getClass();
		/*
		 * 成员变量也是对象 java.lang.reflect.Field Field类封装了关于成员变量的操作
		 * getFields()方法获取的是所有的public的成员变量的信息
		 * getDeclaredFields获取的是该类自己声明的成员变量的信息
		 */
		// Field[] fs = c.getFields();
		Field[] fs = c.getDeclaredFields();
		for (Field field : fs) {
			// 得到成员变量的类型的类类型
			Class fieldType = field.getType();
			String typeName = fieldType.getName();
			// 得到成员变量的名称
			String fieldName = field.getName();
			System.out.println(typeName + " " + fieldName);
		}

	}

	@Test
	public void printConMessage() {
		Class c = new MenuVo().getClass();
		/*
		 * 构造函数也是对象 java.lang. Constructor中封装了构造函数的信息
		 * getConstructors获取所有的public的构造函数 getDeclaredConstructors得到所有的构造函数
		 */
		// Constructor[] cs = c.getConstructors();
		Constructor[] cs = c.getDeclaredConstructors();
		for (Constructor constructor : cs) {
			System.out.print(constructor.getName() + "(");
			// 获取构造函数的参数列表--->得到的是参数列表的类类型
			Class[] paramTypes = constructor.getParameterTypes();
			for (Class class1 : paramTypes) {
				System.out.print(class1.getName() + ",");
			}
			System.out.println(")");
		}

	}

	@Test
	public void testMain() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
		// 反射:在运行的时候，对于任意一个类，我们可以获取这个类的所
		//有属性和方法；对于任意一个对象，都可以调用这个对象的任意一个方法和属性。
		//1. class.forname
		Class<?> aClass = Class.forName("com.ma.relfect.Person");
		//2. 获取变量
		Field[] declaredFields = aClass.getDeclaredFields();
		for (Field field : declaredFields) {
			System.out.println(field);
		}
		System.out.println("===");
		// 3. 获取方法
		Method[] declaredMethods = aClass.getMethods();
		for (Method method : declaredMethods) {
			System.out.println(method);
		}
		Method method = aClass.getDeclaredMethod("getString");
		// 通过反射调用类的方法
		Object obj = aClass.newInstance();
		//在调用method.invoke 的时候，第一个参数传递的应该是调用 调用该方法的本身的对象，而我粗心的传递成了Class对象，所以出现问题。
		// 也就是说这里本应该传这个对象的实例，（毕竟是对象调用方法），我错误的传递成了Class对象。
		// 错误：String invoke = (String)methd.invoke(aClass);
		// 正确：
		String invoke = (String) method.invoke(obj);
		System.out.println(invoke);
	}

	@Test
	public void testO() {
		MenuVo menuVo = new MenuVo();
		menuVo.setId(200L);
		menuVo.setPid(0L);
		menuVo.setTitle("其他管理");

		MenuVo menuVo1 = new MenuVo();
		menuVo1.setId(201L);
		menuVo1.setPid(1L);
		menuVo1.setTitle("其他");

		MenuVo menuVo2 = new MenuVo();
		menuVo2.setId(202L);
		menuVo2.setPid(2L);
		menuVo2.setTitle("管理");

		List listAA = new ArrayList();
		listAA.add(menuVo1);
		menuVo.setChild(listAA);

		System.out.println(menuVo);
		Class c = menuVo.getClass();

//		Object obj = c.newInstance();
		try {
			if (c.getMethod("getChild").invoke(menuVo) == null) {
				System.out.println("设置子类为一个数组");
				c.getMethod("setChild", List.class).invoke(menuVo, new ArrayList());
				System.out.println("第一次获取子集：" + c.getMethod("getChild").invoke(menuVo));
			}
			Class<?> aClass = c.getMethod("getChild").invoke(menuVo).getClass();
			Object obj = aClass.newInstance();
			obj = c.getMethod("getChild").invoke(menuVo);
			System.out.println("获取到的obj：" + obj);

			obj.getClass().getMethod("add", Object.class).invoke(obj, menuVo2);
			System.out.println("添加了menuVo2后的obj：" + obj);
			//设置最后的子类
//			System.out.println("设置最后的子类");
//			c.getMethod("setChild", List.class).invoke(menuVo, obj);
			System.out.println("最后的子类：" + c.getMethod("getChild").invoke(menuVo));
			System.out.println("最后的数据：" + menuVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void add() {
		//创建一个数组存储日期
		String[] str = new String[7];
		Date date = new Date();
		SimpleDateFormat year_format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar;
		//获取近七天的日期
		for (int i = 0; i < 7; i++) {
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -i);
			//把时间插入到数组中，并倒叙插入
			str[6 - i] = year_format.format(calendar.getTime());
		}
		System.out.println("数组长度：" + str.length);
		for (int i = 0; i < str.length; i++) {
			String day = str[i];
			//获取签到的随机数
			int activeUserDay = (int) (Math.random() * 67);
			//获取缺勤的数量
			int notAttendance = 66 - activeUserDay;
			//获取迟到的随机数
			int late = (int) (Math.random() * activeUserDay);
			//获取早退的随机数
			int exLeave = (int) (Math.random() * activeUserDay);
			//获取请假的随机数
			int leave = (int) (Math.random() * notAttendance);
			//签到数
			redisUtil.hset(RedisConst.activeUserDay, day, activeUserDay);
			//迟到数
			redisUtil.hset(RedisConst.late, day, late);
			//缺勤数
			redisUtil.hset(RedisConst.notAttendance, day, notAttendance);
			//早退
			redisUtil.hset(RedisConst.exLeave, day, exLeave);
			//请假
			redisUtil.hset(RedisConst.leave, day, leave);
		}
	}

	@Test
	public void getIp(HttpServletRequest request) {
		String Xip = request.getHeader("X-Real-IP");
		String XFor = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = XFor.indexOf(",");
			if (index != -1) {
				XFor.substring(0, index);
				System.out.println(XFor);
			} else {
				System.out.println(XFor);
			}
		}
		XFor = Xip;
		if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			System.out.println(XFor);
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getRemoteAddr();
		}
		System.out.println(XFor);
	}

}
