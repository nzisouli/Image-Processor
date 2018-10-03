package ce325.hw2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import javax.swing.JOptionPane;

public class ImageProcessing extends JFrame implements ActionListener{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	PPMImage ppmImage;
	YUVImage yuvImage;
	JLabel shownImage;
	JPanel biggerPanel;
	
	public ImageProcessing(){
		super("Image Processor");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		biggerPanel = new JPanel();
		biggerPanel.setLayout(new GridLayout(1,3));

		shownImage = new JLabel();
		biggerPanel.add(shownImage);

		add(biggerPanel, BorderLayout.CENTER);
		//Create menu 
		JMenuBar menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("1 File");
		JMenu actionsMenu = new JMenu("2 Actions");

		JMenu openMenu = new JMenu("1.1 Open");
		JMenu saveMenu = new JMenu("1.2 Save");
		//Options for opening an image of .ppm or .yuv extension
		JMenuItem ppm1Mi = new JMenuItem("1.1.1 PPM File");
		ppm1Mi.addActionListener(this);
		JMenuItem yuv1Mi = new JMenuItem("1.1.2 YUV File");
		yuv1Mi.addActionListener(this);
		openMenu.add(ppm1Mi);
		openMenu.add(yuv1Mi);
		// Options for saving an opened image as a .ppm or .yuv file
		JMenuItem ppm2Mi = new JMenuItem("1.2.1 PPM File");
		ppm2Mi.addActionListener(this);
		JMenuItem yuv2Mi = new JMenuItem("1.2.2 YUV File");
		yuv2Mi.addActionListener(this);
		saveMenu.add(ppm2Mi);
		saveMenu.add(yuv2Mi);

		fileMenu.add(openMenu);
		fileMenu.add(saveMenu);
		//Actions for opened image
		JMenuItem grayMi = new JMenuItem("2.1 Grayscale");
		grayMi.addActionListener(this);
		JMenuItem incMi = new JMenuItem("2.2 Increase Size");
		incMi.addActionListener(this);
		JMenuItem decMi = new JMenuItem("2.3 Decrease Size");
		decMi.addActionListener(this);
		JMenuItem rotMi = new JMenuItem("2.4 Rotate Clockwise");
		rotMi.addActionListener(this);
		JMenuItem equMi = new JMenuItem("2.5 Equalize Histogram");
		equMi.addActionListener(this);
		JMenu stackMenu = new JMenu("2.6 Stacking Algorithm");

		JMenuItem selDirMi = new JMenuItem("2.6.2 Select directory");
		selDirMi.addActionListener(this);
		stackMenu.add(selDirMi);

		actionsMenu.add(grayMi);
		actionsMenu.add(incMi);
		actionsMenu.add(decMi);
		actionsMenu.add(rotMi);
		actionsMenu.add(equMi);
		actionsMenu.add(stackMenu);

		menubar.add(fileMenu);
		menubar.add(actionsMenu);

		setJMenuBar(menubar);
	}
	//Refresh window with the current image
	private void changeShownImage(){
		BufferedImage img = new BufferedImage(ppmImage.getWidth()+1, ppmImage.getHeight()+1, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < ppmImage.getHeight(); i++){
			for(int j = 0; j < ppmImage.getWidth(); j++){
				int red = ppmImage.getImage()[i][j].getRed();
				int green = ppmImage.getImage()[i][j].getGreen();
				int blue = ppmImage.getImage()[i][j].getBlue();
				int mask = 0x00000000;
				red = ((red | mask) << 16) | mask;
				green = ((green | mask) << 8) | mask;
				blue = blue | mask;
				int rgb = (red | green | blue) | mask;
				img.setRGB(j, i, rgb);
			}
		}
		ImageIcon image = new ImageIcon(img);
		shownImage.setIcon(image);
	}

	public void actionPerformed(ActionEvent e) {
		String menuString = e.getActionCommand();
		// Graphic Input prompt for entering filenames/paths for the options taken
		if(menuString.equals("1.1.1 PPM File")){
			String filename = JOptionPane.showInputDialog("Please enter filename to open");
			while(true){
				File file = new File(filename);
				try{
					ppmImage = new PPMImage(file);
					break;
				}
				catch(UnsupportedFileFormatException ex){
					JOptionPane.showMessageDialog(this, "Not a .ppm file!");
					filename = JOptionPane.showInputDialog("Please enter filename to open");
				}
				catch(FileNotFoundException ex){
					JOptionPane.showMessageDialog(this, "File not found!");
					filename = JOptionPane.showInputDialog("Please enter filename to open");
				}
			}

			yuvImage = new YUVImage(ppmImage);

			changeShownImage();
		}
		else if(menuString.equals("1.1.2 YUV File")){
			String filename = JOptionPane.showInputDialog("Please enter filename to open");
			while(true){
				File file = new File(filename);
				try{
					yuvImage = new YUVImage(file);
					break;
				}
				catch(UnsupportedFileFormatException ex){
					JOptionPane.showMessageDialog(this, "Not a .yuv file!");
					filename = JOptionPane.showInputDialog("Please enter filename to open");
				}
				catch(FileNotFoundException ex){
					JOptionPane.showMessageDialog(this, "File not found!");
					filename = JOptionPane.showInputDialog("Please enter filename to open");
				}
			}

			ppmImage = new PPMImage(yuvImage);

			changeShownImage();
		}
		else if(menuString.equals("1.2.1 PPM File")){
			String filename = JOptionPane.showInputDialog("Please enter filename to save");
			File file = new File(filename);

			ppmImage.toFile(file);
		}
		else if(menuString.equals("1.2.2 YUV File")){
			String filename = JOptionPane.showInputDialog("Please enter filename to save");
			File file = new File(filename);

			yuvImage.toFile(file);
		}
		else if(menuString.equals("2.1 Grayscale")){
			ppmImage.grayscale();

			yuvImage = new YUVImage(ppmImage);

			changeShownImage();
		}
		else if(menuString.equals("2.2 Increase Size")){
			ppmImage.doublesize();

			yuvImage = new YUVImage(ppmImage);

			changeShownImage();
		}
		else if(menuString.equals("2.3 Decrease Size")){
			ppmImage.halfsize();

			yuvImage = new YUVImage(ppmImage);

			changeShownImage();
		}
		else if(menuString.equals("2.4 Rotate Clockwise")){
			ppmImage.rotateClockwise();

			yuvImage = new YUVImage(ppmImage);

			changeShownImage();
		}
		else if(menuString.equals("2.5 Equalize Histogram")){
			yuvImage.equalize();

			ppmImage = new PPMImage(yuvImage);

			changeShownImage();
		}
		else if(menuString.equals("2.6.2 Select directory")){
			PPMImageStacker imageStacker;
			while(true){
				String dirname = JOptionPane.showInputDialog("Please enter directory name");
				File dir = new File(dirname);

				imageStacker = new PPMImageStacker(dir);
				if (imageStacker.getSuccess() == 1){
					break;
				}
				JOptionPane.showMessageDialog(this, "Error in directory!");
			}
			ppmImage = imageStacker.getStackedImage();

			yuvImage = new YUVImage(ppmImage);

			changeShownImage();
		}
	}

    public static void main(String[] args) {
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			ImageProcessing gui = new ImageProcessing();
    			gui.setVisible(true);
    		}
    	});
    }
}