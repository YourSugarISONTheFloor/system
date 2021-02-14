package cn.fantuan.system.modular.util.ip;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

public class IpAddressUtil {
	/**
	 * 获取本地IP地址的方法
	 *
	 * @return
	 */
	public static String getLocalAddress() {
		String hostAddress = null;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("由于各种奇怪的原因获取不到IP");
			e.printStackTrace();
		}
		return hostAddress;
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	 *
	 * @param request
	 * @return
	 */
	public static String getRealIP(HttpServletRequest request) {
		// nginx代理获取的真实用户ip
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Natapp-Ip");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// 对于通过多个代理的情况， 第一个IP为客户端真实IP,多个IP按照','分割 "***.***.***.***".length() = 15
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		System.out.println(ip);
		return ip;
	}


	/**
	 * 获取外网ip
	 *
	 * @return
	 */
	public static String getV4Ip() {
		try {
			//建立连接
			String path = "http://pv.sohu.com/cityjson?ie=utf-8";// 要获得html页面内容的地址
			URL url = new URL(path);// 创建url对象
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			//获取输入流
			InputStream input = httpUrlConn.getInputStream();
			//将字节输入流转换为字符输入流
			InputStreamReader read = new InputStreamReader(input, "utf-8");
			//为字符输入流添加缓冲
			BufferedReader br = new BufferedReader(read);
			// 读取返回结果
			String data = br.readLine();
			//var returnCitySN = {"cip": "139.204.114.28", "cid": "510000", "cname": "四川省"};
			// 释放资源
			br.close();
			read.close();
			input.close();
			httpUrlConn.disconnect();
			String str1 = data.substring(data.substring(0, data.indexOf("{") - 1).length(), data.length() - 1);//截取@之前的字符串
			Map maps = (Map) JSON.parse(str1);
			return String.valueOf(maps.get("cip"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
