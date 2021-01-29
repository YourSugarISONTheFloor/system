package cn.fantuan.system.modular.util.code;

public enum ErrorCode {
	HAPPEN_ERROR(333, "出错啦！"),
	NOT_HAS_MENU(333, "没有该父类菜单"),
	//验证码异常
	CAPTCHA_ERROR(408, "验证码错误"),
	//用户账号异常
	NO_THIS_USER(400, "该用户走丢啦"),
	USER_NOT_REG(401, "该用户还没住进灰姑凉家里呢"),
	USER_ALREADY_REG(401, "该用户已经在灰姑凉家里了"),
	ACCOUNT_FREEZED(401, "账号被冻结"),
	ERROR_PASSWORD(402, "密码错误"),
	//token异常
	TOKEN_EXPIRED(700, "token过期"),
	TOKEN_ERROR(700, "token验证失败");

	// 定义一个 private 修饰的实例变量
	private String message;

	private Integer code;

	//定义一个带参数的构造器，枚举类的构造器只能使用private修饰
	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
