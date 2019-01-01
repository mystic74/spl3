package bgu.spl.net.api.message;

import java.io.Serializable;

import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguField;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguPM extends bguProtocol{
	
	private BguFieldString userName;
	private BguFieldString content;
	
	public BguPM(short op) {
		super(op);
		this.userName 	= new BguFieldString();
		this.content 	= new BguFieldString();
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode 				 + 
							 this.userName.getMyString() + '\0' +
							 this.content.getMyString()  + '\0');
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		if (!this.userName.isDone())
		{
			this.userName.decode(nextByte);
			return null;
		}
		if (!this.content.isDone())
		{
			this.content.decode(nextByte);
			return null;
		}
		return this;
	}

	@Override
	public Serializable act(int ClientID) {
		if (DataBase.getInstance().getUser(this.userName.getMyString())==null)
		{
			//TODO send ERROR
		}
		for (User user: DataBase.getInstance().getUsersForClient(ClientID))
		{
			if (!user.isLogIN())
			{
				//TODO send ERROR
			}
			else
			{
				DataBase.getInstance().addPMmessage(user.getUserName(), this.userName.getMyString() , this.content.getMyString());
				//TODO finish
			}
		}
		return this;
	}


}
