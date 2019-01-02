package bgu.spl.net.api.message;

import java.io.Serializable;

import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class bguNotification extends bguProtocol{

	private byte NotificationType;
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
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode + 
							 this.NotificationType + 
							 this.PostingUser.getMyString() +'\0' +
							 this.Content.getMyString() +'\0');
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
	public Serializable act(int ClientID) {
		// TODO Auto-generated method stub
		return this;
	}



}
