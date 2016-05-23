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

import collision.BoundingBox;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.HumanPowerController;
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
import geometry.Triangle;
import gui.Button;
import gui.GuiRenderer;
import gui.GuiTexture;

public class MainGameLoop{
	
	private static GameState actualState;
	

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		actualState = new MainMenu();
		
		
		while(!Display.isCloseRequested() && actualState !=null){
			
			actualState.render();
			actualState.update();
			
		}
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
		
		
///////////// TESTING \\\\\\\\\
RawModel rw = OBJLoader.loadObjModel("hole", loader);
ModelTexture rwModel = new ModelTexture(loader.loadTexture("white"));
rwModel.setHasTransparency(true);
Entity ent = new Entity (new TexturedModel(rw,rwModel),new Vector3f(100,10,100),0,0,0,28);

ArrayList<Entity> collideEnt = new ArrayList<Entity>();
collideEnt.add(ent);
entities.add(ent);

		
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
			ball.move(course.getCurrentTerrain(),collideEnt);
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
		
		
		
		
		
		
		
		//menu start
		int exitLoop = 0;
		while(!(Display.isCloseRequested() || exitLoop != 0)){
			exitLoop = checkMainMenuImputs();
			
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
	
	
	private static int checkActualGameImputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_P)){
			return 1;
		}
		return 0;
		
	}
	private static void generateManualWorld() {
		course = new Course();
		course.addTerrain(new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")),"heightMap"));
		
		course.addWater(new WaterTile(400,360,-5));
		
//		RawModel dragonModel = OBJLoader.loadObjModel("dragon", loader);
//		RawModel treeModel = OBJLoader.loadObjModel("tree", loader);
//		RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
//		RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
//		RawModel lampModel = OBJLoader.loadObjModel("lamp", loader);
	   
		RawModel ballModel = OBJLoader.loadObjModel("golfBall", loader);
		
		
		course.setBall(new Ball(new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("white"))),new Vector3f(0,0,20),0,0,0,1));
		float yball = (float) course.getHeightOfTerrain(400, 360);
		course.getBall().setPosition(new Vector3f(course.getBall().getPosition().x,yball,course.getBall().getPosition().z));
		
//		TexturedModel staticDragonModel = new TexturedModel(dragonModel,new ModelTexture(loader.loadTexture("gold")));
//		staticDragonModel.getTexture().setReflectivity(0.7f);
//		staticDragonModel.getTexture().setShineDamper(5);
//		TexturedModel staticTreeModel = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
//		TexturedModel staticGrassModel = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
//		staticGrassModel.getTexture().setNumberOfRows(3);
//		staticGrassModel.getTexture().setHasTransparency(true);
//		staticGrassModel.getTexture().setUseFakeLight(true);
//		ModelTexture fernTextures = new ModelTexture(loader.loadTexture("fern"));
//		fernTextures.setNumberOfRows(2);
//		fernTextures.setHasTransparency(true);
//		TexturedModel staticFernModel = new TexturedModel(fernModel,fernTextures);
//		TexturedModel lampTexturedModel = new TexturedModel(lampModel,new ModelTexture(loader.loadTexture("lamp")));
		
		
				
//		course.addEntity(new Entity(staticDragonModel, new Vector3f(360,course.getHeightOfTerrain(360, 400),400),0,-90,0,5));
//		course.addEntity(new Entity(staticDragonModel, new Vector3f(440,course.getHeightOfTerrain(440, 400),400),0,-90,0,5));


		Random random = new Random();
		
			
			
/*		for(int i=0;i<300;i++){
			if(i%100 == 0){
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
		course.addLight(lamp2.getLight());*/
		Light sun = new Light(new Vector3f(100,20000,-10000),new Vector3f(1f,1f,1f));
		course.addLight(sun);
		
	}
	public static void Notify(GameState newState){
		actualState = newState;
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
