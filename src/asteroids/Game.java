package asteroids;

import java.awt.Font;

import psilox.core.Config;
import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.input.Input;
import psilox.input.InputEvent;
import psilox.input.InputEvent.InputState;
import psilox.input.InputEvent.InputType;
import psilox.node.Node;
import psilox.node.ui.Label;
import psilox.utils.Log;
import psilox.utils.Pointer.StringPointer;

public class Game extends Node {

	private Player player;
	private Sky sky;
	private Node bulletList;
	private StringPointer treeDisplay;
	
	public void enteredTree() {
		this.tag = "game";
		
		player = new Player();
		player.tag = "player";
		player.position.set(viewSize().half());
		player.position.z = 1;
		
		bulletList = new Node();
		bulletList.tag = "bulletList";
		bulletList.position.z = .5f;
		
		sky = new Sky();
		sky.tag = "sky";
		
		treeDisplay = new StringPointer("");
		Label treeLabel = new Label(Color.WHITE, new Font("Verdana", 0, 16), "%s", treeDisplay);
		treeLabel.position.z = 5;
		
		addChildren(sky, bulletList, player, treeLabel);
		
		setInputListening(true);
	}
	
	public void update() {
		if(Psilox.ticks() % 60 == 0) {
			Log.getLines().clear();
			printTree(Psilox.root);
			String[] lines = Log.getLines().toArray(new String[Log.getLines().size()]);
			String res = "";
			for(String l : lines) {
				res += l + "\n";
			}
			treeDisplay.set(res);
		}
	}
	
	public void render() {
		
	}
	
	public void receiveInput(InputEvent ev) {
		if(ev.matches(new InputEvent(InputType.KEYBOARD, Input.BACKSPACE, InputState.RELEASED))) {
			printTree(Psilox.root);
		}
	}
	
	public static void main(String[] args) {
		Psilox.start(new Config("Asteroids", 1280, 720, false), new Game());
	}
	
}
