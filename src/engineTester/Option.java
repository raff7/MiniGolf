package engineTester;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
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

	private boolean isPressed = false;

	private static int count = 0;
	private static int tempWidth = DisplayManager.getWidth();
	private static int tempHeight = DisplayManager.getHeight();
	
	public Option(GameState activeGameState){

		loader = Loader.getLoader();
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
		if((y > 20 && y < 130 && x > -270 && x < 275)){
			menuButtons.get(0).setSel(true);
			if(Mouse.isButtonDown(0)){
				if(!isPressed){
				scrollScreenSize();
				isPressed = true;}
			}
			else{
				isPressed = false;
			}
		}else{
			menuButtons.get(0).setSel(false);
		}
		//back
		if((y > -420 && y < -311 && x > -130 && x < 135)){
			menuButtons.get(1).setSel(true);
			if(Mouse.isButtonDown(0)){
				if(!isPressed) {

					changeGameState(activeGameState);//go back to main menu
					isPressed = true;}
			}
			else{
				isPressed = false;
			}
		}else{
			menuButtons.get(1).setSel(false);
		}
		
	}


	public static void scrollScreenSize(){
		if(count == 0){
			count++;

			tempWidth = 850;
			tempHeight = 500;

			System.out.println(tempWidth + " x " + tempHeight);

		}

		else if(count == 1){
			count++;

			tempWidth = 1300;
			tempHeight = 800;

			System.out.println(tempWidth + " x " + tempHeight);

		}

		else if(count == 2){
			count++;

			tempWidth = 1920;
			tempHeight = 1080;

			System.out.println(tempWidth + " x " + tempHeight);

		}

		else if(count == 3){
			count = 0;

			tempWidth = 650;
			tempHeight = 400;

			System.out.println(tempWidth + " x " + tempHeight);

		}

	}

	public static void changeScreenSize(){

		if(count == 1){

			try{
				Display.setDisplayMode(new DisplayMode(tempWidth,tempHeight));}
			catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		else if(count == 2){

			try{
				Display.setDisplayMode(new DisplayMode(tempWidth,tempHeight));}
			catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		else if(count == 3){

			try{
				Display.setDisplayMode(new DisplayMode(tempWidth,tempHeight));}
			catch (LWJGLException e) {
				e.printStackTrace();
			}


		}
		else if(count == 0){

			try{
				Display.setDisplayMode(new DisplayMode(tempWidth,tempHeight));}
			catch (LWJGLException e) {
				e.printStackTrace();
			}


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
