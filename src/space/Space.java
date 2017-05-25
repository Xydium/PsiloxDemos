package space;

import psilox.core.Psilox;
import psilox.core.Window;
import psilox.graphics.Color;
import psilox.math.Mat4;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.utility.Log;
import psilox.utility.Time;

public class Space extends Node {

	public static final int WIDTH = 1280, HEIGHT = 720;
	
	public Space(String tag) { super(tag); }
	
	public void enteredTree() {
		addLayer("background", 0);
		addLayer("asteroids", 1);
		addLayer("lasers", 2);
		addLayer("ship", 3);
		
		addChild(new Background("background"));
		addChild(new Node("laserList"));
		addChild(new Ship("player"));
	}
	
	public void update() {
		print("%f %f %f", Psilox.dt, Psilox.du, Psilox.df);
	}
	
	public static void main(String[] args) {
		Psilox.start(new Window("Space", WIDTH, HEIGHT, false, false, true, Color.BLACK), new Space("space"));
	}
	
}
