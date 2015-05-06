package part2;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Blurs a selected image by averaging the color components of each pixel and its neighbors.
 * 
 * @author Andy Ford u0537938
 * 
 * 4/10/2014
 *
 */
public class BlurFilter implements ImageFilter {
	
	/**
	 * Applies a filter to BufferedImage i.
	 */
	public BufferedImage filter(BufferedImage i) {
		
		BufferedImage result = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);

		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {
				
				int redSum = 0;
				int greenSum = 0;
				int blueSum = 0;
				
				// Create an array of the neighbor pixels.
				ArrayList<Integer> neighbors = new ArrayList<Integer>();
				
				if(x == 0){
					if(y == 0){
						neighbors.add(i.getRGB(x,  y));
						neighbors.add(i.getRGB(x, y + 1));
						neighbors.add(i.getRGB(x + 1, y));
						neighbors.add(i.getRGB(x + 1, y + 1));
					}else if(y == (i.getHeight() - 1)){
						neighbors.add(i.getRGB(x, y - 1));
						neighbors.add(i.getRGB(x,  y));
						neighbors.add(i.getRGB(x + 1, y - 1));
						neighbors.add(i.getRGB(x + 1, y));
					}
					else{
						neighbors.add(i.getRGB(x, y - 1));
						neighbors.add(i.getRGB(x,  y));
						neighbors.add(i.getRGB(x, y + 1));
						neighbors.add(i.getRGB(x + 1, y - 1));
						neighbors.add(i.getRGB(x + 1, y));
						neighbors.add(i.getRGB(x + 1, y + 1));
					}
				}else if(x == (i.getWidth() - 1)){
					if(y == 0){
						neighbors.add(i.getRGB(x,  y));
						neighbors.add(i.getRGB(x, y + 1));
						neighbors.add(i.getRGB(x - 1, y));
						neighbors.add(i.getRGB(x - 1, y + 1));
					}else if(y == (i.getHeight() - 1)){
						neighbors.add(i.getRGB(x, y - 1));
						neighbors.add(i.getRGB(x,  y));
						neighbors.add(i.getRGB(x - 1, y - 1));
						neighbors.add(i.getRGB(x - 1, y));
					}
					else{
						neighbors.add(i.getRGB(x, y - 1));
						neighbors.add(i.getRGB(x,  y));
						neighbors.add(i.getRGB(x, y + 1));
						neighbors.add(i.getRGB(x - 1, y - 1));
						neighbors.add(i.getRGB(x - 1, y));
						neighbors.add(i.getRGB(x - 1, y + 1));
					}
				}else if(y == 0){
					neighbors.add(i.getRGB(x - 1, y));
					neighbors.add(i.getRGB(x - 1, y + 1));
					neighbors.add(i.getRGB(x,  y));
					neighbors.add(i.getRGB(x, y + 1));
					neighbors.add(i.getRGB(x + 1, y));
					neighbors.add(i.getRGB(x + 1, y + 1));
				}else if(y == (i.getHeight() - 1)){
					neighbors.add(i.getRGB(x - 1, y));
					neighbors.add(i.getRGB(x - 1, y - 1));
					neighbors.add(i.getRGB(x,  y));
					neighbors.add(i.getRGB(x, y - 1));
					neighbors.add(i.getRGB(x + 1, y));
					neighbors.add(i.getRGB(x + 1, y - 1));
				}
				else{
					neighbors.add(i.getRGB(x - 1, y - 1));
					neighbors.add(i.getRGB(x - 1, y));
					neighbors.add(i.getRGB(x - 1, y + 1));
					neighbors.add(i.getRGB(x, y - 1));
					neighbors.add(i.getRGB(x,  y));
					neighbors.add(i.getRGB(x, y + 1));
					neighbors.add(i.getRGB(x + 1, y - 1));
					neighbors.add(i.getRGB(x + 1, y));
					neighbors.add(i.getRGB(x + 1, y + 1));
				}
				
				for(int n : neighbors){
					// Decompose the pixel in the amounts of red, green, and blue.
					int redAmount = (n >> 16) & 0xff;
					int greenAmount = (n >> 8) & 0xff;
					int blueAmount = (n >> 0) & 0xff;
				
					// Sum the amounts of each color.
					redSum += redAmount;
					greenSum += greenAmount;
					blueSum += blueAmount;
				}
				
				// Average each color.
				int redAverage = redSum / 9;
				int greenAverage = greenSum / 9;
				int blueAverage = blueSum / 9;
				
				// Compose the new pixel, switching the green and blue components.
				int newPixel = (redAverage << 16 ) | (greenAverage << 8) | blueAverage;
				
				// Set the pixel of the new image.
				result.setRGB(x, y, newPixel);
			}
		
		return result;
	}
	
}
