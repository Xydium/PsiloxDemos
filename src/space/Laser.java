package space;

import psilox.graphics.Texture;
import psilox.math.Vec;
import psilox.node.Node;

public class Laser extends Node {
	
	private static Texture laserTexture;
	private static final float SPEED = 30;
	
	public Laser(String tag, Vec position, float rotation) {
		super(tag);
		this.position.set(position);
		position.z = 0.5f;
		this.rotation = rotation;
		if(laserTexture == null) laserTexture = new Texture("space/assets/laser_normal.png");
		texture = laserTexture;
		dimensions = new Vec(laserTexture.getWidth(), laserTexture.getHeight());
	}
	
	public void update() {
		position.add(Vec.UP.rot(-rotation).scl(SPEED));
		
		if(position.x < 0 || position.x > Space.WIDTH || position.y < 0 || position.y > Space.HEIGHT) {
			freeSelf();
		}
	}
	
}
