package bgu.spl.net.api.message;

import java.io.Serializable;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguStat extends bguProtocol {
	
	private String userName;
	
	public BguStat(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.userName+'\0');
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable act(int ClientID) {
		User user = DataBase.getInstance().getUser(this.userName);
		if (user==null || !(user.isLogIN()))
		{
			//TODO send ERROR
		}
		else
		{
			//TODO send ACK
		}
		return this;
	}



}
