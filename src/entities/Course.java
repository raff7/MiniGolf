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
	
	public ArrayList<Entity> entities;
	private NodeNetwork net;
	private ArrayList<Light> lights;
	private ArrayList<Terrain> terrains;
	private ArrayList<WaterTile> waters;
	private Vector3f startingPosition;	
	
	private static final long serialVersionUID = 4085508378960573248L;
	 Course(ArrayList<Entity> entities, ArrayList<Light> lights, ArrayList<Terrain> terrains,ArrayList<WaterTile> waters, Ball ball/*, Node[][][] world*/){
		this.entities = entities;
		this.lights = lights;
		this.terrains = terrains;
		this.waters = waters;
		
		ArrayList<Node> nodesList = new ArrayList<Node>();
		
		//get world nodes
		Hole hole = null;
		for(Entity entity:entities){
			if(entity.getHole()!= null){
				for(Triangle triangle: entity.getTriangles())
					nodesList.add(new Node(triangle, "a"));
				
				hole = entity.getHole();
			}
		}
		nodesList = hole.getNodesNetwork(nodesList);
		net = new NodeNetwork(nodesList);		
	}
	public Course(){
		entities = new ArrayList<Entity>();
		terrains = new ArrayList<Terrain>();
		lights = new ArrayList<Light>();
		waters = new ArrayList<WaterTile>();
		for(Entity entity:entities){
			if(entity instanceof Ball){
				entities.remove(entity) ;
				System.out.println("removing ball") ;
			}
		}
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
	public NodeNetwork getNetwork()
	{
		return net;
	}
	public Vector3f getStartingPosition() {
		Vector3f position = new Vector3f(startingPosition.x, startingPosition.y, startingPosition.z) ;
		return position ;
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
	
	public void createNetwork(){
		ArrayList<Node> nodesList = new ArrayList<Node>();
		Hole hole = null;
		for(Entity entity:entities){
			if(entity.getHole()!= null){
				for(Triangle triangle: entity.getTriangles())
					nodesList.add(new Node(triangle, "a"));
				
				hole = entity.getHole();
			}
		}
		nodesList = hole.getNodesNetwork(nodesList);
		net = new NodeNetwork(nodesList);	
	}
	
}
