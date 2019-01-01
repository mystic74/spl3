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
		// Shouldn't get here should we?
		
		return null;
	}

	@Override
	public Serializable act(int ClientID) {
		ConcurrentLinkedQueue<User> usersForClient = DataBase.getInstance().getUsersForClient(ClientID);
		for (User user : usersForClient) {
			if (!user.isLogIN())
			{
				return new BguError((short)11, this.opcode);

				
			}

			user.logout();

			
		}

		return new BguACK((short) 10, this.opcode);

	}


}
