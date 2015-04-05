package locomServer283;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.StringTokenizer;

public class WorkerThread extends Thread{

	private Socket s;
	private Users users;
    private Broadcasts broadcasts;

	public WorkerThread(Socket s, Users users, Broadcasts broadcasts) {
		this.s = s;
		this.users = users;
		
		System.out.println("Workerthread created: " + this.getId());
	}

	@Override
	public void run() {

		System.out.println("Workerthread started running: " + this.getId());
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream());

			String line;
			if (LocomServer.shutdown) {
				out.println("Bank is Closed");
				out.flush();
			} else {

				while ((line = is.readLine()) != null) {
					if (line.equals("SHUTDOWN")
							&& s.getInetAddress().isLoopbackAddress()) {
						LocomServer.shutdown = true;
						//out.println("Shutdown request received");
						//System.out.println("Shutdown request received");
						out.flush();
						LocomServer.threads.remove(this);
						Iterator<WorkerThread> it = LocomServer.threads.iterator();
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
						out.println();
						System.out.println();
						out.flush();
						System.out.flush();
						break;

					} else {
						// handle requests
						String message;
						StringTokenizer tokens = new StringTokenizer(line);
						out.println(message = parseRequest(tokens));
						System.out.println(message);

					}
					out.flush();
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

	public String parseRequest(StringTokenizer tokens) {
		if (tokens.countTokens() == 3) {
			int amount = Integer.parseInt(tokens.nextToken());
			int source = Integer.parseInt(tokens.nextToken());
			int destination = Integer.parseInt(tokens.nextToken());

			String message;
			//message = handleRequest(amount, source, destination);

			return "hi//@@";
		} else {
			return "bad request";
		}
	}


}
