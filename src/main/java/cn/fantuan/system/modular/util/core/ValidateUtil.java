package cn.fantuan.system.modular.util.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidateUtil {
	/**
	 * 对象是否不为空(新增)
	 */
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	/**
	 * 对象是否为空
	 */
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof String) {
			if (o.toString().trim().equals("")) {
				return true;
			}
		} else if (o instanceof List) {
			if (((List) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Map) {
			if (((Map) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Set) {
			if (((Set) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Object[]) {
			if (((Object[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof int[]) {
			if (((int[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof long[]) {
			if (((long[]) o).length == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象组中是否存在空对象
	 */
	public static boolean isOneEmpty(Object... os) {
		for (Object o : os) {
			if (isEmpty(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象组中是否全是空对象
	 */
	public static boolean isAllEmpty(Object... os) {
		for (Object o : os) {
			if (!isEmpty(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将对象转为map集合
	 *
	 * @param object
	 * @return
	 */
	public static Map<String, Object> object2Map(Object object) {
		Map<String, Object> result = new HashMap<>();
		//获得类的的属性名 数组
		Field[] fields = object.getClass().getDeclaredFields();
		try {


			for (Field field : fields) {
				field.setAccessible(true);
				String name = new String(field.getName());
				result.put(name, field.get(object));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
