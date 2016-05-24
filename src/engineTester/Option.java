package engineTester;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import gui.Button;
import gui.GuiRenderer;
import gui.GuiTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public class Option implements GameState{

	private GameState activeGameState;
	
	private ArrayList<GuiTexture> menuGuis;
	private ArrayList<Button> menuButtons;

	private Loader loader;
	private GuiRenderer guiRenderer;
	
	public Option(GameState activeGameState){

		loader = new Loader();
		guiRenderer = new GuiRenderer(loader);
		
		this.activeGameState = activeGameState;
		menuGuis = new ArrayList<GuiTexture>();
		menuButtons = new ArrayList<Button>();

		
		GuiTexture backGround = new GuiTexture(loader.loadTexture("backGround"),new Vector2f (0,-1f),new Vector2f(1.35f,2f));
		GuiTexture title = new GuiTexture(loader.loadTexture("title"),new Vector2f (0,0.5f),new Vector2f(0.75f,0.75f));
		Button screenSize = new Button(loader.loadTexture("screenSize"),loader.loadTexture("screenSizeSel"),new Vector2f (0.02f,0.1f),new Vector2f(0.3f,0.15f));
		/*Button multiPlayer = new Button(loader.loadTexture("multiPlayer"),loader.loadTexture("multiPlayerSel"),new Vector2f (0.02f,-0.1f),new Vector2f(0.3f,0.15f));
		Button editCourse = new Button(loader.loadTexture("editCourse"),loader.loadTexture("editCourseSel"),new Vector2f (0.02f,-0.3f),new Vector2f(0.3f,0.15f));
		Button option = new Button(loader.loadTexture("options"),loader.loadTexture("optionsSel"),new Vector2f (0.1f,-0.5f),new Vector2f(0.3f,0.15f));*/
		Button back = new 	Button(loader.loadTexture("back"),loader.loadTexture("backSel"),new Vector2f (0.035f,-0.7f),new Vector2f(0.3f,0.15f));

		menuGuis.add(backGround);
		menuButtons.add(screenSize);
		menuButtons.add(back);
		menuGuis.add(title);
	}
	@Override
	public void update() {
		int x = Mouse.getX()-(DisplayManager.getWidth()/2);
		int y =Mouse.getY()-(DisplayManager.getHeight()/2);
		//ScreenSize
		if((y > 0) && (y < 50) && (x > -99) && (x < 95)){
			menuButtons.get(0).setSel(true);
			if(Mouse.isButtonDown(0)){
				//TODO change screen size 
			}
		}else{
			menuButtons.get(0).setSel(false);
		}
		//quit
		if((y > -160) && (y < -121) && (x > -39) && (x < 39)){
			menuButtons.get(1).setSel(true);
			if(Mouse.isButtonDown(0)){
				changeGameState(null);//go back to main menu
			}
		}else{
			menuButtons.get(1).setSel(false);
		}
		
	}

	@Override
	public void render() {
		guiRenderer.renderMenu(menuGuis,menuButtons);
		DisplayManager.updateDisplay();		
	}

	@Override
	public void changeGameState(GameState newState) {
			
		loader.cleanUp();
		guiRenderer.cleanUp();
		
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		MainGameLoop.changeGameState(activeGameState);
		
	}

}
