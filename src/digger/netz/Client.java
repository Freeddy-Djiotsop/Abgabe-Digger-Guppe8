package digger.netz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Client implements Runnable
{

	static List<Client> clients = new ArrayList<Client>();
	DataInputStream fromClient;
	DataOutputStream toClient;
	int id;
	int playerId;
	boolean playing;
	boolean connected;
	private boolean status;
	private int stopp;
	
	Client(DataInputStream f, DataOutputStream t, int i) {
		fromClient = f;
		toClient = t;
		id = i;
		playerId = 0;
		playing=false;
		status=true;
		connected = true;
		stopp = 0;
	}

	@Override
	public void run() {
		
		while(!playing&&status)
		{
			hostTryToConnectWithFriend();
		}
	}
	
	private void hostTryToConnectWithFriend()
	{
		Client friend = null;
		try 
		{
			int playerHostId = (int) fromClient.readDouble();
			int playerFriendId = (int) fromClient.readDouble();
			
			if(playerHostId==0)
			{
				if(playerFriendId==0)
				{
					friendAnswer((int)fromClient.readDouble());
				}
				else if(playerFriendId==1)//for Broadcast
				{
					broadcastForFindGame();
				}
				
				return;
			}
			if(playerHostId==playerFriendId)//Zu verhindern, der Versuch mit sich selbst zu spielen
			{
				toClient.writeInt(0);
				toClient.writeDouble(id);
				toClient.writeInt(4);//Das Entscheidend, wird in Controller empfangen
				return;
			}
			
			if(id!=playerHostId)//Wir sind in flaschem Client, kommt fast nie vor!!! 
			{
				return;
			}
			if(clients.size()==1)//Falls nur ein Client mit dem Server verbinden ist
			{
				toClient.writeInt(0);
				toClient.writeDouble(playerFriendId);
				toClient.writeInt(7);
				return;
			}
			for(Client c : clients)//nach dem friend suchen
			{
				
				if(c.id!=id)
				{
					if(c.id==playerFriendId)//Friend gefunden
					{
						if(friend==null)
							friend = c;
						
						if(friend.connected)
						{
							if(!friend.playing)//Wenn nicht am Spielen
							{
								friend.toClient.writeInt(2);//2 fuer accept connexion
								friend.toClient.writeDouble(id);
								friend.toClient.writeInt(6);//um die Luecke in Servicee zu füllen, weil der Wert nur dem Host wichtig ist
								return;
								
							}
							else//Friend spielt schon
							{
								toClient.writeInt(0);
								toClient.writeDouble(friend.id);
								toClient.writeInt(2);
								return;
							}
						}
						else
						{
							toClient.writeInt(0);
							toClient.writeDouble(friend.id);
							toClient.writeInt(7);
							return;
						}

					}
					else//das war nicht der gesuchte ID
						stopp++;//Damit wissen wir ob alle Clients schon durchgelaufen sind
					
					
				}
				else//Das war der host selbst
					stopp++;
				
				if(clients.size()==stopp)//Eingegebener ID ist der Server unbekannt
				{
					toClient.writeInt(0);
					toClient.writeDouble(playerFriendId);
					toClient.writeInt(3);//der friend ist unbekannt
					return;
				}
			}
		}
		catch (IOException e) 
		{
			System.out.println("Konnte id des anderen Client nicht lesen");
			if(++stopp==10)
			{
				status = false;
				connected = false;
			}
		}		
	}

	/**
	 * @param i ist ID des Host, der seinen Freund um ein Spiel bittet
	 * */
	private void friendAnswer(int i)
	{
		Client host=null;
	
		for(Client c : clients)//nach dem friend suchen
		{
			if(c.id!=id)
			{
				if(c.id==i)
				{
					if(host==null)
						host = c;
					try 
					{
						if(host.playing)//dies ist besonders wichtig fuer Find a Game
						{
							this.toClient.writeInt(0);//Wir senden an den Friend dass das spielt nicht gestarten werden soll
							return;
						}
						else//wenn nicht am Spielen
							this.toClient.writeInt(1);//Wir senden an den Friend dass das spielt gestarten werden soll
					} catch (IOException e) {System.out.println("If am Spielen falsch gesendet");}
					
					//Wir gehen hier weitern nur wenn der Host nicht gerade spielt
					int entscheidung = -1;
					try {
						entscheidung = fromClient.readInt();
					} catch (IOException e) {
						System.out.println(id+" Könnte Entscheidung nicht empfangen");
					}
					
					try//Entsheidung wird an Host gesendet
					{
						if(entscheidung==1)
						{
							
								host.toClient.writeInt(0);
								host.toClient.writeDouble(id);
								host.toClient.writeInt(0);
								host.playing=true;
								host.playerId = 1;
								this.playing = true;
								this.playerId = 2;
								int index = Server.anzahlTwoPlayer++;
								TwoPlayers.allTwoPlayers.add(new TwoPlayers(host, this, index));//allTwoPlayers erweitern
								new ConnectTwoPlayers(TwoPlayers.allTwoPlayers.get(index).host, TwoPlayers.allTwoPlayers.get(index).friend, TwoPlayers.allTwoPlayers.get(index).id);
							
						}
						else if(entscheidung==0)
						{
							host.toClient.writeInt(0);//0 damit hostRecieveFromFriend() in Controller aufgerufen wird
							host.toClient.writeDouble(id);
							host.toClient.writeInt(1);//1 um hinzuweisen dass der Friend die Verbindung abgelehnt hat
							host.connected = true;
							host.playing = false;
							this.connected = true;
							this.playing = false;
						} 
						
					}catch (IOException e) {System.out.println("Friend "+id+" konnte Entscheidung nicht richtig senden an Host "+host.id);}
				}
			}
		}
	}


	private void broadcastForFindGame()
	{
		if(clients.size()==1)//Falls nur ein Client mit dem Server verbinden ist
		{
			try {
				toClient.writeInt(0);
				toClient.writeDouble(0);
				toClient.writeInt(7);
				return;
			} catch (IOException e) { System.out.println("In Broadcast, konnte Rückmeldung an Host nicht senden"); }
			
		}
		int stop = 0;
		for(Client friend: clients)
		{
			if(friend!=this)
			{
				if(friend.connected)
				{
					if(!friend.playing)//Wir erreichern nur Client die nicht am Spielen sind
					{
						try {
							connected = true;
							playing = false;
							friend.toClient.writeInt(1);//1 fuer die Broadcast benachrichtigung in Controller
							System.out.println(id);
							friend.toClient.writeDouble(id);
							friend.toClient.writeInt(10);
						} catch (IOException e) { System.out.println("Host "+id+" konnte an "+friend.id+" nicht senden."); }
					}
				}
				else
				{
					stop++;
				}
			}
			else
				stop++;
		}
		if(stop==clients.size())
		{
			try {
				this.toClient.writeInt(0);//0 fuer hostRecieveFromFriend in Controller
				this.toClient.writeDouble(id);
				this.toClient.writeInt(5);
			}  catch (IOException e) { System.out.println("Konnte Rückmelden an Host "+id+" nicht senden, dass kein Spieler verbunden ist"); }
		}
	}
}

