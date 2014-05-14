package rocket.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Button {

	private int IMGwidth, IMGheight, IMGx, IMGy, posX, posY, width, height;
	private boolean checkBox = false;
	public int getIMGx() {
		return IMGx;
	}

	public void setIMGx(int iMGx) {
		IMGx = iMGx;
	}

	public int getIMGy() {
		return IMGy;
	}

	public void setIMGy(int iMGy) {
		IMGy = iMGy;
	}

	public String path, text;
	BufferedImage image;
	private int textSize;
	
	public Button(String path, int x, int y, int imgwidth, int imgheight, String text, int textSize, int posX, int posY, int width, int height) {
		this.IMGx = x;
		this.IMGy = y;
		this.IMGwidth = imgwidth;
		this.IMGheight = imgheight;
		this.path = path;
		image = SpriteSheet.getBufferedImage(path, x, y, imgwidth, imgheight);
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.text = text;
		this.textSize = textSize;
	}
	
	public void drawButton(Graphics g){
		g.setColor(Color.black);
		g.setFont(new Font("Cambria", 40, this.textSize));
		g.drawImage(image, this.posX, this.posY, width, height, null);
		g.drawString(text, this.posX+(this.IMGwidth/3), (int) (this.posY+(height/1.5)));
	}
	
	public boolean isInside(int x, int y){
		if(this.posX < x && x < (this.posX + this.width) && this.posY < y && y < (this.posY + this.height))
			return true;
		return false;
	}
	
	public Button setCheckBox(){
		this.checkBox = true;
		return this;
	}
	
	public boolean isCheckBox(){
		return this.checkBox;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void click(boolean mark) {
		int y = this.getIMGy();
		if(mark){
			y = (this.getIMGy()+this.IMGheight);
		}
		image = SpriteSheet.getBufferedImage(path, this.getIMGx(), y, this.IMGwidth, this.IMGheight);
	}
}
