package rocket.src.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet {
	
	public byte[] data;
	public String channel;
	public int length;
	
	ByteArrayOutputStream bos;
	ByteArrayInputStream bis;
	public DataOutputStream output;
	public DataInputStream input;
	
	public boolean hasInput = false;
	
	public Packet(String channel, byte[] data) {
		if(data != null) {
			bis = new ByteArrayInputStream(data);
			input = new DataInputStream(bis);
			this.hasInput = true;
		}else {
			this.channel = channel;
		}
		bos = new ByteArrayOutputStream();
		this.output = new DataOutputStream(bos);
	}
	
	public Packet(String channel, byte[] data, int length){
		this.channel = channel;
		this.data = data;
		this.length = length;
	}
	
	public Packet copy() {
		return new Packet(this.channel, this.data, this.length);
	}
	
	public static String readString(DataInput input) throws IOException {
		short short1 = input.readShort();
	    if (short1 > 32767) {
	        throw new IOException("Received string length longer than 32767!");
	    }else if (short1 < 0) {
	        throw new IOException("Received string length is less than zero!");
	    }else {
	        StringBuilder stringbuilder = new StringBuilder();
	
	        for (int j = 0; j < short1; ++j) {
	            stringbuilder.append(input.readChar());
	        }
	
	        return stringbuilder.toString();
	    }
	}

	public static void writeString(DataOutput out, String string) throws IOException {
		if(string.length() > 32767) {
			throw new IOException("String to long!");
		}else {
			out.writeShort(string.length());
			out.writeChars(string);
		}
	}

	/**
     * Writes a byte array to the DataOutputStream
     */
    public static void writeByteArray(DataOutput par0DataOutput, byte[] par1ArrayOfByte) throws IOException
    {
        par0DataOutput.writeShort(par1ArrayOfByte.length);
        par0DataOutput.write(par1ArrayOfByte);
    }

    /**
     * the first short in the stream indicates the number of bytes to read
     */
    public static byte[] readBytesFromStream(DataInput par0DataInput) throws IOException
    {
        short short1 = par0DataInput.readShort();

        if (short1 < 0)
        {
            throw new IOException("Key was smaller than nothing!  Weird key!");
        }
        else
        {
            byte[] abyte = new byte[short1];
            par0DataInput.readFully(abyte);
            return abyte;
        }
    }
    
    public void writeToOutput(DataOutputStream out) throws IOException {
    	writeString(out, channel);
    	writeByteArray(out, bos.toByteArray());
    	this.output.flush();
    	this.output.close();
    	this.bos.flush();
    	this.bos.close();
    }
    
    public void writePacket() throws IOException{
    	writeString(this.output, channel);
    }
    public void readPacket() throws IOException{
    	this.input.close();
    	this.bis.close();
    }
}
