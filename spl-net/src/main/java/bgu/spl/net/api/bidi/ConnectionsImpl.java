package bgu.spl.net.api.bidi;

import java.util.concurrent.ConcurrentHashMap;

import bgu.spl.net.srv.ConnectionHandler;

public class ConnectionsImpl<T> implements Connections<T> {

	
	private ConcurrentHashMap<Integer, ConnectionHandler<T>> m_ClientList;
	
	public ConnectionsImpl() {
		this.m_ClientList = new ConcurrentHashMap<Integer, ConnectionHandler<T>>();
	}
	
	@Override
	public boolean send(int connectionId, T msg) {
		ConnectionHandler<T> tempHandler = this.m_ClientList.get(connectionId);
		if (tempHandler == null)
			return false;
		
		// Can we trust this? who closes the socket? if we reference this, should the GC not call
		// The destructor?
		tempHandler.send(msg);
		
		return true;
	}

	@Override
	public void broadcast(T msg) {
		// Addressing the keys to avoid a case where we duplicate the connectionHandler,
		// this way, if someone disconnects midway, we won't still send him a broadcast
		for (Integer nKey : this.m_ClientList.keySet()) {
			ConnectionHandler<T> tempHandler = this.m_ClientList.get(nKey);
			if (tempHandler != null)
				tempHandler.send(msg);
		}
	}

	@Override
	public void disconnect(int connectionId) {
		// This method does nothing if the key is not in the map
		this.m_ClientList.remove(connectionId);
	}
	
	public void addConnection(int connectionID, ConnectionHandler<T> CH)
	{
		this.m_ClientList.put(connectionID, CH);
	}
}
