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
			System.out.println("The string is " + myString);
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
		return this.myString.getBytes();
	}

}
