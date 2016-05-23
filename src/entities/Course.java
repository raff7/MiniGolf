package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import bot.Node;
import terrains.Terrain;
import water.WaterTile;

public class Course {
	
	private ArrayList<Entity> entities;
	private ArrayList<Light> lights;
	private ArrayList<Terrain> terrains;
	private ArrayList<WaterTile> waters;
	private Vector3f startingPosition;	
	//might need to add some methods !!
	//private Node[][][] worldNodes ;
	
	public Course(ArrayList<Entity> entities, ArrayList<Light> lights, ArrayList<Terrain> terrains,ArrayList<WaterTile> waters, Ball ball/*, Node[][][] world*/){
		this.entities = entities;
		this.lights = lights;
		this.terrains = terrains;
		this.waters = waters;
		//this.worldNodes = world ;
		
		
	}
	public Course(){
		entities = new ArrayList<Entity>();
		terrains = new ArrayList<Terrain>();
		lights = new ArrayList<Light>();
		waters = new ArrayList<WaterTile>();
		//worldNodes = new Node[x][y][z] ;

	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public void addEntity(Entity  entity) {
		this.entities.add(entity);
	}
	public ArrayList<Light> getLights() {
		return lights;
	}
	public void addLight(Light light) {
		this.lights.add(light);
	}
	public ArrayList<Terrain> getTerrains() {
		return terrains;
	}
	public void addTerrain(Terrain terrain) {
		this.terrains.add(terrain);
	}
	public ArrayList<WaterTile> getWaters(){
		return this.waters;
	}
	public void addWater(WaterTile water) {
		this.waters.add(water);
	}
	public Vector3f getStartingPosition() {

		return startingPosition;
	}
	public void setStartingPosition(Vector3f position){
		startingPosition=position;
	}
	
}
