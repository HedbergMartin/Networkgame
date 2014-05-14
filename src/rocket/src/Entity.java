package rocket.src;

import java.awt.Graphics;

public class Entity {
	
	public int posX;
	public int posY;
	public int ticksToLive = 500;
	public int ticksAlive = 0;
	public boolean isDead = false;
	public int health = 0;
	
	public int width = 16;
	public int height = 16;
	
	public Entity(int posX, int posY, int health) {
		this.posX = posX;
		this.posY = posY;
		this.health = health;
	}

	public void onUpdate() {
		if(health != -100 && health <= 0) {
			this.setDead();
		}
		if(ticksAlive >= ticksToLive) {
			this.setDead();
		}
		ticksAlive++;
	}
	
	public boolean collisionCheck(int posX, int posY) {
		if(posX > this.posX && this.posX+width > posX && posY > this.posY && this.posY+height > posY) {
			return true;
		}else {
			return false;
		}
	}
	
	public void setDead() {
		this.isDead = true;
	}
	
	public void renderEntity(Graphics g) {
		
	}
}
