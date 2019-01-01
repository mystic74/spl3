package bgu.spl.net.api.message;

import java.io.Serializable;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguLogin extends bguProtocol {
	
	BguFieldString username; 
	BguFieldString password;
	
	public BguLogin(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode +
							 this.username.getMyString() + '\0' + 
							 this.password.getMyString() + '\0');
	}

	@Override
	public bguProtocol decode(byte nextByte) {
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
		User user = DataBase.getInstance().getUser(this.username.getMyString());
		
		if (user==null||!(user.getPassword().equals(this.password))||(user.isLogIN()))
		{
			return new BguError((short)11, this.opcode);
		}
		user.login();
		return new BguACK((short) 10, this.opcode);
	}

}
