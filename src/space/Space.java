package space;

import psilox.core.Psilox;
import psilox.core.Window;
import psilox.graphics.Color;
import psilox.node.Node;

import static psilox.audio.Audio.*;

public class Space extends Node {

	public static final int WIDTH = 1280, HEIGHT = 720;
	
	public Space(String tag) { super(tag); }
	
	public void enteredTree() {
		addLayer("background", 0);
		addLayer("asteroids", 1);
		addLayer("lasers", 2);
		addLayer("ship", 3);
		
		addSound("laser", "space/assets/laser.wav");
		addSound("shield_down", "space/assets/shield_down.wav");
		addSound("shield_up", "space/assets/shield_up.wav");
		addMusic("song", "space/assets/tenfour_cavebouncer.wav");
		
		loopMusic("song", 0.25);
		
		addChild(new Background("background"));
		addChild(new Node("laserList"));
		addChild(new Ship("player"));
	}
	
	public static void main(String[] args) {
		Psilox.start(new Window("Space", WIDTH, HEIGHT, false, false, true, Color.BLACK), new Space("space"));
	}
	
}
