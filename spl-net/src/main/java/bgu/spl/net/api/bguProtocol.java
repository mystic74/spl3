package bgu.spl.net.api;

public abstract class bguProtocol {
	
	short opcode;
	
	public bguProtocol(short op)
	{
		this.opcode=op;
	}
	
	protected abstract byte[] encode();
	protected abstract bguProtocol decode(byte nextByte);
	

}
