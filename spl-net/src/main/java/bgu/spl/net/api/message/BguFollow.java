package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.bguFieldStringList;
import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.ConnectionsImpl;
import bgu.spl.net.api.message.bguAckMessages.BguAckFollow;

@SuppressWarnings("serial")
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
		for (BguFieldString bguFieldusername : this.UsersNameList.get_userNameList()) {
			User of = DataBase.getInstance().getUser(bguFieldusername.getMyString());
			if (of != null)
			{
				list.offer(of);
			}
			
		}
		return list;
	}

	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(this.opcode);
		bf.put(Follow);
		bf.putShort(this.numOfUsers.mShort);
		bf.put(this.UsersNameList.toByteArray());
		return bf.array();
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
		}
		
		if (this.UsersNameList.isDone())
			return this;
		
		return null;
	}

	@Override
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> myConnections) {
		User userForClient= DataBase.getInstance().getUsersForClient(ClientID);
		
		//for all the users of the client

			int succesfullNum=0;

			if ((userForClient == null ) || (!userForClient.isLogIN()))
			{
				return new BguError((short)11, this.opcode);
			}
			else
			{
				
				for (User toFollow : stringNamesToList()) {
					
			
					if ((this.Follow==0) && ( toFollow.getFollower().contains(userForClient)==false))
					{
						toFollow.addFollower(userForClient);
						succesfullNum++;
					}
					if ((this.Follow==1) && (toFollow.getFollower().contains(userForClient)))
					{
						toFollow.removeFollower(userForClient);
						succesfullNum++;
					}
					
				}
			}
			if (succesfullNum==0)
			{
				return new BguError((short)11, this.opcode);
			}

		
		return new BguAckFollow((short)10, this.numOfUsers, this.UsersNameList);

	}





	@Override
	public bguProtocol isDone() {
		if ((this.Follow != -1) && this.numOfUsers.isDone() && this.UsersNameList.isDone())
			return this;
		return null;
	}



}
