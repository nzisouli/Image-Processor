package ce325.hw2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Histogram{
	short [] hist;
	int pixels;

	public Histogram(YUVImage img){
		pixels = img.getWidth()*img.getHeight();
		hist = new short[255];
		for(int i = 0; i < 255; i++){
			hist[i] = 0;
		}
		for(int i = 0; i < img.getHeight(); i++){
			for (int j = 0; j<img.getWidth(); j++){
				hist[img.getImage()[i][j].getY()] ++;
			}
		}
	}

	public String toString(){
		String out = "";
		for(int i = 0; i < 255; i++){
			out += i;
			for(int j = 0; j < hist[i]; j++){
				out += "*";
				if(j == 79){
					break;
				}
			}
			out += "\n";
		}

		return out;
	}

	public void toFile(File file){
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

	public void equalize(){
		//Create probability table for equalisation method
		float [] probability = new float[255];
		for(int i = 0; i < 255; i++){
			probability[i] = (float)hist[i] / (float)pixels;
		}
		//Cumulative distribution function calculation
		float [] cdf = new float[255];
		cdf[0] = probability[0];
		for(int i = 1; i < 255; i++){
			cdf[i] = probability[i] + cdf[i-1];
		}
		//Replace current Histogram with the equalised one
		for(int i = 1; i < 255; i++){
			hist[i] = (short)(cdf[i] * 255);
			if(hist[i] > 255){
				hist[i] = 255;
			}
		}
	}

	public short getEqualizedLuminocity(int luminocity){
		return hist[luminocity];
	}
}