package bgu.spl.net.api.message;

import java.io.Serializable;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguProtocol;

public class BguUserList extends bguProtocol{

	public BguUserList(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable act(int ClientID) {
		for (User user : DataBase.getInstance().getUsersForClient(ClientID))
		{
			if (!user.isLogIN())
			{
				//TODO send ERROR
			}
			else
			{
				//TODO send ACK
			}
		}
		return this;
	}


}
