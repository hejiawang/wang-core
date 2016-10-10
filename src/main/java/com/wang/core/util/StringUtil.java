package com.wang.core.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * 
 * string 工具类
 * 
 * @author HeJiawang
 * @date 2016.10.10
 *
 */
public class StringUtil implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	 private static final Logger 
	 logger = Logger.getLogger(StringUtil.class);
	private static final long serialVersionUID = 4081994931030127193L;

	public static final int HIGHEST_SPECIAL = '>';
	public static char[][] specialCharactersRepresentation = new char[HIGHEST_SPECIAL + 1][];
	static {
		specialCharactersRepresentation['&'] = "&amp;".toCharArray();
		specialCharactersRepresentation['<'] = "&lt;".toCharArray();
		specialCharactersRepresentation['>'] = "&gt;".toCharArray();
		specialCharactersRepresentation['"'] = "&#034;".toCharArray();
		specialCharactersRepresentation['\''] = "&#039;".toCharArray();
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(Integer val) {
		if(val==null){
			return false;
		}else{
			String str=String.valueOf(val);
			if (str == null || str.trim().length() == 0) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	
	public static boolean isNotEmpty(Double val) {
		if(val==null){
			return false;
		}else{
			String str=String.valueOf(val);
			if (str == null || str.trim().length() == 0) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public static boolean isNotEmpty(Long val) {
		if(val==null){
			return false;
		}else{
			String str=String.valueOf(val);
			if (str == null || str.trim().length() == 0) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	/**
	 * 
	 * 描述
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param value
	 * @param delimeters
	 * @return
	 */
	public static String[] toArray(String value, String delimeters) {
		return (String[]) toList(value, delimeters).toArray();
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param value
	 * @param delimeters
	 * @return
	 */
	public static List<Object> toList(String value, String delimeters) {
		List<Object> list = new ArrayList<Object>();
		if (isEmpty(value)) {
			return list;
		}
		StringTokenizer st = new StringTokenizer(value, delimeters, false);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param value
	 * @param separator
	 * @return
	 */
	public static List<Object> toListByStr(String value, String separator) {
		List<Object> list = new ArrayList<Object>();
		String token;
		int index0 = 0;
		int index1 = value.indexOf(separator, 0);
		if (index1 == -1) {
			list.add(value);
		} else {
			while (true) {
				token = value.substring(index0, index1);
				if (StringUtil.isNotEmpty(token)) {
					list.add(token);
				}
				index0 = index1 + separator.length();
				index1 = value.indexOf(separator, index0);
				if (index1 == -1) {
					token = value.substring(index0);
					if (StringUtil.isNotEmpty(token)) {
						list.add(token);
					}
					break;
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param s
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String convertToQuestionMark(List s) {
		if(s.size()==0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.size(); index++) {
			buf.append("?,");
		}
		int end = buf.lastIndexOf(",");
		return buf.substring(0, end);
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param s
	 * @return
	 */
	public static String convertToQuestionMark(Object[] s) {
		if(s.length==0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.length; index++) {
			buf.append("?,");
		}
		int end = buf.lastIndexOf(",");
		return buf.substring(0, end);
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param s
	 * @return
	 */
	public static String convertToStr(String[] s) {
		if(s.length==0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.length; index++) {
			buf.append("'").append(s[index]).append("'").append(",");
		}
		int end = buf.lastIndexOf(",");
		return buf.substring(0, end);
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param s
	 * @return
	 */
	public static String convertToStr(Object[] s) {
		if(s.length==0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.length; index++) {
			buf.append("'").append(s[index]).append("'").append(",");
		}
		int end = buf.lastIndexOf(",");
		return buf.substring(0, end);
	}
	
	/**
	 * 
	 * 描述
	 * 
	 * @param s
	 * @return
	 */
	public static String convertToStr(Long[] s) {
		if(s.length==0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.length; index++) {
			buf.append(s[index]).append(",");
		}
		int end = buf.lastIndexOf(",");
		return buf.substring(0, end);
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param arr
	 * @param item
	 * @return
	 */
	public static boolean exist(String[] arr, String item) {
		if (arr != null) {
			for (int index = 0; index < arr.length; index++) {
				if (arr[index].equals(item)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param buffer
	 * @return
	 */
	public static String escapeXml(String buffer) {
		int start = 0;
		int length = buffer.length();
		char[] arrayBuffer = buffer.toCharArray();
		StringBuffer escapedBuffer = null;

		for (int i = 0; i < length; i++) {
			char c = arrayBuffer[i];
			if (c <= HIGHEST_SPECIAL) {
				char[] escaped = specialCharactersRepresentation[c];
				if (escaped != null) {
					// create StringBuffer to hold escaped xml string
					if (start == 0) {
						escapedBuffer = new StringBuffer(length + 5);
					}
					// add unescaped portion
					if (start < i) {
						escapedBuffer.append(arrayBuffer, start, i - start);
					}
					start = i + 1;
					// add escaped xml
					escapedBuffer.append(escaped);
				}
			}
		}
		// no xml escaping was necessary
		if (start == 0) {
			return buffer;
		}
		// add rest of unescaped portion
		if (start < length) {
			escapedBuffer.append(arrayBuffer, start, length - start);
		}
		return escapedBuffer.toString();
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param str
	 * @return
	 */
	public static String trimTo20Char(String str) {
		if (str.length() > 20) {
			return str.substring(0, 20) + "...";
		} else {
			return str;
		}
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeVBS(String str) {
		if (str == null)
			return "";

		StringBuffer sb = new StringBuffer();
		char ch;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\"') {
				sb.append("\"&chr(34)&\"");
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceMatchAsSQL(String str) {
		if (isEmpty(str)) {
			return null;
		}
		boolean isOnlyWildcard = true;
		char[] chs = str.toCharArray();
		for (char ch : chs) {
			if (ch != '?' && ch != '*') {
				isOnlyWildcard = false;
				break;
			}
		}
		if (isOnlyWildcard) {
			return null;
		}
		return escapeSQL(str).replaceAll("\\*", "%").replaceAll("\\?", "_");
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeSQL(String str) {
		boolean isWildcard = false;
		char[] chs = str.toCharArray();
		for (char ch : chs) {
			if (ch == '%' || ch == '_') {
				isWildcard = true;
				break;
			}
		}
		if (!isWildcard) {
			return str;
		}
		return str.replaceAll("%", "/%").replaceAll("_", "/_");
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param parameterSuffix
	 * @param parameterName
	 * @return
	 */
	public static String getParameter(Long parameterSuffix, String parameterName) {
		if (parameterSuffix == null || isEmpty(parameterName)) {
			return "";
		}
		return parameterName + "_" + String.valueOf(parameterSuffix);
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param uri
	 * @return
	 */
	public static String decodeURI(String uri) {
		try {
			return java.net.URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isLong(String value) {
		if (StringUtil.isEmpty(value)) {
			return false;
		}
		if (value.matches("[1-9]{1}[0-9]*")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param newexp
	 * @param oldexp
	 * @param content
	 * @return
	 */
	public static String exchangeExp(String newexp, String oldexp,
			String content) {
		if (StringUtil.isEmpty(content)) {
			return content;
		}
		if (newexp.equals(oldexp)) {
			return content;
		}
		String result = content;
		int o1 = result.indexOf(oldexp);
		int index = 0;
		while (o1 != -1) {
			result = exchangeExp1(newexp, oldexp, result, index);
			index = o1 + newexp.length();
			o1 = result.indexOf(oldexp, index);
		}
		return result;
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param newexp
	 * @param oldexp
	 * @param content
	 * @param index
	 * @return
	 */
	private static String exchangeExp1(String newexp, String oldexp,
			String content, int index) {
		int o1 = content.indexOf(oldexp, index);
		if (o1 == -1) {
			return content;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(content.substring(0, o1));
		sb.append(newexp);
		sb.append(content.substring(o1 + oldexp.length(), content.length()));
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals("") || obj.equals("0"))) {
			return true;
		} else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
}
