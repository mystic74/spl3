package bgu.spl.net.api.message.bguAckMessages;

import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.message.BguACK;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

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
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode + (short)7 + this.NumOfUsers.getmShort() + this.userNameList.getMyString()+'\0');
	}

}
