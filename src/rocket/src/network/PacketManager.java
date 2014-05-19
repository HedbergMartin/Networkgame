package rocket.src.network;

import rocket.lib.References;
import rocket.main.Rocket;
import rocket.src.DataWriter;
import rocket.src.Player;

public class PacketManager {
	
	private NetworkHandler network;
	
	public PacketManager(NetworkHandler network) {
		this.network = network;
	}

	public void sendJoindata(String username) {
		DataWriter data = new DataWriter();
		data.addString(username);
		network.sendPacket(data.finalizePacket(References.CHANNEL_JOIN));
	}
	
	public void sendStartgame(){
		DataWriter data = new DataWriter();
		data.addInt(0);
		network.sendPacket(data.finalizePacket(References.CHANNEL_STARTGAME));
	}
	
	public void sendPlayerUpdate(){
		DataWriter data = new DataWriter();
		Player player = Rocket.getRocket().player;
		data.addString(player.name);
		data.addInt(player.posX);
		data.addInt(player.posY);
		data.addInt(player.health);
		network.sendPacket(data.finalizePacket(References.CHANNEL_PLAYER_UPDATE));
	}
}
