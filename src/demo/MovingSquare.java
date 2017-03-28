package demo;

import static psilox.graphics.Color.*;
import static psilox.graphics.Draw.*;
import static psilox.graphics.Shape.*;
import psilox.graphics.Shape;
import static psilox.input.Input.*;
import static psilox.math.Random.*;
import static psilox.math.Vec.*;
import psilox.math.Vec;
import psilox.node.Node;

public class MovingSquare extends Node {

	private Shape square;
	
	public void enteredTree() {
		square = cquadf(ZERO, new Vec(100), RED, BLUE, GREEN, WHITE);
		position.set(viewSize().half());
	}
	
	public void update() {
		move();
		turn();
		teleport();
	}
	
	private void move() {
		Vec d = new Vec();
		
		if(keyDown(W)) d.add(Vec.UP); else
		if(keyDown(S)) d.add(Vec.DOWN);
		
		if(keyDown(A)) d.add(Vec.LEFT); else
		if(keyDown(D)) d.add(Vec.RIGHT);
		
		position.add(d.scl(10));
	}
	
	private void turn() {
		if(keyDown(Q)) rotation += 3; else
		if(keyDown(E)) rotation -= 3;
	}
	
	private void teleport() {
		if(keyTap(SPACE) || !position.btn(Vec.ZERO, viewSize())) {
			position.set(viewSize().pro(new Vec(floatVal(1), floatVal(1))));
		}
	}
	
	public void render() {
		shape(square);
	}
	
}
