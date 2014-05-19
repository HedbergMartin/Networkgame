package rocket.src.network;

import rocket.lib.References;
import rocket.main.Rocket;
import rocket.server.Server;
import rocket.src.DataReader;
import rocket.src.DataWriter;
import rocket.src.Gui;
import rocket.src.Player;
import rocket.src.World;

public class PacketHandler implements IPacketHandler {

	private Rocket rocket;
	public PacketHandler(Rocket rocket) {
		this.rocket = rocket;
	}
	
	@Override
	public void packetIncoming(Packet packet) {
		if(packet.channel.equals(References.CHANNEL_PLAYER_UPDATE)) {
			this.updatePlayer(packet);
		}else if(packet.channel.equals(References.CHANNEL_PLAYER_DC)) {
			this.playerDC(packet);
		}else if(packet.channel.equals(References.CHANNEL_SPAWNPLAYER)) {
			this.spawnPlayer(packet);
		}else if(packet.channel.equals(References.CHANNEL_OPENGUI)) {
			this.openGui(packet);
		}else if(packet.channel.equals(References.CHANNEL_JOIN)) {
			this.joinLobby(packet);
		}else if(packet.channel.equals(References.CHANNEL_STARTGAME)) {
			this.startGame(packet);
		}
	}
	
	private void startGame(Packet packet) {
		rocket.theWorld = new World(true);
		Gui.openNewGui(References.GUI_INGAME);
		DataWriter data = new DataWriter();
		data.addString(rocket.player.name);
		data.addInt(rocket.player.posX);
		data.addInt(rocket.player.posY);
		data.addInt(rocket.player.health);
		rocket.network.sendPacket(data.finalizePacket(References.CHANNEL_SPAWNPLAYER));
	}
	
	private void joinLobby(Packet packet){
		DataReader data = new DataReader(packet.data);
		String playername = data.getString();
		NetworkHandler.connectedPlayers.add(playername);
	}
	
	public void updatePlayer(Packet packet) {
		DataReader dataread = new DataReader(packet.data);
		String name = dataread.getString();
		int posX = dataread.getInt();
		int posY = dataread.getInt();
		int health = dataread.getInt();
		Player player = (Player) rocket.theWorld.entitysInWorld.get(NetworkHandler.isPlayerAdded(name));
		player.posX = posX;
		player.posY = posY;
		player.health = health;
	}
	
	private void playerDC(Packet packet){
		DataReader data = new DataReader(packet.data);
		String username = data.getString();
		int user = NetworkHandler.isPlayerAdded(username);
		NetworkHandler.connectedPlayers.remove(user);
	}

	private void spawnPlayer(Packet packet) {
		DataReader data = new DataReader(packet.data);
		String name = data.getString();
		int posX = data.getInt();
		int posY = data.getInt();
		int health = data.getInt();
		
		Player player = new Player(posX, posY, health, name);
		rocket.theWorld.spawnEntity(player);
	}
	
	private void openGui(Packet packet) {
		DataReader data = new DataReader(packet.data);
		Gui.openNewGui(data.getInt());
	}
}
