package rocket.src;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import rocket.lib.References;
import rocket.main.Rocket;

public class GuiJoin extends Gui {
	
	Button join = new Button("/Gui.png", 0, 0, 96, 16, "Join Game", 40, 50, 360, 300, 60);
	Button ip = new Button("/Gui.png", 0, 0, 96, 16, "Set IP", 40, 50, 440, 300, 60);
	Button cancel = new Button("/Gui.png", 0, 0, 96, 16, "Cancel", 40, 50, 520, 300, 60);
	
	public GuiJoin() {
		this.compList.add(0, join);
		this.compList.add(1, ip);
		this.compList.add(2, cancel);
	}
	
	@Override
	public void drawGui(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 400, 700);
		g.setColor(Color.WHITE);
		g.drawString(Rocket.getRocket().properties.IP, 100, 200);
		join.drawButton(g);
		ip.drawButton(g);
		cancel.drawButton(g);
	}
	
	@Override
	public void preform(int source) {
		if(source == 0) {
			openNewGui(References.GUI_LOBBY);
		}else if(source == 1) {
			Object ip = JOptionPane.showInputDialog("Set IP address to join.");
			if(ip != null && ip instanceof String) {
				Rocket.getRocket().properties.IP = (String) ip;
			}
		}else if(source == 2) {
			closeGui(this);
		}
	}
}
