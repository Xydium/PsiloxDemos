package space;

import psilox.graphics.Shader;
import psilox.graphics.Texture;
import psilox.math.Vec;
import psilox.node.Node;

public class Flame extends Node {

	private static Texture flameTexture;
	private static Shader flameShader;
	
	static {
		flameTexture = new Texture("space/assets/flame.png");
		flameShader = new Shader("space/assets/flame.shd");
	}
	
	public Flame(String tag) { 
		super(tag);
		texture = flameTexture;
		dimensions = texture.getDimensions();
		shader = flameShader;
	}
	
}
