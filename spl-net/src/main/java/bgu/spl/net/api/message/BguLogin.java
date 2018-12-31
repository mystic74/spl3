package bgu.spl.net.api.message;

import java.io.Serializable;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguLogin extends bguProtocol {
	
	String username;
	String password;

	public BguLogin(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.username+'\0'+this.password+'\0');
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable act(int ClientID) {
		User user=DataBase.getInstance().getUser(this.username);
		if (user==null||!(user.getPassword().equals(this.password))||(user.isLogIN()))
		{
			//TODO send ERROR
		}
		user.login();
		return this;
	}

}
