package bgu.spl.net.api.message;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguACK extends bguProtocol {
	
	private short MassageOpcode;
	//TODO add Optional
	
	public BguACK(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.MassageOpcode);
	}

	@Override
	protected bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

}
