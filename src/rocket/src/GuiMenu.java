package rocket.src;

import java.awt.Color;
import java.awt.Graphics;

import rocket.lib.References;
import rocket.main.Rocket;

public class GuiMenu extends Gui {

	Button join = new Button("/Gui.png", 0, 0, 96, 16, "Join Game", 40, 50, 200, 300, 80);
	Button create = new Button("/Gui.png", 0, 0, 96, 16, "Create Game", 40, 50, 300, 300, 80);
	Button options = new Button("/Gui.png", 0, 0, 96, 16, "Options", 40, 50, 400, 300, 80);
	Button exit = new Button("/Gui.png", 0, 0, 96, 16, "Exit", 40, 50, 500, 300, 80);

	public GuiMenu() {
		super();
		compList.add(0, join);
		compList.add(1, create);
		compList.add(2, options);
		compList.add(3, exit);
	}

	@Override
	public void drawGui(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 400, 700);
		join.drawButton(g);
		create.drawButton(g);
		options.drawButton(g);
		exit.drawButton(g);
	}
	
	@Override
	public void preform(int source){
		switch(source) {
		case(0): openNewGui(References.GUI_JOINSCREEN); break;
		case(1): Rocket.getRocket().player.isHost = true; openNewGui(References.GUI_LOBBY); break;
		case(3): Rocket.getRocket().isRunning = false; break;
		}
	}
}
