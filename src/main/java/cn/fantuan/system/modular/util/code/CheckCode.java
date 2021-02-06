package cn.fantuan.system.modular.util.code;

public class CheckCode {
	private static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 判断传过来的数据是否是邮箱格式数据
	 *
	 * @param code 验证的数据
	 * @return
	 */
	public static Integer checkEmail(String code) {
		if (code.matches(EMAIL)) {
			return 1;
		}
		return 0;
	}
}
