package bgu.spl.net.api.message.bguAckMessages;


import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.bguFieldStringList;
import bgu.spl.net.api.message.BguACK;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

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
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode + (short)4 + this.numOfUsers.getmShort() + this.UsersNameList.toString()+'\0');
	}

	
}
