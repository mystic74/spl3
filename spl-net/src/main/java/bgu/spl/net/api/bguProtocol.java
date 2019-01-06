package bgu.spl.net.api;

import java.io.Serializable;

import bgu.spl.net.api.bidi.ConnectionsImpl;

@SuppressWarnings("serial")
public abstract class bguProtocol  implements Serializable{
	
	protected short opcode;
	
	public bguProtocol(short op)
	{
		this.opcode=op;
	}
	public abstract bguProtocol isDone();
	public abstract byte[] encode();
	public abstract bguProtocol decode(byte nextByte);
	public abstract Serializable act(int ClientID, ConnectionsImpl<bguProtocol> myConnections);
	

}
