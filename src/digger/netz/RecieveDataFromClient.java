package digger.netz;

import java.io.DataInputStream;
import java.io.IOException;


class RecieveDataFromClient implements Runnable
{
	DataInputStream din;
	int id, stopp=0;
	int gameId;
	double[] gameData;
	int[] playerData;
	boolean status;
	
	RecieveDataFromClient(DataInputStream d, int i, int j)
	{
		status=true;
		gameData = new double[7];
		playerData = new int[2];
		din = d;
		id = i;
		gameId = j;
		playerData[0] = id;
		playerData[1] = gameId;
	}
	
	@Override public void run() 
	{
		while(status)
		{
			try 
			{
				gameData[0] = din.readDouble();
				gameData[1] = din.readDouble();
				gameData[2] = din.readDouble();
				gameData[3] = din.readDouble();
				gameData[4] = din.readDouble();
				gameData[5] = din.readDouble();
				gameData[6] = din.readDouble();
				new SendDataToClient(gameData[0], gameData[1], gameData[2], gameData[3], gameData[4], gameData[5], gameData[6], id, gameId);//by sending I specified you sent
			} 
			catch (IOException e) 
			{
				stopp++;
				System.out.println("konnte data von Client "+ id+ " nicht empfangen");
				if(stopp==10)
					status=false;
			}
		}
	}
}

