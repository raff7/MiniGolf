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
		
		course.setStartingPosition(new Vector3f(70,1,40));
		
		RawModel rw = OBJLoader.loadObjModel("course1", loader);
		ModelTexture rwModel = new ModelTexture(loader.loadTexture("white"));
		rwModel.setUseFakeLight(false);
		rwModel.setHasTransparency(true);

		Entity ent = new Entity (new TexturedModel(rw,rwModel),new Vector3f(0,0,0),0,0,0,14);
	
		course.addEntity(ent);

	//	course.addTerrain(new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")),"heightMap"));
		
	//	course.addWater(new WaterTile(400,360,-5));
		
		Light sun = new Light(new Vector3f(0,100000,100000),new Vector3f(1f,1f,1f));
		course.addLight(sun);

		course.createNetwork();
		
		return course;
	}
}
