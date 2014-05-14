package rocket.src;

import java.awt.Graphics;
import java.util.ArrayList;

import rocket.main.Rocket;
import rocket.src.handlers.GameRegister;

public class Gui {

	public ArrayList<Object> compList = new ArrayList<Object>();
	
	public Gui() {
		
	}
	
	public void update(){
		
	}
	
	public void drawGui(Graphics g) {
		
	}
	
	private static Gui getFromHandler(int guiID){
		for(int i = 0; i < GameRegister.getGuiHandlers().size(); i++){
			Gui gui = GameRegister.getGuiHandlers().get(i).registerGui(guiID, Rocket.getRocket().player);
			if(gui != null){
				return gui;
			}
		}
		return null;
	}
	
	public static void openNewGui(int guiID){
		Rocket.getRocket().GuiMemory.add(Rocket.getRocket().GuiMemory.toArray().length, getFromHandler(guiID));
		updateGuiScreen();
	}
	
	public static void closeGui(Gui gui){
		Rocket.getRocket().GuiMemory.remove(gui);
		updateGuiScreen();
	}
	
	private static void updateGuiScreen(){
		Rocket.getRocket().currentGui = Rocket.getRocket().GuiMemory.get(Rocket.getRocket().GuiMemory.size()-1);
	}
	
	public void preform(int source){
		
	}
}
