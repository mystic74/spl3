package bgu.spl.net.api.message;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguPM extends bguProtocol{
	
	private String userName;
	private String content;
	
	public BguPM(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.userName+'\0'+this.content+'\0');
	}

	@Override
	protected bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

}
