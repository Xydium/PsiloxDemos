package space;

import psilox.graphics.Texture;
import psilox.math.Anchor;
import psilox.math.Rect;
import psilox.math.Vec;
import psilox.node.Node;

public class Background extends Node {

	public Background(String tag) {
		super(tag);
		anchor = Anchor.BOTTOM_LEFT;
		texture = new Texture("space/assets/blue.png");
		textureRegion = new Rect(0, 0, 1280, 720);
		dimensions = new Vec(1280, 720);
	}
	
}
