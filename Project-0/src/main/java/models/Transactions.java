package models;
import java.io.Serializable;
public class Transactions implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String user;
	private String content;
	private int balance = 25;
	
	public Transactions() {
		
	}
	
	public Transactions(String user, String content) {
		this.user = user;
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getContent() {
		return balance;
	}

	public void setContent(String content) {
		this.content = content;
		balance = (Integer.parseInt(content) + balance);
	}

	@Override
	public String toString() {
		return "Post [user=" + user + ", content=" + balance + "]";
	}
	
}
