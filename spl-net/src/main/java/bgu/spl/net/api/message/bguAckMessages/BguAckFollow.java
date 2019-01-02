package bgu.spl.net.api.message.bguAckMessages;


import java.nio.ByteBuffer;

import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.bguFieldStringList;
import bgu.spl.net.api.message.BguACK;

@SuppressWarnings("serial")
public class BguAckFollow extends BguACK {

	private bguFieldShort numOfUsers;
	private bguFieldStringList UsersNameList;
	
	public BguAckFollow(short op,bguFieldShort Num, bguFieldStringList NameList) {
		super(op, (short) 4);
		this.numOfUsers 	= Num;
		this.UsersNameList 	= NameList;
	}

	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		bf.putShort((short)4);
		bf.putShort(this.numOfUsers.mShort);
		bf.put(this.UsersNameList.toByteArray());
		bf.putChar('\0');
		return bf.array();
	}

	
}
