package bgu.spl.net.api;

public class PMmessage {
	
	private User from;
	private User to;
	private String message;
	
	
	public PMmessage(User From, User To, String msg)
	{
		this.from=From;
		this.to=To;
		this.message=msg;
	}
	
	
	public User getSender()
	{
		return this.from;
	}
	
	public User getReceiver()
	{
		return this.to;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	

}
