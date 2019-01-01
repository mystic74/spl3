package bgu.spl.net.impl.rci;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.newsfeed.NewsFeed;

import java.io.Serializable;

public class RemoteCommandInvocationProtocol<T> implements BidiMessagingProtocol<Serializable> {
	
	private int  ClientID;
	private Connections<Serializable> connectionInstance;
	
	public RemoteCommandInvocationProtocol(NewsFeed feed) {
	}


    @Override
    public boolean shouldTerminate() {
        return false;
    }


	@Override
	public void start(int connectionId, Connections<Serializable> connections) {
		this.ClientID=connectionId;
		this.connectionInstance = connections;
		//TODO finish
		
	}


	@Override
	public void process(Serializable message) {
		this.connectionInstance.send(this.ClientID,((bguProtocol) message).act(this.ClientID));
	}


}
