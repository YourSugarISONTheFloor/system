package cn.fantuan.system.modular.exception.enums;

import cn.fantuan.system.modular.exception.AbstractBaseExceptionEnum;

public enum  CoreExceptionEnum  implements AbstractBaseExceptionEnum {
	/**
	 * 其他
	 */
	INVLIDE_DATE_STRING(400, "输入日期格式不对"),
	ENCRYPT_ERROR(600, "加解密错误");

	CoreExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private Integer code;

	private String message;

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
