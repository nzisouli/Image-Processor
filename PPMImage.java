package ce325.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class PPMImage extends RGBImage{

	public PPMImage(File file) throws UnsupportedFileFormatException, FileNotFoundException{
		super(1, 1, 255);
		if(file.exists() == false){
			throw new FileNotFoundException();
		}
		//Check if given file's extension is .ppm
		String fileFormat = file.getName().substring(file.getName().length()-3, file.getName().length());
		if(!fileFormat.equals("ppm")){
			throw new UnsupportedFileFormatException();
		}
		//Collect data from given .ppm image
		int width, height, colordepth;
		Scanner in = new Scanner(file);

		in.next();
		width = in.nextInt();
		height = in.nextInt();
		colordepth = in.nextInt();
		

		short red, green, blue;
		RGBPixel [][] image = new RGBPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				red = in.nextShort();
				green = in.nextShort();
				blue = in.nextShort();
				image[i][j] = new RGBPixel(red, green, blue);
			}
		}
		super.setImage(image);
	}

	public PPMImage(RGBImage img){
		super(img);
	}

	public PPMImage(YUVImage img){
		super(img);
	}
	//Create the contents of the .ppm file
	public String toString(){
		String out = "";
		out += "P3\n";
		out += super.getWidth() + " " + super.getHeight() + "\n";
		out += super.getColordepth() + "\n";

		RGBPixel [][] image = super.getImage();
		for(int i = 0; i < super.getHeight(); i++){
			for(int j = 0; j < super.getWidth(); j++){
				out += image[i][j].getRed() + " ";
				out += image[i][j].getGreen() + " ";
				out += image[i][j].getBlue();
				if (j != super.getWidth() - 1){
					out += " ";
				}
			}
			out += "\n";
		}
		return out;
	}

	public void toFile(File file){
		if(file.exists()){
			file.delete();
		}
		try{
			//file.createNewFile();

			FileWriter out = new FileWriter(file.getName());

			out.write(toString());
			out.close();
		}
		catch(IOException ex){
			System.out.println("IOException");
		}
	}
}