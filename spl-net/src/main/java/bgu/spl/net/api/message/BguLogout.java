package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.ConnectionsImpl;

@SuppressWarnings("serial")
public class BguLogout extends bguProtocol{

	public BguLogout(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		return bf.array();
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		return null;
	}

	@Override
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> myConnections) {
		User userForClient = DataBase.getInstance().getUsersForClient(ClientID);
	
			if (!userForClient.isLogIN())
			{
				return new BguError((short)11, this.opcode);

				
			}
			userForClient.logout();

		return new BguACK((short) 10, this.opcode);

	}

	@Override
	public bguProtocol isDone() {
		// TODO Auto-generated method stub
		return this;
	}


}
