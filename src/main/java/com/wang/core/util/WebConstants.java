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
	
	/**
	 * 文件上传路径
	 */
	public static String  UPLOADURL;
	
	/**
	 * 
	 */
	public static String  Config_ACCESS_KEY;
	
	/**
	 * 
	 */
	public static String  Config_SECRET_KEY;
	
	/**
	 * 
	 */
	public static String  bucketName;
	
	/**
	 * 权限——组织的根节点
	 */
	public static Integer OrgRootID;
	
	/**
	 * 权限管理系统超级管理员ID、超级管理员角色ID、值为1
	 */
	public static Integer permissionAdminID;
	
	/**
	 * 权限管理系统
	 */
	public static String NAMESPACE_PERMISSION_WEB;
	
	/**
	 * 权限管理系统session
	 */
	public static String NAMESPACE_PERMISSION_WEB_SESSION;
	
	/**
	 * so love
	 */
	public static String NAMESPACE_SOLOVE_WEB;
	
	/**
	 * so love session
	 */
	public static String NAMESPACE_SOLOVE_WEB_SESSION;
	
	static {
		ResourceBundle config = ResourceBundle.getBundle("core/web-constants");
		
		UPLOADURL=config.getString("UPLOADURL");
		Config_ACCESS_KEY=config.getString("Config_ACCESS_KEY");
		Config_SECRET_KEY=config.getString("Config_SECRET_KEY");
		bucketName=config.getString("bucketName");
		OrgRootID = Integer.parseInt(config.getString("permission.OrgRootID"));
		permissionAdminID = Integer.parseInt(config.getString("permission.adminID"));
		
		NAMESPACE_PERMISSION_WEB = config.getString("permission.web");
		NAMESPACE_PERMISSION_WEB_SESSION = NAMESPACE_PERMISSION_WEB + ":session:";
		
		NAMESPACE_SOLOVE_WEB = config.getString("soLove.web");
		NAMESPACE_SOLOVE_WEB_SESSION = NAMESPACE_SOLOVE_WEB + ":session:";
	} 
	
}
