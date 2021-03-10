package digger.netz;

import java.io.DataInputStream;
import java.io.IOException;


class RecieveDataFromServer implements Runnable
{
	DataInputStream din;
	double[] gameData;
	int id, stopp=0;
	
	RecieveDataFromServer(DataInputStream d, int i) 
	{
		gameData = new double[5];
		din = d;
		id = i;
		System.out.println("Input fuer "+id+" bereit");
	}
	
	@Override public void run()
	{
		int i=0;
		while(true)
		{i++;
			try 
			{
				System.out.println(id+" Warte"+i+" auf Daten...");
				gameData[0] =  din.readDouble();
				System.out.println("c bon avec x: "+gameData[0]);
				gameData[1] =  din.readDouble();
				System.out.println("c bon avec x: "+gameData[0]+" et y: "+gameData[1]);
				gameData[2] = din.readDouble();
				gameData[3] = din.readDouble();
				gameData[4] = din.readDouble();
				System.out.println("c bon avec x: "+gameData[0]+", y: "+gameData[1]+" et k:"+gameData[2]);
				System.out.println(gameData[2]+": "+id+" schickt "+gameData[0]+"|"+gameData[1]);
//				updateCoord(x,y,s,k,n,id);//id help to know you recieve the data
			} 
			catch (IOException e) 
			{
				stopp++;
				System.out.println(id+" konnte data nicht empfangen");
				if(stopp==10)
					System.exit(-1);
			}
		}
		
	}
	
}

