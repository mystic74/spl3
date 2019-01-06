/**
 * 
 */
package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.impl.BGUEncoderDecoder;
import bgu.spl.net.impl.MsgFeed;
import bgu.spl.net.impl.rci.BguCommandInvocationProtocol;
import bgu.spl.net.srv.Server;

/**
 * @author tom
 *
 */
public class TPCMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MsgFeed feed = new MsgFeed(); //one shared object
		Integer port 		= Integer.decode(args[0]);
		
		Server.threadPerClient(	port,
				() -> new BguCommandInvocationProtocol<>(feed), //protocol factory,
				BGUEncoderDecoder::new).serve(); //message encoder decoder factory)


	}

}
