package com.wang.core.util;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * web常量类
 * 
 * @author HeJiawang
 * @date   2016.09.20
 */
public class WebConstants implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = -3329954815267942671L;
	
	public static String  UPLOADURL;
	public static String  Config_ACCESS_KEY;
	public static String  Config_SECRET_KEY;
	public static String  bucketName;
	
	static {
		ResourceBundle config = ResourceBundle.getBundle("core/web-constants");
		
		UPLOADURL=config.getString("UPLOADURL");
		Config_ACCESS_KEY=config.getString("Config_ACCESS_KEY");
		Config_SECRET_KEY=config.getString("Config_SECRET_KEY");
		bucketName=config.getString("bucketName");
	}
	
}
