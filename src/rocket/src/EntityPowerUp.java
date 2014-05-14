package rocket.src;

import java.awt.Graphics;

import rocket.src.powerups.PowerUp;

public class EntityPowerUp extends Entity {
	
	PowerUp power;
	
	public EntityPowerUp(PowerUp power, int posX, int posY, int health) {
		super(posX, posY, -100);
		this.power = power;
	}
	
	public void onUpdate() {
		
	}
	
	public void collide(Player player){
		/*if(this.collisionCheck(player.posX, player.posY) || this.collision(player.posX+10, player.posY) || this.collision(player.posX, player.posY+20) || this.collision(player.posX+10, player.posY+20)) {
			player.setActivePowerUp(this);
			this.setDead();
		}*/
	}
	
	@Override
	public void renderEntity(Graphics g) {
		this.power.drawPowerup(g, posX, posY);
	}
}
