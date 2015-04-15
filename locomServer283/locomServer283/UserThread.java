package locomServer283;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.google.gson.Gson;


public class UserThread extends Thread {

	private Socket s;

	// contains structure holding all connected users
	private Users AppUsers;

	// structure containing all past broadcasts that haven't timed out.
	private Broadcasts broadcasts;

	// contains the user information for the user connected to this thread
	// User info: location
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
		this.user = new User("Unset", new Location(1.1, 1.1), new InterestTags(defaultInterest), outStream);

		System.out.println("Userthread created: " + this.getId());
		
		try {
			this.inStream = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			this.outStream = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {

			}
		}

	}

	@Override
	public void run() {

		System.out.println("Userthread started running: " + this.getId());

		try{
			String line;
			if (LocomServer.shutdown) {
				this.outStream.println("Bank is Closed");
				this.outStream.flush();
			} else {

				while ((line = inStream.readLine()) != null) {
					if (line.equals("SHUTDOWN")
							&& s.getInetAddress().isLoopbackAddress()) {
						LocomServer.shutdown = true;
						// out.println("Shutdown request received");
						// System.out.println("Shutdown request received");
						outStream.flush();
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
						outStream.println();
						System.out.println();
						outStream.flush();
						System.out.flush();
						break;

					} else {
						// handle requests
						String message;
						StringTokenizer tokens = new StringTokenizer(line);
						outStream.println(message = parseRequest(tokens));
						System.out.println(message);

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

		System.out.println("Workerthread exited: " + this.getId());
	}

	public void parseJson(){
		//serialize
		Gson gson = new Gson();
		//String xyzAsString = gson.toJson(xyz);
		
		//deserialize
		//Classname xyz = gson.fromJson(JSONedString, Classname.class);
	}
	public String parseRequest(StringTokenizer tokens) {
		if (tokens.countTokens() == 3) {
			int amount = Integer.parseInt(tokens.nextToken());
			int source = Integer.parseInt(tokens.nextToken());
			int destination = Integer.parseInt(tokens.nextToken());

			String message;
			// message = handleRequest(amount, source, destination);

			return "hi//@@";
		} else {
			return "bad request";
		}
	}

}
