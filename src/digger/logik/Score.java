package digger.logik;

import javafx.scene.canvas.GraphicsContext;

public class Score {

	public int score;
       
        public ResourceManager resourceManager = new ResourceManager();

	public void drawScore(GraphicsContext gc) {

		gc.strokeText("Score : " + score, 2, 10);

	}
      
        
         
        
}
