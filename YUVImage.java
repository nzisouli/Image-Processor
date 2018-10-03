package ce325.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class YUVImage{
	private int width, height;
	private YUVPixel [][] image;

	public YUVImage(int width, int height){
		this.width = width;
		this.height = height;

		image = new YUVPixel[height][width];

		for (int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				image[i][j] = new YUVPixel((short)16, (short)128, (short)128);
			}
		}
	}

	public YUVImage(YUVImage copyImg){
		this(copyImg.getWidth(), copyImg.getHeight());
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				image[i][j] = copyImg.getImage()[i][j];
			}
		}
	}

	public YUVImage(RGBImage RGBImg){
		this(RGBImg.getWidth(), RGBImg.getHeight());
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				image[i][j] = new YUVPixel(RGBImg.getImage()[i][j]);
			}
		}

	}

	public YUVImage(File file) throws UnsupportedFileFormatException, FileNotFoundException{
		if(file.exists() == false){
			throw new FileNotFoundException();
		}
		//Check if given file's extension is  .yuv
		if(!file.getName().substring(file.getName().length()-3).equals("yuv")){
			throw new UnsupportedFileFormatException();
		}
		//Collect data from given .yuv image
		Scanner in = new Scanner(file);

		in.next();
		width = in.nextInt();
		height = in.nextInt();
		

		short Y, U, V;
		image = new YUVPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				Y = in.nextShort();
				U = in.nextShort();
				V = in.nextShort();
				image[i][j] = new YUVPixel(Y, U, V);
			}
		}
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public YUVPixel [][] getImage(){
		return image;
	}
	//Contents for new .yuv image
	public String toString(){
		String out = "";
		out += "YUV3\n";
		out += width + " " + height + "\n";

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				out += image[i][j].getY() + " ";
				out += image[i][j].getU() + " ";
				out += image[i][j].getV();
				if (j != width - 1){
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
			file.createNewFile();

			FileWriter out = new FileWriter(file.getName());

			out.write(toString());
			out.close();
		}
		catch(IOException ex){
			System.out.println("IOException");
		}
	}
	//Equalize image using histogram object
	public void equalize(){
		File file = new File("image.yuv");
		toFile(file);
		try{
			Histogram hist = new Histogram(new YUVImage(file));
			hist.equalize();

			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					image[i][j].setY(hist.getEqualizedLuminocity(image[i][j].getY()));
				}
			}
		}
		catch(UnsupportedFileFormatException ex){
			System.out.println("UnsupportedFileFormatException");
		}
		catch(FileNotFoundException ex){
			System.out.println("FileNotFoundException");
		}
	}
}