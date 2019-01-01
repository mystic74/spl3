package bgu.spl.net.api.message.bguAckMessages;

import java.io.Serializable;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.message.BguACK;

@SuppressWarnings("serial")
public class BguAckFollow extends BguACK {

	public BguAckFollow(short op) {
		super(op, (short) 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		// TODO Auto-generated method stub
		return super.encode();
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return super.decode(nextByte);
	}
}
