package cn.fantuan.system.core.common.constant;

/**
 * 系统对应的缓存前缀
 */
public interface RedisConst {
	//用户部门
	String dept = "dept:";
	//用户角色—>路径
	String role = "role:";
	//用户信息
	String USER = "user:";
	//迟到统计标识
	String late = "LATE";
	//早退统计标识
	String exLeave = "EXLEAVE";
	//缺勤统计标识
	String notAttendance = "NOTATTENDANCE";
	//请假统计标识
	String leave = "LEAVE";
	//签到统计标识
	String activeUserDay = "ACTIVEUSERDAY";
	//签到统计
	String report = "report:";
}
