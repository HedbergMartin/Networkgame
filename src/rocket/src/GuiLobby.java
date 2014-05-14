package rocket.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.net.UnknownHostException;

import rocket.main.Rocket;
import rocket.server.Server;
import rocket.src.network.NetworkHandler;

public class GuiLobby extends Gui {

	Button Start = new Button("/Gui.png", 0, 0, 96, 16, "Start", 40, 50, 360, 300, 60);
	Button Kick = new Button("/Gui.png", 0, 0, 96, 16, "Kick", 40, 50, 440, 300, 60);
	Button Leave = new Button("/Gui.png", 0, 0, 96, 16, "Leave", 40, 50, 520, 300, 60);
	private int focusedButton = -1;
	public int playersConnected = 4;
	boolean host;
	
	public GuiLobby(boolean Op) {
		host = Op;
		compList.add(0, Leave);
		if(host){
			compList.add(1, Start);
			compList.add(2, Kick);
			Rocket.getRocket().serverRunning = true;
			Thread serverThread = new Thread(new Server());
			serverThread.setDaemon(true);
			serverThread.start();
		}
		try {
			Rocket.getRocket().network.connect();
		} catch (UnknownHostException e) {
			System.err.println("Couldn't find host");
		} catch (IOException e) {
			System.err.println("Couldn't connect to host");
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		if(!Rocket.getRocket().network.isConnected){
			closeGui(this);
		}
		if(!host){
			
		}else{
			
		}
		/*if(Rocket.getRocket().player.buttom[0]){
			Rocket.getRocket().connectedPlayers.add(1);
			//compList.add(compList.toArray().length, new Button("/Gui.png", 0, 32, 16, 4, "Player "+Rocket.getRocket().connectedPlayers.toArray().length, 20, 36, -10+(24*compList.toArray().length), 328, 20).setCheckBox());
		}
		if(compList.size() < Rocket.getRocket().connectedPlayers.size()){
			compList.clear();
		}
		Iterator iter = compList.iterator();
		while(iter.hasNext()){
			//if(compList.size() < Rocket.getRocket().connectedPlayers.size()){
				compList.add(compList.size(), new Button("/Gui.png", 0, 32, 16, 4, "Player "+Rocket.getRocket().connectedPlayers.toArray().length, 20, 36, -10+(24*compList.toArray().length), 328, 20).setCheckBox());
			
		}
		/*for(int i = 0; i < Rocket.getRocket().connectedPlayers.toArray().length; i++){
			if(compList.get(i+3) == null){
				compList.add(compList.get(i+4));
				((Button) compList.get(i+3)).setPosY(((Button) compList.get(i+3)).getPosY()-20);
				compList.remove(i+4);
			}
		}*/
		for(int i = 3; i < compList.toArray().length; i++){
			if(focusedButton != i){
				((Button) compList.get(i)).click(false);
			}
		}
	}
	
	
	@Override
	public void drawGui(Graphics g) {
		//g.drawImage(background, 0, 0, null);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 400, 700);
		g.setColor(Color.WHITE);
		g.drawRect(50, 50, 300, 300);
		g.setColor(Color.BLACK);
		for(int k = 0; k < compList.toArray().length; k++){
			((Button) compList.get(k)).drawButton(g);
		}
		g.setColor(Color.CYAN);
		g.setFont(new Font("Cambria", 40, 40));
		g.drawString("Lobby", 26, 40);
		g.setFont(new Font("Cambria", 40, 20));
		for(int i = 0; i < NetworkHandler.connectedPlayers.size(); i++){
			g.drawString(NetworkHandler.connectedPlayers.get(i).name, 55, 70+(i*20));
		}
	}
	
	@Override
	public void preform(int source) {
		switch(source) {
		case(0): 
			Rocket.getRocket().network.disconnect();
			if(host){Rocket.getRocket().serverRunning = false; 
				try {
					Server.server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			closeGui(this); break;
		case(1): Rocket.getRocket().network.sendStartGame(); break;
		case(2): 
			/*if(this.focusedButton != -1){
				Rocket.getRocket().network.connectedPlayers.remove(this.focusedButton-3);
				compList.remove(this.focusedButton);
			}*/
			break;
		}
		if(source > 2){
			this.focusedButton = source;
		}
	}
}
