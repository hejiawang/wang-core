package com.wang.core.util.img.test;

import com.wang.core.util.img.ImageBASE64Util;

/**
 * 本事例主要讲了如下几点:
 * 1:将图片转换为BASE64加密字符串.
 * 2:将图片流转换为BASE64加密字符串.
 * 3:将BASE64加密字符串转换为图片.
 * 4:在jsp文件中以引用的方式和BASE64加密字符串方式展示图片.
 * @author HeJiawang
 * @date 2016.11.03
 */
public class ImageUtilTest {

	public static void main(String[] args) {
		ImageBASE64Util imageUtil = new ImageBASE64Util();
		
		String base64String = imageUtil.convertImageToByte("F:\\me.jpg", "jpg");
		System.out.println(base64String);
		imageUtil.convertByteToImage(base64String, "F:\\me2.jpg", "jpg");
				
		/*InputStream is = ImageUtilTest.class.getResourceAsStream("test.png");
		String base64String2 = imageUtil.convertImageStreamToByte(is, "png");
		System.out.println(base64String2);
		imageUtil.convertByteToImage(base64String2, "D:\\test3.png", "png");*/
	}
}
