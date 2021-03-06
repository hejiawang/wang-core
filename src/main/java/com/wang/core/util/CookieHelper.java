package com.wang.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * COOKIE helper
 *
 * @author HeJiawang
 * @date   2016.09.21
 */
public class CookieHelper {
	private static final Logger log = LoggerFactory.getLogger(CookieHelper.class);

	/**
	 * 获得cookie
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * get cookie to map
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 添加cookie
	 */
	public static void addCookie(String cookieName, String value, /*String domain,*/ String cookiePath,
			int cookieExpiryDate, HttpServletResponse response) {
		log.debug("正在写入Cookie.......");
		Cookie cookie = new Cookie(cookieName, value);
		//cookie.setDomain(domain);
		cookie.setPath(cookiePath);
		cookie.setMaxAge(cookieExpiryDate);
		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 */
	public static void delCookies(HttpServletResponse rs, String cookieName) {
		// String cookieDomain = PropertyUtil.getStrValue("base.properties",
		// "cookieDomain", "cart.mbaobao.com");

		Cookie cook = new Cookie(cookieName, null);
		// cook.setDomain(cookieDomain);
		cook.setPath("/");
		// cook.setMaxAge(-1);
		cook.setMaxAge(0);
		rs.addCookie(cook);
	}

}
