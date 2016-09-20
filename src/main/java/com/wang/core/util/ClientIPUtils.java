package com.wang.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * IP Util
 * @author HeJiawang
 * @date   2016.09.20
 */
public class ClientIPUtils {
	
	/**
	 * 反向代理软件就不能获取到客户端的真实IP地址了。
	 * 但是在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR信息。用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP"); 
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(StringUtils.isNotBlank(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
