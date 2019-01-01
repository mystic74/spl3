package bgu.spl.net.api.message;

import java.io.Serializable;

import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.message.bguAckMessages.BguAckStat;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguStat extends bguProtocol {
	
	private BguFieldString userName;
	
	public BguStat(short op) {
		super(op);
		this.userName = new BguFieldString();
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode + 
							 this.userName.getMyString() + '\0');
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		if (!this.userName.isDone())
		{
			this.userName.decode(nextByte);
			return null;
		}
		return this;
	}

	@Override
	public Serializable act(int ClientID) {
		User user = DataBase.getInstance().getUser(this.userName.getMyString());
		if (user==null || !(user.isLogIN()))
		{
			return new BguError((short)11, this.opcode);
		}

		bguFieldShort numOfPost = new bguFieldShort((short)user.getNumOfPosts());
		bguFieldShort numOffollowers = new bguFieldShort((short)user.getNumOfFollowers());
		short following=0;
		for (User users : DataBase.getInstance().getAllUsers())
		{
			if (users.getFollower().contains(user))
			{
				following++;
			}
		}
		bguFieldShort numFollowing = new bguFieldShort(following);
		
		return new BguAckStat((short)10, numOfPost, numOffollowers, numFollowing);

	}



}
