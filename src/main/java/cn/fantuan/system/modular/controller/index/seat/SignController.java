package cn.fantuan.system.modular.controller.index.seat;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.util.ip.IpAddressUtil;
import cn.fantuan.system.modular.util.ip.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

@Controller
@RequestMapping("/seat/")
public class SignController {
	private final String PATH = "/subsidiary/page/";

	@Autowired
	private RedisUtil redisUtil;

	//签到打卡的视图控制器
	@RequestMapping("sign")
	public String sign() {
		return PATH + "sign";
	}

	//获取客户端的经纬度
	@GetMapping("getIpAddress")
	@ResponseBody
	public CommonResult getIpAddress(HttpServletRequest request) {
		Map o = (Map) LocationUtil.GetLocationByIP(IpAddressUtil.getV4Ip());
		System.out.println(o);
		Map map = new HashMap();
		map.put("ipData", o);
		map.put("message", "范围内签到");
		return new CommonResult(SuccessCode.SUCCESS, map);
	}

	//获取签到时间
	@PostMapping("getTime")
	@ResponseBody
	public CommonResult getTime(String id) {
		//判断用户是否已经签到
		SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String format = year.format(date);
		float state = Float.parseFloat(Objects.equals(redisUtil.hget(RedisConst.report + format, id), null) ? "0" : String.valueOf(redisUtil.hget(RedisConst.report + format, id)));
		Map map = new HashMap();
		//获取当天20点的时间戳
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		map.put("state", state);
		map.put("day", format);
		//结束时间戳
		map.put("end", calendar.getTimeInMillis());
		//开始时间戳
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		map.put("start", calendar.getTimeInMillis());
		return new CommonResult(SuccessCode.SUCCESS, map);
	}

	//用户签到
	@PostMapping("report")
	@ResponseBody
	public CommonResult report(String id) {
		SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String format = year.format(date);
		redisUtil.hset(RedisConst.report + format, id, System.currentTimeMillis());
		return new CommonResult(SuccessCode.SUCCESS);
	}
}
