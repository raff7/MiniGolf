package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import gui.Button;
import gui.GuiRenderer;
import gui.GuiTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public class MainMenu implements GameState{
	
	private static GuiRenderer guiRenderer;
	private static List<GuiTexture> menuGuis;
	private static List<Button> menuButtons;
	private static Loader loader;

	
	public MainMenu(){
		
		loader = new Loader();
		guiRenderer = new GuiRenderer(loader);

		menuGuis = new ArrayList<GuiTexture>();
		menuButtons = new ArrayList<Button>();
		GuiTexture backGround = new GuiTexture(loader.loadTexture("backGround"),new Vector2f (0,-1f),new Vector2f(1.35f,2f));
		GuiTexture title = new GuiTexture(loader.loadTexture("title"),new Vector2f (0,0.5f),new Vector2f(0.75f,0.75f));
		Button singlePlayer = new Button(loader.loadTexture("singlePlayer"),loader.loadTexture("singlePlayerSel"),new Vector2f (0.02f,0.1f),new Vector2f(0.3f,0.15f));
		Button multiPlayer = new Button(loader.loadTexture("multiPlayer"),loader.loadTexture("multiPlayerSel"),new Vector2f (0.02f,-0.1f),new Vector2f(0.3f,0.15f));
		Button editCourse = new Button(loader.loadTexture("editCourse"),loader.loadTexture("editCourseSel"),new Vector2f (0.02f,-0.3f),new Vector2f(0.3f,0.15f));
		Button option = new Button(loader.loadTexture("options"),loader.loadTexture("optionsSel"),new Vector2f (0.1f,-0.5f),new Vector2f(0.3f,0.15f));
		Button quit = new 	Button(loader.loadTexture("quit"),loader.loadTexture("quitSel"),new Vector2f (0.035f,-0.7f),new Vector2f(0.3f,0.15f));
		
		menuGuis.add(backGround);
		menuButtons.add(singlePlayer);
		menuButtons.add(multiPlayer);
		menuButtons.add(editCourse);
		menuButtons.add(option);
		menuButtons.add(quit);
		menuGuis.add(title);
		
	}
	public void update() {
		int x = Mouse.getX()-(DisplayManager.getWidth()/2);
		int y =Mouse.getY()-(DisplayManager.getHeight()/2);
		//SinglePlayer
		if((y > 20) && (y < 150) && (x > -243) && (x < 270)){
			menuButtons.get(0).setSel(true);
			if(Mouse.isButtonDown(0)){
				changeGameState(new SinglePlayer());//go to singlePlayer menu
			}
		}else{
			menuButtons.get(0).setSel(false);
		}
		
		//MultiPlayer
		if((y > -102) && (y < -10) && (x > -245) && (x < 260)){
			menuButtons.get(1).setSel(true);
			if(Mouse.isButtonDown(0)){
				changeGameState(null);//changeGameState(new MultiPlayer());//go to MultiPlayer menu
			}
		}else{
			menuButtons.get(1).setSel(false);
		}
		//EditCourse
				if((y > -200) && (y < -110) && (x > -243) && (x < 260)){
					menuButtons.get(2).setSel(true);
					if(Mouse.isButtonDown(0)){
						changeGameState(null);//changeGameState(new CourseDesigner());//go to courseDesigner menu
					}
				}else{
					menuButtons.get(2).setSel(false);
				}
		
		//options
		if((y > -278) && (y < -210) && (x > -178) && (x < 150)){
			menuButtons.get(3).setSel(true);
			if(Mouse.isButtonDown(0)){
				changeGameState(null);//changeGameState(new Option(this));//go to Options menu
			}
		}else{
			menuButtons.get(3).setSel(false);
		}
		//quit
		if((y > -400) && (y < -280) && (x > -99) && (x < 87)){
			menuButtons.get(4).setSel(true);
			if(Mouse.isButtonDown(0)){
				changeGameState(null);
			}
		}else{
			menuButtons.get(4).setSel(false);
		}


	}

	public void render() {
		guiRenderer.renderMenu(menuGuis,menuButtons);
		DisplayManager.updateDisplay();		
	}

	public void changeGameState(GameState newState) {
		MainGameLoop.Notify(newState);
		
	}

}