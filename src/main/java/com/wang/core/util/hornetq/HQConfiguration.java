package com.wang.core.util.hornetq;

import java.util.ResourceBundle;

/**
 * 访问配置文件 - 单例
 * @author HeJiawang
 * @date   2016.09.20
 */

public class HQConfiguration {
	
	private static ResourceBundle rb    			= null;
	private volatile static HQConfiguration instance	= null;
	
	private HQConfiguration(String configFile) {
		rb = ResourceBundle.getBundle(configFile);
	}
	
	public static HQConfiguration getInstance(String configFile) {
		if(instance == null) {
			synchronized(HQConfiguration.class) {
				if(instance == null) {
					instance = new HQConfiguration(configFile);
				}
			}
		}
		return instance;
	}
	
	public String getValue(String key) {
		return (rb.getString(key));
	}
}
