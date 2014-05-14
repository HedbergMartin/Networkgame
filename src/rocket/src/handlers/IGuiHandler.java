package rocket.src.handlers;

import rocket.src.Gui;
import rocket.src.Player;

public interface IGuiHandler {

	public Gui registerGui(int id, Player player);
}
