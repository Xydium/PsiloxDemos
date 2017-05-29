package space;

import psilox.core.Psilox;
import psilox.core.Window;
import psilox.graphics.Color;
import psilox.input.Input;
import psilox.node.Node;

import static psilox.audio.Audio.*;

public class Space extends Node {

	public static Window window;
	public static final int WIDTH = 1920, HEIGHT = 1080;
	
	public Space(String tag) { super(tag); }
	
	public void enteredTree() {
		addLayer("background", 0);
		addLayer("asteroids", 1);
		addLayer("lasers", 2);
		addLayer("ship", 3);
		
		addSound("laser", "space/assets/laser.wav");
		addSound("shield_down", "space/assets/shield_down.wav");
		addSound("shield_up", "space/assets/shield_up.wav");
		addMusic("game_song", "space/assets/tenfour_cavebouncer.wav");
		addMusic("engine", "space/assets/engine_loop.wav");
		
		loopMusic("game_song", 0.25);
		
		addChild(new Background("background"));
		addChild(new Node("laserList"));
		addChild(new Ship("player"));
	}
	
	public void exitedTree() {
		stopMusic("game_song");
	}
	
	public void update() {
		if(Input.keyTap(Input.ESCAPE)) {
			Psilox.changeScene(new Menu("menu"));
		}
	}
	
}
