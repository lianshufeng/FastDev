package com.fast.dev.core.util.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

public class ImageScaleUtil {

	/**
	 * 缩放图片
	 * 
	 * @param sourceFile
	 *            原路径
	 * @param targetFile
	 *            输出路径
	 * @param rate
	 *            率
	 * @return
	 */
	public static boolean scale(File sourceImageFile, File outputImageFile, double rate) {
		try {
			BufferedImage sourceImage = ImageIO.read(sourceImageFile);
			// 缩放率
			int width = (int) (sourceImage.getWidth() * rate);
			int height = (int) (sourceImage.getHeight() * rate);
			scale(sourceImageFile, outputImageFile, width, height);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 缩放图片
	 * 
	 * @param sourceImageFile
	 * @param outputImageFile
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean scale(File sourceImageFile, File outputImageFile, int width, int height) {
		try {
			BufferedImage sourceImage = ImageIO.read(sourceImageFile);
			// 缩放率
			// 创建原图缩放比例的图片
			Image image = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage targetImage = new BufferedImage(width, height, Image.SCALE_SMOOTH);
			Graphics2D graphics2d = targetImage.createGraphics();
			BufferedImage bufferedImage = graphics2d.getDeviceConfiguration()
					.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
			graphics2d.dispose();
			Graphics2D newGraphics2D = bufferedImage.createGraphics();
			// 绘制缩小后的图
			newGraphics2D.drawImage(image, 0, 0, null);
			newGraphics2D.dispose();

			// 保存图片
			String imageFormat = FilenameUtils.getExtension(outputImageFile.getName());
			ImageIO.write(bufferedImage, imageFormat, outputImageFile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
