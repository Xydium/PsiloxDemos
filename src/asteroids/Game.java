package asteroids;

import java.awt.Font;
import java.util.List;

import psilox.core.Config;
import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.node.ui.Container;
import psilox.node.ui.Label;
import psilox.node.utility.Timer;
import psilox.utils.Pointer.IntPointer;

public class Game extends Node {

	private Player player;
	private Sky sky;
	private Node bulletList;
	private Node asteroidList;
	private Container UI;
	private IntPointer score;
	
	public void enteredTree() {
		this.tag = "game";
		
		player = new Player();
		player.tag = "player";
		player.position.set(viewSize().half());
		player.position.z = 1;
		
		bulletList = new Node();
		bulletList.tag = "bulletList";
		bulletList.position.z = .5f;
	
		asteroidList = new Node();
		asteroidList.tag = "asteroidList";
		asteroidList.position.z = .25f;
		
		sky = new Sky();
		sky.tag = "sky";
	
		Timer spawner = new Timer(5f, false, () -> asteroidList.addChild(Asteroid.spawn(viewSize())));
		spawner.tag = "asteroidSpawner";
		spawner.start();
		
		UI = new Container(viewSize(), new Vec(20));
		UI.tag = "UI";
		UI.position.z = 4;
		
		Label scoreLabel = new Label(Color.WHITE, new Font("Verdana", 0, 24), "Score: %s", score = new IntPointer(0));
		scoreLabel.tag = "scoreLabel";
		scoreLabel.setAnchor(Anchor.TL);
		UI.topLeft.addChild(scoreLabel);
		
		addChildren(sky, asteroidList, bulletList, player, spawner, UI);
	}
	
	public void update() {
		runCollisions();
		
		if(Psilox.ticks() % 60 == 0) {
			printTree(Psilox.root);
		}
	}
	
	private void runCollisions() {
		List<Bullet> bullets = bulletList.getChildren(Bullet.class);
		List<Asteroid> asteroids = asteroidList.getChildren(Asteroid.class);
		
		for(Asteroid a : asteroids) {
			boolean split = false;
			for(Bullet b : bullets) {
				if(a.position.dst(b.position) < (a.small ? 20 : 40)) {
					a.split();
					b.freeSelf();
					split = true;
					score.add(a.small ? 20 : 40);
					break;
				}
			}
			
			if(split) continue;
			
			if(a.position.dst(player.position) < (a.small ? 20 : 40)) {
				asteroidList.removeAllChildren();
				bulletList.removeAllChildren();
				addChild(new DeathEffect());
				//player.position.set(viewSize().half());
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		Psilox.start(new Config("Asteroids", 1280, 720, false), new Game());
	}
	
}
