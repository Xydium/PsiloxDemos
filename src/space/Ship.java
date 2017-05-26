package space;

import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.graphics.Texture;
import psilox.input.Input;
import psilox.math.Vec;
import psilox.node.Node;

import static psilox.audio.Audio.*;

public class Ship extends Node {

	private static final Vec EMITTER_LEFT = new Vec(-45, 5);
	private static final Vec EMITTER_RIGHT = new Vec(45, 5);
	
	private static Texture shipTexture;
	
	private static final float ACCELERATION = 0.1f;
	private static final float TURN_RATE = 4f; 
	
	public Vec velocity;
	
	public Node laserList; 
	
	public Ship(String tag) {
		super(tag);
		position = new Vec(Space.WIDTH / 2, Space.HEIGHT / 2, layer("ship"));
		if(shipTexture == null)
			shipTexture = new Texture("space/assets/ship.png");
		texture = shipTexture;
		dimensions = new Vec(texture.getWidth(), texture.getHeight());
		velocity = new Vec(0);
	}
	
	public void enteredTree() {
		laserList = nodePath("./laserList");
	}
	
	public void update() {
		if(Input.keyDown(Input.W)) {
			velocity.add(Vec.UP.rot(-rotation).scl(ACCELERATION));
		}
		
		if(Input.keyDown(Input.D)) {
			rotation += TURN_RATE;
		} else if(Input.keyDown(Input.A)) {
			rotation -= TURN_RATE;
		}
		
		if(Input.keyDown(Input.SPACE) && Psilox.ticks % 10 == 0) {
			laserList.addChild(new Laser("laser", position.sum(EMITTER_LEFT.rot(-rotation)), rotation));
			laserList.addChild(new Laser("laser", position.sum(EMITTER_RIGHT.rot(-rotation)), rotation));
			playSound("laser", 0.5);
		}
		
		position.add(velocity);
		
		position.x = (position.x + Space.WIDTH) % Space.WIDTH;
		position.y = (position.y + Space.HEIGHT) % Space.HEIGHT;
	}
	
}
