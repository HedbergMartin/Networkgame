package rocket.src;

import java.awt.Graphics;

import rocket.main.Rocket;
import rocket.src.powerups.PowerUp;

public class Player extends Entity {
	
	public int motionX;
	public int motionY;
	public int width = 10;
	public int height = 20;
	public int ammo;
	public String name;
	public boolean isHost = false;
	
	public PowerUp currentPower = null;
	
	/** 0 = null, 1 = up, 2 = left, 3 = down, 4 = right **/
	public boolean[] buttom = new boolean[4];

	public Player(int x, int y, int heal, String username) {
		super(x, y, heal);
		this.motionX = 0;
		this.motionY = 0;
		this.name = username;
		for(int i = 0; i < buttom.length; i++){
			this.buttom[i] = false;
		}
	}
	
	public void update(){
		
		if(motionX > 10)
			motionX = 10;
		if(motionY > 10)
			motionY = 10;
		if(motionX < -10)
			motionX = -10;
		if(motionY < -10)
			motionY = -10;
		

		if(motionY < 0 && this.buttom[0] == false)
			motionY += 1;
		if(motionX < 0 && this.buttom[1] == false)
			motionX += 1;
		if(motionY > 0 && this.buttom[2] == false)
			motionY -= 1;
		if(motionX > 0 && this.buttom[3] == false)
			motionX -= 1;
		
		if(this.posX < 0){
			this.posX = 0;
			this.motionX = -this.motionX;
		}
		if(this.posX > Rocket.getRocket().getWidth()-10){
			this.posX = Rocket.getRocket().getWidth()-10;
			this.motionX = -this.motionX;
		}
		if(this.posY < 0){
			this.posY = 0;
			this.motionY = -this.motionY;
		}
		if(this.posY > Rocket.getRocket().getHeight()-20){
			this.posY = Rocket.getRocket().getHeight()-20;
			this.motionY = -this.motionY;
		}
		
		posX += motionX;
		posY += motionY;
	}
	
	public void renderPlayer(Graphics g) {
		System.out.println(this.currentPower);
		if(this.currentPower != null) {
			System.out.println("ded");
			this.currentPower.drawPlayerPrePower(g, this);
		}
		/** Render Player here. **/
		g.fillRect(this.posX, this.posY, this.width, this.height);
		/** To here **/
		if(this.currentPower != null) {
			this.currentPower.drawPlayerPostPower(g, this);
		}
	}

	public void setActivePowerUp(PowerUp powerUps) {
		System.out.println("Added: "+powerUps.theName);
		this.currentPower = powerUps;
	}
}
