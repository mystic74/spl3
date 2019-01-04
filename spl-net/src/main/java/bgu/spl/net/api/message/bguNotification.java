package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;

import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.ConnectionsImpl;

@SuppressWarnings("serial")
public class bguNotification extends bguProtocol{

	private byte NotificationType = -1;
	private BguFieldString PostingUser;
	private BguFieldString Content;
	
	public bguNotification(short op, byte type,BguFieldString posting, BguFieldString content) {
		super(op);
		// Invalid value
		this.NotificationType 	= type;
		this.PostingUser 		= posting;
		this.Content 			= content;
	}

	@Override
	public byte[] encode() {
		
		ByteBuffer bf = ByteBuffer.allocate(2 +
											1 + 
											this.PostingUser.encode().length +
											1 + 
											this.Content.encode().length +
											1);
		bf.putShort(opcode);
		bf.put(this.NotificationType);
		bf.put(this.PostingUser.encode());
		bf.put((byte) 0);
		bf.put(this.Content.encode());
		bf.put((byte) 0);
		return bf.array();
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		
		if (this.NotificationType == -1)
		{
			this.NotificationType = nextByte;
			return null;
		}
		if (!this.PostingUser.isDone())
		{
			this.PostingUser.decode(nextByte);
			return null;
		}
		
		if (!this.Content.isDone())
		{
			this.Content.decode(nextByte);
			return null;
		}
		
		return this;
	}

	@Override
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> myConnections) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public bguProtocol isDone() {
		if ((this.NotificationType != -1) && this.Content.isDone() && this.PostingUser.isDone())
			return this;
		
		return null;
	}

                  

}
