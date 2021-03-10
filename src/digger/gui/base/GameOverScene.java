package digger.gui.base;

import java.io.IOException;
import java.util.ArrayList;

import digger.gui.controller.Controller;
import digger.gui.extend.BackgroundPane;
import digger.gui.extend.ImageButton;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @see Controller, Map
 * @author Afraa Habbab
 *
 */
public class GameOverScene {
	private Scene scene;
	private BackgroundPane pane;
	private ArrayList<ImageButton> buttons;
        public int [] highScore ;
        public ArrayList<Label> labels =new ArrayList<Label> ()  ;
	
	public GameOverScene() throws IOException {
		pane = new BackgroundPane("/digger/gui/extend/GameOver.jpg");
		for(int i = 0; i<10; i++) {
			labels.add(new Label());

		}
		try {
			newButton();
			
		} catch (IOException e) {
			System.out.println("In Package gui.base, in der Funktion GameOverScene()");
		}
		for(int i = 0; i<10; i++) {
			labels.get(i).relocate(0, -i*10+300);
		}

		
		scene = new Scene(pane);
		pane.getChildren().addAll(buttons);
		pane.getChildren().addAll(labels);
	}
	
	private void newButton() throws IOException{
		buttons = new ArrayList<ImageButton>();
		
		/**
         * button[0] : new game option
         * button[1] : go back to main menu
         * button[2] : Exit the game
         */
		buttons.add(0, new ImageButton(("/digger/gui/extend/NewGame.jpg"),
				("/digger/gui/extend/NewGame1.jpg")));
		buttons.get(0).setTranslateX(450);
		buttons.get(0).setTranslateY(160);
		buttons.add(1, new ImageButton(("/digger/gui/extend/ReturnToMainMenu.jpg"),
				("/digger/gui/extend/ReturnToMainMenu1.jpg")));
		buttons.get(1).setTranslateX(330);
		buttons.get(1).setTranslateY(315);
		buttons.add(2, new ImageButton(("/digger/gui/extend/Exit.jpg"),
				("/digger/gui/extend/Exit1.jpg")));
		buttons.get(2).setTranslateX(500);
		buttons.get(2).setTranslateY(470);
		
		/*
		 * buttons functionality
		 */
		buttons.get(0).setOnAction(e -> Controller.setSceneNewGameMenu());
		buttons.get(1).setOnAction(e -> Controller.setSceneMainMenu());
		buttons.get(2).setOnAction(e -> Platform.exit());
		
	}
        
        private ArrayList<Label> initScoreLabels(ArrayList<Label> labels) throws IOException{
		
		final int maxFontSize = 40;
		for(int i=9;i>=0;--i){
			
            Label currLabel = labels.get(i);
            currLabel.setText(String.valueOf(highScore[i]));
    	    currLabel.setFont(new Font(maxFontSize));
            currLabel.setTextFill(Color.GREENYELLOW);
            currLabel.setTranslateX(150);
            currLabel.setTranslateY(-i*30+200);
		}
		return labels;}
	
	public Scene getScene(int [] highSco) throws IOException {
        highScore=highSco;
        initScoreLabels(labels);
		return scene;
	}
	
	public Scene getMyScene() {return scene;}

	public void setScene(Scene scene) { this.scene = scene;}

}
