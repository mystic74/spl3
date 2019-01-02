package bgu.spl.net.api.message;

import java.io.Serializable;
import java.nio.ByteBuffer;

import bgu.spl.net.api.BguFieldString;
import bgu.spl.net.api.DataBase;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.message.bguAckMessages.BguAckUserList;

@SuppressWarnings("serial")
public class BguUserList extends bguProtocol{

	public BguUserList(short op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		return bf.array();
	}

	@Override
	public bguProtocol decode(byte nextByte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable act(int ClientID) {
		for (User user : DataBase.getInstance().getUsersForClient(ClientID))
		{
			if (!user.isLogIN())
			{
				return new BguError((short)11, this.opcode);
			}
			
			BguFieldString allUsers = new BguFieldString();
			String tempUsersString="";
			for (User users: DataBase.getInstance().getAllUsers())
			{
				tempUsersString= tempUsersString+users.getUserName()+'\0';
			}
			allUsers.setString(tempUsersString);
			bguFieldShort numOfUsers = new bguFieldShort((short)DataBase.getInstance().getAllUsers().size());

			return new BguAckUserList((short)10,numOfUsers,allUsers);
			//i need to send Ack for each of the Client Users
			//but i don't know how to send Ack without return 
			//TODO change this! send Ack to the current user. delete the return
		}
		return this;
	}


}
