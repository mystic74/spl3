package bgu.spl.net.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BguFieldString extends bguField {
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	String myString = new String("".getBytes(), StandardCharsets.UTF_8);
	
	public String getMyString() {
		return new String(out.toByteArray(), StandardCharsets.UTF_8);
	}

	public void setString(String username) {
		this.myString = username;
		out = new ByteArrayOutputStream();
		try {
			out.write(username.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String decode(byte nextByte )
	{
		if (nextByte == 0)
		{
			this.isDone = true;
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
