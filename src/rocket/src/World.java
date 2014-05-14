package rocket.src;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rocket.main.Rocket;
import rocket.src.powerups.PowerUp;

public class World {
	
	public List<Entity> entitysInWorld = new ArrayList<Entity>();
	public Random random = new Random(Rocket.getRocket().properties.seed);
	public int worldWidth;
	public int worldHeight;
	
	public boolean updateWorld = false;
	
	public boolean isRemote = false;

	public World(int width, int height, boolean remote) {
		this.worldWidth = width;
		this.worldHeight = height;
		this.isRemote = remote;
	}
	
	public void onWorldUpdate() {
		if(!this.isRemote) {
			boolean hasPowerUp = false;
			for(int i = 0; i < this.entitysInWorld.size(); i++) {
				this.entitysInWorld.get(i).onUpdate();
				if(this.entitysInWorld.get(i) instanceof EntityPowerUp){
					hasPowerUp = true;
				}
			}

			if(!hasPowerUp) {
				//if(this.random.nextInt(600) == 32) {
					this.spawnEntity(new EntityPowerUp(PowerUp.shield, random.nextInt(this.worldWidth-20), random.nextInt(this.worldHeight-20), 1));
				//}
			}
		}
	}
	
	public void spawnEntity(Entity entity) {
		this.entitysInWorld.add(entity);
	}
	
	public void despawnEntity(Entity entity) {
		this.entitysInWorld.remove(entity);
	}
	
	public void renderWorld(Graphics g) {
		for(int i = 0; i < this.entitysInWorld.size(); i++) {
			this.entitysInWorld.get(i).renderEntity(g);
		}
	}
}
