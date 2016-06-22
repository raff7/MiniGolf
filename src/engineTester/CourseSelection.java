package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	public int courseID ;
	public int players ;
	public Course course ;
	Scanner in = new Scanner(System.in)  ;
	public boolean selectionDone = false ;
	
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
	
	public void amountOfPlayers(){
		
		System.out.println(" How many players ?") ;
		players = in.nextInt() ;
		
		
	}
	
	public void checkSelection(){
		
		System.out.println(" Which course do you want to load ?") ;
		courseID = in.nextInt();
		
		
	}

	@Override
	public void update() {
		
		amountOfPlayers() ;
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
		
		CourseLoader courseLoader = new CourseLoader(courseID) ;
		course = (Course) courseLoader.load() ;
		System.out.println("loading done") ;
	}

}
