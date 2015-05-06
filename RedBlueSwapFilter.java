package part2;

import java.awt.image.BufferedImage;

/**
 * Swaps the red and blue components of the RGB value for each pixel in an image.
 * 
 * @author Erin Parker and Andy Ford u0537938
 * 
 * 4/10/2014
 *
 */
public class RedBlueSwapFilter implements ImageFilter {
	
	/**
	 * Applies a filter to BufferedImage i.
	 */
	public BufferedImage filter(BufferedImage i) {
		
		BufferedImage result = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);

		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {

				int pixel = i.getRGB(x, y);
				
				// Decompose the pixel in the amounts of red, green, and blue.
				int redAmount = (pixel >> 16) & 0xff;
				int greenAmount = (pixel >> 8) & 0xff;
				int blueAmount = (pixel >> 0) & 0xff;
				
				// Compose the new pixel, switching the red and blue components.
				int newPixel = (blueAmount << 16 ) | (greenAmount << 8) | redAmount;
				
				// Set the pixel of the new image.
				result.setRGB(x, y, newPixel);
			}
		
		return result;
	}
}
