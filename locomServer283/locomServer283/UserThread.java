package locomServer283;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import com.google.gson.Gson;


public class UserThread extends Thread {

	private Socket s;

	// contains shared structure holding all connected users
	private Users AppUsers;

	// shared structure containing all past broadcasts that haven't timed out.
	private Broadcasts broadcasts;

	// contains the user information for the user connected to this thread
	// User info: locomLocation
	// tags
	// username
	private User user;

	private BufferedReader inStream;
	private PrintWriter outStream;
	public UserThread(Socket s, Users users, Broadcasts broadcasts) {
		this.s = s;
		
		this.AppUsers = users;
		this.broadcasts = broadcasts;
		String[] defaultInterest = {"Locom"};
		
		System.out.println("Userthread created: " + this.getId());

		//sets random user info until the user logs in
		this.user = new User("Unset", new LocomLocation(1.1, 1.1), new InterestTags(defaultInterest), outStream);

	}

	@Override
	public void run() {

		System.out.println("Userthread started running: " + this.getId());

		try{
			this.inStream = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			this.outStream = new PrintWriter(s.getOutputStream());
			this.user.outStream = this.outStream;

			
			String line;
			if (LocomServer.shutdown) {
				this.outStream.println("Bank is Closed");
				this.outStream.flush();
			} else {

				while ((line = inStream.readLine()) != null) {
					//handles message - no error checking - hope for the best
						messageHandle(line);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {

			}
		}

		//removes the user when they disconnect
		synchronized(this.AppUsers){
			this.AppUsers.removeUser(this.user);
		}
		System.out.println("Workerthread exited: " + this.getId());
	}

	public void messageHandle(String msg){
		Gson gson = new Gson();
		//prints raw JSON
		System.out.println("raw message received: "+ msg);
		
		//sets message to empty string if not formatted properly -
		// prints error and avoids exception
		LocomGSON message = gson.fromJson(msg, LocomGSON.class);
		if (message.type == null){
			message.type = "";
		}
		
		//Dispatches to the different functions for handling each type of message
		switch (message.type){
		case "connect":
			System.out.println("received connect");
			connect(message.user);
			break;
		case "update":
			update(message.user);
			break;
		case "broadcast":
			broadcast(message.broadcast, msg);
			break;
		default:
			System.out.println("Uknown or null message type");
		}
		
	}
	//handles connect message
	public void connect(UserSendable connectUser){
		System.out.println("connecting user: " );
		this.user = new User(connectUser.userName, connectUser.locomLocation, connectUser.tags, this.user.outStream);
		
		synchronized(this.AppUsers){
			this.AppUsers.addUser(this.user);
		}
		
	}
	
	//handles update message
	public void update(UserSendable upUser){
		System.out.println("updating user: " );
		
		//@@checking?
		AppUsers.removeUser(this.user);
		this.user = new User(upUser.userName, upUser.locomLocation, upUser.tags, this.user.outStream);
		AppUsers.addUser(this.user);
		
		
	}
	
	//handles broadcast message by adding broadcast to shared broadcasts set 
	//and forwarding to the users in range and interested in the broadcasts
	public void broadcast(Broadcast receivedcast, String msg){
		System.out.println("broadcast recieved " );
		
		synchronized(this.broadcasts){
			this.broadcasts.add(receivedcast);
		}
		
		for (User u: this.AppUsers.users){

			System.out.println("in broadcast forward user is interested in: ");
			u.tags.printUserInterests();
			if (u.inRange(receivedcast.getLocation(), receivedcast.getRadius()) && u.isInterested(receivedcast.getTags())){
				u.send(msg);
				System.out.println("broadcast forwarded");
			}
		}
	}


}
