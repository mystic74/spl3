package bgu.spl.net.api.message;


import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

public class BguRegister extends bguProtocol {

	String username; 
	String password;
	
	public BguRegister(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected byte[] encode() {
		
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode+this.username+'\0'+this.password+'\0');

	}


	@Override
	protected bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO complete
	public  void register()
	{
		DataBase.getInstance().register(this.username, this.password);
	}



}
