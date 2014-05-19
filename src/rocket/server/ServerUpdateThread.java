package rocket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
	
	public void run(){
		while(true){
			String channel = null;
			byte[] data = null;
			try{
				channel = Packet.readString(in);
				data = Packet.readBytesFromStream(in);
			}catch(IOException e){
				e.printStackTrace();
			}
			Packet packet = new Packet(channel, data, data.length);
			server.dataQue.add(packet);
		}
	}
}
