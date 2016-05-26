package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import GameManager.Bot;
import GameManager.Game;
import GameManager.HumanPlayer;
import GameManager.Player;
import GameManager.Observer;
import entities.Ball;
import entities.Camera;
import entities.Course;
import fileHandler.CourseLoader;
import gui.GuiRenderer;
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

public class SinglePlayer implements GameState, Observer {
	
	private Game game;
	private Player player;

	private Course course;
	private Ball ball;
	private Camera camera;
	
	private Loader loader;
	private MasterRenderer renderer; 
	private GuiRenderer guiRenderer;
	private WaterRenderer waterRenderer;
	private WaterShader waterShader;
	private WaterFrameBuffers buffers;
	
	public SinglePlayer(){
		
		loader = new Loader();
		renderer = new MasterRenderer(loader);
		guiRenderer = new GuiRenderer(loader);
		//choseCourseLoop();
		
		course = SampleCourse.getCourse(loader);
		
		RawModel ballModel = OBJLoader.loadObjModel("golfBall", loader);

		ball = new Ball(new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("white"))),course.getStartingPosition(),0,0,0,1);
		camera = new Camera(ball);
        player = new HumanPlayer(ball);
//player = new Bot(ball, course);
		game = new Game(player);

		course.addEntity(ball);
		waterShader = new WaterShader();
		buffers = new WaterFrameBuffers();
		waterRenderer = new WaterRenderer(loader,waterShader,renderer.getProjectionMatrix(),buffers);
								
	}
	@Override
	public void update() {
		checkImputs();
		if(!game.isPause()){
			if(player.getBall().getVelocity().x ==0 && Math.abs(player.getBall().getVelocity().y) < 2 && player.getBall().getVelocity().z ==0){
				game.addShotArrow();
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				player.increasePower();
				game.getShotPowerGraphics();
				}else if(player.getPower() != 0){
				player.shoot();
				game.removeShotPowerGraphics();
				player.setPower(0);
				}	
			}
			ball.move(course.getEntities());
			camera.move();
		}
	}
	
	@Override
	public void render() {
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		
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
			renderer.renderScene(course.getEntities(), course.getTerrains(),  course.getLights(), camera, new Vector4f(0,-1,0,water.getHeight()));
		}

		//render to screen
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();
		renderer.renderScene(course.getEntities(),course.getTerrains(),course.getLights(),camera, new Vector4f(0,-1,0,150000));
		waterRenderer.render(course.getWaters(), camera);
		guiRenderer.render(game.getGUIs());
		DisplayManager.updateDisplay();
	}		

	@Override
	public void changeGameState(GameState newState) {
		if(!(newState instanceof Option )){
			loader.cleanUp();
			renderer.cleanUp();
			guiRenderer.cleanUp();
			waterShader.cleanUp();
		}
		MainGameLoop.changeGameState(newState);
	}
	
	private void checkImputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			if(!game.isPause())
				game.pause();
			else
				game.unPause();
		}		
	}
	@Override
	public void updateObserver() {
		//final statistics printOut
		changeGameState(null);
		
	}
}