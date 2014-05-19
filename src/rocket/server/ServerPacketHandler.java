package rocket.server;

import rocket.lib.References;
import rocket.src.DataReader;
import rocket.src.DataWriter;
import rocket.src.Player;
import rocket.src.network.IPacketHandler;
import rocket.src.network.Packet;

public class ServerPacketHandler implements IPacketHandler {

	private Server server;
	public ServerPacketHandler(Server server) {
		this.server = server;
	}
	
	@Override
	public void packetIncoming(Packet packet) {
		if(packet.channel.equals(References.CHANNEL_PLAYER_DC)){
			this.playerDC(packet);
		}else if(packet.channel.equals(References.CHANNEL_JOIN)){
			this.join(packet);
		}else if(packet.channel.equals(References.CHANNEL_STARTGAME)) {
			this.startGame(packet);
		}else if(packet.channel.equals(References.CHANNEL_SPAWNPLAYER)) {
			this.spawnPlayer(packet);
		}else if(packet.channel.equals(References.CHANNEL_PLAYER_UPDATE)) {
			this.updatePlayer(packet);
		}else {
			Server.sendDataToAll(packet);
		}
	}
	
	public void updatePlayer(Packet packet) {
		/*DataReader dataread = new DataReader(packet.data);
		String name = dataread.getString();
		int posX = dataread.getInt();
		int posY = dataread.getInt();
		int health = dataread.getInt();
		Player player = (Player) server.world.serverWorld.entitysInWorld.get(Server.isPlayerAdded(name));
		player.posX = posX;
		player.posY = posY;
		player.health = health;*/
		Server.sendDataToAll(packet);
	}
	
	private void startGame(Packet packet) {
		server.startWorld();
		Server.sendDataToAll(packet);
	}
	
	private void join(Packet packet) {
		DataReader dataread = new DataReader(packet.data);
		String name = dataread.getString();
		Server.connectedPlayers.add(name);
		int playerId = Server.isPlayerAdded(name);
		for(int i = 0; i < Server.connectedPlayers.size(); i++) {
			if(i != playerId) {
				DataWriter datawrite = new DataWriter();
				datawrite.addString(Server.connectedPlayers.get(i));
				Server.sendPacket(playerId, datawrite.finalizePacket(References.CHANNEL_JOIN));
			}
		}
		Server.sendDataToAll(packet);
		
	}
	
	private void spawnPlayer(Packet packet) {
		DataReader dataread = new DataReader(packet.data);
		String username = dataread.getString();
		int posX = dataread.getInt();
		int posY = dataread.getInt();
		int health = dataread.getInt();
		Player player = new Player(posX, posY, health, username);
		server.world.serverWorld.spawnEntity(player);
		Server.sendDataToAll(packet);
	}

	@SuppressWarnings("deprecation")
	private void playerDC(Packet packet){
		DataReader data = new DataReader(packet.data);
		String username = data.getString();
		int user = Server.isPlayerAdded(username);
		Server.updater[user].stop();
		Server.updater[user] = null;
		Server.sendDataToAll(packet);
	}
}
