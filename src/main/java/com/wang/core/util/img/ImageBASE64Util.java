package com.wang.core.util.img;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片与BASE64加密字符串之间的转换
 * @author HeJiawang
 * @date 2016.11.03
 */
@SuppressWarnings("restriction")
public class ImageBASE64Util {
	/**
	 * log
	 */
	private static final Logger logger = LoggerFactory.getLogger(ImageBASE64Util.class);
	
	private static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	private static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	/**
	 * 将图片转换为BASE64加密字符串.
	 * @param imagePath 图片路径.
	 * @param format 图片格式.
	 * @return
	 */
	public String convertImageToByte(String imagePath, String format) {
		File file = new File(imagePath);
		BufferedImage bi = null;
		ByteArrayOutputStream baos = null;
		String result = null;
		try {
			bi = ImageIO.read(file);
			baos = new ByteArrayOutputStream();
			ImageIO.write(bi, format == null ? "jpg" : format, baos);
			byte[] bytes = baos.toByteArray();
			result = encoder.encodeBuffer(bytes).trim();
			logger.debug("将图片转换为BASE64加密字符串成功！");
		} catch (IOException e) {
			logger.error("将图片转换为 BASE64加密字符串失败: " + e);
		} finally {
			try {
				if(baos != null) {
					baos.close();
					baos = null;
				}
			} catch (Exception e) {
				logger.error("关闭文件流发生异常: " + e);
			}
		}
		return result;
	}
	
	/**
	 * 将图片流转换为BASE64加密字符串.
	 * @param imageInputStream 图片流
	 * @param format 图片格式.
	 * @return
	 */
	public String convertImageStreamToByte(InputStream imageInputStream, String format) {
		BufferedImage bi = null;
		ByteArrayOutputStream baos = null;
		String result = null;
		try {
			bi = ImageIO.read(imageInputStream);
			baos = new ByteArrayOutputStream();
			ImageIO.write(bi, format == null ? "jpg" : format, baos);
			byte[] bytes = baos.toByteArray();
			result = encoder.encodeBuffer(bytes).trim();
			logger.debug("将图片流转换为BASE64加密字符串成功！");
		} catch (IOException e) {
			logger.error("将图片流转换为 BASE64加密字符串失败: " + e);
		} finally {
			try {
				if(baos != null) {
					baos.close();
					baos = null;
				}
			} catch (Exception e) {
				logger.error("关闭文件流发生异常: " + e);
			}
		}
		return result;
	}
	
	/**
	 * 将BASE64加密字符串转换为图片.
	 * @param base64String BASE64加密字符串
	 * @param imagePath 图片生成路径.
	 * @param format 图片格式.
	 */
	public void convertByteToImage(String base64String, String imagePath, String format) {
		byte[] bytes = null;
		ByteArrayInputStream bais = null;
		BufferedImage bi = null;
		File file = null;
		try {
			bytes = decoder.decodeBuffer(base64String);
			bais = new ByteArrayInputStream(bytes);
			bi = ImageIO.read(bais);
			file = new File(imagePath);
			ImageIO.write(bi, format == null ? "jpg" : format, file);
			logger.debug("将BASE64加密字符串转换为图片成功！");
		} catch (IOException e) {
			logger.error("将BASE64加密字符串转换为图片流失败: " + e);
		} finally {
			try {
				if(bais != null) {
					bais.close();
					bais = null;
				}
			} catch (Exception e) {
				logger.error("关闭文件流发生异常: " + e);
			}
		}
	}
}
