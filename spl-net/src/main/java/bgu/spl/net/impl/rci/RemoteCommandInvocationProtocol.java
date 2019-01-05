package bgu.spl.net.impl.rci;

import bgu.spl.net.api.bguProtocol;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.ConnectionsImpl;
import bgu.spl.net.impl.newsfeed.NewsFeed;

import java.io.Serializable;

public class RemoteCommandInvocationProtocol<T> implements BidiMessagingProtocol<Serializable> {
	
	private int  ClientID;
	private Connections<T> connectionInstance;
	
	public RemoteCommandInvocationProtocol(NewsFeed feed) {
	}


    @Override
    public boolean shouldTerminate() {
        return false;
    }


	@Override
	public void start(int connectionId, Connections<Serializable> connections) {
		this.ClientID=connectionId;
		@SuppressWarnings("unchecked")
		Connections<T> connections2 = (Connections<T>) connections;
		this.connectionInstance = connections2;
		//TODO finish
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public void process(Serializable message) {
	Serializable returnVal = ((bguProtocol) message).act(this.ClientID, ((ConnectionsImpl<bguProtocol>)this.connectionInstance));
	if (returnVal != null)
		this.connectionInstance.send(this.ClientID, (T) returnVal);
	
	}


}
