package part2;

import java.awt.image.BufferedImage;

/**
 * An interface that ensures all classes representing an image filter
 * implement the filter method as specified below.
 * 
 * @author Erin Parker and Andy Ford u0537938
 * 
 * 4/10/2014
 */
public interface ImageFilter {
	
	public BufferedImage filter(BufferedImage i);

}
