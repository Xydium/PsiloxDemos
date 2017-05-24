package space;

import psilox.core.Psilox;
import psilox.core.Window;
import psilox.graphics.Color;
import psilox.math.Vec;
import psilox.node.Node;

public class Space extends Node {

	public Space(String tag) { super(tag); }
	
	public void enteredTree() {
		addChild(new Background("background"));
	}
	
	public static void main(String[] args) {
		Psilox.start(new Window("Space", 1280, 720, false, false, Color.BLACK), new Space("space"));
	}
	
}
