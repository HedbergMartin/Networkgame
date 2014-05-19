package rocket.src;

public class PlayerHost extends Player {
	
	public boolean requierUpdate = false;

	public PlayerHost(int x, int y, int heal, String username) {
		super(x, y, heal, username);
		
	}

	@Override
	public void onUpdate() {
		this.requierUpdate = false;
		if(this.buttom[0] == true) {
			this.motionY--;
			this.requierUpdate = true;
		}
		if(this.buttom[1] == true) {
			this.motionX--;
			this.requierUpdate = true;
		}
		if(this.buttom[2] == true) {
			this.motionY++;
			this.requierUpdate = true;
		}
		if(this.buttom[3] == true) {
			this.motionX++;
			this.requierUpdate = true;
		}
		super.onUpdate();
	}
}
