package bgu.spl.net.api.message.bguAckMessages;

import bgu.spl.net.api.bguFieldShort;
import bgu.spl.net.api.message.BguACK;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;

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
		ObjectEncoderDecoder encdec= new ObjectEncoderDecoder();
		return encdec.encode(super.opcode + (short)8 + this.posts.getmShort()+ this.followers.getmShort()+this.following.mShort);
	}

}
