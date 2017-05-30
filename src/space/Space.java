package space;

import static psilox.audio.Audio.*;

import java.util.List;

import psilox.core.Psilox;
import psilox.core.Window;
import psilox.graphics.Shader;
import psilox.graphics.Texture;
import psilox.input.Input;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.utility.Time;

public class Space extends Node {
	
	private static Shader postShader;
	private static Texture glass;
	
	public static Window window;
	public static final int WIDTH = 1280, HEIGHT = 720;
	
	public Space(String tag) { super(tag); }
	
	public void enteredTree() {
		if(postShader == null) {
			postShader = new Shader("space/assets/post.shd");
			glass = new Texture("space/assets/glass.png");
		}
		
		addLayer("background", 0);
		addLayer("asteroids", 1);
		addLayer("lasers", 2);
		addLayer("asteroids", 3);
		addLayer("ship", 4);
		
		addSound("laser", "space/assets/laser.wav");
		addSound("shield_down", "space/assets/shield_down.wav");
		addSound("shield_up", "space/assets/shield_up.wav");
		addSound("shatter", "space/assets/shatter.wav");
		addSound("rock", "space/assets/rock.wav");
		addMusic("game_song", "space/assets/tenfour_cavebouncer.wav");
		addMusic("engine", "space/assets/engine_loop.wav");
		
		loopMusic("game_song", 0.25);
		
		addChild(new Background("background"));
		addChild(new Node("laserList"));
		Node asteroidList = new Node("asteroidList");
		asteroidList.position.z = layer("asteroids");
		addChild(asteroidList);
		addChild(new Ship("player"));
	}
	
	public void exitedTree() {
		stopMusic("game_song");
		stopMusic("engine");
		window.setPostProcess(null, null, null);
	}
	
	public void update() {
		if(Input.keyTap(Input.ESCAPE)) {
			Psilox.changeScene(new Menu("menu"));
		}
		
		List<Node> al = getChild("asteroidList").getChildren();
		List<Node> ll = getChild("laserList").getChildren();
		Node p = getChild("player");
		
		for(Node x : al) {
			for(Node y : ll) {
				if(x.position.dst(y.position) < Asteroid.RADIUS) {
					x.freeSelf();
					y.freeSelf();
					playSound("rock", .1);
					break;
				}
			}
			
			if(x.position.dst(p.position) < Asteroid.RADIUS * 2) {
				x.freeSelf();
				((Ship) p).takeHit();
			}
		}
		
		if(Psilox.ticks % 180 == 0) {
			Asteroid a = new Asteroid("asteroid", new Vec(Random.intVal(WIDTH), Random.intVal(HEIGHT)));
			getChild("asteroidList").addChild(a);
		}
	}
	
	public void shatter() {
		window.setPostProcess(postShader, glass, this);
	}
	
	public void setUniforms(Shader s) {
		s.setUniform1f("u_norm_fac", .7f);
		s.setUniform1f("u_vign_fac", (float) Time.flipFlop(0, Time.SECOND) * 0.7f);
	}
	
}
