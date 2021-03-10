package digger.netz;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ScoreForTwoPlayers {

	public int score=0;

	public void drawScore(GraphicsContext gc, int id, int i) {

		if(id==1)
		{
			if(i==1)
			{
				gc.setStroke(Color.GREEN);
				gc.strokeText("You: " + score, 2, 10);
			}
			else if(i==2)
			{
				gc.setStroke(Color.CYAN);
				gc.strokeText(score+ " :Your Friend", 680, 10);
			}
		}
		else if(id==2)
		{
			if(i==1)
			{
				gc.setStroke(Color.CYAN);
				gc.strokeText("Your Friend: " + score, 2, 10);
			}
			else if(i==2)
			{
				gc.setStroke(Color.GREEN);
				gc.strokeText(score +" :You", 700, 10);

			}
		}
		

	}

}
