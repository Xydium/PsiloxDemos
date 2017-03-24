package geo;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.math.Vec;
import psilox.node.Node;

public class Trail extends Node {

	private Color color;
	private Vec delta;
	
	public Trail(Color color) {
		this.color = color;
		this.delta = new Vec(0);
	}
	
	public void move(Vec delta) {
		this.delta.set(delta.scl(-1));
	}
	
	public void render() {
		Draw.line(color, Vec.ZERO, delta.rot(-globalRotation()));
		delta.set(0);
	}
	
}
