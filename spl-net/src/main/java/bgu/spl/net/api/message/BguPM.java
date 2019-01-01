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
			return new BguError((short)11, this.opcode);
		}
		for (User user: DataBase.getInstance().getUsersForClient(ClientID))
		{
			if (!user.isLogIN())
			{
				return new BguError((short)11, this.opcode);
			}

			DataBase.getInstance().addPMmessage(user.getUserName(), this.userName.getMyString() , this.content.getMyString());
			BguFieldString sender = new BguFieldString();
			sender.setString(user.getUserName());
			new bguNotification((short)9, (byte)0, sender, this.content);
			//TODO finish
		}
		return this;
	}


}
