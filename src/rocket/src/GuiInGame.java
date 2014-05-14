package rocket.src;

import java.awt.Graphics;

import rocket.main.Rocket;

public class GuiInGame extends Gui {
	
	public Rocket rocket;
	public World world;
	
	public GuiInGame(Rocket arg1, World world) {
		this.rocket = arg1;
		this.world = world;
	}
	
	@Override
	public void update() {
		if(rocket.network.isConnected){
			rocket.network.update();
		}
		world.onWorldUpdate();
		
	}
	
	@Override
	public void drawGui(Graphics g) {
		this.world.renderWorld(g);
//		for(int i = 0; i < NetworkHandler.connectedPlayers.size(); i++){
//			NetworkHandler.connectedPlayers.get(i).renderPlayer(g);
//		}
//		if(this.powerOnScreen != null) {
//			this.powerOnScreen.drawPowerup(g);
//		}
	}
}
