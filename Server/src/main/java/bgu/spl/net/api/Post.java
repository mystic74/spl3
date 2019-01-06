package bgu.spl.net.api;

public class Post {
	
	private User user;
	private String post;
	
	
	public Post (User u, String p)
	{
		this.user=u;
		this.post=p;
	}
	
	public String getPost()
	{
		return this.post;
	}
	
	public User getUser()
	{
		return this.user;
	}

}
