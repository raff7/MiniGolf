package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import entities.Course;
import fileHandler.CourseLoader;
import gui.Button;
import gui.GuiRenderer;
import gui.GuiTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public class CourseSelection implements GameState {
	
	private static GuiRenderer guiRenderer;
	private static List<GuiTexture> menuGuis;
	private static List<Button> menuButtons;
	private static Loader loader;
	public int x ;
	public Course course ;
	
	public CourseSelection(){
		
		loader = new Loader();
		guiRenderer = new GuiRenderer(loader);

		menuGuis = new ArrayList<GuiTexture>();
		menuButtons = new ArrayList<Button>();
		
		GuiTexture backGround = new GuiTexture(loader.loadTexture("backGround"),new Vector2f (0,-1f),new Vector2f(1.35f,2f));
		GuiTexture title = new GuiTexture(loader.loadTexture("title"),new Vector2f (0,0.75f),new Vector2f(0.75f,0.75f));
		
		Button quit = new Button(loader.loadTexture("quit"),loader.loadTexture("quitSel"),new Vector2f (0.035f,-0.7f),new Vector2f(0.3f,0.15f));
		
		menuGuis.add(backGround) ;
		menuGuis.add(title) ;
		menuButtons.add(quit);
	}
	
	public void checkButtons(){
		
		if (Mouse.getX() > 290 && Mouse.getX() < 355 && Mouse.getY() > 50 && Mouse.getY() < 80) {
			if(Mouse.isButtonDown(0)){
				System.out.println("Quit") ;
				changeGameState(new MainMenu());
			}
		}
	}
	
	public void checkSelection(){
		//this will load the course
		
	}

	@Override
	public void update() {
		
		checkButtons() ;
		checkSelection() ;
		loadCourse() ;
		changeGameState(new SinglePlayer(course)) ;
    }

	@Override
	public void render() {
		
		guiRenderer.renderMenu(menuGuis,menuButtons);
		DisplayManager.updateDisplay();		
	}

	@Override
	public void changeGameState(GameState newState) {
		
		MainGameLoop.changeGameState(newState);
	}
	
	public void loadCourse(){
		x = 1 ; //testing reason
		CourseLoader courseLoader = new CourseLoader(x);
		course = (Course) courseLoader.load() ;
		System.out.println("loading done");
	}

}
