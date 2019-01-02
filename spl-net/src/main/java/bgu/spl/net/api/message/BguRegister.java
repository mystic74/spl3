package bgu.spl.net.api.message;


import java.io.Serializable;
import java.nio.ByteBuffer;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;

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
		// TODO Auto-generated method stub
		if (!this.username.isDone())
		{
			this.username.decode(nextByte);
			return null;
		}
		if (!this.password.isDone())
		{
			this.password.decode(nextByte);
			return null;
		}
		return this;
	}
	


	@Override
	public Serializable act(int ClientID) {
		if (DataBase.getInstance().register(this.username.getMyString(), this.password.getMyString(),ClientID)==false)
		{
			return new BguError((short)11, this.opcode);
		}

		return new BguACK((short) 10, this.opcode);

		
	}



}
