package part2;

import java.awt.image.BufferedImage;

/**
 * Increases or decreases the brightness of an image by adding X to each color component.  
 * X > 0 increases brightness.
 * X < 0 decreases brightness.
 * 
 * @author Erin Parker and Andy Ford u0537938
 * 
 * 4/17/2014
 */
public class BiasFilter implements ImageFilter {
	
	private int n;
	
	/**
	 * Constructs a new BiasFilter, using input _n as the value by which the colors will be altered.
	 * 
	 * @param _n - bias value
	 */
	public BiasFilter(int _n){
		n = _n;
	}
	
	/**
	 * Applies a filter to BufferedImage i.
	 */
	public BufferedImage filter(BufferedImage img) {
		
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		for(int y = 0; y < img.getHeight(); y++)
			for(int x = 0; x < img.getWidth(); x++) {

				int pixel = img.getRGB(x, y);
				int redAmount = (pixel >> 16) & 0xff;
				int greenAmount = (pixel >> 8) & 0xff;
				int blueAmount = (pixel >> 0) & 0xff;
				
				redAmount += n;
				if(redAmount < 0)
					redAmount = 0;
				else if(redAmount > 255)
					redAmount = 255;
				
				greenAmount += n;
				if(greenAmount < 0)
					greenAmount = 0;
				else if(greenAmount > 255)
					greenAmount = 255;
			
				blueAmount += n;
				if(blueAmount < 0)
					blueAmount = 0;
				else if(blueAmount > 255)
					blueAmount = 255;
				
				int newPixel = (redAmount << 16 ) | (greenAmount << 8) | blueAmount;
				result.setRGB(x, y, newPixel);
			}
		
		return result;
	}
}
