package bgu.spl.net.api;

import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
	
	private int numOfPosts;
	private ConcurrentLinkedQueue<User> followers;
	private boolean isLogin;
	private String userName;
	private String password;
	private ConcurrentLinkedQueue<bguProtocol> awaitingMessages;
	
	Object key = new Object();
	
	public User(String UserName, String Password)
	{
		this.numOfPosts=0;
		this.followers= new ConcurrentLinkedQueue<>();
		this.isLogin=false;
		this.userName = UserName;
		this.password=Password;
		this.awaitingMessages = new ConcurrentLinkedQueue<>();
	}
	
	public boolean addAwaitMessage(bguProtocol msg)
	{
		synchronized (key) {
			if (!this.isLogin)
			{
					this.awaitingMessages.offer(msg);
					return true;
			}
			return false;
		}
	}
	
	public ConcurrentLinkedQueue<bguProtocol> getAwaitsMessagesAndClear()
	{
		ConcurrentLinkedQueue<bguProtocol> tempQueue = new ConcurrentLinkedQueue<>();
		tempQueue.addAll(this.awaitingMessages);
		this.awaitingMessages.clear();
		return tempQueue;
	}
	
	public void addFollower(User name)
	{
		this.followers.offer(name);
	}
	
	public boolean removeFollower(User name)
	{
		return this.followers.remove(name);
	}
	
	public void login()
	{
		synchronized (key) {
			this.isLogin=true;	
		}
	}
	
	public void logout()
	{
		synchronized (key) {
			this.isLogin= false;
		}
	}
	
	public void addPost()
	{
		this.numOfPosts++;
	}
	
	public ConcurrentLinkedQueue<User> getFollower()
	{
		return this.followers;
	}
	
	public int getNumOfPosts()
	{
		return this.numOfPosts;
	}
	
	public boolean isLogIN()
	{
		return this.isLogin;
	}
	
	public void changePassword (String Password)
	{
		this.password=Password;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public int getNumOfFollowers()
	{
		return this.followers.size();
	}
	

}
