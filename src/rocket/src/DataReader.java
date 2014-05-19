package rocket.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import rocket.src.network.Packet;

public class DataReader {
	ByteArrayInputStream byteInput;
    DataInputStream inputStream;
    
    public DataReader(byte[] data) {
		this.byteInput = new ByteArrayInputStream(data);
		this.inputStream = new DataInputStream(this.byteInput);
	}
    
    public int getInt() {
    	int i = 0;
    	try {
			i = this.inputStream.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return i;
    }
    
    public boolean getBoolean() {
    	try {
			return this.inputStream.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public float getFloat() {
    	try {
			return this.inputStream.readFloat();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return 0.0F;
    }
    
    public double getDouble() {
    	double d = 0.0D;
    	try {
			d = this.inputStream.readDouble();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return d;
    }
    
    public byte getByte() {
    	byte b = 0;
    	try {
			b = this.inputStream.readByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return b;
    }
    
    public byte[] getByteArray() {
    	byte[] b = null;
    	try {
			b = Packet.readBytesFromStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return b;
    }
    
    public String getString() {
    	try {
			return Packet.readString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    public long getLong() {
    	try {
			return this.inputStream.readLong();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return 0;
    }
}
