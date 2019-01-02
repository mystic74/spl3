package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;

import bgu.spl.net.api.bguProtocol;

public class BguError extends bguProtocol {
	
	private short MessageOpcode;
	
	public BguError(short op, short messageOp) {
		super(op);
		this.MessageOpcode=messageOp;
		// TODO Auto-generated constructor stub
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
