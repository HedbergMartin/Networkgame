package rocket.src;

import java.awt.Graphics;
import java.util.ArrayList;

import rocket.main.Rocket;
import rocket.src.handlers.GameRegister;

public class Gui {
	
	public ArrayList<Object> compList = new ArrayList<Object>();
	
	public Gui() {
		
	}

	/** Updating the Gui **/
	public void update(){
		
	}

	/** Draws the Gui **/
	public void drawGui(Graphics g) {
		
	}

	/** Gets the gui handler **/
	private static Gui getFromHandler(int guiID){
		for(int i = 0; i < GameRegister.getGuiHandlers().size(); i++){
			Gui gui = GameRegister.getGuiHandlers().get(i).registerGui(guiID, Rocket.getRocket().player);
			if(gui != null){
				return gui;
			}
		}
		return null;
	}

	/** Opens a new gui and store the old one in a memory array **/
	public static void openNewGui(int guiID){
		Rocket.getRocket().GuiMemory.add(Rocket.getRocket().GuiMemory.toArray().length, getFromHandler(guiID));
		updateGuiScreen();
	}

	/** Closes the current gui and open the previous one **/
	public static void closeGui(Gui gui){
		Rocket.getRocket().GuiMemory.remove(gui);
		updateGuiScreen();
	}

	/** Update the gui **/
	private static void updateGuiScreen(){
		Rocket.getRocket().currentGui = Rocket.getRocket().GuiMemory.get(Rocket.getRocket().GuiMemory.size()-1);
	}

	/** Called when a button is performed **/
	public void preform(int source){
		
	}
}
