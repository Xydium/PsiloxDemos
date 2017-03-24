package geo;

import java.util.List;

import psilox.core.Config;
import psilox.core.Psilox;
import psilox.math.Random;
import psilox.node.Node;

public class Main extends Node {

	private Player player;
	private Node enemyList;
	
	public void enteredTree() {
		player = new Player();
		player.tag = "player";
		player.position.set(viewSize().half());
		player.position.z = 1;
		
		enemyList = new Node();
		addChildren(enemyList, player);
		
		spawnEnemy(2);
	}
	
	public void update() {
		if(Psilox.ticks() % 180 == 0) {
			spawnEnemy(1);
		}
		if(Psilox.ticks() % 240 == 0) {
			enemyList.getChild(0).freeSelf();
		}
		
		List<Node> enemies = enemyList.getChildrenUnsafe();
		boolean reset = false;
		for(Node e : enemies) {
			if(e.position.dst(player.position) <= 30) {
				print("Hit!");
				reset = true;
				break;
			}
		}
		if(reset) {
			enemyList.removeAllChildren();
			spawnEnemy(2);
		}
	}
	
	private void spawnEnemy(int count) {
		for(int i = 0; i < count; i++) {
			BounceEnemy e = new BounceEnemy();
			e.position.set(Random.floatVal(viewSize().x), Random.floatVal(viewSize().y));
			enemyList.addChild(e);
		}
	}
	
	public static void main(String[] args) {
		Psilox.start(new Config("Geo", 1280, 720, false), new Main());
	}
	
}
