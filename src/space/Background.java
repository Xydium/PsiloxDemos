package space;

import psilox.graphics.Color;
import psilox.graphics.Shader;
import psilox.graphics.Texture;
import psilox.math.Anchor;
import psilox.math.Rect;
import psilox.math.Vec;
import psilox.node.Node;

public class Background extends Node {

	public Background(String tag) {
		super(tag);
		anchor = Anchor.BOTTOM_LEFT;
		dimensions = new Vec(Space.WIDTH, Space.HEIGHT);
		texture = new Texture("space/assets/blue.png");
		textureRegion = new Rect(0, 0, Space.WIDTH, Space.HEIGHT);
		modulate = new Color(155, 205, 255);
		position.z = layer("background");
	}
	
}
