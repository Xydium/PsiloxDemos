package space;

import psilox.graphics.Texture;
import psilox.input.Input;
import psilox.math.Rect;
import psilox.math.Vec;
import psilox.node.Node;

public class Ship extends Node {

	private static final float ACCELERATION = 1.0f;
	
	public Vec velocity;
	
	public Ship(String tag) {
		super(tag);
		position = new Vec(Space.WIDTH / 2, Space.HEIGHT / 2, -1);
		texture = new Texture("space/assets/ship.png");
		dimensions = new Vec(texture.getWidth(), texture.getHeight());
		textureRegion = new Rect(0, 0, texture.getWidth(), texture.getHeight());
		velocity = new Vec(0);
	}
	
	public void update() {
		if(Input.keyDown(Input.W)) {
			velocity.add(Vec.UP.rot(rotation).scl(ACCELERATION));
		}
		
		if(Input.keyDown(Input.D)) {
			rotation--;
		} else if(Input.keyDown(Input.S)) {
			rotation++;
		}
	}
	
}
