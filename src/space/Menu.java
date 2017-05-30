package space;

import static psilox.audio.Audio.*;

import java.awt.Font;

import psilox.core.Psilox;
import psilox.core.Window;
import psilox.graphics.Color;
import psilox.graphics.Shader;
import psilox.graphics.Texture;
import psilox.input.Input;
import psilox.math.Anchor;
import psilox.math.Vec;
import psilox.node.Label;
import psilox.node.Node;
import psilox.utility.Time;

public class Menu extends Node {
	
	private static Shader glitchShader;
	
	private Node selected;
	
	public Menu(String tag) { super(tag); }
	
	public void enteredTree() {
		if(glitchShader == null) {
			glitchShader = new Shader("space/assets/glitch.shd");
		}
		
		Background bg = new Background("background");
		bg.position.z = -2;
		addChild(bg);
		
		Label title = new Label("title", Color.WHITE, new Font("Verdana", 0, 72), "Asteroids");
		title.position = new Vec(50, Space.HEIGHT - 50);
		title.shader = glitchShader;
		title.modulate = new Color(0xD59EFCFF);
		title.anchor = Anchor.TOP_LEFT;
		addChild(title);
		
		Node selection = new Node("selection");
		selection.position = new Vec(0, 100, -1);
		addChild(selection);
		
		Flame f1 = new Flame("f1");
		f1.position.y = -3;
		f1.anchor = Anchor.BOTTOM_MIDDLE;
		f1.rotation = 90;
		f1.scale = new Vec(0.3, 2);
		selection.addChild(f1);
		
		Label play = new Label("play", Color.WHITE, new Font("Verdana", 0, 36), "Play");
		play.position = new Vec(50, 100);
		play.shader = glitchShader;
		play.modulate = title.modulate;
		play.anchor = Anchor.MIDDLE_LEFT;
		addChild(play);
		
		Label quit = new Label("quit", Color.WHITE, new Font("Verdana", 0, 36), "Quit");
		quit.position = new Vec(50, 50);
		quit.shader = glitchShader;
		quit.modulate = title.modulate;
		quit.anchor = Anchor.MIDDLE_LEFT;
		addChild(quit);
		
		selected = play;
		
		addSound("change_selection", "space/assets/shield_down.wav");
		addSound("select", "space/assets/shield_up.wav");
		
		addMusic("menu_song", "space/assets/tenfour_cavebouncer.wav");
		loopMusic("menu_song", 0.25);
	}
	
	public void exitedTree() {
		stopMusic("menu_song");
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
				selection.position.y = 50;
				playSound("change_selection");
			} else if(select) {
				playSound("select");
				Psilox.changeScene(new Space("space"));
			}
		} else {
			if(changeSelection) {
				selected = getChild("play");
				selection.position.y = 100;
				playSound("change_selection");
			} else if(select) {
				playSound("select");
				Psilox.quit();
			}
		}
	}
	
	
	public static void main(String[] args) {
		Psilox.start(Space.window = new Window("Space", Space.WIDTH, Space.HEIGHT, false, false, true, Color.BLACK), new Menu("menu"));
	}
	
}
