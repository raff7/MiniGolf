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
import entities.Ball;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")),"heightMap");

		
		RawModel dragonModel = OBJLoader.loadObjModel("dragon", loader);
		RawModel treeModel = OBJLoader.loadObjModel("tree", loader);
		RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
		RawModel fernModel = OBJLoader.loadObjModel("fern", loader);


		

		TexturedModel staticDragonModel = new TexturedModel(dragonModel,new ModelTexture(loader.loadTexture("gold")));
		staticDragonModel.getTexture().setReflectivity(0.7f);
		staticDragonModel.getTexture().setShineDamper(5);
		TexturedModel staticTreeModel = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel staticGrassModel = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel staticFlowerModel = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("flower")));
		ModelTexture fernTextures = new ModelTexture(loader.loadTexture("fern1"));
		fernTextures.setNumberOfRows(1);
		TexturedModel staticFernModel = new TexturedModel(fernModel,fernTextures);
		Ball player = new Ball(staticTreeModel, new Vector3f(400,terrain.getHeightOfTerrain(400, 400),400),0,0,0,20);
		
		List<Entity> entities = new ArrayList<Entity>();
		
		fernTextures.setHasTransparency(true);
		staticGrassModel.getTexture().setHasTransparency(true);
		staticFlowerModel.getTexture().setHasTransparency(true);
		staticGrassModel.getTexture().setUseFakeLight(true);
		staticFlowerModel.getTexture().setUseFakeLight(true);
		staticFernModel.getTexture().setHasTransparency(true);
		
		Entity testGrass = new Entity(staticGrassModel,new Vector3f(400,terrain.getHeightOfTerrain(400, 400),400),0,0,0,3);
		entities.add(testGrass);

		Random random = new Random();
		for(int i=0;i<300;i++){
			if(i%20 == 0){
				float x =random.nextFloat()*800;
				float z = random.nextFloat() * 800;
				float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(staticDragonModel, new Vector3f(x,y,z),0,0,0,3));
			}
			if(	i%5 == 0){
				float x =random.nextFloat()*800;
				float z = random.nextFloat() * 800;
				float y = terrain.getHeightOfTerrain(x, z);
				Entity currentTree = new Entity(staticTreeModel, new Vector3f(x,y,z),0,0,0,3);
				currentTree.setScale(20);
				entities.add(currentTree);
			}
			if(i%4==0){
				float x =random.nextFloat()*800;
				float z = random.nextFloat()*800;
				float y = terrain.getHeightOfTerrain(x, z);
				Entity fern = new Entity(staticFernModel,random.nextInt(4), new Vector3f(x,y,z),0,0,0,3);
				entities.add(fern);
			}
			if(i%2 ==0){
				float x =random.nextFloat()* 800;
				float z = random.nextFloat() * 800;
				float y = terrain.getHeightOfTerrain(x, z);
				Entity grass = null;
				if(i%4==0){
					grass = new Entity(staticFlowerModel, new Vector3f(x,y,z),0,0,0,3);
				}else
					grass = new Entity(staticGrassModel, new Vector3f(x,y,z),0,0,0,3);
				entities.add(grass);
			}
		}
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		
		
		Camera camera = new Camera(player);	
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move(terrain);
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
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
