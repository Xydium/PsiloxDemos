package asteroids;

import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.graphics.Shader;
import psilox.math.Vec;
import psilox.node.Node;

public class Sky extends Node {

	private Shader sky;
	
	public void enteredTree() {
		sky = new Shader("asteroids/sky.shd");
		sky.setUniform4f("color", new Color(0, 5, 10));
		sky.setUniform1f("threshold", .97f);
		sky.disable();
	}
	
	public void render() {
		sky.enable();
		sky.setUniform1f("time", Psilox.ticks() / 15f);
		Draw.quad(Color.WHITE, Vec.ZERO, viewSize());
		sky.disable();
	}
	
}
