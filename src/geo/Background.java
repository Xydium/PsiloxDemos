package geo;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.graphics.Shader;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;

public class Background extends Node {

	private Shader shader;
	private float targetTime;
	private float actualTime;
	
	public void enteredTree() {
		shader = new Shader("geo/background.shd");
		shader.setUniform4f("low", new Color(5, 0, 35, 55));
		shader.setUniform4f("high", new Color(15, 35, 75, 55));
		shader.setUniform2f("res", viewSize().x / 5, viewSize().y / 5);
		shader.disable();
	}
	
	public void update() {
		if(Random.intVal(10) == 1) {
			targetTime = Random.floatVal(-1, 1);
		}
		actualTime = ((targetTime - actualTime) / 1000) + actualTime; 
	}
	
	public void render() {
		shader.enable();
		shader.setUniform1f("time", actualTime);
		Draw.quad(Color.WHITE, Vec.ZERO, viewSize());
		shader.disable();
	}
	
}
