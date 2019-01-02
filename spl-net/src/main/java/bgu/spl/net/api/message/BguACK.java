package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;

import bgu.spl.net.api.bguProtocol;

@SuppressWarnings("serial")
public class BguACK extends bguProtocol {
	
	private short MessageOpcode;
	
	public BguACK(short op, short msgOpCode) {
		super(op);
		this.MessageOpcode = msgOpCode;
	}

	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		bf.putShort(MessageOpcode);
		return bf.array();
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
