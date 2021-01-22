package cn.fantuan.system.modular.util.code;

public enum SuccessCode {
	SUCCESS(200, "操作成功"),

	SUCCESS_REGISTER(220,"该用户入住灰姑凉成功"),

	SUCCESS_CHANGE(230,"更改密码成功");


	// 定义一个 private 修饰的实例变量
	private String message;

	private Integer code;

	//定义一个带参数的构造器，枚举类的构造器只能使用private修饰
	SuccessCode(int code, String message) {
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
