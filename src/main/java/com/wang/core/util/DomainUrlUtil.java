package com.wang.core.util;

/**
 * 文件路径
 * 
 * @author HeJiawang
 * @date   2016.09.20
 */
public class DomainUrlUtil {

	/**
	 * 工程路径
	 */
	public static String BASEURL_DOMAIN;
	
	/**
	 * JS路径
	 */
	public static String JS_BASEURL_DOMAIN;
	
	/**
	 * CSS路径
	 */
	public static String CSS_BASEURL_DOMAIN;

	/**
	 * 图片路径
	 */
	public static String IMG_BASEURL_DOMAIN;
	
	/**
	 * COOKIE名称
	 */
	public static String COOKIE_DOMAIN;
	
	/**
	 * js模板handlebars.js用到的hbs文件目录
	 */
	public static String TPL_DOMAIN;
	
	/**
	 * search-web URL : solr、memcached等非数据库搜索工程
	 */
	public static String SEARCH_DOMAIN;
	
	/**
	 * 权限管理系统URL
	 */
	public static String PERMISSION_DOMAIN;
	
	public static String getPERMISSION_DOMAIN() {
		return PERMISSION_DOMAIN;
	}

	public static void setPERMISSION_DOMAIN(String pERMISSION_DOMAIN) {
		PERMISSION_DOMAIN = pERMISSION_DOMAIN;
	}

	public static String getSEARCH_DOMAIN() {
		return SEARCH_DOMAIN;
	}

	public static void setSEARCH_DOMAIN(String sEARCH_DOMAIN) {
		SEARCH_DOMAIN = sEARCH_DOMAIN;
	}

	public static String getTPL_DOMAIN() {
		return TPL_DOMAIN;
	}

	public static void setTPL_DOMAIN(String tPL_DOMAIN) {
		TPL_DOMAIN = tPL_DOMAIN;
	}

	public String getCOOKIE_DOMAIN() {
		return COOKIE_DOMAIN;
	}

	public void setCOOKIE_DOMAIN(String cOOKIE_DOMAIN) {
		COOKIE_DOMAIN = cOOKIE_DOMAIN;
	}

	public String getBASEURL_DOMAIN() {
		return BASEURL_DOMAIN;
	}

	public void setBASEURL_DOMAIN(String bASEURL_DOMAIN) {
		BASEURL_DOMAIN = bASEURL_DOMAIN;
	}

	public String getJS_BASEURL_DOMAIN() {
		return JS_BASEURL_DOMAIN;
	}

	public void setJS_BASEURL_DOMAIN(String jS_BASEURL_DOMAIN) {
		JS_BASEURL_DOMAIN = jS_BASEURL_DOMAIN;
	}

	public String getCSS_BASEURL_DOMAIN() {
		return CSS_BASEURL_DOMAIN;
	}

	public void setCSS_BASEURL_DOMAIN(String cSS_BASEURL_DOMAIN) {
		CSS_BASEURL_DOMAIN = cSS_BASEURL_DOMAIN;
	}

	public String getIMG_BASEURL_DOMAIN() {
		return IMG_BASEURL_DOMAIN;
	}

	public void setIMG_BASEURL_DOMAIN(String iMG_BASEURL_DOMAIN) {
		IMG_BASEURL_DOMAIN = iMG_BASEURL_DOMAIN;
	}

}
