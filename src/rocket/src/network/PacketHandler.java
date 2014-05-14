package rocket.src.network;

import rocket.lib.References;
import rocket.main.Rocket;
import rocket.server.Server;
import rocket.src.DataReader;
import rocket.src.Gui;
import rocket.src.Player;
import rocket.src.PlayerClient;

public class PacketHandler implements IPacketHandler {

	@Override
	public void packetIncoming(Packet packet) {
		if(packet.channel.equals(References.CHANNEL_PLAYER_UPDATE)){
			this.updatePlayer(packet);
		}else if(packet.channel.equals(References.CHANNEL_PLAYER_DC)){
			this.playerDC(packet);
		}else if(packet.channel.equals(References.CHANNEL_SPAWNPLAYER)){
			this.spawnPlayer(packet);
		}else if(packet.channel.equals(References.CHANNEL_OPENGUI)){
			this.openGui(packet);
		}
	}
	
	private void updatePlayer(Packet packet){
		DataReader data = new DataReader(packet.data);
		String name = data.getString();
		int posX = data.getInt();
		int posY = data.getInt();
		int health = data.getInt();
		data.done();
		int playerId = NetworkHandler.isPlayerAdded(name);
		if(playerId != -1){
			NetworkHandler.connectedPlayers.get(playerId).posX = posX;
			NetworkHandler.connectedPlayers.get(playerId).posY = posY;
			NetworkHandler.connectedPlayers.get(playerId).health = health;
		}else {
			NetworkHandler.connectedPlayers.add(new PlayerClient(posX, posY, health, name));
		}
	}
	
	private void playerDC(Packet packet){
		DataReader data = new DataReader(packet.data);
		String username = data.getString();
		data.done();
		int user = NetworkHandler.isPlayerAdded(username);
		NetworkHandler.connectedPlayers.remove(user);
	}

	private void spawnPlayer(Packet packet) {
		DataReader data = new DataReader(packet.data);
		String name = data.getString();
		int posX = data.getInt();
		int posY = data.getInt();
		int health = data.getInt();
		data.done();
		int playerId = Server.isPlayerAdded(name);
		if(playerId != -1){
			Player player = new Player(posX, posY, health, name);
			Rocket.getRocket().theWorld.spawnEntity(player);
		}
	}
	
	private void openGui(Packet packet) {
		DataReader data = new DataReader(packet.data);
		Gui.openNewGui(data.getInt());
		data.done();
	}
}
