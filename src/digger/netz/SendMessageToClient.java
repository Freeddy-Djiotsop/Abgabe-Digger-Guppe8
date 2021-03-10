package digger.netz;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

class SendMessageToClient
{
	private DateFormat time = DateFormat.getDateTimeInstance();
	
	SendMessageToClient(String msg, Socket socket)
	{
		try{
			for (Unterhaltung client : Unterhaltung.clientsChat) {
				if (client.socket != socket) {
					PrintWriter pw = new PrintWriter(client.socket.getOutputStream(), true);
					pw.println("(" + time.format(new Date()) + ") : " + msg + " ");
				}
			} 
			
		}catch (IOException e) {System.out.println("Couldn't send Message");}
	}
}
