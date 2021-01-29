package cn.fantuan.system.modular.exception.enums;

import cn.fantuan.system.modular.exception.AbstractBaseExceptionEnum;

public enum BizExceptionEnum implements AbstractBaseExceptionEnum {
	/**
	 * 字典
	 */
	DICT_EXISTED(400, "字典已经存在");

	BizExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private Integer code;

	private String message;

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
