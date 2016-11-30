package com.wang.core.util.img;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * java给图片添加水印文字
 * @author HeJiawang
 * @Date 2016.11.26
 */
public class WaterMarkUtils {
	/**
	 * 图片添加水印
	 * 
	 * @param srcImgPath
	 *            需要添加水印的图片的路径
	 * @param outImgPath
	 *            添加水印后图片输出路径
	 * @param markContentColor
	 *            水印文字的颜色
	 * @param waterMarkContent
	 *            水印的文字
	 */
	public static void mark(String srcImgPath, String outImgPath,
			Color markContentColor, String waterMarkContent) {
		try {
			// 读取原图片信息
			File srcImgFile = new File(srcImgPath);
			Image srcImg = ImageIO.read(srcImgFile);
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			// Font font = new Font("Courier New", Font.PLAIN, 12);
			Font font = new Font("微软雅黑", Font.PLAIN, 20);
			g.setColor(markContentColor); // 根据图片的背景设置水印颜色
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));//透明度
			g.setFont(font);
			int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;
			int y = srcImgHeight - 3;
			// int x = (srcImgWidth - getWatermarkLength(watermarkStr, g)) / 2;
			// int y = srcImgHeight / 2;
			g.drawString(waterMarkContent, x, y);
			g.dispose();
			// 输出图片
			FileOutputStream outImgStream = new FileOutputStream(outImgPath);
			ImageIO.write(bufImg, "jpg", outImgStream);
			outImgStream.flush();
			outImgStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取水印文字总长度
	 * 
	 * @param waterMarkContent
	 *            水印的文字
	 * @param g
	 * @return 水印文字总长度
	 */
	private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(
				waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}
	
    /**
     * 添加图片水印
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param waterImg 水印图片路径，如：C://myPictrue//logo.png
     * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public final static void pressImage(String targetImg, String waterImg) {
            try {
                File file = new File(targetImg);
                Image image = ImageIO.read(file);
                int width = image.getWidth(null);
                int height = image.getHeight(null);
                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = bufferedImage.createGraphics();
                g.drawImage(image, 0, 0, width, height, null);
             
                Image waterImage = ImageIO.read(new File(waterImg));    // 水印文件
                int width_1 = waterImage.getWidth(null);
                int height_1 = waterImage.getHeight(null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
                 
                int widthDiff = width - width_1;
                int heightDiff = height - height_1;
                
                g.drawImage(waterImage, widthDiff, heightDiff, width_1, height_1, null); // 水印文件结束
                g.dispose();
                ImageIO.write(bufferedImage, "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

	public static void main(String[] args) {
		// 原图位置, 输出图片位置, 水印文字颜色, 水印文字
		WaterMarkUtils.mark("E:/1.jpg", "E:/1.jpg", Color.red, "水印效果测试");
		WaterMarkUtils.pressImage("E:/2.jpg", "E:/1.jpg");
	}
}
