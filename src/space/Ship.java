package space;

import static psilox.audio.Audio.*;

import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.graphics.Shader;
import psilox.graphics.Texture;
import psilox.input.Input;
import psilox.math.Anchor;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.utility.Time;

public class Ship extends Node {

	private static final Vec EMITTER_LEFT = new Vec(-45, 5);
	private static final Vec EMITTER_RIGHT = new Vec(45, 5);
	
	private static Texture shipTexture;
	private static Shader shipShader;
	
	static {
		shipTexture = new Texture("space/assets/ship.png");
		shipShader = new Shader("space/assets/ship.shd");
		shipShader.setUniform4f("u_outline_color", new Color(0xD59EFCFF));
		shipShader.setUniform2f("u_step_size", 1.0f / shipTexture.getWidth(), 1.0f / shipTexture.getHeight());
		shipShader.setUniform1i("u_noise", 1);
	}
	
	private static final float ACCELERATION = 0.2f;
	private static final float TURN_RATE = 4f; 
	private static final float MAX_SPEED = 15;
	
	public Vec velocity;
	
	public Node laserList;
	
	public Ship(String tag) {
		super(tag);
		position = new Vec(Space.WIDTH / 2, Space.HEIGHT / 2, layer("ship"));
		texture = shipTexture;
		shader = shipShader;
		dimensions = new Vec(texture.getWidth(), texture.getHeight());
		velocity = new Vec(0);
	}
	
	public void enteredTree() {
		laserList = nodePath("./laserList");
		Flame f = new Flame("flame");
		f.position.set(0, -38, -0.1f);
		f.scale = new Vec(0.12, 0.3);
		f.rotation = 180;
		f.anchor = Anchor.BOTTOM_MIDDLE;
		f.timeMult = 5;
		addChild(f);
	}
	
	public void update() {
		if(getChild(0).visible = Input.keyDown(Input.W)) {
			velocity.add(Vec.UP.rot(-rotation).scl(ACCELERATION));
			velocity = velocity.clm(MAX_SPEED);
			if(!isMusicPlaying("engine")) 
				loopMusic("engine", 0.10);
		} else {
			stopMusic("engine");
		}
		
		if(Input.keyDown(Input.D)) {
			rotation += TURN_RATE;
		} else if(Input.keyDown(Input.A)) {
			rotation -= TURN_RATE;
		}
		
		if(Input.keyDown(Input.SPACE) && Psilox.ticks % 10 == 0) {
			laserList.addChild(new Laser("laser", position.sum(EMITTER_LEFT.rot(-rotation)), rotation));
			laserList.addChild(new Laser("laser", position.sum(EMITTER_RIGHT.rot(-rotation)), rotation));
			playSound("laser", 0.25);
		}
		
		position.add(velocity);
		
		position.x = (position.x + Space.WIDTH) % Space.WIDTH;
		position.y = (position.y + Space.HEIGHT) % Space.HEIGHT;
		
		if(Input.keyTap(Input.ENTER)) {
			if(shader == null) shader = shipShader;
			else shader = null;
			
			playSound(shader == null ? "shield_down" : "shield_up", 0.5);
		}
	}
	
	public void setUniforms(Shader s) {
		s.setUniform1f("u_step_mult", (float) Time.flipFlop(0, Time.SECOND) * 3);
	}
	
}
