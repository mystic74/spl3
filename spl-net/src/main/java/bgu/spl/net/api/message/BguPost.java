package bgu.spl.net.api.message;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguPost extends bguProtocol{

	private BguFieldString content;
	
	public BguPost(short op) {
		super(op);
		this.content = new BguFieldString();
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.content.getMyString() + '\0');
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
	
	private ConcurrentLinkedQueue<User> usersToSendTo(int ClientID)
	{
		ConcurrentLinkedQueue<User> usersToSendTo = new ConcurrentLinkedQueue<>();
		
		//adds all the followers
		for (User users : DataBase.getInstance().getUsersForClient(ClientID))
		{
			usersToSendTo.addAll(users.getFollower());
		}
		String name="";
		boolean found =  false;
		int stringIndex = 0;
		int endingIndex = 0;
		
		// -1 = not found i think.
		while (stringIndex != -1)
		{
			stringIndex = this.content.getMyString().indexOf('@', stringIndex);
			endingIndex = this.content.getMyString().indexOf(' ' , stringIndex);
			if (endingIndex == -1)
			{
				// Ima shelahem zona.
				// Should not happen i think.
				return null; // ? I guess? what should i do?
			}
			usersToSendTo.offer(DataBase.getInstance().getUser(this.content.getMyString().substring(stringIndex, endingIndex)));
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
	public Serializable act(int ClientID) {
		ConcurrentLinkedQueue<User> usersToSendPostTo = this.usersToSendTo(ClientID);
		for (User user: DataBase.getInstance().getUsersForClient(ClientID))
		{
			user.addPost();
			DataBase.getInstance().addPost(this.content.getMyString(), user.getUserName());

		}
		//TODO finish
		return this;
	}



}
