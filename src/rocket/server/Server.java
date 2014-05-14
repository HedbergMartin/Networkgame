package rocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import rocket.main.Rocket;
import rocket.src.World;
import rocket.src.network.IPacketHandler;
import rocket.src.network.Packet;

public class Server implements Runnable {

	public static ServerSocket server;
	public static Socket socket;
	static ServerUpdateThread[] updater = new ServerUpdateThread[4];
	public static List<IPacketHandler> packetHandels = new ArrayList<IPacketHandler>();
    public LinkedBlockingQueue<Packet> dataQue;
	
	public void initServer() throws IOException{
		System.out.println("Server started");
		Server.packetHandels.add(new ServerPacketHandler());
		server = new ServerSocket(7777);
		while(Rocket.getRocket().serverRunning) {
			socket = server.accept();
			if(server.isClosed()) {
				break;
			}
			System.out.println("Connection");
			for(int i = 0; i < 4; i++) {
				if (updater[i] == null) {
					updater[i] = new ServerUpdateThread(socket, i, this);
					updater[i].setDaemon(true);
					updater[i].start();
					break;
				}
			}
		}
	}
	
	public World serverWorld;
	public static Server serverInstance;
	
	public void serverGameUpdate() {
		serverInstance = this;
		this.serverWorld = new World(Rocket.getRocket().getWidth(), Rocket.getRocket().getHeight(), false);
		Thread serverGame = new Thread() {
            public void run(){
                while(true){
                	serverWorld.onWorldUpdate();
        			try {
        				Thread.sleep(17);
        			} catch (InterruptedException e) {
        				e.printStackTrace();
        			}
                }
            }
        };

        serverGame.setDaemon(true);
        serverGame.start();
	}
	
	public void dataDeque(){
        dataQue = new LinkedBlockingQueue<Packet>();
		Thread dataHandling = new Thread() {
            public void run(){
                while(true){
                    Packet packet = (Packet) dataQue.poll();
                    if(packet != null) {
            			if(packet.channel != null){
            				for(int handlers = 0; handlers < Server.packetHandels.size(); handlers++){
            					Server.packetHandels.get(handlers).packetIncoming(packet);
            				}
            			}
                    }
                }
            }
        };

        dataHandling.setDaemon(true);
        dataHandling.start();
	}
	
	public static int isPlayerAdded(String player) {
		for(int i = 0; i < updater.length; i++) {
			if(updater[i] != null && updater[i].player != null){
				if(updater[i].player.name.equals(player)) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public void run() {
		try {
			dataDeque();
			serverGameUpdate();
			initServer();
		} catch (IOException e) {
			System.out.println("Server is shutting down");
			try {
				socket.close();
			} catch (IOException e1) { }
			for(int i = 0; i < updater.length; i++) {
				updater[i] = null;
			}
		}
	}

	public static void sendDataToAll(Packet packet) {
		for(int k = 0; k < updater.length; k++) {
			if(updater[k] != null){
				sendPacket(k, packet);
			}
		}
	}
	
	public static void sendPacket(int player, Packet packet) {
		try {
			Packet.writeString(updater[player].out, packet.channel);
			Packet.writeByteArray(updater[player].out, packet.data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
