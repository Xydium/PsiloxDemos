package demo;

import java.awt.Font;
import java.util.HashMap;

import psilox.core.Config;
import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.input.Input;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.node.ui.Button;
import psilox.node.ui.Label;

public class Main extends Node {

	private HashMap<String, Node> demos = new HashMap<String, Node>();
	
	public void enteredTree() {
		addReturnListener();
		addLabels();
		
		registerDemo("Moving Square", new MovingSquare());
		registerDemo("Particles", new Particles());
		registerDemo("Asteroids", new asteroids.Game());
		registerDemo("Geo", new geo.Game());
	}
	
	private void registerDemo(String name, Node demo) {
		demos.put(name, demo);
		Button b = new Button(new Vec(250, 25), name, () -> Psilox.changeScene(demo));
		b.position.set(new Vec(50));
		b.position.y += 30 * (demos.size() - 1);
		addChild(b);
	}
	
	private void addReturnListener() {
		if(Psilox.root.getChildren().size() == 1) {
			Psilox.root.addChild(new Node() {
				public void update() {
					if(Input.keyTap(Input.ESCAPE)) {
						Psilox.changeScene(new Main());
					}
				}
			});
		}
	}
	
	private void addLabels() {
		Label title = new Label(Color.WHITE, new Font("Verdana", 0, 64), "Psilox Demos");
		title.setAnchor(Anchor.TL);
		title.position.set(50, viewSize().y - 50);
		addChild(title);
		
		Label ret = new Label(Color.WHITE, new Font("Verdana", 0, 24), "ESC returns to Menu");
		ret.setAnchor(Anchor.BR);
		ret.position.set(viewSize().x - 50, 50);
		addChild(ret);
	}
	
	public static void main(String[] args) {
		Psilox.start(new Config("Complete Demo", 1920, 1080, true), new Main());
	}
	
}
