package ce325.hw2;

public class RGBPixel{
	private short red, green, blue;
	private int rgb;

	public RGBPixel(short red, short green, short blue){
		this.red = red;
		this.blue = blue;
		this.green = green;
	}

	public RGBPixel(RGBPixel pixel){
		this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
	}

	public RGBPixel(YUVPixel pixel){
		short C = (short)(pixel.getY() - 16);
		short D = (short)(pixel.getU() - 128);
		short E = (short)(pixel.getV() - 128);

		red = (short)((298*C + 409*E + 128) >> 8);
		if (red < 0){
			red = 0;
		}
		else if (red > 255){
			red = 255;
		}

		green = (short)((298*C - 100*D - 208*E + 128) >> 8);
		if (green < 0){
			green = 0;
		}
		else if (green > 255){
			green = 255;
		}

		blue = (short)((298*C + 516*D + 128) >> 8);
		if (blue < 0){
			blue = 0;
		}
		else if (blue > 255){
			blue = 255;
		}
	}

	public short getRed(){
		return red;
	}

	public short getGreen(){
		return green;
	}

	public short getBlue(){
		return blue;
	}

	public void setRed(short red){
		this.red = red;
	}

	public void setBlue(short blue){
		this.blue = blue;
	}

	public void setGreen(short green){
		this.green = green;
	}
}