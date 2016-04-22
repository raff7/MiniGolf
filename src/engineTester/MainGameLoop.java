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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
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
	private static WaterRenderer waterRenderer;
	private static WaterShader waterShader;
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

		clean();

	}



	private static void actualGameLoop(){
	
		loader = null;
		renderer = null;
		guiRenderer = null;
		waterShader = null;
		waterRenderer = null;
		
		
		
		loader = new Loader();
		renderer = new MasterRenderer(loader);
		guiRenderer = new GuiRenderer(loader);
		
		List<Light> lights = course.getLights();
		List<Entity> entities = course.getEntities();
		List<WaterTile> waters = course.getWaters();
		List<Terrain> terrains = course.getTerrains();

		Ball ball = course.getBall();
		Camera camera = new Camera(ball);
		entities.add(ball);
		MousePicker mouse = new MousePicker(camera , renderer.getProjectionMatrix()) ; //might need to be removed, only for testing..
		
		GuiTexture gui = new GuiTexture(loader.loadTexture("exampleGUI"),new Vector2f (-0.9f,0.9f),new Vector2f(0.1f,0.15f));
		List<GuiTexture> inGameGuis = new ArrayList<GuiTexture>();
		inGameGuis.add(gui);
		//**** WATER ***
		
				WaterShader waterShader = new WaterShader();
				WaterFrameBuffers buffers = new WaterFrameBuffers();
				waterRenderer = new WaterRenderer(loader,waterShader,renderer.getProjectionMatrix(),buffers);
								
				
		//***************

		
//							****GAME LOOP****		
		int exitLoop=0;
		while(!(Display.isCloseRequested() || exitLoop != 0)){
			exitLoop = checkActualGameImputs();
			ball.move(course.getCurrentTerrain());
			camera.move();
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			for(WaterTile water:waters){
				//render reflection on water texture
				buffers.bindReflectionFrameBuffer();
				float distance = 2*(camera.getPosition().y-water.getHeight());
				camera.getPosition().y -= distance;
				camera.invertPitch();
				renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0,1,0,-water.getHeight()));
				camera.getPosition().y += distance;
				camera.invertPitch();
				
				//render refraction on water texture
				buffers.bindRefractionFrameBuffer();
				renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0,-1,0,water.getHeight()));
			}
			//render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(entities,terrains,lights,camera, new Vector4f(0,-1,0,150000));
			waterRenderer.render(waters, camera);
			guiRenderer.render(inGameGuis);
			DisplayManager.updateDisplay();
			mouse.update() ;
		}		
		if(exitLoop == 1){
			pauseMenu();
		}
		clean();
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
			//startMultiPlayerMenu();
		}else if(exitLoop==3){
			startCourseDesignerMenu();
		}else if(exitLoop==4){
			//startSettingMenu();
		}
		clean();
	}
	private static int checkMainMenuImputs() {
		int x = Mouse.getX()-(DisplayManager.getWidth()/2);
		int y =Mouse.getY()-(DisplayManager.getHeight()/2);
		//SinglePlayer
		if((y > 20) && (y < 150) && (x > -243) && (x < 270)){
			menuButtons.get(0).setSel(true);
			if(Mouse.isButtonDown(0)){
				return 1;//go to singlePlayer menu
			}
		}else{
			menuButtons.get(0).setSel(false);
		}
		
		//MultiPlayer
		if((y > -102) && (y < -10) && (x > -245) && (x < 260)){
			menuButtons.get(1).setSel(true);
			if(Mouse.isButtonDown(0)){
				return 2;//go to MultiPlayer menu
			}
		}else{
			menuButtons.get(1).setSel(false);
		}
		//EditCourse
				if((y > -200) && (y < -110) && (x > -243) && (x < 260)){
					menuButtons.get(2).setSel(true);
					if(Mouse.isButtonDown(0)){
						return 3;//go to courseDesigner menu
					}
				}else{
					menuButtons.get(2).setSel(false);
				}
		
		//options
		if((y > -278) && (y < -210) && (x > -178) && (x < 150)){
			menuButtons.get(3).setSel(true);
			if(Mouse.isButtonDown(0)){
				return 4;//go to Options menu
			}
		}else{
			menuButtons.get(3).setSel(false);
		}
		//quit
		if((y > -400) && (y < -280) && (x > -99) && (x < 87)){
			menuButtons.get(4).setSel(true);
			if(Mouse.isButtonDown(0)){
				clean();
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
	
	private static void startMultyPlayerMenu(){}
	
	private static void courseDesignerLoop(){
		loader = null;
		renderer = null;
		guiRenderer = null;
		loader = new Loader();
		renderer = new MasterRenderer(loader);
		guiRenderer = new GuiRenderer(loader);
				
		List<Light> lights = course.getLights();
		List<WaterTile> waters = course.getWaters();
		List<Entity> entities = course.getEntities();
		RawModel model = OBJLoader.loadObjModel("grassModel", loader);
		
		Ball ball = new Ball(new TexturedModel(model,new ModelTexture(loader.loadTexture("invisible"))),course.getStartingPosition(),0,0,0,1);
		ball.setPosition(new Vector3f(course.getBall().getPosition().x,course.getBall().getPosition().y,course.getBall().getPosition().z));
		course.setBall(ball);
		Camera camera = new Camera(ball);
		entities.add(ball);
		
		MousePicker picker = new MousePicker(camera,renderer.getProjectionMatrix());
		
		List<Terrain> terrains = course.getTerrains();
		List<GuiTexture> inGameGuis = new ArrayList<GuiTexture>();
		
		//**** WATER ***
		
		WaterShader waterShader = new WaterShader();
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		waterRenderer = new WaterRenderer(loader,waterShader,renderer.getProjectionMatrix(),buffers);
						
		
		//***************
		
		int exitLoop=0;
		while(!(Display.isCloseRequested() || exitLoop != 0)){
			exitLoop = checkActualGameImputs();
			ball.move();
			camera.move();
			
			picker.update();

			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			for(WaterTile water:waters){
				//render reflection on water texture
				buffers.bindReflectionFrameBuffer();
				float distance = 2*(camera.getPosition().y-water.getHeight());
				camera.getPosition().y -= distance;
				camera.invertPitch();
				renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0,1,0,-water.getHeight()));
				camera.getPosition().y += distance;
				camera.invertPitch();
				
				//render refraction on water texture
				buffers.bindRefractionFrameBuffer();
				renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0,-1,0,water.getHeight()));
			}
			//render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(entities,terrains,lights,camera, new Vector4f(0,-1,0,150000));
			waterRenderer.render(waters, camera);
			guiRenderer.render(inGameGuis);
			DisplayManager.updateDisplay();
		}		
		if(exitLoop == 1){
			pauseMenu();
		}
		clean();
	}
	
	private static void startCourseDesignerMenu(){
		generateManualWorld();
		courseDesignerLoop();
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
		
		course.addWater(new WaterTile(400,360,-5));
		
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
		Light sun = new Light(new Vector3f(20000,20000,2000),new Vector3f(1f,1f,1f));
		course.addLight(sun);
		
	}
	private static void clean(){
		if(loader!=null)
			loader.cleanUp();
		if(renderer!=null)
			renderer.cleanUp();
		if(guiRenderer!=null)
			guiRenderer.cleanUp();
		if(waterShader!=null)
			waterShader.cleanUp();
		DisplayManager.closeDisplay();
		System.exit(0);
	}
	
}
