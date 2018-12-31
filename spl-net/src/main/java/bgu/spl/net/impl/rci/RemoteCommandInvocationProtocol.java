package bgu.spl.net.impl.rci;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.newsfeed.NewsFeed;

import java.io.Serializable;

public class RemoteCommandInvocationProtocol<T> implements BidiMessagingProtocol<Serializable> {
	
	private int  ClientID;
	
	public RemoteCommandInvocationProtocol(NewsFeed feed) {
	}


    @Override
    public boolean shouldTerminate() {
        return false;
    }


	@Override
	public void start(int connectionId, Connections<Serializable> connections) {
		this.ClientID=connectionId;
		//TODO finish
		
	}


	@Override
	public Serializable process(Serializable message) {
		return ((bguProtocol) message).act(this.ClientID);
	}


}
