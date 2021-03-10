package digger.netz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


//import Server.Client;
//import Server.RecievePositionFromClient;

public class Server
{

	private ServerSocket serverSocket;
	

	private int id=2021000;
	public static int anzahlClient=0, anzahlTwoPlayer=0;
	
	public Server()
	{	
		
		try 
		{
			serverSocket = new ServerSocket(2021);
			acceptConnexion();
		
		}
		catch (IOException e) {}
	}

	public void acceptConnexion()
	{
		while(anzahlClient<100)//Wir erlauben 100 Client.
		{
			try 
			{
				System.out.println("Warten auf Verbindung von Client "+(anzahlClient+1)+" ...");
				Socket socketClient = serverSocket.accept();
				
				DataInputStream fromClient = new DataInputStream(socketClient.getInputStream());
				DataOutputStream toClient = new DataOutputStream(socketClient.getOutputStream());
				
				toClient.writeDouble(id);//id senden
				Client.clients.add(new Client(fromClient, toClient, id));//client hinzufuegen
				new Thread(Client.clients.get(anzahlClient)).start();//sein Thread vorbereiten und laufen lassen. dieser Client kann Daten an Server senden
//				Unterhaltung.clientsChat.add(new Unterhaltung(socketClient, id));
//				new Thread(Unterhaltung.clientsChat.get(anzahlClient)).start();
				id +=1;
				anzahlClient++;
			} 
			catch (IOException e) 
			{System.out.println("konnte Client "+ (anzahlClient+1) +" nicht verbinden");}
		}
	}	
	
	
	@SuppressWarnings("unused")
	private void removeThisClientFromTheList()
	{
		
	}
			
	public static void main(String[] args) { new Server(); }
}

