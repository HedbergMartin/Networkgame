package rocket.server;

import rocket.lib.References;
import rocket.src.DataReader;
import rocket.src.Player;
import rocket.src.network.IPacketHandler;
import rocket.src.network.Packet;

public class ServerPacketHandler implements IPacketHandler {

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

	private void updatePlayer(Packet packet) {
		Packet packetOut = packet.copy();
		DataReader data = new DataReader(packet.data);
		String name = data.getString();
		int posX = data.getInt();
		int posY = data.getInt();
		int health = data.getInt();
		data.done();
		int playerId = Server.isPlayerAdded(name);
		if(playerId != -1){
			Player player = Server.updater[playerId].player;
			player.posX = posX;
			player.posY = posY;
			player.health = health;
		}
		Server.sendDataToAll(packetOut);
	}

	private void spawnPlayer(Packet packet) {
		Packet packetOut = packet.copy();
		DataReader data = new DataReader(packet.data);
		String name = data.getString();
		int posX = data.getInt();
		int posY = data.getInt();
		int health = data.getInt();
		data.done();
		int playerId = Server.isPlayerAdded(name);
		if(playerId != -1){
			Player player = new Player(posX, posY, health, name);
			Server.serverInstance.serverWorld.spawnEntity(player);
		}
		Server.sendDataToAll(packetOut);
	}

	@SuppressWarnings("deprecation")
	private void playerDC(Packet packet){
		DataReader data = new DataReader(packet.data);
		String username = data.getString();
		data.done();
		int user = Server.isPlayerAdded(username);
		Server.updater[user].stop();
		Server.updater[user] = null;
		Server.sendDataToAll(packet);
	}
	
	private void openGui(Packet packet) {
		Server.sendDataToAll(packet.copy());
	}
}
