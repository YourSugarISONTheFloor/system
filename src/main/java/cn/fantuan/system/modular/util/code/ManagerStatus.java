package cn.fantuan.system.modular.util.code;

import lombok.Getter;

/**
 * 用户状态
 */
@Getter
public enum ManagerStatus {

	OK(0, "启用"), FREEZED(1, "禁用"), DELETED(2, "冻结");

	Integer code;
	String message;

	ManagerStatus(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getDescription(String value) {
		if (value == null) {
			return "";
		} else {
			for (ManagerStatus ms : ManagerStatus.values()) {
				if (ms.getCode().equals(value)) {
					return ms.getMessage();
				}
			}
			return "";
		}
	}
}
