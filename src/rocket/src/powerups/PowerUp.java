package rocket.src.powerups;

import java.awt.Graphics;
import java.awt.Image;

import rocket.src.Player;
import rocket.src.SpriteSheet;

public class PowerUp {
	
	public String theName;
	public int ampliFyer;
	public int id;
	public static PowerUp powerList[] = new PowerUp[16];
	public Image image;
	public int width;
	public int height;
	
	public static PowerUp shield = new PowerUp(0, "shield", 0);

	public PowerUp(int id, String name, int amp) {
		if(powerList[id] != null) {
			System.err.println("Error! "+id+" allready exist!");
		}else {
			powerList[id] = this;
			this.id = id;
			this.theName = name;
			this.ampliFyer = amp;
			this.width = 16;
			this.height = 16;
		}
	}
	
	public PowerUp setImage(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		this.image = SpriteSheet.getBufferedImage("/Gui.png", x, y, width, height);
		return this;
	}

	public void drawPowerup(Graphics g, int x, int y) {
		if(image != null) {
			g.drawImage(image, x, y, null);
		}
	}
	
	public void drawPlayerPrePower(Graphics g, Player player) {
		
	}
	
	public void drawPlayerPostPower(Graphics g, Player player) {
		
	}
	
	public void tickOnPlayerUpdate(Player player) {
		
	}
}
