package part2;

import java.awt.image.BufferedImage;

/**
 * Rotates an image 90 degrees counter-clockwise.
 * 
 * @author Andy Ford u0537938
 * 
 * 4/10/2014
 *
 */
public class CounterClockwiseRotation implements ImageFilter{
	
	/**
	 * Applies a filter to BufferedImage i.
	 */
	public BufferedImage filter(BufferedImage i) {
		
		BufferedImage result = new BufferedImage(i.getHeight(), i.getWidth(), BufferedImage.TYPE_INT_RGB);
		
		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {

				int pixel = i.getRGB(x, y);
							
				// Set the pixel of the new image.
				result.setRGB(y, result.getHeight() - 1 - x, pixel);
			}

		return result;
	}

}

