package digger.logik;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

public class Map {
	public Canvas canvas = new Canvas(780, 600);
	public GraphicsContext gc = canvas.getGraphicsContext2D();
	Group root = new Group();
	Scene s = new Scene(root);
	private Dimension d;
	public Digger digger = new Digger();
	public Nobbins nobbins = new Nobbins();
	public Score score = new Score();
	private final int blockSize = 30;
//	private final int nBlock = 40;
//	private final int screenSize = nBlock * blockSize;
	public boolean goDown1 = false, nugEat1 = false, finaleBroke1 = false;
	public int nugX1, nugY1, broke1 = 0, counter = 0;
	private boolean goDown2 = false, nugEat2 = false, finaleBroke2 = false;
	private int nugX2, nugY2, broke2 = 0;
	private boolean goDown3 = false, nugEat3 = false, finaleBroke3 = false;
	private int nugX3, nugY3, broke3 = 0;
	private boolean goDown4 = false, nugEat4 = false, finaleBroke4 = false;
	private int nugX4, nugY4, broke4 = 0;
	private boolean goDown5 = false, nugEat5 = false, finaleBroke5 = false;
	private int nugX5, nugY5, broke5 = 0;

	private final int levelData[][] = {
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2,
					2, 2, 2, 2, 2 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 49, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
					3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3,
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
	public boolean up = false, down = false, right = false, left = false, active = false, killed = false, go = true,
			move = false;
	public String dir = "l";
	public int fire = 1;
	public int cherrie;
	public boolean cherrieRespawn = false;
	public boolean monsterRespawnOn = true;
	public boolean cherrieEat = false;
	public boolean lastRound = false;
	public ResourceManager resourceManager = new ResourceManager();
	public int[] highScore = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	public SaveHighScore saveHighScore;

	public Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> { // für fireball

		go = true;

	}));

	public SaveData saveData;
	public Clip mediaPlayer;
	public boolean lData2;

	public Map() {

		try 
		{
			try 
			{
				mediaPlayer = AudioSystem.getClip();
				mediaPlayer.open(AudioSystem.getAudioInputStream(new File("digger/gui/extend/Digger.wav")));
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

		loadHighScore();
		initVariables();
		initLevel(levelData);
		drawMap(gc);
		diggerMove();
//		timer.start();

	}

	public void loadHighScore() {

		try {
			SaveHighScore saveHigh = (SaveHighScore) resourceManager.load("2.save");
			highScore = saveHigh.highScore;

		} catch (Exception e) {
			System.out.println("Couldn't load save Data" + e.getMessage());
		}

	}

	public void scoreEntering(int scoreCurrent) {

		Arrays.sort(highScore);

		for (int i = 0; i < 10; i++) {
			if (scoreCurrent > highScore[i]) {

				highScore[i] = scoreCurrent;

				break;
			}

		}
		Arrays.sort(highScore);
		saveHighScore = new SaveHighScore(highScore);
		try {
			System.out.println(" saved");
			resourceManager.save(saveHighScore, "2.save");
		} catch (Exception e) {
			System.out.println("Couldn't save" + e.getMessage());
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
			saveData = new SaveData(screenData, score.score, dyingNr, nobbins.monsterX, nobbins.monsterY,
					digger.diggerX, digger.diggerY);
			try {
				System.out.println(" saved");
				resourceManager.save(saveData, "1.save");
			} catch (Exception e) {
				System.out.println("Couldn't save" + e.getMessage());
			}
			initLevel(levelData2);
			lData2 = true;
			digger.diggerX = 400;
			digger.diggerY = 540;
			nobbins = null;
			nobbins = new Nobbins();
			nobbins.maxMonsterNumber = 5;
			nobbins.rand = 6;
			cherrie = 0;
			cherrieEat = false;

		}

	}

	private void initVariables() {
		screenData = new int[30][40]; // wir haben 30 zeile und 30 spalte in der Karte d.h. , dass jede zeile hat 30
										// block,der Länge 20 ist und auch bei Spalten
		d = new Dimension(600, 800); // Größe der fenster

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
		if (dyingNr < 3) {
			// gc.setFill(Color.ORANGE);
			if (lData2 == false)
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/backgroundLevel01.jpg")), 0,
						0, d.height, d.width);
			if (lData2 == true)
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/backgroundLevel02.jpg")), 0,
						0, d.height, d.width);
			// gc.fillRect(0, 0, d.height, d.width);
			int i, j;
			int x = 0, y = 0;
			for (i = 0; i < 30; i++) {
				for (j = 0; j < 40; j++) {
					if (screenData[i][j] % 2 == 0) {
						gc.setFill(Color.BLACK);
						gc.fillRect(x, y, blockSize, blockSize);
					}
					if (screenData[i][j] == 5) {
//						gc.setFill(Color.GREEN);
						gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/emerald.jpg")),
								x + 10, y + 10, 15, 15);
//						gc.fillOval(x + 11, y + 11, 10, 10);
					}

					if (screenData[i][j] % 7 == 0) {
						gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/GoldBag.png")),
								x + 10, y + 10, 20, 20);
//                           gc.setFill(Color.PINK);
//                           gc.fillOval(x, y, 20, 20);

					}
					if (screenData[i][j] % 9 == 0) {

						gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Gold.jpg")), x, y, 30,
								30);
//                            gc.setFill(Color.PINK);
//                            gc.fillOval(x, y, 10, 20);

					}

					x += 20;
				}
				x = 0;
				y += 20;
			}

			digger.draw(gc);
			if (fire == 0) {
				digger.drawFireBall(gc);
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
//				gc.setFill(Color.ORANGE);
//				gc.fillRect(0, 0, 20, 20);

			score.drawScore(gc);
			if (lData2 == false)
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Level01.jpg")), 350, 1, 100,
						20);
			if (lData2 == true)
				gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Level02.jpg")), 350, 1, 100,
						20);
		} else {

			scoreEntering(score.score);

			try {
				timer.stop();
				mediaPlayer.stop();
				Controller.setSceneGameOver(highScore);
			} catch (IOException ex) {
				Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

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

		if (cherrie == 3 && cherrieEat == false) {

//			gc.setFill(Color.PINK);
//			gc.fillOval(750, 27, 10, 10);
			gc.drawImage(new Image(getClass().getResourceAsStream("/digger/gui/extend/Cherry.jpg")), 750, 27, 20, 20);
//			System.out.println("Here we are agaian");

		}
		if (digger.diggerX / 20 == 37 && digger.diggerY / 20 == 1 && cherrieEat == false) {
			gc.setFill(Color.BLACK);
			gc.fillRect(740, 20, 20, 20);
			digger.draw(gc);
			score.score += 1000;
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

	public void loadGame() {
		try {
			SaveData data = (SaveData) resourceManager.load("1.save");
			screenData = data.screenDataCurrent;
			dyingNr = data.dyingNrCurrent;
			score.score = data.scoreCurrent;
			nobbins.monsterX = data.monsterXCurrent;
			nobbins.monsterY = data.monsterYCurrent;
			digger.diggerX = data.diggerXCurrent;
			digger.diggerY = data.diggerYCurrent;

		} catch (Exception e) {
			System.out.println("Couldn't load save Data" + e.getMessage());
		}
	}

	public boolean mediaPlayerOn;

	public void diggerMove() {

		s.setOnKeyPressed((KeyEvent event) -> {

			switch (event.getCode()) {
			case ENTER: {
				saveData = new SaveData(screenData, score.score, dyingNr, nobbins.monsterX, nobbins.monsterY,
						digger.diggerX, digger.diggerY);
				try {
					System.out.println(" saved");
					resourceManager.save(saveData, "1.save");
				} catch (Exception e) {
					System.out.println("Couldn't save" + e.getMessage());
				}
				break;
			}

			case F7: {
				if (mediaPlayerOn) {
					mediaPlayer.stop();
					mediaPlayerOn = false;
				} else {
					mediaPlayer.start();
					mediaPlayerOn = true;
				}

				break;

			}
			case SPACE: {

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
																						// Emeraldsstelle eintrifft,dann
																						// erhöhe die Score

						score.score += 25;
					}
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 8; // Digger lässt in seiner Stelle Schwarze
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
					if ((screenData[digger.diggerY / 20 + 1][digger.diggerX / 20] == 5)) { // hier bedeutet wenn Digger
																							// Emeraldsstelle
																							// eintrifft,dann
																							// erhöhe die Score

						score.score += 25;
					}
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 4; // Digger lässt in seiner Stelle Schwarze
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
						} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 21
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

							digger.diggerX -= 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 21;
						} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 35
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

							digger.diggerX -= 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 35;
						} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 49
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

							digger.diggerX -= 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 49;
						}

						else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 77
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] % 7 != 0) {

							digger.diggerX -= 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 77;
						}

						else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] == 21
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] == 7) {

							digger.diggerX -= 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) - 1] = 21;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) - 2] = 7;
						}

					} else {
						digger.diggerX -= 20;
					}
					if (active == false)
						digger.fireBallX = digger.diggerX;
					if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 5) { // hier bedeutet wenn Digger
																						// Emeraldsstelle eintrifft,dann
																						// erhöhe die Score

						score.score += 25;
					}
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 2; // Digger lässt in seiner Stelle Schwarze
																				// Weg
					/*
					 * for(int i = 0;i<30;i++){ for(int j = 0;j<30;j++)
					 * System.out.print(screenData[i][j]); System.out.println();}
					 */
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
					digger.diggerX += 20;
					if (active == false)
						digger.fireBallX = digger.diggerX;
					if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 5) { // hier bedeutet wenn Digger
																						// Emeraldsstelle eintrifft,dann
																						// erhöhe die Score

						score.score += 25;
					}
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 6; // Digger lässt in seiner Stelle Schwarze
					if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] % 7 == 0) {
						if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 7
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

							digger.diggerX += 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 7;
						} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 21
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

							digger.diggerX += 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 21;
						}

						else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 7
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] == 21) {

							digger.diggerX += 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] = 21;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 7;
						} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 35
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

							digger.diggerX += 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 35;
						} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 49
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

							digger.diggerX += 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 49;
						} else if (screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] == 77
								&& screenData[digger.diggerY / 20][(digger.diggerX / 20) + 2] % 7 != 0) {

							digger.diggerX += 20;
							screenData[digger.diggerY / 20][(digger.diggerX / 20) + 1] = 77;
						}
					}

					else {
						digger.diggerX += 20;
					}
					if (active == false)
						digger.fireBallX = digger.diggerX;
					if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 5) { // hier bedeutet wenn Digger
																						// Emeraldsstelle eintrifft,dann
																						// erhähe die Score

						score.score += 50;
					}
					screenData[digger.diggerY / 20][digger.diggerX / 20] = 2; // Digger lässt in seiner Stelle Schwarze
																				// Weg
																				// Weg
					/*
					 * for(int i = 0;i<30;i++){ for(int j = 0;j<30;j++)
					 * System.out.print(screenData[i][j]); System.out.println();}
					 */
					break;
				}
			}
			default:
				break;

			}

			drawMap(gc); // wird nochmal die karte ausdruckt nach jede Bewegung von Digger, ist
							// untershiedlich von Swing. in swing steht die Methode repaint()
		});
		s.setOnKeyReleased((KeyEvent event) -> {

			timeline.play();
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
			nobbins.move(screenData, digger);
			dying();
			delay(100);
			drawMap(gc);

		}
	};

	private static void delay(int i) { // um delay (Verzögerung) zu erstellen

		try {
			Thread.sleep(i);
		} catch (InterruptedException ex) {
			Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public Timeline timeline5 = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {

	}));

	public void dying() {
		for (int i = 0; i < nobbins.maxMonsterNumber; i++) {
			if ((Math.abs(nobbins.monsterX[i] - digger.diggerX) < 20
					&& Math.abs(nobbins.monsterY[i] - digger.diggerY) < 20)) {

				dyingNr++;

				cherrieRespawn = true;

				for (int j = 0; j < nobbins.maxMonsterNumber; j++) {

					nobbins.monsterX[j] = 780;
					nobbins.monsterY[j] = 20;

				}
				digger.drawDead(gc);

				delay(1000);

				digger.diggerX = 400;
				digger.fireBallX = 400;
				digger.diggerY = 540;
				digger.fireBallY = 540;
				nobbins.k = 1;

			}

		}
	}

	public boolean killMonster() {

		for (int i = 0; i < nobbins.maxMonsterNumber; i++) {

			if (Math.abs(digger.fireBallX - nobbins.monsterX[i]) < 30
					&& Math.abs(digger.fireBallY - nobbins.monsterY[i]) < 30) {
				score.score += 250;

				if (cherrie < 3) {
					cherrie++;
//					System.out.println(cherrie);

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

	private void goldNuggetGoDown1() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 40; j++)

			{
				if (screenData[i][j] == 21 && screenData[i + 1][j] % 2 == 0) {

					nugX1 = j;
					nugY1 = i;
					goDown1 = true;

					break;

				}

			}
		}
		if (goDown1 == true && screenData[nugY1 + 1][nugX1] % 2 == 0) {

			killMonster();
			if (finaleBroke1 != true) {
				screenData[nugY1][nugX1] = 8;
				screenData[nugY1 + 1][nugX1] = 21;
			} else
				screenData[nugY1][nugX1] = 8;
			broke1 += 1;
			nugY1++;

		}

		if (broke1 > 1 && screenData[nugY1 + 1][nugX1] % 2 != 0 && nugEat1 == false) {
			if (broke1 >= 2)
				finaleBroke1 = true;
			screenData[nugY1][nugX1] = 27;
		}
		if (screenData[digger.diggerY / 20][digger.diggerX / 20] == 27 && nugEat1 == false) {
			score.score += 500;

			nugEat1 = true;
		} else if (nugEat1 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

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
			score.score += 500;

			nugEat2 = true;
		} else if (nugEat2 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

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
			score.score += 500;

			nugEat3 = true;
		} else if (nugEat3 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

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
			score.score += 500;

			nugEat4 = true;
		} else if (nugEat4 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

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
			score.score += 500;

			nugEat5 = true;
		} else if (nugEat5 == true) {
			screenData[(digger.diggerY / 20)][digger.diggerX / 20] = 2;

		}

	}

	public Scene getScene() {
		return s;
	}

	public void setScene(Scene scene) {
		this.s = scene;
	}

	public Group getRoot() {
		return root;
	}

	public void setRoot(Group root) {
		this.root = root;
	}

	@Override
	public void finalize() {
	}

}
