package bgu.spl.net.api;

import bgu.spl.net.api.message.BguFollow;
import bgu.spl.net.api.message.BguLogin;
import bgu.spl.net.api.message.BguLogout;
import bgu.spl.net.api.message.BguPM;
import bgu.spl.net.api.message.BguPost;
import bgu.spl.net.api.message.BguRegister;
import bgu.spl.net.api.message.BguStat;
import bgu.spl.net.api.message.BguUserList;

public class bguMessageFactory {
	
	
	public bguProtocol getMessage(short Opcode)
	{
		switch(Opcode){
		case 1:
			return new BguRegister(Opcode);
		case 2:
			return new BguLogin(Opcode);
		case 3:
			return new BguLogout(Opcode);
		case 4:
			return new BguFollow(Opcode);
		case 5:
			return new BguPost(Opcode);
		case 6:
			return new BguPM(Opcode);
		case 7:
			return new BguUserList(Opcode);
		case 8:
			return new BguStat(Opcode);
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		}
		
		return null;
	}

}
