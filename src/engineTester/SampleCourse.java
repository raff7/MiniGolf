package engineTester;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import bot.Node;
import bot.NodeNetwork;
import entities.Course;
import entities.Entity;
import entities.Light;
import geometry.Triangle;
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
		
		course.setStartingPosition(new Vector3f(0,01,0));

		
		RawModel rw = OBJLoader.loadObjModel("pistatemporanea", loader);
		ModelTexture rwModel = new ModelTexture(loader.loadTexture("white"));
		rwModel.setUseFakeLight(false);
		rwModel.setHasTransparency(true);

		Entity ent = new Entity (new TexturedModel(rw,rwModel),new Vector3f(0,0,0),0,0,0,15);
	
		course.addEntity(ent);

	//	course.addTerrain(new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")),"heightMap"));
		int waterHeight=-5;	
		course.addWater(new WaterTile(0,0,waterHeight));


//		course.addWater(new WaterTile(0,0,waterHeight));
//		course.addWater(new WaterTile(0,60,waterHeight));
//		course.addWater(new WaterTile(60,60,waterHeight));




		
		Light sun = new Light(new Vector3f(0,100000,100000),new Vector3f(1f,1f,1f));
		course.addLight(sun);

		course.createNetwork();
		
		return course;
	}
}
