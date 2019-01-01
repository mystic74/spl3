package bgu.spl.net.api;

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

}
