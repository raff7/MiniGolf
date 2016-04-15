package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Ball;
import entities.Camera;
import entities.Course;
import entities.Entity;
import entities.Lamp;
import entities.Light;
import gui.Button;
import gui.GuiRenderer;
import gui.GuiTexture;

public class MainGameLoop {
	private static Course course;
	private static Loader loader;
	private static MasterRenderer renderer;
	private static GuiRenderer guiRenderer;
	private static List<GuiTexture> pauseMenuGuis;
	private static List<Button> pauseMenuButtons;
	private static List<GuiTexture> menuGuis;
	private static List<Button> menuButtons;

	public static void main(String[] args) {


		DisplayManager.createDisplay();
		loader = new Loader();
		pauseMenuGuis = new ArrayList<GuiTexture>();
		//generatePauseMenuGui();
		renderer = new MasterRenderer(loader);
		guiRenderer = new GuiRenderer(loader);
		
		//generateManualWorld();//temp
		//actualGameLoop();	//
		startMainMenuGui();

		quit();

	}



	private static void actualGameLoop(){
		loader = null;
		renderer = null;
		guiRenderer = null;
		loader = new Loader();
		renderer = new MasterRenderer(loader);
		guiRenderer = new GuiRenderer(loader);
		List<Light> lights = course.getLights();
		List<Entity> entities = course.getEntities();
		Ball ball = course.getBall();
		Camera camera = new Camera(ball);	
		List<Terrain> terrains = course.getTerrains();
		GuiTexture gui = new GuiTexture(loader.loadTexture("exampleGUI"),new Vector2f (-0.9f,0.9f),new Vector2f(0.1f,0.15f));
		List<GuiTexture> inGameGuis = new ArrayList<GuiTexture>();
		inGameGuis.add(gui);
		int exitLoop=0;
		while(!(Display.isCloseRequested() || exitLoop != 0)){
			exitLoop = checkActualGameImputs();
			ball.move(course.getCurrentTerrain());
			camera.move();
			renderer.processEntity(ball);
			for(Terrain terrain:terrains)
			renderer.processTerrain(terrain);
			
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(lights, camera);
			guiRenderer.render(inGameGuis);
			DisplayManager.updateDisplay();
		}		
		if(exitLoop == 1){
			pauseMenu();
		}
		quit();
	}

	private static void pauseMenu() {
		int exitLoop = 0;
		while(!(Display.isCloseRequested()||exitLoop!=0)){
			
		}
		
	}
	private static void startMainMenuGui() {
		menuGuis=null;
		menuGuis = new ArrayList<GuiTexture>();
		menuButtons = null;
		menuButtons = new ArrayList<Button>();
		GuiTexture backGround = new GuiTexture(loader.loadTexture("flat"),new Vector2f (0,0),new Vector2f(1f,1f));
//		GuiTexture title = new GuiTexture(loader.loadTexture("white"),new Vector2f (0,0),new Vector2f(1f,1f));
		Button singlePlayer = new Button(loader.loadTexture("singlePlayer"),loader.loadTexture("singlePlayerSel"),new Vector2f (0.02f,0.1f),new Vector2f(0.2f,0.1f));
		Button multiPlayer = new Button(loader.loadTexture("multiPlayer"),loader.loadTexture("multiPlayerSel"),new Vector2f (0.02f,-0.1f),new Vector2f(0.2f,0.1f));
		Button editCourse = new Button(loader.loadTexture("editCourse"),loader.loadTexture("editCourseSel"),new Vector2f (0.02f,-0.3f),new Vector2f(0.2f,0.1f));
		Button option = new Button(loader.loadTexture("options"),loader.loadTexture("optionsSel"),new Vector2f (0.085f,-0.5f),new Vector2f(0.2f,0.1f));	
		Button quit = new 	Button(loader.loadTexture("quit"),loader.loadTexture("quitSel"),new Vector2f (0.035f,-0.7f),new Vector2f(0.11f,0.09f));
		
		
		
		
		menuGuis.add(backGround);
		menuButtons.add(singlePlayer);
		menuButtons.add(multiPlayer);
		menuButtons.add(editCourse);
		menuButtons.add(option);
		menuButtons.add(quit);
//		menuGuis.add(title);
		
		
		//menu start
		int exitLoop = 0;
		while(!(Display.isCloseRequested() || exitLoop != 0)){
			exitLoop = checkMainMenuImputs();
			guiRenderer.renderMenu(menuGuis,menuButtons);
			DisplayManager.updateDisplay();
		}
		if(exitLoop==1){
			startSinglePlayerMenu();
		}else if(exitLoop==2){
			//startSinglePlayerMenu();
		}else if(exitLoop==3){
			//startSinglePlayerMenu();
		}else if(exitLoop==4){
			//startSinglePlayerMenu();
		}
		quit();
	}
	private static int checkMainMenuImputs() {
		int x = Mouse.getX()-(DisplayManager.getWidth()/2);
		int y =Mouse.getY()-(DisplayManager.getHeight()/2);
		//SinglePlayer
		if((y > 40) && (y < 100) && (x > -162) && (x < 180)){
			menuButtons.get(0).setSel(true);
			if(Mouse.isButtonDown(0)){
				return 1;//go to singlePlayer menu
			}
		}else{
			menuButtons.get(0).setSel(false);
		}
		
		//MultiPlayer
		if((y > -68) && (y < -13) && (x > -162) && (x < 178)){
			menuButtons.get(1).setSel(true);
			if(Mouse.isButtonDown(0)){
				return 2;//go to MultiPlayer menu
			}
		}else{
			menuButtons.get(1).setSel(false);
		}
		//EditCourse
				if((y > -175) && (y < -123) && (x > -162) && (x < 178)){
					menuButtons.get(2).setSel(true);
					if(Mouse.isButtonDown(0)){
						return 2;//go to MultiPlayer menu
					}
				}else{
					menuButtons.get(2).setSel(false);
				}
		
		//options
		if((y > -278) && (y < -229) && (x > -103) && (x < 100)){
			menuButtons.get(3).setSel(true);
			if(Mouse.isButtonDown(0)){
				return 4;//go to Options menu
			}
		}else{
			menuButtons.get(3).setSel(false);
		}
		//options
		if((y > -385) && (y < -342) && (x > -66) && (x < 58)){
			menuButtons.get(4).setSel(true);
			if(Mouse.isButtonDown(0)){
				quit();
			}
		}else{
			menuButtons.get(4).setSel(false);
		}


			
		
		return 0;
	}
	private static void startSinglePlayerMenu(){
		generateManualWorld();
		actualGameLoop();
	}
	private static int checkActualGameImputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_P)){
			return 1;
		}
		return 0;
		
	}
	private static void generateManualWorld() {
		course = new Course();
		course.addTerrain(new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")),"heightMap"));
		
		
		RawModel dragonModel = OBJLoader.loadObjModel("dragon", loader);
		RawModel treeModel = OBJLoader.loadObjModel("tree", loader);
		RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
		RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
		RawModel lampModel = OBJLoader.loadObjModel("lamp", loader);
		RawModel playerModel = OBJLoader.loadObjModel("person", loader);
		
		
		course.setBall(new Ball(new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture"))),new Vector3f(400,0,360),0,0,0,1));
		float yball = (float) course.getHeightOfTerrain(400, 360);
		course.getBall().setPosition(new Vector3f(course.getBall().getPosition().x,yball,course.getBall().getPosition().z));

		TexturedModel staticDragonModel = new TexturedModel(dragonModel,new ModelTexture(loader.loadTexture("gold")));
		staticDragonModel.getTexture().setReflectivity(0.7f);
		staticDragonModel.getTexture().setShineDamper(5);
		TexturedModel staticTreeModel = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel staticGrassModel = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
		staticGrassModel.getTexture().setNumberOfRows(3);
		staticGrassModel.getTexture().setHasTransparency(true);
		staticGrassModel.getTexture().setUseFakeLight(true);
		ModelTexture fernTextures = new ModelTexture(loader.loadTexture("fern"));
		fernTextures.setNumberOfRows(2);
		fernTextures.setHasTransparency(true);
		TexturedModel staticFernModel = new TexturedModel(fernModel,fernTextures);
		TexturedModel lampTexturedModel = new TexturedModel(lampModel,new ModelTexture(loader.loadTexture("lamp")));
		
		
				
		course.addEntity(new Entity(staticDragonModel, new Vector3f(360,course.getHeightOfTerrain(360, 400),400),0,-90,0,5));
		course.addEntity(new Entity(staticDragonModel, new Vector3f(440,course.getHeightOfTerrain(440, 400),400),0,-90,0,5));


		Random random = new Random();
		
			
			
		for(int i=0;i<300;i++){
			if(i%20 == 0){
				float x =random.nextFloat()*800;
				float z = random.nextFloat() * 800;
				float y = course.getHeightOfTerrain(x, z);
				if((x> 500 || x<300)&&(y> 500 || y<300))
			course.addEntity(new Entity(staticDragonModel, new Vector3f(x,y,z),0,0,0,3));
			}
			if(	i%5 == 0){
				float x =random.nextFloat()*800;
				float z = random.nextFloat() * 800;
				float y = course.getHeightOfTerrain(x, z);
				Entity currentTree = new Entity(staticTreeModel, new Vector3f(x,y,z),0,0,0,20);
				if((x> 500 || x<300)&&(y> 500 || y<300))
					course.addEntity(currentTree);
			}
			if(i%4==0){
				float x =random.nextFloat()*800;
				float z = random.nextFloat()*800;
				float y = course.getHeightOfTerrain(x, z);
				Entity fern = new Entity(staticFernModel,random.nextInt(4), new Vector3f(x,y,z),0,0,0,1);
				course.addEntity(fern);
			}
			if(i%2 ==0){
				float x =random.nextFloat()* 800;
				float z = random.nextFloat() * 800;
				float y = course.getHeightOfTerrain(x, z);
				
				Entity grass = new Entity(staticGrassModel,random.nextInt(9), new Vector3f(x,y,z),0,0,0,3);
				course.addEntity(grass);
			}
				
		}
			
		Lamp lamp = new Lamp(lampTexturedModel, new Vector3f(400,course.getHeightOfTerrain(400, 400),400),0,0,0,1,new Vector3f(0,1,0));
		course.addEntity(lamp);
		Lamp lamp1 = new Lamp(lampTexturedModel, new Vector3f(320,course.getHeightOfTerrain(320, 400),400),0,0,0,1,new Vector3f(1,0,0));
		course.addEntity(lamp1);
		Lamp lamp2 = new Lamp(lampTexturedModel, new Vector3f(480,course.getHeightOfTerrain(480, 400),400),0,0,0,1,new Vector3f(1,1,0));
		course.addEntity(lamp2);

		course.addLight(lamp.getLight());
		course.addLight(lamp1.getLight());
		course.addLight(lamp2.getLight());
		Light sun = new Light(new Vector3f(20000,20000,2000),new Vector3f(0.3f,0.2f,0.5f));
		course.addLight(sun);
		
	}
	private static void quit(){
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		System.exit(0);
	}
	
}
