package rocket.src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import rocket.main.Rocket;

public class KeyBoardHandler implements KeyListener {
	Rocket rocket;
	
	public KeyBoardHandler(Rocket r) {
		this.rocket = r;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case(KeyEvent.VK_W) : rocket.player.buttom[0] = true; break;
		case(KeyEvent.VK_A) : rocket.player.buttom[1] = true; break;
		case(KeyEvent.VK_S) : rocket.player.buttom[2] = true; break;
		case(KeyEvent.VK_D) : rocket.player.buttom[3] = true; break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case(KeyEvent.VK_W) : rocket.player.buttom[0] = false; break;
		case(KeyEvent.VK_A) : rocket.player.buttom[1] = false; break;
		case(KeyEvent.VK_S) : rocket.player.buttom[2] = false; break;
		case(KeyEvent.VK_D) : rocket.player.buttom[3] = false; break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
}
