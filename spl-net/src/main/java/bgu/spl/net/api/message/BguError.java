package bgu.spl.net.api.message;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguError extends bguProtocol {
	
	private short MessageOpcode;
	
	public BguError(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.MessageOpcode);
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

}
