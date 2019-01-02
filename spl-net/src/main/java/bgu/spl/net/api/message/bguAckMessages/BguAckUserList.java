package bgu.spl.net.api.message.bguAckMessages;

import java.nio.ByteBuffer;

import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.message.BguACK;

@SuppressWarnings("serial")
public class BguAckUserList extends BguACK{
	
	bguFieldShort NumOfUsers;
	BguFieldString userNameList;
	
	public BguAckUserList(short op, bguFieldShort numOfUsers, BguFieldString UserNameList) {
		super(op, (short)7);
		this.NumOfUsers=numOfUsers;
		this.userNameList=UserNameList;
	}
	
	
	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		bf.putShort((short)7);
		bf.putShort(this.NumOfUsers.mShort);
		bf.put(this.userNameList.encode());
		bf.putChar('\0');
		return bf.array();
	}

}
