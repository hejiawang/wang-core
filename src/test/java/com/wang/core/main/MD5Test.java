package com.wang.core.main;

import org.junit.Test;

import com.wang.core.util.MD5;

/**
 * MD5测试
 * 
 * @author HeJiawang
 * @date   2016.09.21
 */
public class MD5Test {

	@Test
	public void getMD5String4Test() {
		String strMD5 = MD5.getInstrance().getMD5String4("123456");
		System.out.println(strMD5);
	}
}
