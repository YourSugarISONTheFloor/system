package cn.fantuan.system.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
	private Integer code;
	private String message;
	private T data;

	public CommonResult(Integer code, String message) {
		this(code, message, null);
	}

	@SneakyThrows
	public CommonResult(Enum e) {
		// 得到枚举类对象
		Class<?> clz = e.getClass();
		//得到枚举类中的方法
		Method getCode = clz.getMethod("getCode");
		Method getMessage = clz.getMethod("getMessage");
		// 调用对应方法，得到枚举常量中字段的值
		this.code = (Integer) getCode.invoke(e);
		this.message = (String) getMessage.invoke(e);
		this.data = null;
	}

	@SneakyThrows
	public CommonResult(Enum e, T data) {
		this(e);
		this.data = data;
	}
}