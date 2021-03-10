package digger.gui.base;

import java.io.IOException;
import java.util.ArrayList;



import digger.gui.controller.Controller;
import digger.gui.extend.BackgroundPane;
import digger.gui.extend.ImageButton;
//import digger.gui.extend.Image_label;
import javafx.scene.Scene;

/**
 * 
 * @author Afraa Habbab
 *
 */
public class FindGameMenu {
	private Scene scene;
	private BackgroundPane pane;
	private ArrayList<ImageButton> buttons;
	
	public FindGameMenu() throws IOException{
		pane = new BackgroundPane(("/digger/gui/extend/FindGameBG.jpg"));
		try {
			newButton();
		}catch (IOException e) {
			System.out.println("In Package gui.base, in der Funktion FindGameMenu()");
		}
		scene = new Scene(pane);
		pane.getChildren().addAll(buttons);
	
	}
		
		private void newButton() throws IOException{
			buttons = new ArrayList<ImageButton>();
			
	        //button[0] : searching for a game
			//button[1] : back to main menu
			buttons.add(0, new ImageButton(("/digger/gui/extend/WaitingForMatch.jpg"),
					("/digger/gui/extend/WaitingForMatch.jpg")));
			buttons.get(0).setTranslateX(100);
			buttons.get(0).setTranslateY(230);
			buttons.add(1, new ImageButton(("/digger/gui/extend/Back.jpg"),
					("/digger/gui/extend/Back1.jpg")));
			buttons.get(1).setTranslateX(320);
			buttons.get(1).setTranslateY(400);
			//button 1 functionality
			buttons.get(1).setOnAction(e -> Controller.setSceneMainMenu());
		}
		
		
		public Scene getScene() {
			return scene;
		}

		public void setScene(Scene scene) {
			this.scene = scene;
		}

}
