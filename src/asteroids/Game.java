package asteroids;

import psilox.core.Config;
import psilox.core.Psilox;
import psilox.node.Node;

public class Game extends Node {

	private Player player;
	
	public void enteredTree() {
		player = new Player();
		player.position.set(viewSize().half());
		player.position.z = 1;
		addChild(player);
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}
	
	public static void main(String[] args) {
		Psilox.start(new Config("Asteroids", 1280, 720, false), new Game());
	}
	
}
