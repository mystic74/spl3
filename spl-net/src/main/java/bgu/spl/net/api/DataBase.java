package bgu.spl.net.api;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;



public class DataBase {
	private static class DataBaseHolder{
		private static DataBase instance= new DataBase();
	}
	
	
	private ConcurrentLinkedQueue<User> UsersList;
	private ConcurrentLinkedQueue<Post> posts;
	private ConcurrentLinkedQueue<PMmessage> PMmessages;
	private ConcurrentHashMap<Integer,User> ClientIdUsers;
	
	private DataBase()
	{
		this.UsersList = new ConcurrentLinkedQueue<>();
		this.posts = new ConcurrentLinkedQueue<>();
		this.PMmessages = new ConcurrentLinkedQueue<>();
		this.ClientIdUsers = new ConcurrentHashMap<>();
	}
	
	public static DataBase getInstance()
	{
		return DataBaseHolder.instance;
	}
	
	
	//add new user to the users list
	//return false if the user is already exist
	public boolean register(String userName, String Password, int ClientID)
	{
		
		for (User user : UsersList) {
			if (user.getUserName().equals(userName))
			{
				return false;
			}	
		}
		
		User user = new User(userName, Password);
		this.UsersList.offer(user);


		ClientIdUsers.put(ClientID, user);

		return true;
	}
	
	
	//delete the user from the users list. return true if user exist and false otherwise
	public boolean unRegister(User userName, int ClientID)
	{
		boolean flag=false;
		for (User user : UsersList) {
			if (user.getUserName().equals(userName.getUserName()))
			{
				flag =  this.UsersList.remove(user);
				
			}
			
		}
		ClientIdUsers.remove(ClientID);

		return flag;
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
	
	public User getUsersForClient(int ClientID)
	{
		return this.ClientIdUsers.get(ClientID);
	}
	
	public String getUsersAsStringsForClient(int ClientID)
	{
		
		return this.ClientIdUsers.get(ClientID).getUserName();

 	}
	
	
	public ConcurrentLinkedQueue<User> getAllUsers()
	{
		return this.UsersList;
	}
	

}
