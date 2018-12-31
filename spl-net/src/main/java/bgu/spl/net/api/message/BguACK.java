package bgu.spl.net.api.message;

import java.io.Serializable;

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
	public byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.MassageOpcode);
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable act(int ClientID) {
		// TODO Auto-generated method stub
		return this;
	}



}
