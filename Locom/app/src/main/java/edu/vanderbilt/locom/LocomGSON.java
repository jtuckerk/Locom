package edu.vanderbilt.locom;

//Wrapper class to allow JSON messages with different objects to be parsed.
//the type tells the message parser which field will not be null.
//Gson allows for fields to be null.
// an example: a message with type "connect" would only be sent with a userSendable object
// 		the connect function does not need a broadcast so it will not care that that field is null
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
