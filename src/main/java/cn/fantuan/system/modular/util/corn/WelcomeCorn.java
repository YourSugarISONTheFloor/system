package cn.fantuan.system.modular.util.corn;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WelcomeCorn {

	@Autowired
	private RedisUtil redisUtil;

	@Scheduled(cron = "0 0 9 * * ? ")
	public void active_user_day() {
		Date date = new Date();
		SimpleDateFormat year_format = new SimpleDateFormat("yyyy-MM-dd");
		String day = year_format.format(date);
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
