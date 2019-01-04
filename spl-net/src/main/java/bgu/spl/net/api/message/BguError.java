package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;

import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.ConnectionsImpl;

@SuppressWarnings("serial")
public class BguError extends bguProtocol {
	
	private bguFieldShort MessageOpcode;
	
	public BguError(short op, short messageOp) {
		super(op);
		this.MessageOpcode = new bguFieldShort();
		this.MessageOpcode.mShort = messageOp;
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		bf.putShort(MessageOpcode.getmShort());
		return bf.array();
	}
	
	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable act(int ClientID, ConnectionsImpl<bguProtocol> myConnections) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public bguProtocol isDone() {
		if(this.MessageOpcode.isDone())
			return this;
		
		return null;
	}



}
