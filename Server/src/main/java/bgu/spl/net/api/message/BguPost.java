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
public class BguPost extends bguProtocol{

	private BguFieldString content;
	
	public BguPost(short op) {
		super(op);
		this.content = new BguFieldString();
	}

	@Override
	public byte[] encode() {
		
		ByteBuffer bf = ByteBuffer.allocate(2 +
											this.content.encode().length + 
											1);
		bf.putShort(opcode);
		bf.put(this.content.encode());
		bf.put((byte) 0);
		return bf.array();
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		if (!this.content.isDone())
		{
			this.content.decode(nextByte);
		}
		if (this.content.isDone())
			return this;
		
		return null;
	}
	
	private ConcurrentLinkedQueue<User> UsersToSend(int ClientID)
	{
		ConcurrentLinkedQueue<User> usersToSendTo = new ConcurrentLinkedQueue<>();
		User user = DataBase.getInstance().getUsersForClient(ClientID);
		
		if (user == null)
			return null;
		
		for (User followers: user.getFollower())
		{
			usersToSendTo.add(followers);
		}
		
		int stringIndex = 0;
		int endingIndex = 0;
		
		stringIndex = this.content.getMyString().indexOf('@', stringIndex);
		endingIndex = this.content.getMyString().indexOf(' ' , stringIndex);
		
		while (stringIndex != -1)
		{			
			// We have a '@' but can't fine a ' ', must be the end of the message.
			if (endingIndex == -1)
			{
				endingIndex = this.content.getMyString().length();
			}
			
			String substring = this.content.getMyString().substring(stringIndex + 1, endingIndex);
			User usrFromString = DataBase.getInstance().getUser(substring);
			if (usrFromString != null)
				usersToSendTo.offer(usrFromString);		
		
			stringIndex = this.content.getMyString().indexOf('@', stringIndex + 1);
			endingIndex = this.content.getMyString().indexOf(' ' , stringIndex + 1);
		}
		
		return usersToSendTo;
	}
	
	@SuppressWarnings("unused")
	private ConcurrentLinkedQueue<String> usersToSendTo(int ClientID)
	{
		ConcurrentLinkedQueue<String> usersToSendTo = new ConcurrentLinkedQueue<>();
		
		//adds all the followers
		User user = DataBase.getInstance().getUsersForClient(ClientID);
			for (User followers: user.getFollower())
			{
				usersToSendTo.add(followers.getUserName());
			}
		
		
//		String name="";
//		boolean found =  false;
		int stringIndex = 0;
		int endingIndex = 0;
		
		// -1 = not found i think.
		while (stringIndex != -1)
		{
			stringIndex = this.content.getMyString().indexOf('@', stringIndex);
			endingIndex = this.content.getMyString().indexOf(' ' , stringIndex);
			
			// We have a '@' but can't fine a ' ', must be the end of the message.
			if (endingIndex == -1)
			{
				endingIndex = this.content.getMyString().length();
			}
			
			usersToSendTo.offer(DataBase.getInstance().getUser(this.content.getMyString().substring(stringIndex, endingIndex)).getUserName());
		}
	
		return usersToSendTo;
		
	}

	@Override
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> currConnections) {
		
		ConcurrentLinkedQueue<User> usersToSend = this.UsersToSend(ClientID);
		
		if(usersToSend == null)
			return null;
		
		User user = DataBase.getInstance().getUsersForClient(ClientID);
		if (user == null)
			return null;
		
		DataBase.getInstance().addPost(this.content.getMyString(), user.getUserName());
		BguFieldString userField = new BguFieldString();
		userField.setString(user.getUserName());
		bguNotification notification = new bguNotification((short)9, (byte)1, userField, this.content);
		
		for (User sendToUser: usersToSend)
		{
			if (!sendToUser.addAwaitMessage(notification))
			{
				String[] list = { sendToUser.getUserName() };
				currConnections.sendTo(list, notification);
			}				
		}
		
		return new BguACK((short) 10, this.opcode);
	}

	@Override
	public bguProtocol isDone() {
		if (this.content.isDone())
			return this;
		
		return null;
	}



}
