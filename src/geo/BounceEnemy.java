package geo;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.graphics.Shape;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;

public class BounceEnemy extends Node {

	private static final float MOVEMENT_SPEED = 10;
	
	private Shape shape;
	
	public void enteredTree() {
		rotation = Random.floatVal(360);
		shape = Shape.cquado(Vec.ZERO, new Vec(20), new Color(163, 80, 249));
	}
	
	public void update() {		
		if(!position.btn(Vec.ZERO, viewSize())) {
			rotation += 180 + Random.floatVal(-20, 20);
		}
		
		Vec forward = Vec.angMag(rotation, MOVEMENT_SPEED);
		position.add(forward);
	}
	
	public void render() {
		Draw.shape(shape);
	}
	
}
