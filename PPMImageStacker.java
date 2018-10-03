package ce325.hw2;

import java.io.File;
import java.lang.Object;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.ListIterator;

public class PPMImageStacker{
	//PPMImage type List for keeping the given directory's .ppm images for stacking
	private List <PPMImage> stackingList;
	private PPMImage final_img;
	private int success;

	public PPMImageStacker(File dir){
		success = 0;
		if (dir.exists() == false){
			System.out.println("[ERROR] Directory "+ dir.toString() + "does not exist!");
			success = -1;
		}
		else if(dir.isDirectory()== false){
			System.out.println("[ERROR]!"+ dir.toString()+ "is not a directory");
			success = -1;
		}
		else{
			stackingList = new ArrayList<PPMImage>();
			//Add files to the list as PPMImage objects
			for (File f : dir.listFiles()){
				try{
					PPMImage img = new PPMImage(f);
					stackingList.add(img);
				}
				catch(FileNotFoundException ex){
					System.out.println("FileNotFoundException");
					success = -1;
				}
				catch(UnsupportedFileFormatException ex){
					System.out.println("UnsupportedFileFormatException");
					success = -1;
				}
			}
			if (success == 0){
				success = 1;
			}
		}
	}
	//Construction success
	public int getSuccess(){
		return success;
	}

	public void stack(){
		//Begin stacking method by finding and creating 
		//the max measures of the photos in the directory
		int max_width, max_height, max_colordepth;
		max_height = 0;
		max_width = 0;
		max_colordepth = 0;
		for (int i = 0; i < stackingList.size(); i++){
			PPMImage temp_img = new PPMImage(stackingList.get(i));
			if (temp_img.getHeight() > max_height){
				max_height = temp_img.getHeight();
			}
			if(temp_img.getWidth() > max_width){
				max_width = temp_img.getWidth();
			}
			if (temp_img.getColordepth() > max_colordepth){
				max_colordepth= temp_img.getColordepth();
			}
		}
		
		RGBImage image = new RGBImage(max_width, max_height, max_colordepth);
		RGBPixel [][] img = new RGBPixel[max_height][max_width];
		img = image.getImage();
		//Combine rgb values for the final image 
		short median_red, median_green, median_blue;
		RGBPixel temp;
		median_red = 0;
		median_green = 0;
		median_blue = 0;
		for (int i = 0; i < max_height; i++){
			for (int j = 0; j < max_width; j++){
				for (int k = 0; k < stackingList.size(); k++){
					PPMImage temp_img = new PPMImage(stackingList.get(k));
					temp = new RGBPixel(temp_img.getImage()[i][j]);
					median_red = (short)(median_red + temp.getRed());
					median_green = (short)(median_green + temp.getGreen());
					median_blue = (short)(median_blue + temp.getBlue());
				}//Average rgb values 
				median_red = (short)(median_red / stackingList.size());
				median_green = (short)(median_green / stackingList.size());
				median_blue = (short)(median_blue / stackingList.size());
				img[i][j] = new RGBPixel(median_red, median_green, median_blue);
				median_red = 0;
				median_green = 0;
				median_blue = 0;
			}
		}

		final_img = new PPMImage(image);
	}

	
	public PPMImage getStackedImage(){
		stack();
		return final_img;
	}
}