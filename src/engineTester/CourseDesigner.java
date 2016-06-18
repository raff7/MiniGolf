package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Ball;
import entities.Camera;
import entities.Course;
import entities.Entity;
import gui.Button;
import gui.GuiRenderer;
import gui.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;
import toolbox.MousePicker;
import toolbox.MousePickerTraveler;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class CourseDesigner implements GameState{
	
	private Loader loader;
	private MasterRenderer renderer;
	private GuiRenderer guiRenderer;
	
	private List<GuiTexture> guis;	
	
	private WaterShader waterShader;
	private WaterRenderer waterRenderer;
	private WaterFrameBuffers buffers;
	
	
	private Course course;
	private Ball ball;
	private Camera camera;
	
	private MousePicker picker ;
	private MousePickerTraveler traveler ;
	private Matrix4f projection ;
	ArrayList<Entity> entitiesList = new ArrayList<Entity>() ;
	private Vector3f collisionLocation = new Vector3f() ;
	int trvlrCntr = 0 ;
	
	private boolean place = false ;
	Entity entity = null ;
	
	RawModel ballModel;
	
	

	public CourseDesigner(){
		loader = new Loader();

		renderer = new MasterRenderer(loader);
		guiRenderer = new GuiRenderer(loader);
		
		initializeGuis();
		
		//choseCourseLoop();
		course=SampleCourse.getCourse(loader);//for testing
		
		RawModel model = OBJLoader.loadObjModel("grassModel", loader);
		ball = new Ball(new TexturedModel(model,new ModelTexture(loader.loadTexture("invisible"))),new Vector3f(0,0,0),0,0,0,1);
		camera = new Camera(ball);
		
		waterShader = new WaterShader();
		buffers = new WaterFrameBuffers();
		waterRenderer = new WaterRenderer(loader,waterShader,renderer.getProjectionMatrix(),buffers);
		entitiesList = course.getEntities() ;
		
		projection = renderer.getProjectionMatrix() ;
		picker = new MousePicker(camera , projection) ;
		
		
		GuiTexture obstacle1 = new GuiTexture(loader.loadTexture("editorBall"),new Vector2f (-0.825f,0.75f),new Vector2f(0.17f,0.15f));
		guis.add(obstacle1) ;
		
		GuiTexture obstacle2 = new GuiTexture(loader.loadTexture("endPiece"),new Vector2f (-0.825f,0.35f),new Vector2f(0.2f,0.2f));
		guis.add(obstacle2) ;
		
		GuiTexture obstacle3 = new GuiTexture(loader.loadTexture("editorMidllePiece"),new Vector2f (-0.825f,-0.05f),new Vector2f(0.175f,0.175f));
		guis.add(obstacle3) ;
		
		GuiTexture obstacle4 = new GuiTexture(loader.loadTexture("quit"),new Vector2f (-0.85f,-0.45f),new Vector2f(0.3f,0.3f));
		guis.add(obstacle4) ;
		
		GuiTexture obstacle5 = new GuiTexture(loader.loadTexture("quit"),new Vector2f (-0.85f,-0.85f),new Vector2f(0.3f,0.3f));
		guis.add(obstacle5) ;
		
		
		GuiTexture obstacle6 = new GuiTexture(loader.loadTexture("editorCube"),new Vector2f (0.95f,0.75f),new Vector2f(0.3f,0.3f));
		guis.add(obstacle6) ;
		
		GuiTexture obstacle7 = new GuiTexture(loader.loadTexture("quit"),new Vector2f (0.9f,0.35f),new Vector2f(0.3f,0.3f));
		guis.add(obstacle7) ;
		
		GuiTexture obstacle8 = new GuiTexture(loader.loadTexture("clear"),new Vector2f (0.875f,-0.05f),new Vector2f(0.25f,0.25f));
		guis.add(obstacle8) ;
		
		GuiTexture obstacle9 = new GuiTexture(loader.loadTexture("save"),new Vector2f (0.87f,-0.45f),new Vector2f(0.3f,0.3f));
		guis.add(obstacle9) ;
		
		GuiTexture quitButton = new GuiTexture(loader.loadTexture("quit"),new Vector2f (0.9f,-0.85f),new Vector2f(0.3f,0.3f));
		guis.add(quitButton) ;
	}

	@Override
	public void update() {
		checkImputs();
		picker.update();
		//System.out.print("  x = : " + Mouse.getX()) ;
		//System.out.print("  y = : " + Mouse.getY()) ;
		//System.out.println();
		traveler = new MousePickerTraveler(camera , picker, course) ;
		//System.out.println("ray CHECK : " + picker.getCurrentRay()) ;
		//System.out.println("cam CHECK : " + camera.getPosition()) ;
		//System.out.println("ray CHECK : " + traveler.getPosition()) ;
		while(traveler.hasHit == false){
			//System.out.println("Pos1 CHECK : " + traveler.getPosition()) ;
			//System.out.println("ray CHECK : " + picker.getCurrentRay()) ;
			//System.out.println("trvl dir CHECK : " + traveler.getDirection()) ;
			traveler.progress();
			traveler.setPosition(traveler.getProgressVector());
			//System.out.println("Pos2 CHECK : " + traveler.getPosition()) ;
			//BELOW HERE SOMEWHERE IS THE PROBLEM
			if(traveler.collision() == true){
				collisionLocation = traveler.collisionLocation() ;
				//System.out.println("Collision !!!!!!!!!") ;
				//System.out.println("Location : " + collisionLocation) ;
			}
			else{
				trvlrCntr++ ;
				if(trvlrCntr > 1000){
					trvlrCntr = 0 ;
					traveler.hasHit = true ;
				}
			}
		}
		//System.out.println("collisionLocation : " + collisionLocation) ;
		checkButtons() ;
		checkRotation() ;
		//System.out.println(place()) ;
		if(place()){
			placeObstacle(entity, collisionLocation) ;
			entity = null ;
		}
		ball.moving() ;
		camera.move() ;
	}
	
	private void checkButtons(){
		
		//leftSide
		if(Mouse.getX() > 5 && Mouse.getX() < 65 && Mouse.getY() > 330 && Mouse.getY() < 395){
			//button1 Ball!
			if(Mouse.isButtonDown(0)){
				System.out.println("Ball") ;
				RawModel ballModel = OBJLoader.loadObjModel("golfBall", loader);
				entity = new Ball(new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("white"))),new Vector3f(),0,0,0,1);
			}
		}
		if(Mouse.getX() > 5 && Mouse.getX() < 65 && Mouse.getY() > 250 && Mouse.getY() < 315){
			//button2 HolePiece
			if(Mouse.isButtonDown(0)){
				System.out.println("EndPiece") ;
				RawModel rw = OBJLoader.loadObjModel("finishPoint", loader);
				ModelTexture rwModel = new ModelTexture(loader.loadTexture("white"));
				entity = new Entity (new TexturedModel(rw,rwModel),new Vector3f(0,0,0),0,0,0,15);
			}
		}
		if(Mouse.getX() > 5 && Mouse.getX() < 65 && Mouse.getY() > 170  && Mouse.getY() < 235){
			//button3 MiddlePiece
			if(Mouse.isButtonDown(0)){
				System.out.println("MiddlePiece") ;
				RawModel rw2 = OBJLoader.loadObjModel("middlepiece", loader);
				ModelTexture rwModel2 = new ModelTexture(loader.loadTexture("white"));
				entity = new Entity(new TexturedModel(rw2,rwModel2), new Vector3f(0,0,-89.2f),0,0,0,15);
			}
		}
		if(Mouse.getX() > 5 && Mouse.getX() < 65 && Mouse.getY() > 90 && Mouse.getY() < 155){
			//button4 StartPiece
			if(Mouse.isButtonDown(0)){
				System.out.println("StartPiece");
			}
		}
		if(Mouse.getX() > 5 && Mouse.getX() < 65 && Mouse.getY() > 10 && Mouse.getY() < 75){
			//button5 Corner Piece
			if(Mouse.isButtonDown(0)){
				System.out.println("corner piece") ;
			}
		}
		
		//rightSide
		if(Mouse.getX() > 575 && Mouse.getX() < 635 && Mouse.getY() > 330 && Mouse.getY() < 395){
			//button6 cube obstacle
			if(Mouse.isButtonDown(0)){
				System.out.println("Cube") ;
				RawModel rw4 = OBJLoader.loadObjModel("cube", loader);
				ModelTexture rwModel4 = new ModelTexture(loader.loadTexture("white"));
				entity = new Entity(new TexturedModel(rw4,rwModel4), new Vector3f(0,13,-50),0,0,0,10);
			}
		}
		if(Mouse.getX() > 575 && Mouse.getX() < 635 && Mouse.getY() > 250 && Mouse.getY() < 315){
			//button7 A-Frame Piece/obstacle
			if(Mouse.isButtonDown(0)){
				System.out.println("A frame piece") ;
			}
		}
		if(Mouse.getX() > 575 && Mouse.getX() < 635 && Mouse.getY() > 170  && Mouse.getY() < 235){
			//button8 clear obstacle
			if(Mouse.isButtonDown(0)){
				System.out.println("clear selected Obstacle") ;
				entity = null ;
			}
		}
		if(Mouse.getX() > 575 && Mouse.getX() < 635 && Mouse.getY() > 90 && Mouse.getY() < 155){
			//button9 save course
			if(Mouse.isButtonDown(0)){
				System.out.println("saving course...") ;
				//TODO!!
				System.out.println("course saved ! ") ;
			}
		}
		if(Mouse.getX() > 575 && Mouse.getX() < 635 && Mouse.getY() > 10 && Mouse.getY() < 75){
			//button10 back to MainMenu
			if(Mouse.isButtonDown(0)){
				System.out.println("QUIT") ;
				changeGameState(new MainMenu()) ;
			}
		}
		
	}
	
	//error somewhere in here
	public void placeObstacle(Entity obs, Vector3f loc){
		
		entitiesList = course.getEntities() ;
		entitiesList.add(obs) ;
		obs.setPosition(loc) ;
				
	}
	
	public void checkRotation(){
		if(entity != null && Keyboard.isKeyDown(Keyboard.KEY_R)){
			entity.increaseRotation(0f, 90f, 0f);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("rotated") ;
		}
		
	}
	
	public boolean place(){
		if(Keyboard.isKeyDown(Keyboard.KEY_P) && entity != null){
			place = true ;
			return place ;
		}
		else{
			place = false ;
			return place ;
		}
	}


	@Override
	public void render() {
		for(WaterTile water:course.getWaters()){
			//render reflection on water texture
			buffers.bindReflectionFrameBuffer();
			float distance = 2*(camera.getPosition().y-water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(course.getEntities(), course.getTerrains(), course.getLights(), camera, new Vector4f(0,1,0,-water.getHeight()));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			//render refraction on water texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(course.getEntities(), course.getTerrains(), course.getLights(), camera, new Vector4f(0,-1,0,water.getHeight()));
		}
		//render to screen
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();
		renderer.renderScene(course.getEntities(),course.getTerrains(),course.getLights(),camera, new Vector4f(0,-1,0,150000));
		waterRenderer.render(course.getWaters(), camera);
		guiRenderer.render(guis);
		DisplayManager.updateDisplay();
		
	}


	
	public void changeGameState(GameState newState) {
		
		MainGameLoop.changeGameState(newState);
	}
	
	private void initializeGuis() {
		guis = new ArrayList<GuiTexture>();
		
	}
	
	private void checkImputs(){
		//TODO
	}
	
	
}
