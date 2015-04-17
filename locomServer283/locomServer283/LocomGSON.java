package locomServer283;

public class LocomGSON {
	public String type;
	public Broadcast broadcast;
	public UserSendable user;

	public LocomGSON(String type, Broadcast bc, UserSendable user){
		this.type = type;
		this.broadcast = bc;
		this.user = user;
	}
}
