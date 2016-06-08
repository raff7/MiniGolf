package engineTester;

import org.lwjgl.util.vector.Vector3f;

import entities.Course;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import water.WaterTile;

public class SampleCourse {
	public static Course getCourse(Loader loader){
		
		Course course = new Course();
		
		course.setStartingPosition(new Vector3f(20,60,20));
		
		RawModel rw = OBJLoader.loadObjModel("finishPoint", loader);
		ModelTexture rwModel = new ModelTexture(loader.loadTexture("white"));
		rwModel.setUseFakeLight(false);
		rwModel.setHasTransparency(true);

		Entity ent = new Entity (new TexturedModel(rw,rwModel),new Vector3f(0,0,0),0,0,0,15);
		
//Testing cubes
RawModel rw2 = OBJLoader.loadObjModel("middlepiece", loader);
ModelTexture rwModel2 = new ModelTexture(loader.loadTexture("white"));
Entity ent2 = new Entity(new TexturedModel(rw2,rwModel2), new Vector3f(0,0,-89.2f),0,0,0,15);
RawModel rw3 = OBJLoader.loadObjModel("cube", loader);
ModelTexture rwModel3 = new ModelTexture(loader.loadTexture("white"));
Entity ent3 = new Entity(new TexturedModel(rw3,rwModel3), new Vector3f(0,0,-250),0,0,0,100);
RawModel rw4 = OBJLoader.loadObjModel("cube", loader);
ModelTexture rwModel4 = new ModelTexture(loader.loadTexture("white"));
Entity ent4 = new Entity(new TexturedModel(rw4,rwModel4), new Vector3f(0,13,-50),0,0,0,10);

course.addEntity(ent4);
course.addEntity(ent3);
course.addEntity(ent2);


	course.addEntity(ent);
		


		course.addTerrain(new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")),"heightMap"));
		
		course.addWater(new WaterTile(400,360,-5));
		
//		RawModel dragonModel = OBJLoader.loadObjModel("dragon", loader);
//		RawModel treeModel = OBJLoader.loadObjModel("tree", loader);
//		RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
//		RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
//		RawModel lampModel = OBJLoader.loadObjModel("lamp", loader);
	   		
		
		
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


/*		Random random = new Random();
		
			
			
		for(int i=0;i<300;i++){
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
		Light sun = new Light(new Vector3f(0,100000,100000),new Vector3f(1f,1f,1f));
		course.addLight(sun);
		
		
		return course;
	}
}
