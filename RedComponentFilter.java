package part2;

import java.awt.image.BufferedImage;

/**
 * Alters an image so that only the red component of the RGB value is shown.
 * 
 * @author Andy Ford u0537938
 * 
 * 4/10/2014
 *
 */
public class RedComponentFilter implements ImageFilter {
	
	/**
	 * Applies a filter to BufferedImage i.
	 */
	public BufferedImage filter(BufferedImage i) {
		
		BufferedImage result = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);

		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {

				int pixel = i.getRGB(x, y);
				
				// Get the red component of the pixel.
				int redAmount = (pixel >> 16) & 0xff;
				
				// Compose the new pixel, setting the green and blue components to zero.
				int newPixel = (redAmount << 16 ) | 0 | 0;
				
				// Set the pixel of the new image.
				result.setRGB(x, y, newPixel);
			}
		
		return result;
	}
}
