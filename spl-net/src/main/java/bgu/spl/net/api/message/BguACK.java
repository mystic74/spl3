package bgu.spl.net.api.message;

import java.io.Serializable;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

@SuppressWarnings("serial")
public class BguACK extends bguProtocol {
	
	private short MessageOpcode;
	
	public BguACK(short op, short msgOpCode) {
		super(op);
		this.MessageOpcode = msgOpCode;
	}

	@Override
	public byte[] encode() {
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode + this.MessageOpcode);
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
