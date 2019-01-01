package bgu.spl.net.api;

import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
	
	private int numOfPosts;
	private ConcurrentLinkedQueue<User> followers;
	private boolean isLogin;
	private String userName;
	private String password;
	
	public User(String UserName, String Password)
	{
		this.numOfPosts=0;
		this.followers= new ConcurrentLinkedQueue<>();
		this.isLogin=false;
		this.userName = UserName;
		this.password=Password;
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
		this.isLogin=true;
	}
	
	public void logout()
	{
		this.isLogin= false;
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
