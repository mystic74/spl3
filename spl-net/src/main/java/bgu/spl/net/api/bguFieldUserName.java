package bgu.spl.net.api;

public class bguFieldUserName {
	
	String username = "";
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	boolean isDone = false;
	
	public boolean isDone() {
		return isDone;
	}

	public String decode(byte nextByte )
	{
		if (nextByte == 0)
		{
			this.isDone = true;
			return username;
		}
		else
		{
			this.username += (char)nextByte;
		}
		return null;
	}

}
