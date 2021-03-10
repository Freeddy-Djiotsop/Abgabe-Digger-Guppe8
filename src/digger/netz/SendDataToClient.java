package digger.netz;

import java.io.IOException;

class SendDataToClient {

	SendDataToClient(double x, double y, double s, double k, double n, double p, double d, int id, int index)
	{
		
		System.out.println("In sendCoord fuer "+id);
		if(TwoPlayers.allTwoPlayers.get(index).id==index)//Wir stellen sicher dass wir richtig sind
		{
			if(TwoPlayers.allTwoPlayers.get(index).friend.playerId==id)//Wenn der Friend senden moechte, dann an Host senden
			{
				try 
				{
					TwoPlayers.allTwoPlayers.get(index).host.toClient.writeDouble(x);
					TwoPlayers.allTwoPlayers.get(index).host.toClient.writeDouble(y);
					TwoPlayers.allTwoPlayers.get(index).host.toClient.writeDouble(s);
					TwoPlayers.allTwoPlayers.get(index).host.toClient.writeDouble(k);
					TwoPlayers.allTwoPlayers.get(index).host.toClient.writeDouble(n);
					TwoPlayers.allTwoPlayers.get(index).host.toClient.writeDouble(p);
					TwoPlayers.allTwoPlayers.get(index).host.toClient.writeDouble(d);
					System.out.println(x+":"+y+" gesendet an Host "+TwoPlayers.allTwoPlayers.get(index).host.id+", key: "+k);
				} 
				catch (IOException e) 
				{
					
					System.out.println("Friend "+TwoPlayers.allTwoPlayers.get(index).friend.id+"konnte data an Host "+TwoPlayers.allTwoPlayers.get(index).host.id+" nicht senden");
					
				}
			}
			else if(TwoPlayers.allTwoPlayers.get(index).host.playerId==id)//Wenn der Friend senden moechte, dann an Host senden
			{
				try 
				{
					TwoPlayers.allTwoPlayers.get(index).friend.toClient.writeDouble(x);
					TwoPlayers.allTwoPlayers.get(index).friend.toClient.writeDouble(y);
					TwoPlayers.allTwoPlayers.get(index).friend.toClient.writeDouble(s);
					TwoPlayers.allTwoPlayers.get(index).friend.toClient.writeDouble(k);
					TwoPlayers.allTwoPlayers.get(index).friend.toClient.writeDouble(n);
					TwoPlayers.allTwoPlayers.get(index).friend.toClient.writeDouble(p);
					TwoPlayers.allTwoPlayers.get(index).friend.toClient.writeDouble(d);
					System.out.println(x+":"+y+" gesendet an Friend "+TwoPlayers.allTwoPlayers.get(index).friend.id+", key: "+k);
				} 
				catch (IOException e) 
				{
					
					System.out.println("Host "+TwoPlayers.allTwoPlayers.get(index).host.id+" konnte data an Friend "+TwoPlayers.allTwoPlayers.get(index).friend.id+" nicht senden");
					
				}
			}
		}
		System.out.println("Ende von sendeDataToClient");
	}

}
