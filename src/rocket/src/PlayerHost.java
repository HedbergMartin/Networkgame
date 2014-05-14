package rocket.src;

public class PlayerHost extends Player {
	
	public boolean requierUpdate = true;

	public PlayerHost(int x, int y, int heal, String username) {
		super(x, y, heal, username);
		
	}

	@Override
	public void update() {
		if(this.buttom[0] == true)
			this.motionY--;
		if(this.buttom[1] == true)
			this.motionX--;
		if(this.buttom[2] == true)
			this.motionY++;
		if(this.buttom[3] == true)
			this.motionX++;
		super.update();
	}
}
