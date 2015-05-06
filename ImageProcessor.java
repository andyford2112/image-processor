package part2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Creates an image processing application that allows the user to open an image file, apply one 
 * of various filters to the image, and save the image file.
 * 
 * @author Andy Ford u0537938
 * 
 * 4/17/2014
 *
 */
public class ImageProcessor extends JFrame implements ActionListener, ChangeListener {

	private static JMenu fileMenu;
	private static JMenu filterMenu;
	private static JMenuItem openFile;
	private static JMenuItem saveFile;
	private static JMenuItem redGreen;
	private static JMenuItem redBlue;
	private static JMenuItem greenBlue;
	private static JMenuItem blackAndWhite;
	private static JMenuItem clockwise;
	private static JMenuItem counterClockwise;
	private static JMenuItem gain;
	private static JMenuItem bias;
	private static JMenuItem blur;
	private static JMenuItem redComponent;
	private static JMenuItem greenComponent;
	private static JMenuItem blueComponent;
	private static BufferedImage image;
	private static BufferedImage newImage;
	private static JTabbedPane tabbed;
	private static JPanel content;
	private static JSlider biasSlider;
	private int biasVal;
	private static JSlider gainSlider;
	private double gainVal = 1.25;
	
	public static void main(String[] args) {
		ImageProcessor processor = new ImageProcessor();
	}
	
	/**
	 * Constructs an ImageProcessor by setting up all the GUI components.
	 */
	public ImageProcessor(){
		// Create the File menu with open and save options.
		fileMenu = new JMenu("File");

		openFile = new JMenuItem("Open a new image file");
		openFile.addActionListener(this);
		fileMenu.add(openFile);

		saveFile = new JMenuItem("Save the filtered image");
		saveFile.addActionListener(this);
		fileMenu.add(saveFile);
		saveFile.setEnabled(false);

		// Create the Filter menu with each of the filters.
		filterMenu = new JMenu("Filter");

		
		redGreen = new JMenuItem("Red-Green Swap");
		addMenuItem(redGreen, "Swaps the red and green components of each pixel");
		
		redBlue = new JMenuItem("Red-Blue Swap");
		addMenuItem(redBlue, "Swaps the red and blue components of each pixel");
		
		greenBlue = new JMenuItem("Green-Blue Swap");
		addMenuItem(greenBlue, "Swaps the green and blue components of each pixel");
		
		blackAndWhite = new JMenuItem("Black and White");
		addMenuItem(blackAndWhite, "Converts the image to black and white");
		
		clockwise = new JMenuItem("Clockwise Rotation");
		addMenuItem(clockwise, "Rotates the image 90 degrees clockwise");
		
		counterClockwise = new JMenuItem("Counter-clockwise Rotation");
		addMenuItem(counterClockwise, "Rotates the image 90 degrees counter-clockwise");
		
		gain = new JMenuItem("Gain");
		addMenuItem(gain, "Alters the gain of the image");
		
		bias = new JMenuItem("Bias");
		addMenuItem(bias, "Alters the bias of the image");
		
		blur = new JMenuItem("Blur");
		addMenuItem(blur, "Blurs the image");
		
		redComponent = new JMenuItem("Red Component");
		addMenuItem(redComponent, "Shows only the red component of the RGB value of the image");
		
		greenComponent = new JMenuItem("Green Component");
		addMenuItem(greenComponent, "Shows only the green component of the RGB value of the image");
		
		blueComponent = new JMenuItem("Blue Component");
		addMenuItem(blueComponent, "Shows only the blue component of the RGB value of the image");
		
		// Add the File and Filter menus to a menu bar.
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(filterMenu);
		filterMenu.setEnabled(false);

		// Create a JTabbedPane.
		tabbed = new JTabbedPane();

		// Create a content panel.
		content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(tabbed, BorderLayout.CENTER);
		
		// Create the bias and gain sliders.
		biasSlider = new JSlider(-200, 200);
		biasSlider.addChangeListener(this);
		biasSlider.setMajorTickSpacing(100);
		biasSlider.setMinorTickSpacing(50);
		biasSlider.setPaintTicks(true);
		biasSlider.setPaintLabels(true);
		biasSlider.setToolTipText("Bias: positive = brighter, negative = darker.");
		
		gainSlider = new JSlider(2, 8);
		gainSlider.addChangeListener(this);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(2, new JLabel("0.5"));
		labelTable.put(3, new JLabel("0.75"));
		// skip gain factor 1.0, since that produces an image the same as the original
		labelTable.put(5, new JLabel("1.25"));
		labelTable.put(6, new JLabel("1.5"));
		labelTable.put(7, new JLabel("1.75"));
		labelTable.put(8, new JLabel("2.0"));
		gainSlider.setLabelTable(labelTable);
		gainSlider.setPaintLabels(true);
		gainSlider.setToolTipText("Gain: value > 1 = brighter, value < 1 = darker.");
		
		// Set up the frame.
		JFrame frame = new JFrame();
		frame.add(menuBar);
		frame.setContentPane(content);
		frame.setJMenuBar(menuBar);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setVisible(true);
		frame.pack();

	}
	
	/**
	 * Handles clicks for the open file, save file, and filter menu items.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(openFile)){
			// Open a file.
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("jpg, jpeg, png, bmp, or gif", "jpg", "jpeg", "png", "bmp", "gif");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(fileFilter);
			fileChooser.showOpenDialog(null);
			File file = fileChooser.getSelectedFile();

			image = null;

			try{
				image = ImageIO.read(file);
			}
			catch(IOException exception){
				JOptionPane.showMessageDialog(null, "The file you selected cannot be opened.");
			}
			tabbed.removeAll();
			tabbed.add(new ImagePanel(image), "Original Image");
			filterMenu.setEnabled(true);
		}
		else if(e.getSource().equals(saveFile)){
			// Save the file.
			FileNameExtensionFilter fileFilter2 = new FileNameExtensionFilter("jpg", "jpg");
			JFileChooser fileChooser2 = new JFileChooser();
			fileChooser2.setFileFilter(fileFilter2);
			fileChooser2.setSelectedFile(new File("new_image.jpg"));
			fileChooser2.showSaveDialog(null);
			File file2 = fileChooser2.getSelectedFile();

			try{
				ImageIO.write(ImageProcessor.newImage, "jpg", file2);
			}
			catch(IOException exception){
				JOptionPane.showMessageDialog(null, "Your file could not be saved.");
			}
		}
		else{		
			// Process image using the selected filter.	
			ImageFilter f = null;
			
			biasSlider.setEnabled(false);
			biasSlider.setVisible(false);
			gainSlider.setEnabled(false);
			gainSlider.setVisible(false);
			
			if(e.getSource().equals(redGreen))
				f = new RedGreenSwapFilter();
			else if(e.getSource().equals(redBlue))
				f = new RedBlueSwapFilter();
			else if(e.getSource().equals(greenBlue))
				f = new GreenBlueSwapFilter();
			else if(e.getSource().equals(blackAndWhite))
				f = new BlackAndWhiteFilter();
			else if(e.getSource().equals(clockwise))
				f = new ClockwiseRotation();
			else if(e.getSource().equals(counterClockwise))
				f = new CounterClockwiseRotation();
			else if(e.getSource().equals(gain)){
				f = new GainFilter(gainVal);
				content.add(gainSlider, BorderLayout.SOUTH);
				gainSlider.setVisible(true);
				gainSlider.setEnabled(true);
			}
			else if(e.getSource().equals(bias)){
				f = new BiasFilter(biasVal);
				content.add(biasSlider, BorderLayout.SOUTH);
				biasSlider.setVisible(true);
				biasSlider.setEnabled(true);
			}
			else if(e.getSource().equals(blur))
				f = new BlurFilter();
			else if(e.getSource().equals(redComponent))
				f = new RedComponentFilter();
			else if(e.getSource().equals(greenComponent))
				f = new GreenComponentFilter();
			else if(e.getSource().equals(blueComponent))
				f = new BlueComponentFilter();
			
			newImage = f.filter(image);
			
			if(tabbed.getTabCount() == 1)
				tabbed.add(new ImagePanel(newImage), "Filtered Image");
			if(tabbed.getTabCount() == 2)
				tabbed.setComponentAt(1, new ImagePanel(newImage));
			
			saveFile.setEnabled(true);
		}
	}
	
	/**
	 * Handles changes to the bias and gain sliders.
	 */
	public void stateChanged(ChangeEvent e) {
		if(e.getSource().equals(biasSlider)){
			if(!biasSlider.getValueIsAdjusting())
				biasVal = biasSlider.getValue();
			ImageFilter f = new BiasFilter(biasVal);
			newImage = f.filter(image);
		}
		else{
			if(!gainSlider.getValueIsAdjusting())
				gainVal = gainSlider.getValue() * 0.25;
			ImageFilter f = new GainFilter(gainVal);
			newImage = f.filter(image);
		}
		
		if(tabbed.getTabCount() == 1)
			tabbed.add(new ImagePanel(newImage), "Filtered Image");
		if(tabbed.getTabCount() == 2)
			tabbed.setComponentAt(1, new ImagePanel(newImage));
		
	}
	
	/**
	 * Adds a menu item to the filterMenu and sets the tool tip text.
	 * @param item - JMenuItem to be add to filterMenu
	 * @param toolTip - tool tip to be displayed for item
	 */
	public void addMenuItem(JMenuItem item, String toolTip){
		item.addActionListener(this);
		item.setToolTipText(toolTip);
		filterMenu.add(item);
	}
}
