package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		RawModel dragonModel = OBJLoader.loadObjModel("dragon", loader);
		RawModel treeModel = OBJLoader.loadObjModel("tree", loader);
		RawModel stallModel = OBJLoader.loadObjModel("stall", loader);
		RawModel bunnyModel = OBJLoader.loadObjModel("bunny", loader);

		
		TexturedModel staticDragonModel = new TexturedModel(dragonModel,new ModelTexture(loader.loadTexture("white")));
		TexturedModel staticTreeModel = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel staticStallModel = new TexturedModel(stallModel,new ModelTexture(loader.loadTexture("stallTexture")));
		TexturedModel staticBunnyModel = new TexturedModel(bunnyModel,new ModelTexture(loader.loadTexture("white")));

		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<10;i++){
			entities.add(new Entity(staticDragonModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
		}
		for(int i=0;i<50;i++){
			Entity currentTree = new Entity(staticTreeModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3);
			currentTree.setScale(20);
			entities.add(currentTree);
		}
		for(int i=0;i<20;i++){
			entities.add(new Entity(staticBunnyModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
		}
		for(int i=0;i<10;i++){
			entities.add(new Entity(staticStallModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,100,0,3));
		}
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();	
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
			camera.move();
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
