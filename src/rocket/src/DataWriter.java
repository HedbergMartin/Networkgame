package rocket.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rocket.src.network.Packet;

public class DataWriter {

	ByteArrayOutputStream bos;
	DataOutputStream output;
	
	public DataWriter() {
		this.bos = new ByteArrayOutputStream();
		this.output = new DataOutputStream(this.bos);
	}
	
	public void addInt(int data){
		try {
			this.output.writeInt(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addByte(byte data){
		try {
			this.output.writeByte(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addByteArray(byte[] data){
		try {
			Packet.writeByteArray(this.output, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addBoolean(boolean data){
		try {
			this.output.writeBoolean(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addString(String data){
		try {
			Packet.writeString(output, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] finalizeData(){
		return this.bos.toByteArray();
	}
	
	public Packet finalizePacket(String channel){
		byte[] outputData = this.finalizeData();
		try {
			this.bos.flush();
			this.bos.close();
			this.output.flush();
			this.output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Packet(channel, outputData, outputData.length);
	}
}
