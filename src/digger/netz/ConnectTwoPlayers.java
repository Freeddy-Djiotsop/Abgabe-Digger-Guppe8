package digger.netz;

class ConnectTwoPlayers
{
	static Thread threadHost;
	static Thread threadFriend;
	static int gameId;
	ConnectTwoPlayers(Client host, Client friend, int i)
	{
		gameId = i;		
		try
		{
			
			host.toClient.writeDouble(400);
			host.toClient.writeDouble(540);
			host.toClient.writeDouble(0);//score
			host.toClient.writeDouble(0);//keyNumber
			host.toClient.writeDouble(0);//goldNugget
			host.toClient.writeDouble(0);//pause
			host.toClient.writeDouble(0);//dying
			threadHost = new Thread(new RecieveDataFromClient(host.fromClient, host.playerId, gameId));
		
			friend.toClient.writeDouble(300);
			friend.toClient.writeDouble(540);
			friend.toClient.writeDouble(0);//score
			friend.toClient.writeDouble(0);//keyNumber
			friend.toClient.writeDouble(0);//goldNugget
			friend.toClient.writeDouble(0);//pause
			friend.toClient.writeDouble(0);//dying
			threadFriend = new Thread(new RecieveDataFromClient(friend.fromClient, friend.playerId, gameId));
			
			//threads laufen lassen
			threadHost.start();
			threadFriend.start();
			System.out.println("Threads fuer Spiel "+(i+1)+" laufen");
		} 
		catch (Exception e) 
		{System.out.println("konnte die Clients "+ host.id +" und " + friend.id + " nicht verbinden");}
	}

}
