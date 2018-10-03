package ce325.hw2;

public class RGBImage implements Image{
	private int width, height, colordepth;
	private RGBPixel [][] image;

	public RGBImage(int width, int height, int colordepth){
		this.width = width;
		this.height = height;
		this.colordepth = colordepth;

		image = new RGBPixel[height][width];
	}

	public RGBImage(RGBImage copyImg){
		this(copyImg.getWidth(), copyImg.getHeight(), copyImg.getColordepth());
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				image[i][j] = copyImg.getImage()[i][j];
			}
		}
	}

	public RGBImage(YUVImage YUVImg){
		this(YUVImg.getWidth(), YUVImg.getHeight(), 255);
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				image[i][j] = new RGBPixel(YUVImg.getImage()[i][j]);
			}
		}

	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getColordepth(){
		return colordepth;
	}

	public RGBPixel [][] getImage(){
		return image;
	}

	public void setImage(RGBPixel [][] image){
		height = image.length;
		width = image[0].length;
		this.image = new RGBPixel[height][width];
		this.image = image;
	}

	public void grayscale(){
		short gray;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				gray = (short)(image[i][j].getRed()*0.3 + image[i][j].getGreen()*0.59 + image[i][j].getBlue()*0.11);
				image[i][j].setRed(gray);
				image[i][j].setGreen(gray);
				image[i][j].setBlue(gray);
			}
		}
	}

	public void doublesize(){
		int newWidth = width * 2;
		int newHeight = height * 2;
		RGBPixel [][] newImage = new RGBPixel[newHeight][newWidth];

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				newImage[2*i][2*j] = image[i][j];
				newImage[2*i+1][2*j] = image[i][j];
				newImage[2*i][2*j+1] = image[i][j];
				newImage[2*i+1][2*j+1] = image[i][j];
			}
		}

		width = newWidth;
		height = newHeight;		
		image = new RGBPixel[height][width];
		image = newImage;
	}

	public void halfsize(){
		int newWidth = width / 2;
		int newHeight = height / 2;
		RGBPixel [][] newImage = new RGBPixel[newHeight][newWidth];
		short newRed, newGreen, newBlue;

		for(int i = 0; i < newHeight; i++){
			for(int j = 0; j < newWidth; j++){
				newRed = (short)((image[2*i][2*j].getRed() + image[2*i+1][2*j].getRed() +
					image[2*i][2*j+1].getRed() + image[2*i+1][2*j+1].getRed())/4);
				newGreen = (short)((image[2*i][2*j].getGreen() + image[2*i+1][2*j].getGreen() +
					image[2*i][2*j+1].getGreen() + image[2*i+1][2*j+1].getGreen())/4);
				newBlue = (short)((image[2*i][2*j].getBlue() + image[2*i+1][2*j].getBlue() +
					image[2*i][2*j+1].getBlue() + image[2*i+1][2*j+1].getBlue())/4);
				newImage[i][j] = new RGBPixel(newRed, newGreen, newBlue);
				
			}
		}

		width = newWidth;
		height = newHeight;		
		image = new RGBPixel[height][width];
		image = newImage;
	}

	public void rotateClockwise(){
		int newWidth = height;
		int newHeight = width;
		RGBPixel [][] newImage = new RGBPixel[newHeight][newWidth];

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				newImage[j][newWidth-1-i] = image[i][j];			
			}
		}

		width = newWidth;
		height = newHeight;		
		image = new RGBPixel[height][width];
		image = newImage;
	}
}