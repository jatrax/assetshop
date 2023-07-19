package application;
import java.util.ArrayList;

public class User {
	private int ID;
	private String username;
	private String password;
	private float wallet;
	public ArrayList<Integer> library = new ArrayList<Integer>();
	
	public User(int ID, String username, String password, float wallet,int[] owned) {
		this.ID = ID;
		this.username = username;
		this.password = password;
		this.wallet = wallet;
		if(owned != null)
			for(int i : owned)
				library.add(i);
		
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public float getWallet() {
		return wallet;
	}
	
	public void setWallet(float wallet) {
		this.wallet = wallet;
	}
}