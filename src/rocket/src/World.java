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
	
	public boolean updateWorld = false;
	
	public boolean isRemote = false;

	public World(boolean remote) {
		this.isRemote = remote;
	}
	
	public void onWorldUpdate() {
		if(!this.isRemote) {
			for(int i = 0; i < this.entitysInWorld.size(); i++) {
				this.entitysInWorld.get(i).onUpdate();
			}
		}else {
			Rocket.getRocket().player.onUpdate();
			Rocket.getRocket().network.manager.sendPlayerUpdate();
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
