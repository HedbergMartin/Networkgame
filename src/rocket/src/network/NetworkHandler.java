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
import rocket.src.World;

public class NetworkHandler implements Runnable {

	Socket socket;
	DataOutputStream out;
	DataInputStream in;
	public static ArrayList<String> connectedPlayers = new ArrayList<String>();
	public static List<IPacketHandler> packetHandels = new ArrayList<IPacketHandler>();
    public LinkedBlockingQueue<Packet> dataQue;
	public Rocket rocket;
	public boolean isConnected = false;
	private Thread thread;
	public PacketManager manager;
	
	public NetworkHandler(Rocket rocket) {
		this.rocket = rocket;
		this.manager = new PacketManager(this);
	}

	/** Connect to the server **/
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
		this.manager.sendJoindata(rocket.player.name);
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
			if(connectedPlayers.get(i).equals(player)){
				return i;
			}
		}
		return -1;
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
