package space;

import psilox.core.Psilox;
import psilox.core.Window;
import psilox.graphics.Color;
import psilox.math.Vec;
import psilox.node.Node;

public class Space extends Node {

	public static final int WIDTH = 1280, HEIGHT = 720;
	
	public Space(String tag) { super(tag); }
	
	public void enteredTree() {
		addChild(new Background("background"));
		addChild(new Ship("player"));
	}
	
	public static void main(String[] args) {
		Psilox.start(new Window("Space", WIDTH, HEIGHT, false, false, Color.BLACK), new Space("space"));
	}
	
}
