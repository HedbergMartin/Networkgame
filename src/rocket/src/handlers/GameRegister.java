package rocket.src.handlers;

import java.util.ArrayList;
import java.util.List;

public class GameRegister {
	
	private static List<IGuiHandler> guiHandlers = new ArrayList<IGuiHandler>();

	public static List<IGuiHandler> getGuiHandlers() {
		return guiHandlers;
	}

	public static void registerGuiHandel(IGuiHandler handler){
		guiHandlers.add(handler);
	}
}
