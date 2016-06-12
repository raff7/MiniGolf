package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Ball;
import entities.Camera;
import entities.Course;
import gui.GuiRenderer;
import gui.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;
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
		
	}



	@Override
	public void update() {
		checkImputs();
		ball.move();
		camera.move();
		
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


	@Override
	public void changeGameState(GameState newState) {
		//TODO
	}
	
	private void initializeGuis() {
		guis = new ArrayList<GuiTexture>();
		
	}
	
	private void checkImputs(){
		//TODO
	}
	
	
}
