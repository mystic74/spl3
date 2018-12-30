package bgu.spl.net.api.message;

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

}
