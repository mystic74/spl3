package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;

import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.ConnectionsImpl;

@SuppressWarnings("serial")
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
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		bf.put(this.userName.encode());
		bf.putChar('\0');
		bf.put(this.content.encode());
		bf.putChar('\0');
		return bf.array();
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
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> myConnections) {
		if (DataBase.getInstance().getUser(this.userName.getMyString())==null)
		{
			return new BguError((short)11, this.opcode);
		}
		User userForClient = DataBase.getInstance().getUsersForClient(ClientID);


			DataBase.getInstance().addPMmessage(userForClient.getUserName(), this.userName.getMyString() , this.content.getMyString());
			BguFieldString sender = new BguFieldString();
			sender.setString(userForClient.getUserName());
			bguNotification notification =new bguNotification((short)9, (byte)0, sender, this.content);
			
			if (!userForClient.isLogIN())
			{	
				userForClient.addAwaitMessage(notification);
			}
			
			else
			{
				String [] userToArray = new String[1];
				myConnections.sendTo(userToArray,notification);

			}
			
		
		return null;
	}


}
