package digger.gui.base;

import java.io.IOException;
import java.util.ArrayList;

import digger.gui.controller.Controller;
import digger.gui.extend.BackgroundPane;
import digger.gui.extend.ImageButton;
import javafx.scene.Scene;

/**
 * @see Controller
 * @author Afraa Habbab
 *
 */
public class NewGameMenu {
	private Scene scene;
	private BackgroundPane pane;
	private ArrayList<ImageButton> buttons;
	
	public NewGameMenu() throws IOException{
		pane = new BackgroundPane(("/digger/gui/extend/NewGameBG.jpg"));
		try {
			newButton();
		}catch (IOException e) {
			System.out.println("In Package gui.base, in der Funktion NewGameMenu()");
		}
		scene = new Scene(pane);
		pane.getChildren().addAll(buttons);}
		
		private void newButton() throws IOException{
			buttons = new ArrayList<ImageButton>();
			
			/**
	         * button[0] : one player option
	         * button[1] : two player option
	         * button[2] : back to main menu
	         */
			buttons.add(0, new ImageButton(("/digger/gui/extend/OnePlayer.jpg"),
					("/digger/gui/extend/OnePlayer1.jpg")));
			buttons.get(0).setTranslateX(250);
			buttons.get(0).setTranslateY(190);
			buttons.add(1, new ImageButton(("/digger/gui/extend/TwoPlayer.jpg"),
					("/digger/gui/extend/TwoPlayer1.jpg")));
			buttons.get(1).setTranslateX(245);
			buttons.get(1).setTranslateY(290);
			buttons.add(2, new ImageButton(("/digger/gui/extend/Back.jpg"),
					("/digger/gui/extend/Back1.jpg")));
			buttons.get(2).setTranslateX(325);
			buttons.get(2).setTranslateY(390);
			//buttons functionality
			buttons.get(2).setOnAction(e -> Controller.setSceneMainMenu());
			buttons.get(1).setOnAction(e -> Controller.setSceneClient());
			buttons.get(0).setOnAction(e -> Controller.setSceneMap());
			
		}
		
		public Scene getScene() {
			return scene;
		}

		public void setScene(Scene scene) {
			this.scene = scene;
		}

}
	
