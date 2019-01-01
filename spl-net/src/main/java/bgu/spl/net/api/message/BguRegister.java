package bgu.spl.net.api.message;


import java.io.Serializable;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

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
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.username.getMyString()+'\0'+this.password+'\0');
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
			//TODO send ERROR
		}
		else
		{
			return new BguACK((short) 10, this.opcode);
		}
		return this;
		
	}



}
