package bgu.spl.net.api.message;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;
import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.bguFieldStringList;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguFollow extends bguProtocol{
	
	private byte Follow;
	private bguFieldShort numOfUsers;
	private bguFieldStringList UsersNameList;

	public BguFollow(short op) {
		super(op);
		this.Follow 		= -1;
		this.numOfUsers 	= new bguFieldShort();
		this.UsersNameList 	= new bguFieldStringList();
	}
	
	
	private ConcurrentLinkedQueue<User> stringNamesToList()
	{
		ConcurrentLinkedQueue<User> list =  new ConcurrentLinkedQueue<>();
		String CurUser = "";
		for (int i=0;i<this.UsersNameList.length();i++)
		{
			if (!(this.UsersNameList.charAt(i)=='\0'))
			{
				CurUser=CurUser+ UsersNameList.charAt(i);
			}
			else
			{
				list.offer(DataBase.getInstance().getUser(CurUser));
				CurUser="";
			}
		}
		return list;
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode + this.Follow + this.numOfUsers.getmShort() + this.UsersNameList.toString());
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		if (this.Follow == -1)
		{
			this.Follow = nextByte;
			return null;
		}
		if (!this.numOfUsers.isDone())
		{
			this.numOfUsers.decode(nextByte);
			return null;
		}
		
		if (!this.UsersNameList.isDone())
		{
			this.UsersNameList.decode(nextByte, new Integer(this.numOfUsers.getmShort()));
			return null;
		}
		
		return this;
	}

	@Override
	public Serializable act(int ClientID) {
		ConcurrentLinkedQueue<User> usersForClient= DataBase.getInstance().getUsersForClient(ClientID);
		
		//for all the users of the client
		for (User follower : usersForClient) {
			int succesfullNum=0;

			if (!follower.isLogIN())
			{
				//TODO Send ERROR
			}
			else
			{
				for (User toFollow : stringNamesToList()) {
					if ((this.Follow==0) && ( toFollow.getFollower().contains(follower)==false))
					{
						toFollow.addFollower(follower);
						succesfullNum++;
					}
					if ((this.Follow==1) && (toFollow.getFollower().contains(follower)))
					{
						toFollow.removeFollower(follower);
						succesfullNum++;
					}
					
				}
			}
			if (succesfullNum==0)
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
