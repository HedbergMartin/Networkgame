package rocket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import rocket.lib.References;
import rocket.main.Rocket;
import rocket.src.DataWriter;
import rocket.src.Player;
import rocket.src.PlayerClient;
import rocket.src.network.Packet;

public class ServerUpdateThread extends Thread {
	
	public DataInputStream in;
	public DataOutputStream out;
	public PlayerClient player;
	int ID;
	private Server server;

	
	public ServerUpdateThread(Socket socket, int id, Server server) throws IOException{
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
		this.ID = id;
		this.server = server;
	}
	
	public void init(){
		for(int i = 0; i < Server.serverInstance.serverWorld.entitysInWorld.size(); i++){
			if(Server.serverInstance.serverWorld.entitysInWorld.get(i) instanceof Player){
				if(Server.updater[i].player != null){
					DataWriter data = new DataWriter();
					data.addString(Server.updater[i].player.name);
					data.addInt(Server.updater[i].player.posX);
					data.addInt(Server.updater[i].player.posY);
					data.addInt(Server.updater[i].player.health);
					Server.sendPacket(ID, data.finalizePacket(References.CHANNEL_SPAWNPLAYER));
				}
			}
		}
	}
	
	public void run(){
		while(Rocket.getRocket().serverRunning){
			String channel = null;
			byte[] data = null;
			try{
				channel = Packet.readString(in);
				data = Packet.readBytesFromStream(in);
			}catch(IOException e){
				e.printStackTrace();
			}
			Packet packet = new Packet(channel, data, data.length);
//			if(this.player == null && packet.channel.equals(References.CHANNEL_PLAYER_UPDATE)){
//				DataReader playerData = new DataReader(packet.data);
//				String name = playerData.getString();
//				int posX = playerData.getInt();
//				int posY = playerData.getInt();
//				int health = playerData.getInt();
//				this.player = new PlayerClient(posX, posY, health, name);
//				init();
//			}
			server.dataQue.add(packet);
		}
	}
}
