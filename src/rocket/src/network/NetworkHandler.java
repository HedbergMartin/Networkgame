package rocket.src.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import rocket.lib.References;
import rocket.main.Rocket;
import rocket.src.DataWriter;
import rocket.src.Player;

public class NetworkHandler implements Runnable {

	Socket socket;
	DataOutputStream out;
	DataInputStream in;
	public static ArrayList<Player> connectedPlayers = new ArrayList<Player>();
	public static List<IPacketHandler> packetHandels = new ArrayList<IPacketHandler>();
    public LinkedBlockingQueue<Packet> dataQue;
	Rocket rocket;
	public boolean isConnected = false;
	Thread thread;
	
	public NetworkHandler(Rocket rocket) {
		this.rocket = rocket;
	}
	
	public void connect() throws UnknownHostException, IOException{
		this.socket = new Socket(Rocket.getRocket().properties.IP,Rocket.getRocket().properties.port);
		this.in = new DataInputStream(this.socket.getInputStream());
		this.out = new DataOutputStream(this.socket.getOutputStream());
        dataQue = new LinkedBlockingQueue<Packet>();
		this.isConnected = true;
		dataQueHandling();
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void dataQueHandling() {
		Thread messageHandling = new Thread() {
            public void run(){
                while(true){
                    Packet packet = dataQue.poll();
                    if(packet != null) {
                    	if(packet.channel != null){
            				for(int handlers = 0; handlers < NetworkHandler.packetHandels.size(); handlers++){
            					NetworkHandler.packetHandels.get(handlers).packetIncoming(packet);
            				}
            			}
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
	}

	@Override
	public void run() {
		this.sendPlayerUpdate();
		while(isConnected){
			String channel = null;
			byte[] data = null;
			try{
				channel = Packet.readString(in);
				data = Packet.readBytesFromStream(in);
			}catch(IOException e){
				e.printStackTrace();
			}
			if(channel == null) {
				System.out.println("Error!");
			}
			Packet packet = new Packet(channel, data, data.length);
			dataQue.add(packet);
		}
	}
	
	public static int isPlayerAdded(String player){
		for(int i = 0; i < connectedPlayers.size(); i++){
			if(connectedPlayers.get(i).name.equals(player)){
				return i;
			}
		}
		return -1;
	}
	
	public void update(){
		if(Rocket.getRocket().player.requierUpdate){
			this.sendPlayerUpdate();
		}
	}
	
	public void sendPlayerUpdate(){
		DataWriter data = new DataWriter();
		data.addString(Rocket.getRocket().player.name);
		data.addInt(Rocket.getRocket().player.posX);
		data.addInt(Rocket.getRocket().player.posY);
		data.addInt(Rocket.getRocket().player.health);
		this.sendPacket(data.finalizePacket(References.CHANNEL_PLAYER_UPDATE));
	}
	
	public void disconnect(){
		DataWriter data = new DataWriter();
		data.addString(Rocket.getRocket().player.name);
		this.sendPacket(data.finalizePacket(References.CHANNEL_PLAYER_DC));
		this.close();
	}
	
	public void sendStartGame(){
		DataWriter data = new DataWriter();
		data.addInt(References.GUI_INGAME);
		this.sendPacket(data.finalizePacket(References.CHANNEL_OPENGUI));
	}
	
	public void sendPacket(Packet packet) {
		try {
			Packet.writeString(this.out, packet.channel);
			Packet.writeByteArray(this.out, packet.data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void close() {
		this.isConnected = false;
		this.thread.stop();
		connectedPlayers.clear();
	}
}
