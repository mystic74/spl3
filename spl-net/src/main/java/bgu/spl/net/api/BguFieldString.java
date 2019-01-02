package bgu.spl.net.api;

import java.nio.ByteBuffer;

public class BguFieldString extends bguField {
	
	String myString = "";
	public String getMyString() {
		return myString;
	}

	public void setString(String username) {
		this.myString = username;
	}

	public String decode(byte nextByte )
	{
		if (nextByte == 0)
		{
			this.isDone = true;
			return myString;
		}
		else
		{
			this.myString += (char)nextByte;
		}
		return null;
	}
	
	public byte[] encode()
	{
		ByteBuffer bf = ByteBuffer.allocate(4);
		for (int i=0;i<this.myString.length();i++)
		{
			bf.putChar(this.myString.charAt(i));
		}
		return bf.array();
	}

}
