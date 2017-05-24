package space;

import psilox.core.Psilox;
import psilox.graphics.Texture;
import psilox.input.Input;
import psilox.math.Vec;
import psilox.node.Node;

public class Ship extends Node {

	private static final float ACCELERATION = 0.1f;
	private static final float TURN_RATE = 4f; 
	
	public Vec velocity;
	
	public Ship(String tag) {
		super(tag);
		position = new Vec(Space.WIDTH / 2, Space.HEIGHT / 2, 1);
		texture = new Texture("space/assets/ship.png");
		dimensions = new Vec(texture.getWidth(), texture.getHeight());
		velocity = new Vec(0);
	}
	
	public void update() {
		if(Input.keyDown(Input.W)) {
			velocity.add(Vec.UP.rot(-rotation).scl(ACCELERATION));
		}
		
		if(Input.keyDown(Input.D)) {
			rotation += TURN_RATE;
		} else if(Input.keyDown(Input.A)) {
			rotation -= TURN_RATE;
		}
		
		if(Input.keyDown(Input.SPACE) && Psilox.ticks % 10 == 0) {
			getParent().addChild(new Laser("laser", position, rotation));
		}
		
		position.add(velocity);
		
		position.x = (position.x + Space.WIDTH) % Space.WIDTH;
		position.y = (position.y + Space.HEIGHT) % Space.HEIGHT;
	}
	
}
