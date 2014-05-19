package rocket.server;

import rocket.src.World;

public class ServerWorld implements Runnable {

	public World serverWorld;
	
	public ServerWorld() {
		this.serverWorld = new World(false);
	}
	
	public void run() {
		while(true){
            this.serverWorld.onWorldUpdate();
        }
	}
}
