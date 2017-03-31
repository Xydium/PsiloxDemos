package asteroids;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.graphics.Shape;
import psilox.input.Input;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.node.utility.Timer;

public class Player extends Node {

	private static final float ACCELERATION = .1f;
	private static final float MAX_SPEED = 20;
	private static final float ROTATION = 3;
	
	private Shape ship;
	private Shape flame;
	private Vec velocity;
	private Node bulletList;
	
	private boolean accelerating;
	private boolean canShoot;
	
	public void enteredTree() {
		ship = new Shape(Shape.POLY, new Vec[] {
			new Vec(0, 20), new Vec(-10, -20), new Vec(0, 0), new Vec(10, -20)
		}, Color.WHITE);
		
		flame = new Shape(Shape.QUAD, new Vec[] {
			new Vec(0), new Vec(-10, -20), new Vec(0), new Vec(10, -20)	
		}, new Color[] {
			Color.CYAN.aAdj(.7f), Color.YELLOW.aAdj(.4f), Color.RED.aAdj(.17f), Color.YELLOW.aAdj(.4f)
		});
		
		velocity = new Vec(0);
		bulletList = nodePath("/game/bulletList");
		canShoot = true;
		
		Timer t;
		addChild(t = new Timer(.2f, true, () -> canShoot = true));
		t.tag = "bulletCooldown";
	}
	
	public void update() {
		if(accelerating = Input.keyDown(Input.W)) {
			velocity.add(Vec.UP.rot(rotation).scl(ACCELERATION));
			velocity = velocity.clm(MAX_SPEED);
		}
		
		if(Input.keyDown(Input.A)) rotation += ROTATION; else
		if(Input.keyDown(Input.D)) rotation -= ROTATION;
		
		position.add(velocity);
		
		Vec viewSize = viewSize();
		position.x = (position.x < 0 ? viewSize.x - 1 : position.x) % viewSize.x;
		position.y = (position.y < 0 ? viewSize.y - 1 : position.y) % viewSize.y;
		
		if(Input.keyDown(Input.SPACE) && canShoot) {
			bulletList.addChild(Bullet.fireFrom(this));
			canShoot = false;
			getChild(0, Timer.class).start();
		}
	}
	
	public void render() {
		Draw.shape(ship);
		if(accelerating) {
			flame.setVertAt(2, new Vec(Random.floatVal(-3, 3), Random.floatVal(-5, 5) - 40));
			Draw.shape(flame);
		}
	}
	
}
