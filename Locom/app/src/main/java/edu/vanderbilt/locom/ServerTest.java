package edu.vanderbilt.locom;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


import com.google.gson.Gson;

public class ServerTest {
	static final String SERVER_ADDRESS = "localhost";
	public static final int PORT = 2000;

	public static void main(String[] args) throws IOException {

		Socket socket = new Socket(SERVER_ADDRESS, PORT);
		System.out.println("Client benchmark connected to server");

		// set up input and output streams
		PrintStream ps = new PrintStream(socket.getOutputStream());

		
			// ask the server to transfer $1 from source to destination account
			
			String [] tag = {"babies","free","food"};
			InterestTags tags = new InterestTags(tag);
			User u = new User("UserA", new Location(15.5, 15.5), tags , null);
			
			Gson gson = new Gson();
			
			UserSendable us = new UserSendable(u);
			
			LocomGSON LOCOMmsg = new LocomGSON("connect",null,us);

			System.out.println("Here2");
			//GsonTestClass tc = new GsonTestClass();
			String jsonStr = gson.toJson(LOCOMmsg);
			
			System.out.println(jsonStr);
			
			String line = jsonStr;
			ps.println(line);		
			
			socket.close();
	}
 
}