package space;

import psilox.graphics.Texture;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;

public class Asteroid extends Node {

	public static final float RADIUS = 30;
	
	private static Texture asteroidTexture;
	
	static {
		asteroidTexture = new Texture("space/assets/meteor.png");
	}
	
	private Vec velocity;
	private float turnSpeed;
	
	public Asteroid(String tag, Vec position) {
		super(tag);
		this.position = position;
		this.texture = asteroidTexture;
		this.dimensions = texture.getDimensions();
		this.velocity = Vec.TWO_D.rot(Random.intVal(360)).scl(Random.floatVal(1, 3));
		this.turnSpeed = Random.floatVal(-5, 5);
		this.scale = new Vec(Random.floatVal(0.9f, 1.1f), Random.floatVal(.9f, 1.1f));
	}
	
	public void update() {
		position.add(velocity);
		position.x = (position.x + Space.WIDTH) % Space.WIDTH;
		position.y = (position.y + Space.HEIGHT) % Space.HEIGHT;
		rotation += turnSpeed;
	}
	
}
