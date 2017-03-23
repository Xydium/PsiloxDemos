package asteroids;

import org.lwjgl.opengl.GL11;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.graphics.Shape;
import psilox.input.Input;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;

public class Player extends Node {

	private static final float ACCELERATION = .1f;
	private static final float MAX_SPEED = 10;
	private static final float ROTATION = 3;
	
	private Shape ship;
	private Shape flame;
	
	private Vec velocity;
	
	private boolean accelerating;
	
	public void enteredTree() {
		ship = new Shape(GL11.GL_LINE_LOOP, new Vec[] {
			new Vec(0, 20), new Vec(-10, -20), new Vec(0, 0), new Vec(10, -20)
		}, Color.WHITE);
		
		flame = new Shape(Shape.QUAD, new Vec[] {
			new Vec(0), new Vec(-10, -20), new Vec(0), new Vec(10, -20)	
		}, new Color[] {
			Color.BLUE, Color.YELLOW, Color.RED, Color.YELLOW
		});
		
		velocity = new Vec(0);
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
	}
	
	public void render() {
		Draw.shape(ship);
		if(accelerating) {
			flame.getVerts()[6] = Random.floatVal(-3, 3); //x component of 3rd vec
			flame.getVerts()[7] = Random.floatVal(-5, 5) - 40; //y component of 3rd vec
			Draw.shape(flame);
		}
	}
	
}
