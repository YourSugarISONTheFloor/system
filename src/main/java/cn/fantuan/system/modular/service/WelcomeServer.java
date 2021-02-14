package cn.fantuan.system.modular.service;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.SuccessCode;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class WelcomeServer {

	@Autowired
	private RedisUtil redisUtil;


	//获取近七日的相关数据
	public CommonResult getLastSevenDays() {
		//创建一个数组用来接收返回出去的集合
		Map<String, Object> param = new HashMap<>();
		//创建一个数组存储日期
		String[] str = new String[7];
		Date date = new Date();
		SimpleDateFormat year_format = new SimpleDateFormat("yyyy-");
		String year = year_format.format(date);
		//时间格式化工厂
		SimpleDateFormat mouth_format = new SimpleDateFormat("MM-dd");
		Calendar calendar;
		//获取近七天的日期
		for (int i = 0; i < 7; i++) {
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -i);
			//把时间插入到数组中，并倒叙插入
			str[6 - i] = mouth_format.format(calendar.getTime());
		}
		//给集合添加集合
		param.put("categories", str);
		//创建一个集合存储内容
		List<JSONObject> list = new ArrayList<>();
		//创建一个名字数组
		String[] name = new String[]{"迟到数", "早退数", "缺勤数", "请假数", "签到数"};

		for (int j = 0; j < 5; j++) {
			Object[] mathDate = new Object[7];
			JSONObject jsonObject = new JSONObject();
			for (int i = 0; i < 7; i++) {
				String today = str[i];
				if (j == 0) {
					//迟到
					mathDate[i] = Float.parseFloat(Objects.equals(redisUtil.hget(RedisConst.late, year + today), null) ? "0" : String.valueOf(redisUtil.hget(RedisConst.late, year + today)));
				} else if (j == 1) {
					//早退
					mathDate[i] = Float.parseFloat(Objects.equals(redisUtil.hget(RedisConst.exLeave, year + today), null) ? "0" : String.valueOf(redisUtil.hget(RedisConst.exLeave, year + today)));
				} else if (j == 2) {
					//缺勤
					mathDate[i] = Float.parseFloat(Objects.equals(redisUtil.hget(RedisConst.notAttendance, year + today), null) ? "0" : String.valueOf(redisUtil.hget(RedisConst.notAttendance, year + today)));
				} else if (j == 3) {
					//请假
					mathDate[i] = Float.parseFloat(Objects.equals(redisUtil.hget(RedisConst.leave, year + today), null) ? "0" : String.valueOf(redisUtil.hget(RedisConst.leave, year + today)));
				} else {
					//请假
					mathDate[i] = Float.parseFloat(Objects.equals(redisUtil.hget(RedisConst.activeUserDay, year + today), null) ? "0" : String.valueOf(redisUtil.hget(RedisConst.activeUserDay, year + today)));
				}
			}
			jsonObject.put("date", mathDate);
			jsonObject.put("name", name[j]);
			list.add(jsonObject);
		}
		//给集合添加对象
		param.put("series", list);
		return new CommonResult(SuccessCode.SUCCESS, param);
	}

	//获取所有的用户数
	public CommonResult getCountUser() {
//		Set<String> set = redisUtil.getKeys(RedisConst.USER);
//		return new CommonResult(SuccessCode.SUCCESS, set.size());
		return new CommonResult(SuccessCode.SUCCESS, 66);
	}

	//用户活跃量报表
	public CommonResult getActiveUser() {
		Map<String, Object> param = new HashMap<>();
		//创建一个数组存储日期
		String[] str = new String[2];
		SimpleDateFormat year_format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar;
		//获取近七天的日期
		for (int i = 0; i < 2; i++) {
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -i - 1);
			//把时间插入到数组中，并倒叙插入
			str[1 - i] = year_format.format(calendar.getTime());
		}
		//给集合添加集合
		Integer[] mathDate = new Integer[2];
		for (int i = 0; i < 2; i++) {
			mathDate[i] = Integer.parseInt(Objects.equals(redisUtil.hget(RedisConst.activeUserDay, str[i]), null) ? "0" : redisUtil.hget(RedisConst.activeUserDay, str[i]).toString());
		}
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		String result;
		if (mathDate[1] == 0) {
			//当昨天为0时：
			result = numberFormat.format((float) mathDate[0] * 100);
		} else if (mathDate[0] == 0) {
			//当前天为0时：
			result = numberFormat.format((float) mathDate[1] * 100);
		} else {
			//当昨天和前天都有值时：
			result = numberFormat.format(Math.abs(((float) mathDate[1] / (float) mathDate[0]) - 1) * 100);
		}
		if (mathDate[1] - mathDate[0] >= 0) {
			//昨天比前天人数多
			param.put("radio", "增长" + result + "%");
		} else {
			//昨天比前天人数少
			param.put("radio", "下降" + result + "%");
		}
		param.put("count", mathDate[1]);
		return new CommonResult(SuccessCode.SUCCESS, param);
	}
}
