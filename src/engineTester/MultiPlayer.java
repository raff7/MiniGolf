package engineTester;

import java.util.ArrayList;

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
import entities.Entity;
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

public class MultiPlayer implements GameState, Observer {
	
	private Game game;
	private ArrayList<Player> players=new ArrayList<Player>();

	private Course course;
	private ArrayList<Ball> balls=new ArrayList<Ball>();
	
	private Loader loader;
	private MasterRenderer renderer; 
	private GuiRenderer guiRenderer;
	private WaterRenderer waterRenderer;
	private WaterShader waterShader;
	private WaterFrameBuffers buffers;
	
	public MultiPlayer(){
		
		loader = new Loader();
		renderer = new MasterRenderer(loader);
		guiRenderer = new GuiRenderer(loader);
		//choseCourseLoop();
		
		course = SampleCourse.getCourse(loader);
		
		RawModel ballModel = OBJLoader.loadObjModel("golfBall", loader);

		Ball ball1 = new Ball(new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("white"))),course.getStartingPosition(),0,0,0,1);
		Ball ball2 = new Ball(new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("white"))),new Vector3f(course.getStartingPosition().x,course.getStartingPosition().y,course.getStartingPosition().z+5),0,0,0,1);
		
		balls.add(ball1);
		balls.add(ball2);
		
		Camera camera1 = new Camera(ball1);
		Camera camera2 = new Camera(ball2);

        players.add(new HumanPlayer(camera1));
       // players.add(new Bot(camera2, course));
        players.add(new HumanPlayer(camera2));
		game = new Game(players);
		for(Ball ball:balls)
			course.addEntity(ball);
		waterShader = new WaterShader();
		buffers = new WaterFrameBuffers();
		waterRenderer = new WaterRenderer(loader,waterShader,renderer.getProjectionMatrix(),buffers);
								
	}
	@Override
	public void update() {
		checkImputs();
		if(!game.isPause()){
			if(game.getActivePlayer().getBall().getVelocity().x ==0 && Math.abs(game.getActivePlayer().getBall().getVelocity().y) < 2 && game.getActivePlayer().getBall().getVelocity().z ==0){
				//game.addShotArrow();
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
					game.getActivePlayer().increasePower();
					game.getShotPowerGraphics();
				} else if(game.getActivePlayer().getPower() != 0){
					game.getActivePlayer().shoot();
					game.removeShotPowerGraphics();
					game.getActivePlayer().setPower(0);
				}	
			}
		
			game.getActivePlayer().getBall().move(course.getEntities());
			game.getActivePlayer().getCamera().move();
			for(Ball ball:balls){
				if(ball!=game.getActivePlayer().getBall())
					ball.moveNonPlayer(course.getEntities());
			}
		}
	}
	
	@Override
	public void render() {
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		
		for(WaterTile water:course.getWaters()){
			//render reflection on water texture
			buffers.bindReflectionFrameBuffer();
			float distance = 2*(game.getActivePlayer().getCamera().getPosition().y-water.getHeight());
			game.getActivePlayer().getCamera().getPosition().y -= distance;
			game.getActivePlayer().getCamera().invertPitch();
			renderer.renderScene(course.getEntities(), course.getTerrains(), course.getLights(), game.getActivePlayer().getCamera(), new Vector4f(0,1,0,-water.getHeight()));
			game.getActivePlayer().getCamera().getPosition().y += distance;
			game.getActivePlayer().getCamera().invertPitch();
			
			//render refraction on water texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(course.getEntities(), course.getTerrains(),  course.getLights(), game.getActivePlayer().getCamera(), new Vector4f(0,-1,0,water.getHeight()));
		}

		//render to screen
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();
		renderer.renderScene(course.getEntities(),course.getTerrains(),course.getLights(),game.getActivePlayer().getCamera(), new Vector4f(0,-1,0,150000));
		waterRenderer.render(course.getWaters(), game.getActivePlayer().getCamera());
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