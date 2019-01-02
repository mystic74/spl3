package bgu.spl.net.api.message.bguAckMessages;

import java.nio.ByteBuffer;

import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.message.BguACK;

@SuppressWarnings("serial")
public class BguAckStat extends BguACK {
	  
	private bguFieldShort posts;
	private bguFieldShort followers;
	private bguFieldShort following;
	
	public BguAckStat(short op, bguFieldShort numPosts, bguFieldShort numFollowers, bguFieldShort NumFollowing ) {
		super(op, (short)8);
		this.posts=numPosts;
		this.followers=numFollowers;
		this.following=NumFollowing;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public byte[] encode() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(opcode);
		bf.putShort((short)8);
		bf.putShort(this.posts.mShort);
		bf.putShort(this.followers.mShort);
		bf.putShort(this.following.mShort);
		return bf.array();
	}

}
