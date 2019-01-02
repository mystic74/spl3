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
		
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		bf.put(this.content.encode());
		bf.putChar('\0');
		return bf.array();
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		if (!this.content.isDone())
		{
			this.content.decode(nextByte);
			return null;
		}
		
		return this;
	}
	
	private ConcurrentLinkedQueue<String> usersToSendTo(int ClientID)
	{
		ConcurrentLinkedQueue<String> usersToSendTo = new ConcurrentLinkedQueue<>();
		
		//adds all the followers
		for (User users : DataBase.getInstance().getUsersForClient(ClientID))
		{
			for (User followers: users.getFollower())
			{
				usersToSendTo.add(followers.getUserName());
			}
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
		
		/*
		//adds all the @<username> in the post
		for (int i=0;i<this.content.length();i++)
		{
			if (this.content.charAt(i)=='@')
			{
				found = true;
			}
			else 
				if (found= true)
				{
					if (this.content.charAt(i)==' ')
					{
						found= false;
						usersToSendTo.offer(DataBase.getInstance().getUser(name));
						name="";		
					}
					else
					{
						name=name+this.content.charAt(i);
					}
				}
		}
		*/
		return usersToSendTo;
		
	}

	@Override
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> currConnections) {
		ConcurrentLinkedQueue<String> usersToSendPostTo = this.usersToSendTo(ClientID);
		for (User user: DataBase.getInstance().getUsersForClient(ClientID))
		{
			user.addPost();
			DataBase.getInstance().addPost(this.content.getMyString(), user.getUserName());
			BguFieldString userField = new BguFieldString();
			userField.setString(user.getUserName());
			bguNotification notification = new bguNotification((short)9, (byte)1,userField , this.content);
			currConnections.sendTo(usersToSendPostTo.toArray(new String[1]),notification);


		}
		
		return null;
	}



}
