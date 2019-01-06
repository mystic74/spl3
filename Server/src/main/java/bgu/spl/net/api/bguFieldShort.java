package bgu.spl.net.api;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class bguFieldShort extends bguField{

	private final ByteBuffer shortRecived  = ByteBuffer.allocate(2);
	public short mShort;
	
	public bguFieldShort() {
		shortRecived.order(ByteOrder.BIG_ENDIAN);
	}
	
	public bguFieldShort(short number)
	{
		this.mShort=number;
	}
	
	public short getmShort() {
		return mShort;
	}

	public bguFieldShort decode(byte nextByte) {
		shortRecived.put(nextByte);
        if (!shortRecived.hasRemaining()) { //we read 2 bytes and therefore can take the length
        	shortRecived.flip();
        	this.mShort = shortRecived.getShort();
        	this.isDone = true;
        	return this;
        }
        
		return null;
	}
	
	
	
	
}
