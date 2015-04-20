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

	// contains structure holding all connected users
	private Users AppUsers;

	// structure containing all past broadcasts that haven't timed out.
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
					if (line.equals("SHUTDOWN")
							&& s.getInetAddress().isLoopbackAddress()) {
						LocomServer.shutdown = true;
						System.out.println("Shutdown request received");
						// System.out.println("Shutdown request received");
						//outStream.flush();
						LocomServer.threads.remove(this);
						Iterator<UserThread> it = LocomServer.threads
								.iterator();
						while (it.hasNext()) {

							try {
								it.next().join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						// jumps out of read while loop and allows thread to
						// close
						// the socket and die
						
						break;

					} else {
						// handle requests
						
						
						messageHandle(line);
						//StringTokenizer tokens = new StringTokenizer(line);
						//outStream.println(message = parseRequest(tokens));
						//System.out.println(message);

					}
					outStream.flush();
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

		synchronized(this.AppUsers){
			this.AppUsers.removeUser(this.user);
		}
		System.out.println("Workerthread exited: " + this.getId());
	}

	public void messageHandle(String msg){
		Gson gson = new Gson();
		System.out.println("raw message received: "+ msg);
		
		LocomGSON message = gson.fromJson(msg, LocomGSON.class);
		if (message.type == null){
			message.type = "";
		}
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
	
	public void connect(UserSendable connectUser){
		System.out.println("connecting user: " );
		this.user = new User(connectUser.userName, connectUser.locomLocation, connectUser.tags, this.user.outStream);
		
		synchronized(this.AppUsers){
			this.AppUsers.addUser(this.user);
		}
		
	}
	public void update(UserSendable upUser){
		System.out.println("updating user: " );
		
		//@@checking?
		AppUsers.removeUser(this.user);
		this.user = new User(upUser.userName, upUser.locomLocation, upUser.tags, this.user.outStream);
		AppUsers.addUser(this.user);
		
		
	}
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
