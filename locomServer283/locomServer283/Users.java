package locomServer283;

import java.util.HashSet;
import java.util.Set;

//Holds a set of users to be accessed by all users when determining who to send 
// broadcasts to
public class Users {

	public Set<User> users = new HashSet<User>();
	
	public Users(){
		//no op
	}
	
	//adds user returns false if user already exists
	public Boolean addUser(User user){
		return this.users.add(user);
	}
	
	public Boolean exists(User user){
		return this.users.contains(user);
	}
	
	//removes user returns true if user existed and operation was successful
	public Boolean removeUser(User user){
		return this.users.remove(user);
	}
}
