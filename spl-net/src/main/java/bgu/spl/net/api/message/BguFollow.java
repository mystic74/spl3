package bgu.spl.net.api.message;

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

}
