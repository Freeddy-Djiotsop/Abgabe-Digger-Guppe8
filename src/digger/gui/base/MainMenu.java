package digger.gui.base;

import javafx.application.Platform;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.Pane;

import java.awt.Desktop;
//import java.awt.Graphics;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import digger.gui.controller.Controller;
import digger.gui.extend.*;

/**
 * 
 * @see Controller
 * @author Afraa Habbab
 *
 */
public class MainMenu {
	
	private Scene scene;
	private BackgroundPane pane;
	private ArrayList<ImageButton> buttons;
	public MainMenu() throws IOException{
		pane = new BackgroundPane("/digger/gui/extend/BackgroundMainMenu.jpg");
		try {
			newButton();
		}catch (IOException e) {
			System.out.println("In Package gui.base, in der Funktion MainMenu()");
		}
		
		scene = new Scene(pane);
		pane.getChildren().addAll(buttons);
	
	}
	
	private void newButton() throws IOException{
		buttons = new ArrayList<ImageButton>();
		
		/**
         * button[0] : resume game option
         * button[1] : creating a new game option
         * button[2] : searching for a game option
         * button[3] : game manual(original digger instructions)
         * button[4] : exit the game
         */
		buttons.add(0, new ImageButton(("/digger/gui/extend/ResumeGame.jpg"),
				("/digger/gui/extend/ResumeGame1.jpg")));
		buttons.get(0).setTranslateX(250);
		buttons.get(0).setTranslateY(160);
		buttons.add(1, new ImageButton(("/digger/gui/extend/NewGame.jpg"),
				("/digger/gui/extend/NewGame1.jpg")));
		buttons.get(1).setTranslateX(270);
		buttons.get(1).setTranslateY(230);
		buttons.add(2, new ImageButton(("/digger/gui/extend/FindGame.jpg"),
				("/digger/gui/extend/FindGame1.jpg")));
		buttons.get(2).setTranslateX(255);
		buttons.get(2).setTranslateY(300);
		buttons.add(3, new ImageButton(("/digger/gui/extend/HowToPlay.jpg"),
				("/digger/gui/extend/HowToPlay1.jpg")));
		buttons.get(3).setTranslateX(255);
		buttons.get(3).setTranslateY(375);
		buttons.add(4, new ImageButton(("/digger/gui/extend/Exit.jpg"),
				("/digger/gui/extend/Exit1.jpg")));
		buttons.get(4).setTranslateX(320);
		buttons.get(4).setTranslateY(460);
		/**
		 * buttons functionality
		 */
                buttons.get(0).setOnAction(e -> Controller.setSceneResumeGame());
		buttons.get(1).setOnAction(e -> Controller.setSceneNewGameMenu());
		buttons.get(2).setOnAction(e -> Controller.setSceneFindGameMenu());
		buttons.get(3).setOnAction(e -> {
            try {
                Desktop.getDesktop()
                        .browse(new URI("http://digger.org/instruct.html"));
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        });
		buttons.get(4).setOnAction(e -> Platform.exit());
	
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}

