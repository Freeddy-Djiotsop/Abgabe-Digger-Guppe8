package digger.netz;

import java.util.ArrayList;
import java.util.List;

class TwoPlayers
{
	static List<TwoPlayers> allTwoPlayers = new ArrayList<TwoPlayers>();
	Client host;
	Client friend;
	boolean status=true;
	int id;
	
	TwoPlayers(Client h, Client f, int i)
	{
		host = h;
		friend = f;
		id = i;
	}
}

