package rocket.src;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import rocket.main.Rocket;

public class MouseHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(int i = 0; i < Rocket.getRocket().currentGui.compList.toArray().length; i++){
			if(((Button) Rocket.getRocket().currentGui.compList.get(i)).isInside(e.getX(), e.getY())){
				((Button) Rocket.getRocket().currentGui.compList.get(i)).click(true);
				if(((Button) Rocket.getRocket().currentGui.compList.get(i)).isCheckBox()) {
					Rocket.getRocket().currentGui.preform(i);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(int i = 0; i < Rocket.getRocket().currentGui.compList.toArray().length; i++){
			if(((Button) Rocket.getRocket().currentGui.compList.get(i)).isInside(e.getX(), e.getY())){
				if(!((Button) Rocket.getRocket().currentGui.compList.get(i)).isCheckBox()) {
					((Button) Rocket.getRocket().currentGui.compList.get(i)).click(false);
					Rocket.getRocket().currentGui.preform(i);
				}
				return;
			}
			if(!((Button) Rocket.getRocket().currentGui.compList.get(i)).isCheckBox()) {
				((Button) Rocket.getRocket().currentGui.compList.get(i)).click(false);
			}
		}
	}

}
