package asteroids;

import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.math.Vec;
import psilox.node.Node;

public class Bullet extends Node {

	private static final int VELOCITY = 30;
	
	private Vec velocity;
	private float lifetime;
	
	private Bullet() {}
	
	public void update() {
		position.add(velocity);
		lifetime += Psilox.deltaTime;
		
		if(!position.btn(new Vec(-200, -200), viewSize().sum(new Vec(200, 200)))) {
			freeSelf();
		}
	}
	
	public void render() {
		Draw.line(Color.RED, Vec.ZERO, Vec.DOWN.scl(lifetime * 100 + 10));
	}
	
	public static Bullet fireFrom(Player player) {
		Bullet b = new Bullet();
		b.rotation = player.rotation;
		Vec forward = Vec.UP.rot(b.rotation);
		b.position.set(player.position.sum(forward.scl(20)));
		b.velocity = forward.scl(VELOCITY);
		return b;
	}
	
}
