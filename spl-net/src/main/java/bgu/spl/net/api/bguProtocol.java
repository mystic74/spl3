package bgu.spl.net.api;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class bguProtocol  implements Serializable{
	
	protected short opcode;
	
	public bguProtocol(short op)
	{
		this.opcode=op;
	}
	
	public abstract byte[] encode();
	public abstract bguProtocol decode(byte nextByte);
	public abstract Serializable act(int ClientID);
	

}
