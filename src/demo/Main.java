package demo;

import java.util.HashMap;

import psilox.core.Config;
import psilox.core.Psilox;
import psilox.input.Input;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.node.ui.Button;
import psilox.node.ui.Container;

public class Main extends Node {

	private HashMap<String, Node> demos = new HashMap<String, Node>();
	private Container container;
	
	public void enteredTree() {
		if(Psilox.root.getChildren().size() == 1) {
			Psilox.root.addChild(new Node() {
				public void update() {
					if(Input.keyTap(Input.ESCAPE)) {
						Psilox.changeScene(new Main());
					}
				}
			});
		}
		
		container = new Container(viewSize(), new Vec(200));
		addChild(container);
		
		registerDemo("Moving Player", new MovingSquare());
	}
	
	private void registerDemo(String name, Node demo) {
		demos.put(name, demo);
		Button b = new Button(new Vec(250, 25), name, () -> Psilox.changeScene(demo));
		b.setAnchor(Anchor.MM);
		container.bottomLeft.addChild(b);
	}
	
	public static void main(String[] args) {
		Psilox.start(new Config("Complete Demo", 1280, 720, false), new Main());
	}
	
}
