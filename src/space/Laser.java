package space;

import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.graphics.Shader;
import psilox.graphics.Texture;
import psilox.math.Rect;
import psilox.math.Vec;
import psilox.node.Node;

public class Laser extends Node {
	
	private static Texture laserTexture;
	private static Shader laserShader;
	private static final float SPEED = 15;

	static {
		laserTexture = new Texture("space/assets/laser_normal.png");
		laserShader = new Shader("space/assets/laser.shd");
		laserShader.setUniform1f("u_frequency", 20f);
		laserShader.setUniform1f("u_amplitude", 0.2f);
	}
	
	public Laser(String tag, Vec position, float rotation) {
		super(tag);
		this.position.set(position);
		this.position.z = layer("lasers") + ((Psilox.ticks % 1000) / 1000.0f);
		this.rotation = rotation;
		texture = laserTexture;
		shader = laserShader;
		dimensions = new Vec(laserTexture.getWidth() * 3, laserTexture.getHeight() * 3);
		modulate = new Color(0xD59EFCFF);
		textureRegion = new Rect(-1, -1, 3, 3);
	}
	
	public void update() {
		position.add(Vec.UP.rot(-rotation).scl(SPEED));
		
		if(position.x < 0 || position.x > Space.WIDTH || position.y < 0 || position.y > Space.HEIGHT) {
			freeSelf();
		}
	}
	
	public void setUniforms(Shader s) {
		laserShader.setUniform1f("u_time", ticksAlive() / 60f);
	}
	
}
