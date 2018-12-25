package bgu.spl.net.api.message;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class bguNotification extends bguProtocol{

	private char NotificationType;
	private String PostingUser;
	private String Content;
	
	public bguNotification(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.NotificationType+this.PostingUser+'\0'+this.Content+'\0');
	}

	@Override
	protected bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

}
