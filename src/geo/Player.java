package geo;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.input.Input;
import psilox.math.Vec;
import psilox.node.Node;

public class Player extends Node {

	private static final float MOVEMENT_SPEED = 30;
	
	private Vec targetPosition;
	
	public void enteredTree() {
		targetPosition = new Vec(position);
	}
	
	public void update() {
		if(Input.buttonDown(Input.BUTTON_LEFT)) {
			targetPosition.set(Input.position);
		}
		
		Vec diff = targetPosition.dif(position);
		float dist = position.dst(targetPosition);
		if(dist > .99) {
			Vec disp = Vec.angMag(diff.ang(), dist > MOVEMENT_SPEED ? MOVEMENT_SPEED : dist);
			position.add(disp);
		}
	}
	
	public void render() {
		Draw.ellipse(new Color(238, 75, 181), Vec.ZERO, 20, 16);
	}
	
}
