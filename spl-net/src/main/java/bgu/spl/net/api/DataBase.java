package bgu.spl.net.api;

import java.util.concurrent.ConcurrentLinkedQueue;



public class DataBase {
	private static class DataBaseHolder{
		private static DataBase instance= new DataBase();
	}
	
	
	private ConcurrentLinkedQueue<User> UsersList;
	private ConcurrentLinkedQueue<Post> posts;
	private ConcurrentLinkedQueue<PMmessage> PMmessages;
	
	private DataBase()
	{
		this.UsersList = new ConcurrentLinkedQueue<>();
		this.posts = new ConcurrentLinkedQueue<>();
		this.PMmessages = new ConcurrentLinkedQueue<>();
	}
	
	public static DataBase getInstance()
	{
		return DataBaseHolder.instance;
	}
	
	
	//add new user to the users list
	//return false if the user is already exist
	public boolean register(String userName, String Password)
	{
		
		for (User user : UsersList) {
			if (user.getUserName().equals(userName))
			{
				return false;
			}	
		}
		
		this.UsersList.offer(new User(userName, Password));
		return true;
	}
	
	
	//delete the user from the users list. return true if user exist and false otherwise
	public boolean unRegister(User userName)
	{
		for (User user : UsersList) {
			if (user.getUserName().equals(userName))
			{
				return this.UsersList.remove(user);
				
			}
			
		}
		return false;
	}
	
	
	public User getUser(String UserName)
	{
		for (User user : UsersList) {
			if (user.getUserName().equals(UserName))
			{
				return user;
			}
			
		}
		return null;
	}
	
	//adds the post to the posts list and increases the posts number of the user. 
	// return false if the user doesn't exist
	public boolean addPost(String post, String userName)
	{
		boolean result=false;
		for (User user : UsersList) {
			if (user.getUserName().equals(userName))
			{
				user.addPost();
				result=true;
				this.posts.offer(new Post(user,post));
			}
		}
		return result;
		
	}
	
	//adds a PM message to the PMmessages list.
	//return false if the sender or the receiver doesn't exist
	public boolean addPMmessage(String from, String to, String message)
	{
		boolean result=false;
		for (User user1 : UsersList) {
			if (user1.getUserName().equals(from))
			{
				for (User user2 : UsersList)
				{
					if (user2.getUserName().equals(to))
					{
						this.PMmessages.offer(new PMmessage(user1,user2 , message));
						result = true;
					}
				}
					
			}
			
		}
		return result;
	}
	

}