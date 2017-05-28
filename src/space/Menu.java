package space;

import static psilox.audio.Audio.*;

import java.awt.Font;

import psilox.core.Psilox;
import psilox.core.Window;
import psilox.graphics.Color;
import psilox.graphics.Shader;
import psilox.input.Input;
import psilox.math.Anchor;
import psilox.math.Vec;
import psilox.node.Label;
import psilox.node.Node;

public class Menu extends Node {

	private static Shader glitchShader;
	
	private Node selected;
	
	public Menu(String tag) { super(tag); }
	
	public void enteredTree() {
		if(glitchShader == null) glitchShader = new Shader("space/assets/glitch.shd");
		
		Background bg = new Background("background");
		bg.position.z = -2;
		addChild(bg);
		
		Label title = new Label("title", Color.WHITE, new Font("Verdana", 0, 72), "Asteroids");
		title.position = new Vec(Space.WIDTH / 2, Space.HEIGHT - 100);
		title.shader = glitchShader;
		addChild(title);
		
		Node selection = new Node("selection");
		selection.position = new Vec(Space.WIDTH / 2, 497, -1);
		addChild(selection);
		
		Flame f1 = new Flame("f1");
		f1.anchor = Anchor.BOTTOM_MIDDLE;
		f1.rotation = 90;
		f1.scale = new Vec(0.3, 0.7);
		selection.addChild(f1);
		
		Flame f2 = new Flame("f2");
		f2.anchor = Anchor.BOTTOM_MIDDLE;
		f2.rotation = -90;
		f2.scale = new Vec(-0.3, 0.7);
		selection.addChild(f2);
		
		Label play = new Label("play", Color.WHITE, new Font("Verdana", 0, 36), "Play");
		play.position = new Vec(Space.WIDTH / 2, 500);
		play.shader = glitchShader;
		addChild(play);
		
		Label quit = new Label("quit", Color.WHITE, new Font("Verdana", 0, 36), "Quit");
		quit.position = new Vec(Space.WIDTH / 2, 400);
		quit.shader = glitchShader;
		addChild(quit);
		
		selected = play;
		
		addSound("change_selection", "space/assets/shield_down.wav");
		addSound("select", "space/assets/shield_up.wav");
	}
	
	public void update() {
		Node play = getChild("play");
		Node quit = getChild("quit");
		Node selection = getChild("selection");
		
		boolean changeSelection = Input.keyTap(Input.UP) || Input.keyTap(Input.DOWN); 
		boolean select = Input.keyTap(Input.ENTER);
		
		if(selected.RUID == getChild("play").RUID) {
			if(changeSelection) {				
				selected = getChild("quit");
				selection.position.y = 395;
				playSound("change_selection");
			} else if(select) {
				playSound("select");
				Psilox.changeScene(new Space("space"));
			}
		} else {
			if(changeSelection) {
				selected = getChild("play");
				selection.position.y = 495;
				playSound("change_selection");
			} else if(select) {
				playSound("select");
				Psilox.quit();
			}
		}
	}
	
	public static void main(String[] args) {
		Psilox.start(new Window("Space", Space.WIDTH, Space.HEIGHT, true, true, true, Color.BLACK), new Menu("menu"));
	}
	
}
