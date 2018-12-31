package bgu.spl.net.api.message;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguLogout extends bguProtocol{

	public BguLogout(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode);
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable act(int ClientID) {
		boolean flag=true;
		ConcurrentLinkedQueue<User> usersForClient = DataBase.getInstance().getUsersForClient(ClientID);
		for (User user : usersForClient) {
			if (!user.isLogIN())
			{
				//TODO send ERROR
				flag=false;
				
			}
			else
			{
				user.logout();
			}
			
		}
		if (flag)
		{
			//TODO send ACK
		}
		return this;
	}


}
