package digger.netz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Unterhaltung implements Runnable {
	
	static List<Unterhaltung> clientsChat = new ArrayList<Unterhaltung>();
	Socket socket;
	int number;

	Unterhaltung(Socket s, int n)
	{
		socket = s;
		number = n;

	}

	@Override
	public void run() {

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println("Sie sie sind der Client number " + number);
			pw.println();
			pw.println();
		
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			System.out.println("Verbindung mit dem Client number " + number + " mit der Adresse IP:"
					+ socket.getRemoteSocketAddress().toString());
			String str;
			
			while ((str = br.readLine()) != null) {
				new SendMessageToClient(str, socket);
			}
		} 
		catch (IOException e) {System.out.println("Problem in run in Unterhaltung");}

	}

}

