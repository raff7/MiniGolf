package entities;

import java.io.Serializable;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

import bot.Node;
import bot.NodeNetwork;
import geometry.Triangle;
import terrains.Terrain;
import water.WaterTile;

public class Course implements Serializable{
	
	private ArrayList<Entity> entities;
	private NodeNetwork net;
	private ArrayList<Light> lights;
	private ArrayList<Terrain> terrains;
	private ArrayList<WaterTile> waters;
	private Vector3f startingPosition;	
	
	public Course(ArrayList<Entity> entities, ArrayList<Light> lights, ArrayList<Terrain> terrains,ArrayList<WaterTile> waters, Ball ball/*, Node[][][] world*/){
		this.entities = entities;
		this.lights = lights;
		this.terrains = terrains;
		this.waters = waters;
		
		//get world nodes
		Hole hole = null;
		for(Entity entity:entities){
			if(entity.getHole()!= null)
				hole = entity.getHole();
		}//...		
	}
	public Course(){
		entities = new ArrayList<Entity>();
		terrains = new ArrayList<Terrain>();
		lights = new ArrayList<Light>();
		waters = new ArrayList<WaterTile>();
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
	
	public ArrayList<Node> getNodes() {
		return net.getNodesList();
	}
	public Vector3f getStartingPosition() {
		return startingPosition;
	}
	public void setStartingPosition(Vector3f position){
		startingPosition=position;
	}
	/*public ArrayList<Node> getNodeNetwork(){
		for(Entity entity: entities){
			if(entity.hasHole()){
				
					if(triangle.getNormal().y ==0 && triangle.getNormal().z==0 ){
		
				}
			}	
		}
	}*/
	
}
