package bgu.spl.net.api.message;


import java.io.Serializable;
import java.nio.ByteBuffer;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.ConnectionsImpl;

@SuppressWarnings("serial")
public class BguRegister extends bguProtocol {

	BguFieldString username; 
	BguFieldString password;
	
	public BguRegister(short op) {
		super(op);
		this.username = new BguFieldString();
		this.password = new BguFieldString();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		bf.put(this.username.encode());
		bf.putChar('\0');
		bf.put(this.password.encode());
		bf.putChar('\0');
		return bf.array();
	}


	@Override
	public bguProtocol decode(byte nextByte) {
		if (!this.username.isDone())
		{
			this.username.decode(nextByte);
		}		
		else if (!this.password.isDone())
		{
			this.password.decode(nextByte);
		}

		if (this.username.isDone() && this.password.isDone())
			return this;
		
		return null;
		
	}
	


	@Override
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> myConnections) {
		if (DataBase.getInstance().register(this.username.getMyString(), this.password.getMyString(),ClientID)==false)
		{
			return new BguError((short)11, this.opcode);
		}

		return new BguACK((short) 10, this.opcode);

		
	}



}
