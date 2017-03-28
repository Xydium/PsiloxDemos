package asteroids;

import psilox.core.Config;
import psilox.core.Psilox;
import psilox.node.Node;

public class Game extends Node {

	private Player player;
	private Sky sky;
	private Node bulletList;
	
	public void enteredTree() {
		this.tag = "game";
		
		player = new Player();
		player.tag = "player";
		player.position.set(viewSize().half());
		player.position.z = 1;
		
		bulletList = new Node();
		bulletList.tag = "bulletList";
		bulletList.position.z = .5f;
		
		sky = new Sky();
		sky.tag = "sky";
		
		addChildren(sky, bulletList, player);
	}
	
	public static void main(String[] args) {
		Psilox.start(new Config("Asteroids", 1280, 720, false), new Game());
	}
	
}
