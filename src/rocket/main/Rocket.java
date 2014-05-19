package rocket.main;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import rocket.src.Gui;
import rocket.src.GuiMenu;
import rocket.src.KeyBoardHandler;
import rocket.src.MouseHandler;
import rocket.src.PlayerHost;
import rocket.src.Properties;
import rocket.src.World;
import rocket.src.handlers.GameRegister;
import rocket.src.handlers.GuiHandler;
import rocket.src.network.NetworkHandler;
import rocket.src.network.PacketHandler;

public class Rocket extends Applet implements Runnable {

	private static final long serialVersionUID = -5003998791936586835L;
	public PlayerHost player;
	private static Rocket rocket;
	public boolean isRunning;
	public Gui currentGui;
	public ArrayList<Gui> GuiMemory = new ArrayList<Gui>();
	public NetworkHandler network;
	public Random random = new Random();
	public Properties properties = new Properties();
	public World theWorld;
	
	
	@Override
	public void start() {
		Thread mainThread = new Thread(this);
		mainThread.setDaemon(true);
		mainThread.start();
	}
	
	public void init(){
		rocket = this;
		this.setName("Rocket");
		this.setSize(400, 600);
		this.addKeyListener(new KeyBoardHandler(this));
		this.addMouseListener(new MouseHandler());
		GameRegister.registerGuiHandel(new GuiHandler());
		GuiMenu game = new GuiMenu();
		GuiMemory.add(0, game);
		currentGui = (Gui) GuiMemory.get(GuiMemory.toArray().length-1);
		player = new PlayerHost(195, 560, 20, String.valueOf(random.nextInt(3000)));
		NetworkHandler.packetHandels.add(new PacketHandler(this));
		this.network = new NetworkHandler(this);
		
		this.isRunning = true;
	}

	public void run() {
		while(isRunning){
			this.currentGui.update();
			this.repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Closing");
		System.exit(0);
	}
	
	@Override
	public void paint(Graphics g) {
		if(isRunning) {
			this.currentGui.drawGui(g);
		}
	}
	
	public static Rocket getRocket(){
		return rocket;
	}
	
	private Image i;
	private Graphics dG;
	@Override
	public void update(Graphics g) {
		if(i == null){
			i = createImage(this.getSize().width, this.getSize().height);
			dG = i.getGraphics();
		}
		dG.setColor(getBackground());
		dG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		dG.setColor(getForeground());
		paint(dG);
		
		g.drawImage(i, 0, 0, this);
	}
}
