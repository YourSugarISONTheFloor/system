package cn.fantuan.system.modular.util.ip;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import static com.alibaba.fastjson.util.IOUtils.readAll;

public class LocationUtil {
	//	private static String PATH = "http://restapi.amap.com/v3/ip";
//	private static String key = "a173d4054e306a660df522f1fec5d199";
	private static String PATH = "http://api.map.baidu.com/location/ip";
	private static String key = "UiGNeF8kEUY6gNEPofutGGzss0E8ln7D";
	private static String Com = "bd09ll";

	/**
	 * 获取客户HTTP之中的请求来进行定位
	 *
	 * @return
	 */
	public static Object GetLocationByIP() {
		return GetLocationByIP(null);
	}

	/**
	 * 根据输入的ip获取位置信息
	 *
	 * @param IP
	 * @return
	 */
	public static Object GetLocationByIP(String IP) {
		PATH += "?ak=" + key;
		PATH += "&coor=" + Com;
		JSONObject obj = null;
		try {
			obj = getLocationInfo(PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 通过高德地图获取经纬度信息
	 *
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	private static JSONObject getLocationInfo(String urlString) throws IOException {
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String res = readAll(in);
			JSONObject jsonObject = JSONObject.parseObject(res);
			return jsonObject;
		} finally {
			is.close();
		}
	}
}

