package geo;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.graphics.Shape;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;

public class BounceEnemy extends Node {

	private static final float MOVEMENT_SPEED = 20;
	
	private Shape shape;
	private Trail trail;
	
	public void enteredTree() {
		rotation = Random.floatVal(360);
		shape = Shape.cquado(Vec.ZERO, new Vec(20), new Color(163, 80, 249));
		shape.setMode(Shape.OUTLINE); //TODO: Remove in Psilox 0.9.4
		trail = new Trail(new Color(163, 80, 249));
		trail.position.set(-10, 0);
		addChild(trail);
	}
	
	public void update() {
		reflectOffSides();
		moveForward();
	}
	
	public void render() {
		Draw.shape(shape);
	}

	private void reflectOffSides() {
		Vec viewSize = viewSize();
		
		if(!position.btn(Vec.ZERO, viewSize)) {
			Vec normal = null;
			
			if(position.x < 0) normal = new Vec(1, 0); 
			else if(position.x > viewSize.x) normal = new Vec(-1, 0);
			else if(position.y < 0) normal = new Vec(0, -1);
			else if(position.y > viewSize.y) normal = new Vec(0, 1);
			
			Vec velocity = Vec.angMag(rotation, 1);
			rotation = velocity.dif(normal.scl(2 * normal.dot(velocity))).ang();
		}
	}
	
	private void moveForward() {
		Vec forward = Vec.angMag(rotation, MOVEMENT_SPEED);
		position.add(forward);
		trail.move(forward.scl(3));
	}
	
}
