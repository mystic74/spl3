package bgu.spl.net.api.message;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguFollow extends bguProtocol{
	
	private short Follow;
	private short numOfUsers;
	private String UsersNameList;

	public BguFollow(short op) {
		super(op);
		// TODO Auto-generated constructor stub
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
		return encdec.encode(super.opcode+this.Follow+this.numOfUsers+this.UsersNameList);
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
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
