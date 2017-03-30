package asteroids;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.node.utility.Interpolator;

public class DeathEffect extends Node {

	private Color color;
	
	public void enteredTree() {
		position.z = 5;
		color = Color.RED;
		Interpolator i = new Interpolator(v -> color = color.aAdj(v));
		i.addKeyFrames(0, 1, 1, 0);
		i.setCosineInterpolated(true);
		i.setOnEnd(this::freeSelf);
		i.start();
		addChild(i);
	}
	
	public void render() {
		Draw.quad(color, Vec.ZERO, viewSize());
	}
	
}
