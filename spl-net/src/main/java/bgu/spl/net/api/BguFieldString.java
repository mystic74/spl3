package bgu.spl.net.api;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class BguFieldString extends bguField {
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	String myString = new String("".getBytes(), StandardCharsets.UTF_8);
	
	public String getMyString() {
		return new String(out.toByteArray(), StandardCharsets.UTF_8);
	}

	public void setString(String username) {
		this.myString = username;
	}

	public String decode(byte nextByte )
	{
		if (nextByte == 0)
		{
			this.isDone = true;
			System.out.println("The string is " + new String(out.toByteArray(), StandardCharsets.UTF_8));
			//return myString;
	        return new String(out.toByteArray(), StandardCharsets.UTF_8);

		}
		else
		{
			out.write(nextByte);
			this.myString += (char)nextByte;
		}
		return null;
	}
	
	public byte[] encode()
	{
		return out.toByteArray();
	}

}
