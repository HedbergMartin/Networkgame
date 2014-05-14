package rocket.src.handlers;

import rocket.lib.References;
import rocket.main.Rocket;
import rocket.src.Gui;
import rocket.src.GuiInGame;
import rocket.src.GuiJoin;
import rocket.src.GuiLobby;
import rocket.src.GuiMenu;
import rocket.src.Player;

public class GuiHandler implements IGuiHandler {

	@Override
	public Gui registerGui(int id, Player player) {
		if(id == References.GUI_LOBBY){
			return new GuiLobby(player.isHost);
		} else if(id == References.GUI_INGAME){
			return new GuiInGame(Rocket.getRocket(), Rocket.getRocket().theWorld);
		} else if(id == References.GUI_MAINMENU){
			return new GuiMenu();
		} else if(id == References.GUI_JOINSCREEN){
			return new GuiJoin();
		}
		return null;
	}

}
