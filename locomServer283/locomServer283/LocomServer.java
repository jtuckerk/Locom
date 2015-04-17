package locomServer283;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class LocomServer {

	public static volatile boolean shutdown = false;
	public static HashSet<UserThread> threads = new HashSet<UserThread>();

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(2000);

		Users users = new Users();
		Broadcasts broadcasts = new Broadcasts();

		// BananaBank bank = new BananaBank("accounts.txt");

		while (!shutdown) {
			Socket s = ss.accept();

			System.out.println("Connection accepted from client at : "
					+ s.getRemoteSocketAddress());

			UserThread wt = new UserThread(s, users, broadcasts);

			threads.add(wt);
			wt.start();

			System.out.println("bank is shutdown: " + shutdown);

		}
		ss.close();
	}
}
