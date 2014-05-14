package rocket.src.powerups;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import rocket.src.Player;
import rocket.src.SpriteSheet;

public class ShieldPowerUp extends PowerUp {
	
	Image image = SpriteSheet.getBufferedImage("/Gui.png", 0, 40, width, height);
	
	public ShieldPowerUp(int id, String name, int amp) {
		super(id, name, amp);
	}
	
	@Override
	public void drawPlayerPrePower(Graphics g, Player player) {
		g.setColor(Color.CYAN);
		g.fillOval(player.posX, player.posY, width+20, height+20);
	}
}
