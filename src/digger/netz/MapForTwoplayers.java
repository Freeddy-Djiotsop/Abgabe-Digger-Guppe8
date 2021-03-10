package digger.netz;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import digger.gui.controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MapForTwoplayers {
	public Canvas canvas = new Canvas(780, 600);
	public GraphicsContext gc = canvas.getGraphicsContext2D();
	Group root = new Group();
	Scene s = new Scene(root);
	private Dimension d;
	public DiggerForTwoPlayers digger;
	public NobbinsForTwoPlayers nobbins = new NobbinsForTwoPlayers();
	public NobbinsForTwoPlayers nobbins2 = new NobbinsForTwoPlayers();
	public ScoreForTwoPlayers score = new ScoreForTwoPlayers();
	public ScoreForTwoPlayers score2 = new ScoreForTwoPlayers();
	private final int blockSize = 30;
//	private final int nBlock = 40;
//	private final int screenSize = nBlock * blockSize;
	private final int levelData[][] = {
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2,
					2, 2, 2, 2, 2 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 49, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 7, 3, 21, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 77, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 35, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 5, 3, 5, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 5, 3, 5, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 }, };

	private final int levelData2[][] = {
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 6, 6, 6, 6, 6, 6, 6, 6, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					2, 2, 2, 2, 2 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 49, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					4, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					4, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					4, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 2, 2,
					4, 3, 3, 3, 3 },
			{ 3, 3, 3, 77, 3, 3, 3, 3, 8, 3, 3, 3, 5, 3, 5, 3, 5, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 5, 3, 5, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 7, 3, 21, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 5, 3, 5, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 5, 3, 5, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 4, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 5, 3, 5, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					35, 3, 3, 3, 3 },
			{ 3, 3, 5, 3, 5, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 5, 3, 5, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 5, 3, 5, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 5,
					3, 5, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 }, };
	public int[][] screenData;
	public int dyingNr = 0;
	public int dying2Nr = 0;
	public boolean up = false, down = false, right = false, left = false, active = false, killed = false, go = true,
			move = false;
	public boolean up2 = false, down2 = false, right2 = false, left2 = false, active2 = false, killed2 = false,
			go2 = true, move2 = false;
	public boolean tot=false, tot2=false, close=false;
	public String dir = "l";
	public String dir2 = "l";
	public int fire = 1;
	public int fire2 = 1;
	public int cherrie;
	public boolean cherrieRespawn = false;
	public boolean monsterRespawnOn = true;
	public boolean cherrieEat = false;
	public boolean lastRound = false;

	public Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> { // für fireball

		go = true;

	}));

	
	
	Clip mediaPlayer;

	private DataInputStream fromServer;
	private DataOutputStream toServer;
	Thread thread;

	private int id = -3, stopp = 0;
	private double keyNumber=0, goldnuggets=0, pause=0;

	public MapForTwoplayers(int i){
		if(id==0) return;
		try 
		{
			try 
			{
				mediaPlayer = AudioSystem.getClip();
				mediaPlayer.open(AudioSystem.getAudioInputStream(new File("Digger/gui/extend/Digger.wav")));
//				mediaPlayer.open(AudioSystem.getAudioInputStream(new File("src/digger/gui/extend/Digger.wav")));
				mediaPlayer.start();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("Song nicht gefunden.");
		}
		
		id=i;
//		get Server Information
		fromServer = Controller.getFromServer();
		toServer = Controller.getToServer();
		if(id==1)
		{
			try {
				toServer.writeDouble(0);
				toServer.writeDouble(2);
			} catch (IOException e) 
			{
				System.out.println("Konnte Luecke in Class Client der Server nicht fuellen.");
			}
			
		}
		digger = new DiggerForTwoPlayers(id);
		System.out.println("In Map fuer Player "+id);
		screenData = new int[30][40];
		d = new Dimension(600, 800);
		initLevel(levelData);
		drawMap(gc);
		diggerMove();
		moveMonster.start();
//		preparingSendMessage(server);
		new Thread(new RecieveDataFromServer(fromServer, id)).start();
		System.out.println("Thread " + id + " ist gestartet");

	}

	public int getId() {
		return id;
	}
	
	private void updateCoord(double x, double y, double s, double k, double n, double p, double d, int i) {
		int key = (int) k;
		if (id == 2) 
		{
			if(p==1)
				timer.stop();
			else
			{
				timer.start();
				this.digger.diggerX = (int) x;
				this.digger.diggerY = (int) y;
				score.score = (int) s;
				this.dyingNr = (int) d;
				if(this.dyingNr==3)
					tot = true;//Ich bin tot

				switch (key) {
				case 8:
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 8;
					dir = "UP";
					break;
				case 4:
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 4;
					dir = "DOWN";
					break;
				case 2:
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 2;
					dir = "LEFT";
					break;
				case 6:
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 2;
					dir = "RIGHT";
					break;
				default:
					break;
				}
			}

		}
		if (id == 1)
		{
			
			if(p==1)
				timer.stop();
			else
			{
				timer.start();
				this.digger.digger2X = (int) x;
				this.digger.digger2Y = (int) y;
				score2.score = (int) s;
				this.dying2Nr = (int) d;
				if(this.dying2Nr==3)
					tot2 = true;//Mein Freund ist tot

				switch (key) {
				case 8:
					screenData[digger.digger2Y / 20][digger.digger2X / 20] = 8;
					dir = "UP";
					break;
				case 4:
					screenData[digger.digger2Y / 20][digger.digger2X / 20] = 4;
					dir = "DOWN";
					break;
				case 2:
					screenData[digger.digger2Y / 20][digger.digger2X / 20] = 2;
					dir = "LEFT";
					break;
				case 6:
					screenData[digger.digger2Y / 20][digger.digger2X / 20] = 2;
					dir = "RIGHT";
					break;
				default:
					break;
				}

			}
		}
		digger.changeImage(dir,id);
	}
	
	class RecieveDataFromServer implements Runnable
	{
		private DataInputStream din;
		private int id;
		public RecieveDataFromServer(DataInputStream d, int i) 
		{
			din = d;
			id = i;
		}
		
		@Override public void run()
		{
			while(true)
			{
				try 
				{
					double x =  din.readDouble();
					double y =  din.readDouble();
					double score = din.readDouble();
					double keynumber = din.readDouble();
					double goldnuggets = din.readDouble();
					double pause = din.readDouble();
					double dying = din.readDouble();
					updateCoord(x,y,score,keynumber,goldnuggets,pause, dying,id);//id help to know you recieve the data
				} 
				catch (IOException e) 
				{
					stopp++;
					if(stopp==10)
						System.exit(-1);
				}
			}
			
		}
		
	}

	private void sendDataToServer(int x, int y, int score, double k, double n, double p, int d)
	{
		if(id==1)
		{
			if(tot)
				return;//Wir senden keine Daten mehr
		}
		else if(id==2)
		{
			if(tot2)
				return;//Wir senden keine Daten mehr
		}
		//Wenn nicht tot dann seden wir
		try {
			toServer.writeDouble(x);
			toServer.writeDouble(y);
			toServer.writeDouble(score);
			toServer.writeDouble(k);
			toServer.writeDouble(n);
			toServer.writeDouble(p);
			toServer.writeDouble(d);
		} 
		catch (IOException e) 
		{
			System.out.println("Client "+id+" konnte nicht senden");
		}
	}

	public int levelCounter = 0;

	public void continueLevel() {

		int i, j;
		for (i = 0; i < 30; i++) {
			for (j = 0; j < 40; j++) {
				if (screenData[i][j] != 5)
					levelCounter++;
				else {
					levelCounter = 0;
					break;
				}
			}
			if (levelCounter == 0)
				break;
		}
		if (levelCounter == 1200) {
			initLevel(levelData2);
			digger.diggerX = 400;
			digger.diggerY = 540;
			nobbins = null;
			nobbins = new NobbinsForTwoPlayers();
			nobbins.maxMonsterNumber = 5;
			nobbins.rand = 6;
			cherrie = 0;
			cherrieEat = false;

		}

	}

	private void initLevel(int[][] levelData) { // hier level in Screnndata inti.
		int i, j;
		for (i = 0; i < 30; i++) {
			for (j = 0; j < 40; j++) {
				screenData[i][j] = levelData[i][j];
			}
		}
	}

	public void drawMap(GraphicsContext gc) {
		if(tot&&tot2)
		{
			timer.stop();
			mediaPlayer.stop();
			Controller.myScene();
			return;
		}
		if (dyingNr < 3 || dying2Nr<3) {
			gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/backgroundLevel01.jpg")), 0, 0,
					d.height, d.width);
			int i, j;
			int x = 0, y = 0;
			for (i = 0; i < 30; i++) {
				for (j = 0; j < 40; j++) {
					if (screenData[i][j] % 2 == 0) {
						gc.setFill(Color.BLACK);
						gc.fillRect(x, y, blockSize, blockSize);
					}
					if (screenData[i][j] == 5) {
						gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/emerald.jpg")),
								x + 10, y + 10, 15, 15);
					}

					if (screenData[i][j] % 7 == 0) {
						gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/GoldBag.png")),
								x + 10, y + 10, 20, 20);

					}
					if (screenData[i][j] % 9 == 0) {

						gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Gold.jpg")), x, y, 30,
								30);

					}

					x += 20;
				}
				x = 0;
				y += 20;
			}

			digger.draw(gc, id);
			if (fire == 0) {
				digger.drawFireBall(gc);
			}
			for (int k = 0; k < nobbins2.maxMonsterNumber; k++) {

				if (nobbins2.hobbinsOn == false) {
					nobbins2.draw(gc, nobbins2.monsterX[k], nobbins2.monsterY[k]);
				}
				if (nobbins2.hobbinsOn == true) {
					nobbins2.draw2(gc, nobbins2.monsterX[nobbins2.rand], nobbins2.monsterY[nobbins2.rand]);

					if (nobbins2.rand != k) {
						nobbins2.draw(gc, nobbins2.monsterX[k], nobbins2.monsterY[k]);

					}

				}
			}
			for (int k = 0; k < nobbins.maxMonsterNumber; k++) {

				if (nobbins.hobbinsOn == false) {
					nobbins.draw(gc, nobbins.monsterX[k], nobbins.monsterY[k]);
				}
				if (nobbins.hobbinsOn == true) {
					nobbins.draw2(gc, nobbins.monsterX[nobbins.rand], nobbins.monsterY[nobbins.rand]);

					if (nobbins.rand != k) {
						nobbins.draw(gc, nobbins.monsterX[k], nobbins.monsterY[k]);

					}

				}
			}

			
			score.drawScore(gc,id,1);
			score2.drawScore(gc,id,2);
			gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Level01.jpg")), 350, 1, 100, 20);
		}
		

		
		//Begin draw digger life
		if(id==1)
		{
			if (dyingNr == 0) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 80, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 100, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 120, 1, 20, 20);
			}
			if (dyingNr == 1) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 80, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 100, 1, 20, 20);
			}
			if (dyingNr == 2) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 80, 1, 20, 20);
			}
			
			if (dying2Nr == 0) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 620, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 640, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 660, 1, 20, 20);
			}
			if (dying2Nr == 1) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 620, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 640, 1, 20, 20);
			}
			if (dying2Nr == 2) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 620, 1, 20, 20);
			}
		}
		else if(id==2)
		{
			if (dying2Nr == 0) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 620, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 640, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 660, 1, 20, 20);
			}
			if (dying2Nr == 1) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 620, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 640, 1, 20, 20);
			}
			if (dying2Nr == 2) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/DigLife.jpg")), 620, 1, 20, 20);
			}
			
			if (dyingNr == 0) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 100, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 120, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 140, 1, 20, 20);
			}
			if (dyingNr == 1) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 120, 1, 20, 20);
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 140, 1, 20, 20);
			}
			if (dyingNr == 2) {
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Dig2Life.jpg")), 140, 1, 20, 20);
			}
		}
		//End draw digger life
		
		if (cherrie == 3 && cherrieEat == false) {

			gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Cherry.jpg")), 750, 27, 20, 20);

		}
		if (digger.diggerX / 20 == 37 && digger.diggerY / 20 == 1 && cherrieEat == false) {
			gc.setFill(Color.BLACK);
			gc.fillRect(740, 20, 20, 20);
			digger.draw(gc, id);
			score.score += 100;
			cherrie = 0;
			cherrieEat = true;

		}
		if (cherrieRespawn == true) {
			gc.setFill(Color.BLACK);
			gc.fillRect(740, 20, 20, 20);

			cherrie = 0;
			cherrieRespawn = false;

		}

	}

	public boolean mediaPlayerOn = true;

	public void diggerMove() {

		s.setOnKeyPressed((KeyEvent event) -> {

			
			if (id == 2) {
				if(tot2&&close)
				{
					timer.stop();
					mediaPlayer.stop();
					Controller.myScene();
					return;
				}
				if(tot2) 
				{
					close = true;
					sendDataToServer(digger.digger2X, digger.digger2Y, score2.score, keyNumber, goldnuggets, pause, 3);
					pause = 0;
				}
				switch (event.getCode()) {

				case F7: {
					if (mediaPlayerOn) {
						mediaPlayer.stop();
						mediaPlayerOn = false ;
					} else {
						mediaPlayer.start();
						mediaPlayerOn = true;
					}

					break;

				}
				case SPACE: {

					pause = 1;
					timer.stop();
					break;
				}
				case UP: {
					timer.start();
					digger.changeImage("UP");
					up2 = true;
					down2 = false;
					right2 = false;
					left2 = false;
					if (digger.digger2Y <= 30) {
						digger.digger2Y = 30;
						if (active2 == false)
							digger.fireBall2Y = digger.digger2Y;

						break;
					} else {
						if (screenData[((digger.digger2Y / 20) - 1)][digger.digger2X / 20] % 7 == 0) {

							digger.digger2Y = digger.digger2Y;

						} else {
							digger.digger2Y -= 20;
						}
						if (active2 == false)
							digger.fireBall2Y = digger.digger2Y;
						if (screenData[digger.digger2Y / 20][digger.digger2X / 20] == 5) { // hier bedeutet wenn Digger
																							// Emeraldsstelle
																							// eintrifft,dann
																							// erhöhe die Score
							score2.score += 50;
						}
						screenData[digger.digger2Y / 20][digger.digger2X / 20] = 8; // Digger lässt in seiner Stelle
						keyNumber=8;														// Schwarze Wege

						break;
					}
				}
				case DOWN: {
					timer.start();
					digger.changeImage("DOWN");
					up2 = false;
					down2 = true;
					right2 = false;
					left2 = false;
					if (digger.digger2Y >= 550) {
						digger.digger2Y = 550;
						if (active2 == false)
							digger.fireBall2Y = digger.digger2Y;
						break;
					} else {
						if (screenData[((digger.digger2Y / 20) + 1)][digger.digger2X / 20] % 7 == 0) {
							digger.digger2Y = digger.digger2Y;

						} else {
							digger.digger2Y += 20;
						}
						if (active2 == false)
							digger.fireBall2Y = digger.digger2Y;
						if ((screenData[digger.digger2Y / 20 + 1][digger.digger2X / 20] == 5)) { // hier bedeutet wenn
																									// Digger
																									// Emeraldsstelle
																									// eintrifft,dann
																									// erhöhe die Score

							score2.score += 50;
						}
						screenData[digger.digger2Y / 20][digger.digger2X / 20] = 4; // Digger lässt in seiner Stelle
						keyNumber=4;															// Schwarze
																					// Weg

						break;
					}
				}
				case LEFT: {
					timer.start();
					digger.changeImage("LEFT");
					up2 = false;
					down2 = false;
					right2 = false;
					left2 = true;
					if (digger.digger2X <= 20) {
						digger.digger2X = 20;
						if (active2 == false)
							digger.fireBall2X = digger.digger2X;
						break;
					} else {
						if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] % 7 == 0) {
							if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] == 7
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 2] % 7 != 0) {

								digger.digger2X -= 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] = 7;
								goldnuggets=7;
							} else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] == 21
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 2] % 7 != 0) {

								digger.digger2X -= 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] = 21;
								goldnuggets=21;
							} else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] == 35
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 2] % 7 != 0) {

								digger.digger2X -= 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] = 35;
								goldnuggets=35;
							} else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] == 49
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 2] % 7 != 0) {

								digger.digger2X -= 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] = 49;
								goldnuggets=49;
							}

							else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] == 77
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 2] % 7 != 0) {

								digger.digger2X -= 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] = 77;
								goldnuggets=77;
							}

							else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] == 21
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 2] == 7) {

								digger.digger2X -= 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 1] = 21;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) - 2] = 7;
								goldnuggets=78;
							}

						} else {
							digger.digger2X -= 20;
						}
						if (active2 == false)
							digger.fireBall2X = digger.digger2X;
						if (screenData[digger.digger2Y / 20][digger.digger2X / 20] == 5) { // hier bedeutet wenn Digger
																							// Emeraldsstelle
																							// eintrifft,dann
																							// erhöhe die Score

							score2.score += 50;
						}
						screenData[digger.digger2Y / 20][digger.digger2X / 20] = 2; // Digger lässt in seiner Stelle
						keyNumber=2;															// Schwarze
						
						break;
					}
				}
				case RIGHT: {
					timer.start();
					digger.changeImage("RIGHT");
					up2 = false;
					down2 = false;
					right2 = true;
					left2 = false;
					if (digger.digger2X >= 740) {
						digger.digger2X = 740;
						if (active2 == false)
							digger.fireBall2X = digger.digger2X;
						break;
					} else {
						if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] % 7 == 0) {
							if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] == 7
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 2] % 7 != 0) {

								digger.digger2X += 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] = 7;
								goldnuggets=7;
							} else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] == 21
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 2] % 7 != 0) {

								digger.digger2X += 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] = 21;
								goldnuggets=21;
							}

							else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] == 7
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 2] == 21) {

								digger.digger2X += 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 2] = 21;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] = 7;
								goldnuggets=22;
							} else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] == 35
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 2] % 7 != 0) {

								digger.digger2X += 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] = 35;
								goldnuggets=35;
							} else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] == 49
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 2] % 7 != 0) {

								digger.diggerX += 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] = 49;
								goldnuggets=49;
							} else if (screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] == 77
									&& screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 2] % 7 != 0) {

								digger.digger2X += 20;
								screenData[digger.digger2Y / 20][(digger.digger2X / 20) + 1] = 77;
								goldnuggets=77;
							}
						}

						else {
							digger.digger2X += 20;
						}
						if (active2 == false)
							digger.fireBall2X = digger.digger2X;
						if (screenData[digger.digger2Y / 20][digger.digger2X / 20] == 5) { // hier bedeutet wenn Digger
																							// Emeraldsstelle
																							// eintrifft,dann
																							// erhähe die Score

							score2.score += 50;
						}
						screenData[digger.digger2Y / 20][digger.digger2X / 20] = 2; // Digger lässt in seiner Stelle
						keyNumber=6;															// Schwarze
						
						break;
					}
				}
				default:
					break;

				}
				
				sendDataToServer(digger.digger2X, digger.digger2Y, score2.score, keyNumber, goldnuggets, pause, dying2Nr);
				pause = 0;
				
			}
			if (id == 1) {
				if(tot&&close)
				{
					timer.stop();
					mediaPlayer.stop();
					Controller.myScene();
					return;
				}
				if(tot)
				{
					close = true;
					sendDataToServer(digger.diggerX, digger.diggerY, score.score, keyNumber, goldnuggets, pause, 3);
					pause = 0;
				}
				switch (event.getCode()) {

				case F7: {
					if (mediaPlayerOn) {
						mediaPlayer.stop();
						mediaPlayerOn = false ;
					} else {
						mediaPlayer.start();
						mediaPlayerOn = true;
					}

					break;

				}
				case SPACE: {

					pause = 1;
					timer.stop();
					break;
				}
				case UP: {
					timer.start();
					digger.changeImage("UP");
					up = true;
					down = false;
					right = false;
					left = false;
					if (digger.diggerY <= 30) {
						digger.diggerY = 30;
						if (active == false)
							digger.fireBallY = digger.diggerY;

						break;
					} else {
						if (screenData[((digger.diggerY / 20) - 1)][digger.diggerX / 20] % 7 == 0) {

							digger.diggerY = digger.diggerY;

						} else {
							digger.diggerY -= 20;
						}
						if (active == false)
							digger.fireBallY = digger.diggerY;
						if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 5) { // hier bedeutet wenn Digger
																							// Emeraldsstelle
																							// eintrifft,dann
																							// erhöhe die Score

							score.score += 50;
						}
						screenData[digger.diggerY / 20][digger.diggerX / 20] = 8; // Digger lässt in seiner Stelle
						keyNumber = 8;															// Schwarze
																					// Weg

						break;
					}
				}
				case DOWN: {
					timer.start();
					digger.changeImage("DOWN");
					up = false;
					down = true;
					right = false;
					left = false;
					if (digger.diggerY >= 550) {
						digger.diggerY = 550;
						if (active == false)
							digger.fireBallY = digger.diggerY;
						break;
					} else {
						if (screenData[((digger.diggerY / 20) + 1)][digger.diggerX / 20] % 7 == 0) {
							digger.diggerY = digger.diggerY;

						} else {
							digger.diggerY += 20;
						}
						if (active == false)
							digger.fireBallY = digger.diggerY;
						if ((screenData[digger.diggerY / 20 + 1][digger.diggerX / 20] == 5)) { // hier bedeutet wenn
																								// Digger
																								// Emeraldsstelle
																								// eintrifft,dann
																								// erhöhe die Score

							score.score += 50;
						}
						screenData[digger.diggerY / 20][digger.diggerX / 20] = 4; // Digger lässt in seiner Stelle
						keyNumber = 4;															// Schwarze
																					// Weg

						break;
					}
				}
				case LEFT: {
					timer.start();
					digger.changeImage("LEFT");
					up = false;
					down = false;
					right = false;
					left = true;
					if (digger.diggerX <= 20) {
						digger.diggerX = 20;
						if (active == false)
							digger.fireBallX = digger.diggerX;
						break;
					} else {
						if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] % 7 == 0) {
							if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 7
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

								digger.diggerX -= 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 7;
								goldnuggets = 7;
							} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 21
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

								digger.diggerX -= 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 21;
								goldnuggets = 21;
							} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 35
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

								digger.diggerX -= 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 35;
								goldnuggets = 35;
							} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 49
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

								digger.diggerX -= 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 49;
								goldnuggets = 49;
							}

							else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 77
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

								digger.diggerX -= 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 77;
								goldnuggets = 77;
							}

							else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 21
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] == 7) {

								digger.diggerX -= 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 21;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] = 7;
								goldnuggets = 78;
							}

						} else {
							digger.diggerX -= 20;
						}
						if (active == false)
							digger.fireBallX = digger.diggerX;
						if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 5) { // hier bedeutet wenn Digger
																							// Emeraldsstelle
																							// eintrifft,dann
																							// erhöhe die Score

							score.score += 50;
						}
						screenData[digger.diggerY / 20][digger.diggerX / 20] = 2; // Digger lässt in seiner Stelle
						keyNumber = 2;															// Schwarze
						
						break;
					}
				}
				case RIGHT: {
					timer.start();
					digger.changeImage("RIGHT");
					up = false;
					down = false;
					right = true;
					left = false;
					if (digger.diggerX >= 740) {
						digger.diggerX = 740;
						if (active == false)
							digger.fireBallX = digger.diggerX;
						break;
					} else {
						if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] % 7 == 0) {
							if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 7
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

								digger.diggerX += 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 7;
								goldnuggets = 7;
							} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 21
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

								digger.diggerX += 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 21;
								goldnuggets = 21;
							}

							else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 7
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] == 21) {

								digger.diggerX += 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] = 21;
								goldnuggets = 22;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 7;
							} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 35
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

								digger.diggerX += 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 35;
								goldnuggets = 35;
							} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 49
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

								digger.diggerX += 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 49;
								goldnuggets = 49;
							} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 77
									&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

								digger.diggerX += 20;
								screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 77;
								goldnuggets = 77;
							}
						}

						else {
							digger.diggerX += 20;
						}
						if (active == false)
							digger.fireBallX = digger.diggerX;
						if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 5) { // hier bedeutet wenn Digger
																							// Emeraldsstelle
																							// eintrifft,dann
																							// erhähe die Score

							score.score += 50;
						}
						screenData[digger.diggerY / 20][digger.diggerX / 20] = 2; // Digger lässt in seiner Stelle
						keyNumber = 6;															// Schwarze
						
						break;
					}
				}
				default:
					break;

				}
				sendDataToServer(digger.diggerX, digger.diggerY, score.score, keyNumber, goldnuggets, pause, dyingNr);
				pause = 0;
			}

			drawMap(gc); // wird nochmal die karte ausdruckt nach jede Bewegung von Digger, ist
							// untershiedlich von Swing. in swing steht die Methode repaint()
		});
		s.setOnKeyReleased((KeyEvent event) -> {

			timeline.play();

			if (id == 1) {
				if (go == true) {

					switch (event.getCode()) {

					case F2: {
						fire = 0;
						go = false;
						active = true;

						if (up == true) {
							dir = "up";

						}
						if (right == true) {
							dir = "right";
						}
						if (down == true) {
							dir = "down";
						}
						if (left == true) {
							dir = "left";
						}

						break;
					}
					default:
						break;

					}

				}
//				sendDataToServer(digger.diggerX, digger.diggerY, score.score, keyNumber, goldnuggets, pause, dyingNr);
			}

			if (id == 2) {
				if (go2 == true) {

					switch (event.getCode()) {

					case F2: {
						fire2 = 0;
						go2 = false;
						active2 = true;

						if (up2 == true) {
							dir2 = "up";

						}
						if (right2 == true) {
							dir2 = "right";
						}
						if (down2 == true) {
							dir2 = "down";
						}
						if (left2 == true) {
							dir2 = "left";
						}

						break;
					}
					default:
						break;

					}

				}
//				sendDataToServer(digger.digger2X, digger.digger2Y, score2.score, keyNumber, goldnuggets, pause, dying2Nr);			
				
			}

		});
	}

	public void fireBallMove(GraphicsContext gc) {

		switch (dir) {

		case "up": {
			if (digger.fireBallY > 20 && screenData[(digger.fireBallY / 20)][digger.fireBallX / 20] % 2 == 0
					&& killMonster() == false) {
				digger.fireBallY -= 20;

			} else {
				digger.fireBallY = digger.diggerY;
				digger.fireBallX = digger.diggerX;
				active = false;
				dir = "l";
				fire = 1;
			}
			break;
		}
		case "down": {

			if (digger.fireBallY < 600 && screenData[(digger.fireBallY / 20)][digger.fireBallX / 20] % 2 == 0
					&& killMonster() == false) {
				digger.fireBallY += 20;
			} else {
				digger.fireBallY = digger.diggerY;
				digger.fireBallX = digger.diggerX;
				active = false;
				dir = "l";
				fire = 1;
			}
			break;
		}
		case "right": {
			if (digger.fireBallX < 800 && screenData[(digger.fireBallY / 20)][(digger.fireBallX / 20)] % 2 == 0
					&& killMonster() == false) {
				digger.fireBallX += 20;
			} else {
				digger.fireBallY = digger.diggerY;
				digger.fireBallX = digger.diggerX;
				active = false;
				dir = "l";
				fire = 1;
			}
			break;
		}
		case "left": {
			if (digger.fireBallX > 20 && screenData[(digger.fireBallY / 20)][(digger.fireBallX / 20)] % 2 == 0
					&& killMonster() == false) {
				digger.fireBallX -= 20;
			} else {
				digger.fireBallY = digger.diggerY;
				digger.fireBallX = digger.diggerX;
				active = false;
				dir = "l";
				fire = 1;

			}
			break;
		}

		}

	}

	private AnimationTimer moveMonster = new AnimationTimer() {
		@Override
		public void handle(long now) {
			nobbins.move(screenData, digger);
		}
	};
	
	public AnimationTimer timer = new AnimationTimer() { // Timer
		@Override
		public void handle(long now) {

			goldNuggetGoDown1();
			goldNuggetGoDown2();
			goldNuggetGoDown3();
			goldNuggetGoDown4();
			goldNuggetGoDown5();

			fireBallMove(gc);
			continueLevel();
//			nobbins2.move(screenData, digger);
//			nobbins.move(screenData, digger);
			dying();
			delay(100);
			drawMap(gc);

		}
	};

	private static void delay(int i) { // um delay (Verzögerung) zu erstellen

		try {
			Thread.sleep(i);
		} catch (InterruptedException ex) {
			Logger.getLogger(MapForTwoplayers.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public Timeline timeline5 = new Timeline
			(new KeyFrame(Duration.seconds(5), ev -> 
			{

	}));

	public void dying() {
		
		for (int i = 0; i < nobbins.maxMonsterNumber; i++) 
		{
			if(id==1)
			{
				if ((Math.abs(nobbins.monsterX[i] - digger.diggerX) < 20
						&& Math.abs(nobbins.monsterY[i] - digger.diggerY) < 20)
						|| (screenData[(digger.diggerY / 20) - 1][digger.diggerX / 20] % 7 == 0)) 
				{

					dyingNr++;
					if(dyingNr==3) tot = true;
					cherrieRespawn = true;

					for (int j = 0; j < nobbins.maxMonsterNumber; j++) {

						nobbins.monsterX[j] = 780;
						nobbins.monsterY[j] = 20;

					}
					digger.drawDead(gc);

					delay(1000);

					digger.diggerX = 300;
					digger.fireBallX = 300;
					digger.diggerY = 540;
					digger.fireBallY = 540;
					nobbins.k = 1;
					sendDataToServer(digger.diggerX, digger.diggerY, score.score, keyNumber, goldnuggets, pause, dyingNr);

				}
				 
			}
			else if(id==2)
			{
				if ((Math.abs(nobbins.monsterX[i] - digger.digger2X) < 20
						&& Math.abs(nobbins.monsterY[i] - digger.digger2Y) < 20)
						|| (screenData[(digger.digger2Y / 20) - 1][digger.digger2X / 20] % 7 == 0)) 
				{

					dying2Nr++;
					if(dying2Nr==3) tot2 = true;
					cherrieRespawn = true;

					for (int j = 0; j < nobbins.maxMonsterNumber; j++) {

						nobbins.monsterX[j] = 780;
						nobbins.monsterY[j] = 20;

					}
					digger.drawDead(gc);

					delay(1000);

					digger.digger2X = 400;
					digger.fireBall2X = 400;
					digger.digger2Y = 540;
					digger.fireBall2Y = 540;
					nobbins.k = 1;
					sendDataToServer(digger.digger2X, digger.digger2Y, score2.score, keyNumber, goldnuggets, pause, dying2Nr);

				}
				

			}

		}
		
	}

	public boolean killMonster() {

		for (int i = 0; i < nobbins.maxMonsterNumber; i++) {

			if (Math.abs(digger.fireBallX - nobbins.monsterX[i]) < 30
					&& Math.abs(digger.fireBallY - nobbins.monsterY[i]) < 30) {
				score.score += 50;

				if (cherrie < 3) {
					cherrie++;

				}

				nobbins.monsterX[i] = 780;
				nobbins.monsterY[i] = 20;
				monsterRespawnOn = true;

				return true;

			}

		}

		for (int i = 0; i < 5; i++) {

			if (screenData[(nobbins.monsterY[i] / 20) - 1][nobbins.monsterX[i] / 20] % 7 == 0) {
				score.score += 50;

				nobbins.monsterX[i] = 760;
				nobbins.monsterY[i] = 20;

				return true;
			}
		}

		return false;

	}

	public boolean goDown1 = false, nugEat1 = false, finaleBroke1 = false;
	public int nugX1, nugY1, broke1 = 0, counter = 0;
	public ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	public GoldDelay goldDelay = new GoldDelay(counter);

	private void goldNuggetGoDown1() 
	{
		counter = goldDelay.getCounter();
		for (int i = 0; i < 30; i++) 
		{
			for (int j = 0; j < 40; j++)
			{
				if (screenData[i][j] == 7 && screenData[i + 1][j] % 2 == 0) {

					nugX1 = j;
					nugY1 = i;
					goDown1 = true;
					break;

				}
				if (goDown1 == true) {
					break;
				}
			}
		}
		if (goDown1 == true && screenData[nugY1 + 1][nugX1] % 2 == 0) {
			if (counter == 0) {

				scheduledExecutorService.schedule(goldDelay, 1, TimeUnit.SECONDS);

			} else {
				scheduledExecutorService.shutdown();
				dying();
				killMonster();
				if (finaleBroke1 != true) {
					screenData[nugY1][nugX1] = 8;
					screenData[nugY1 + 1][nugX1] = 7;
				} else {
					screenData[nugY1][nugX1] = 8;

				}
				broke1 += 1;
				nugY1++;

			}
		}

		if (broke1 > 1 && screenData[nugY1 + 1][nugX1] % 2 != 0 && nugEat1 == false) {
			if (broke1 >= 2)
				finaleBroke1 = true;
			screenData[nugY1][nugX1] = 9;
		}
		if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 9 && nugEat1 == false) {
			score.score += 100;

			nugEat1 = true;
		} else if (nugEat1 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

	private boolean goDown2 = false, nugEat2 = false, finaleBroke2 = false;
	private int nugX2, nugY2, broke2 = 0;

	private void goldNuggetGoDown2() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 40; j++)

			{
				if (screenData[i][j] == 21 && screenData[i + 1][j] % 2 == 0) {

					nugX2 = j;
					nugY2 = i;
					goDown2 = true;

					break;

				}

			}
		}
		if (goDown2 == true && screenData[nugY2 + 1][nugX2] % 2 == 0) {
			// dying();
			killMonster();
			if (finaleBroke2 != true) {
				screenData[nugY2][nugX2] = 8;
				screenData[nugY2 + 1][nugX2] = 21;
			} else
				screenData[nugY2][nugX2] = 8;
			broke2 += 1;
			nugY2++;

		}

		if (broke2 > 1 && screenData[nugY2 + 1][nugX2] % 2 != 0 && nugEat2 == false) {
			if (broke2 >= 2)
				finaleBroke2 = true;
			screenData[nugY2][nugX2] = 27;
		}
		if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 27 && nugEat2 == false) {
			score.score += 100;

			nugEat2 = true;
		} else if (nugEat2 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

	private boolean goDown3 = false, nugEat3 = false, finaleBroke3 = false;
	private int nugX3, nugY3, broke3 = 0;

	private void goldNuggetGoDown3() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 40; j++)

			{
				if (screenData[i][j] == 35 && screenData[i + 1][j] % 2 == 0) {

					nugX3 = j;
					nugY3 = i;
					goDown3 = true;
					break;

				}

			}
		}
		if (goDown3 == true && screenData[nugY3 + 1][nugX3] % 2 == 0) {
			// dying();
			killMonster();
			if (finaleBroke3 != true) {
				screenData[nugY3][nugX3] = 8;
				screenData[nugY3 + 1][nugX3] = 35;
			} else
				screenData[nugY3][nugX3] = 8;
			broke3 += 1;
			nugY3++;

		}

		if (broke3 > 1 && screenData[nugY3 + 1][nugX3] % 2 != 0 && nugEat3 == false) {
			if (broke3 >= 2)
				finaleBroke3 = true;
			screenData[nugY3][nugX3] = 45;
		}
		if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 45 && nugEat3 == false) {
			score.score += 100;

			nugEat3 = true;
		} else if (nugEat3 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

	private boolean goDown4 = false, nugEat4 = false, finaleBroke4 = false;
	private int nugX4, nugY4, broke4 = 0;

	private void goldNuggetGoDown4() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 40; j++)

			{
				if (screenData[i][j] == 49 && screenData[i + 1][j] % 2 == 0) {

					nugX4 = j;
					nugY4 = i;
					goDown4 = true;
					break;

				}

			}
		}
		if (goDown4 == true && screenData[nugY4 + 1][nugX4] % 2 == 0) {
			// dying();
			killMonster();
			if (finaleBroke4 != true) {
				screenData[nugY4][nugX4] = 8;
				screenData[nugY4 + 1][nugX4] = 49;
			} else
				screenData[nugY4][nugX4] = 8;
			broke4 += 1;
			nugY4++;

		}

		if (broke4 > 1 && screenData[nugY4 + 1][nugX4] % 2 != 0 && nugEat4 == false) {
			if (broke4 >= 2)
				finaleBroke4 = true;
			screenData[nugY4][nugX4] = 81;
		}
		if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 81 && nugEat4 == false) {
			score.score += 100;

			nugEat4 = true;
		} else if (nugEat4 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

	private boolean goDown5 = false, nugEat5 = false, finaleBroke5 = false;
	private int nugX5, nugY5, broke5 = 0;

	private void goldNuggetGoDown5() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 40; j++)

			{
				if (screenData[i][j] == 77 && screenData[i + 1][j] % 2 == 0) {

					nugX5 = j;
					nugY5 = i;
					goDown5 = true;
					break;

				}

			}
		}
		if (goDown5 == true && screenData[nugY5 + 1][nugX5] % 2 == 0) {
			// dying();
			killMonster();
			if (finaleBroke5 != true) {
				screenData[nugY5][nugX5] = 8;
				screenData[nugY5 + 1][nugX5] = 77;
			} else
				screenData[nugY5][nugX5] = 8;
			broke5 += 1;
			nugY5++;

		}

		if (broke5 > 1 && screenData[nugY5 + 1][nugX5] % 2 != 0 && nugEat5 == false) {
			if (broke5 >= 2) {
				finaleBroke5 = true;
			}
			screenData[nugY5][nugX5] = 99;
		}
		if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 99 && nugEat5 == false) {
			score.score += 100;

			nugEat5 = true;
		} else if (nugEat5 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

	public Scene getScene() { return s; }
	public void setScene(Scene scene) { this.s = scene; }
	public Group getRoot() { return root; }
	public void setRoot(Group root) { this.root = root;}
	@Override public void finalize() {}
}
