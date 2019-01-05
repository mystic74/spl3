package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.ConnectionsImpl;

@SuppressWarnings("serial")
public class BguLogin extends bguProtocol {
	
	BguFieldString username; 
	BguFieldString password;
	
	public BguLogin(short op) {
		super(op);
		this.username = new BguFieldString();
		this.password = new BguFieldString();
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		
		ByteBuffer bf = ByteBuffer.allocate(2 + 
											this.username.encode().length +
											1 +
											this.password.encode().length + 
											1);
		bf.putShort(opcode);
		bf.put(this.username.encode());
		bf.put((byte) 0);
		bf.put(this.password.encode());
		bf.put((byte) 0);
		return bf.array();
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		if (!this.username.isDone())
		{
			this.username.decode(nextByte);
		}		
		else if (!this.password.isDone())
		{
			this.password.decode(nextByte);
		}

		if (this.username.isDone() && this.password.isDone())
			return this;
		
		return null;
	}

	@Override
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> mConnectins) {
		User user = DataBase.getInstance().getUser(this.username.getMyString());
		
		if (user==null||!(user.getPassword().equals(this.password.getMyString()))||(user.isLogIN()))
		{
			return new BguError((short)11, this.opcode);
		}
		
		DataBase.getInstance().addClientID(user, ClientID);
		user.login();
		ConcurrentLinkedQueue<bguProtocol> msgQueue = user.getAwaitsMessagesAndClear();
		
		if (!msgQueue.isEmpty())
		{
			for (bguProtocol msg:msgQueue)
			{
				String[] userToArray = new String[1];
				userToArray[0]=user.getUserName();
				mConnectins.sendTo(userToArray,msg);
			}
		}
		
		
		return new BguACK((short) 10, this.opcode);
	}

	@Override
	public bguProtocol isDone() {
		if (this.username.isDone() && this.password.isDone())
			return this;
		
		return null;
	}

}
