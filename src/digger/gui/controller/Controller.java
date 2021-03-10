package digger.gui.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

import digger.gui.base.FindGameMenu;
import digger.gui.base.GameOverScene;
import digger.gui.base.MainMenu;
import digger.gui.base.NewGameMenu;
import digger.logik.Map;
import digger.netz.MapForTwoplayers;
import digger.netz.Servicee;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/* @author Afraa Habbab
 * @author Djiotsop
 */

public class Controller extends Application {

	static Stage window;
	static MainMenu mMenuScene;
	static NewGameMenu nGameScene;
	static FindGameMenu fGameScene;
	static Map map;
	static GameOverScene gOverScene;

	static MapForTwoplayers client;

	private static boolean connected;
	private static boolean versuch;
	private static Socket server;
	private static DataInputStream fromServer;
	private static DataOutputStream toServer;

	Service<int[]> service;
	static int id = -1;
	static int[] value;

	public Controller() throws Exception {
		mMenuScene = new MainMenu();
		nGameScene = new NewGameMenu();
		fGameScene = new FindGameMenu();
		gOverScene = new GameOverScene();
		service = new Servicee();
		connected = false;
		versuch = false;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		connectToServer();
		forService();
		window = primaryStage;
		window.setWidth(793);
		window.setHeight(638);
		window.setScene(mMenuScene.getScene());
		if(connected) window.setTitle("Digger Game. ID = " + id);
		else window.setTitle("Digger Game      Not connected");
		window.getIcons().add(new Image(getClass().getResourceAsStream("/digger/gui/extend/diggerIcon.jpg")));
		window.setResizable(false);
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent arg0) {
				System.out.println("Beenden");
				System.exit(0);
			}
		});
		window.show();
		System.out.println(id);

	}

	private void connectToServer() {
		try {
			server = new Socket("localhost", 2021);/* connect to server */

			/* Preparing communication with Server */
			fromServer = new DataInputStream(server.getInputStream());
			toServer = new DataOutputStream(server.getOutputStream());

			id = (int) fromServer.readDouble();// Recieve ID
			connected = true;
		} catch (Exception e) { System.out.println("Problem in Connect to Client"); }

	}

	private static void notConnectedToServer()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
    	alert.setHeaderText("Sie sind leider nicht mit dem Server verbunden");
    	alert.setContentText("Sie können aber in One-Player-Modus spielen");
    	alert.showAndWait();
	}
	
	private void forService() {
		
		service.setOnSucceeded(e -> {
			value = service.getValue();
			versuch = true;
			if (value[0] == 0)// Host empfaengt die Rueckmendung von Friend
			{
				hostRecieveFromFriend(value[2]);
			} else if (value[0] == 1)// Alle Friend empfangen die Anfrage von findGame
			{
				waitForAnswer(1);
			} else if (value[0] == 2)// Friend empfaengt die Einladung von Host
			{
				waitForAnswer(2);

			}

		});
		service.start();
	}

	private static void waitForAnswer(int i) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Benachrichtigung, ID = " + id);
		if (i == 1)
			alert.setHeaderText(value[1] + " sucht sich einen Spieler");
		else if (i == 2)
			alert.setHeaderText(value[1] + " Möchte mit Ihnen spielen");
		alert.setContentText("Möchten Sie mit Ihm spielen?");

		ButtonType buttonTypeJa = new ButtonType("Ja");
		ButtonType buttonTypeNein = new ButtonType("Nein", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeJa, buttonTypeNein);

		Optional<ButtonType> ergebnis = alert.showAndWait();
		if (ergebnis.get() == buttonTypeJa) {
			friendSendToHost(1);
			int answer = 0;
			try {
				answer = fromServer.readInt();//server sagt uns ob host schon am Spielen ist, das ist besonders für Find a Game wichtig
			} catch (IOException e) { System.out.println(id+" konnte answer von "+value[1]+" nicht empfangen"); }
			
			if(answer == 1) twoPlayer(value[1],2);
			else
			{
		    	alert.setHeaderText("Sie Haben leider etwas zu spät geantwortet");
		    	alert.setContentText(value[1]+" Ist gerade schon mit einem anderen Spielen\nin Verbindung.\nWenn Sie in 2-Player-Modus noch spielen wollen\n müssen Sie der Spiel beenden und wieder starten");
		    	alert.showAndWait();
			}
		} else if (ergebnis.get() == buttonTypeNein) {
			friendSendToHost(0);
		}
		
	}

	private static void friendSendToHost(int answer) {
		try {
			toServer.writeDouble(0);//Die ersten beiden werden in hostTryToConnectWithFriend() in Client empfangen, da springen wir raus
			toServer.writeDouble(0);
			toServer.writeDouble(value[1]);//und die letzen beiden werden in friendAnswer(value[1]) in Client empfangen
			toServer.writeInt(answer);

		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error, ID = " + id);
        	alert.setHeaderText("Server nicht erreichbar");
        	alert.showAndWait();
		}
	}

	private static void hostRecieveFromFriend(int ant) 
	{
		Alert alert;
		if(ant==2 || ant==5 || ant==7)
		{
			alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Benachrichtigung, ID = " + id);
		}
		else
		{
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error, ID = " + id);
		}
		
		if(ant==2||ant==3)
			alert.setContentText("Sie können mit einem anderen ID erneut versuchen,\naber Sie müssen einmal den Spiel beenden und erneut starten\nOder wechseln Sie in One-Player.\nDanke");
		

		if (ant == 0) 
		{
			twoPlayer(value[1],1);
		} else if (ant == 1) {
	    	alert.setHeaderText(value[1] + " Hat die Verbindung abgelehnt");
		} else if (ant == 2) {
	    	alert.setHeaderText(value[1]+" spielt schon mit einem anderen Spieler");
		} else if (ant == 3) {
			alert.setHeaderText(value[1] + " ist nicht erreichbar");
		} else if (ant == 4) {
			alert.setHeaderText(value[1] + " unmöglich mit sich selbst zu spielen");
	    	alert.setContentText("Sie können erneut versuchen und einen gütligen ID eingeben\nAber Sie müssen Sie den Spiel erst beenden und dann wieder starten.\nDanke!");
		}else if (ant == 5) {
			alert.setHeaderText("Kein Spieler verbunden");
	    	alert.setContentText("\nWenn Sie noch in 2-Player-Modus spielen wollen,\ndann müssen Sie den Spiel beenden und wieder starten.\n Danke!");
		}else if (ant == 7) {
			if(value[1]==0)
				alert.setHeaderText("Kein Spieler ist gerade verbunden.");
			else
				alert.setHeaderText("Spieler "+value[1]+" ist gerade nicht verbunden");
			alert.setContentText("\nWenn Sie noch in 2-Player-Modus spielen wollen,\ndann müssen Sie den Spiel beenden und wieder starten.\n Danke!");
		}
		if(ant!=0) alert.showAndWait();
	}
	
	public static void myScene()
	{
		client = null;
		client = new MapForTwoplayers(0);
		window.setScene(gOverScene.getMyScene());
		window.show();
	}
	
	public static Socket getServer() {
		return server;
	}

	public static DataInputStream getFromServer() {
		return fromServer;
	}

	public static DataOutputStream getToServer() {
		return toServer;
	}
	
	public static void setSceneFindGameMenu() {
		
		if(!connected)
		{
			notConnectedToServer();
			return;
		}
		if(versuch)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error, ID = " + id);
			alert.setHeaderText("Bitte beenden Sie einmal den Spiel und starten erneut, um Find a Game benutzen zu können\nOder wechseln Sie in One-Player.\nDanke");
			alert.showAndWait();
			return;
		}
		new Service<Integer>() {

			@Override
			protected Task<Integer> createTask() {
				return new Task<Integer>() {

					@Override
					protected Integer call() throws Exception{
						toServer.writeDouble(0);
						toServer.writeDouble(1);
						return null;
					}
				};
			}
		}.start();
		window.setScene(fGameScene.getScene());
		window.show();
	}
	
	private static void twoPlayer(int i, int j)
	{
		client = new MapForTwoplayers(j);
		client.timer.start();

		if (client.getRoot().getChildren().isEmpty()) {
			client.getRoot().getChildren().add(client.canvas);
			client.getRoot().setFocusTraversable(true);
		}
		window.setScene(client.getScene());
		window.setTitle("Digger Game. ID = " +id+", Player "+client.getId()+" Mit "+i);
		window.show();
	}

	public static void setSceneClient() {
		
		if(!connected)
		{
			notConnectedToServer();
			return;
		}
		if(versuch)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error, ID = " + id);
			alert.setHeaderText("Bitte beenden Sie einmal den Spiel und starten erneut, um 2-Players benutzen zu können\nOder wechseln Sie in One-Player.\nDanke");
			alert.showAndWait();
			return;
		}
		
		TextInputDialog dialog = new TextInputDialog("2021001");
		dialog.setTitle("Connexion, ID = "+id);
		dialog.setHeaderText(null);
		dialog.setContentText("User Id bitte eingeben:");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
		    try 
		    {
		    	double id = Double.parseDouble(result.get());
			    System.out.println(id);
			    toServer.writeDouble(Controller.id);
				toServer.writeDouble(id);	
			} 
		    catch (IOException e)
		    {
		    	Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	        	alert.setHeaderText("Konnte nicht senden");
	        	alert.showAndWait();
			}
		    
		}
		
	}



	public static void setSceneResumeGame() {
		map.timer.start();
		map.loadGame();
		if (map.getRoot().getChildren().isEmpty()) {
			map.getRoot().getChildren().add(map.canvas);
			map.getRoot().setFocusTraversable(true);
		}

		window.setScene(map.getScene());
		map.mediaPlayer.start();
		window.show();

	}

	public static void setSceneNewGameMenu() {
		window.setScene(nGameScene.getScene());
		window.show();
	}

	public static void setSceneMainMenu() {
		window.setScene(mMenuScene.getScene());
		window.show();
	}

	public static void setSceneMap() {
		map = new Map();
		map.timer.start();

		if (map.getRoot().getChildren().isEmpty()) {
			map.getRoot().getChildren().add(map.canvas);
			map.getRoot().setFocusTraversable(true);
		}

		window.setScene(map.getScene());
		window.show();
	}

	public static void setSceneGameOver(int[] highScore) throws IOException {
		map.mediaPlayer.stop();
		map.timer.stop();
		map = null;       
		window.setScene(gOverScene.getScene(highScore));
		window.show();

	}

}
