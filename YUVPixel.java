package ce325.hw2;

public class YUVPixel{
	private short Y, U, V;

	public YUVPixel(short Y, short U, short V){
		this.Y = Y;
		this.V = V;
		this.U = U;
	}

	public YUVPixel(YUVPixel pixel){
		this(pixel.getY(), pixel.getU(), pixel.getV());
	}

	public YUVPixel(RGBPixel pixel){
		Y = (short)(((66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128) >> 8) + 16);
		U = (short)(((-38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128) >> 8) + 128);
		V = (short)(((112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128) >> 8) + 128);
	}

	public short getY(){
		return Y;
	}

	public short getU(){
		return U;
	}

	public short getV(){
		return V;
	}

	public void setY(short Y){
		this.Y = Y;
	}

	public void setV(short V){
		this.V = V;
	}

	public void setU(short U){
		this.U = U;
	}
}