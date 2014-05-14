package rocket.src.network.packet;

import java.io.IOException;

import rocket.src.Player;
import rocket.src.network.Packet;

public class PacketPlayerUpdate extends Packet {
	
	public Player playerToSend;
	
	public PacketPlayerUpdate(Player player, byte[] data) {
		super("packetPlayerUpdate", data);
		this.playerToSend = player;
	}

	public void writePacket() throws IOException {
		super.writePacket();
		writeString(this.output, playerToSend.name);
		this.output.write(playerToSend.posX);
		this.output.write(playerToSend.posY);
	}
	
	@Override
	public void readPacket() throws IOException {
		this.playerToSend.name = readString(input);
		this.playerToSend.posX = this.input.read();
		this.playerToSend.posY = this.input.read();
		super.readPacket();
	}
}
