package rocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import rocket.src.network.IPacketHandler;
import rocket.src.network.Packet;

public class Server implements Runnable {

	public static ServerSocket server;
	public static Socket socket;
	static ServerUpdateThread[] updater = new ServerUpdateThread[4];
	public static List<IPacketHandler> packetHandels = new ArrayList<IPacketHandler>();
    public LinkedBlockingQueue<Packet> dataQue;
	public static ArrayList<String> connectedPlayers = new ArrayList<String>();
	
	public ServerWorld world;
	
	public void startWorld() {
		world = new ServerWorld();
		Thread thread = new Thread(world);
		thread.setDaemon(true);
		thread.start();
	}

	/** Static method to start the server **/
    public static void startServer() {
		Thread serverThread = new Thread(new Server());
		serverThread.setDaemon(true);
		serverThread.start();
    }

	/** Server connection thread **/
	public void initServer() throws IOException{
		System.out.println("Server started");
		Server.packetHandels.add(new ServerPacketHandler(this));
		server = new ServerSocket(7777);
		while(true) {
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

	/** Data thread that pack up data **/
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

	/** Check if a player is added, if not returning -1 **/
	public static int isPlayerAdded(String player){
		for(int i = 0; i < connectedPlayers.size(); i++){
			if(connectedPlayers.get(i).equals(player)){
				return i;
			}
		}
		return -1;
	}

	/** Server run thread **/
	public void run() {
		try {
			dataDeque();
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

	/** Sending a packet to all players **/
	public static void sendDataToAll(Packet packet) {
		for(int k = 0; k < updater.length; k++) {
			if(updater[k] != null){
				sendPacket(k, packet);
			}
		}
	}
	
	/** Sending a packet to a specific player **/
	public static void sendPacket(int player, Packet packet) {
		try {
			Packet.writeString(updater[player].out, packet.channel);
			Packet.writeByteArray(updater[player].out, packet.data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
