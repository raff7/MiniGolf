package entities;

import java.io.Serializable;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

import bot.Node;
import bot.NodeNetwork;
import collision.CollisionHandler;
import geometry.Triangle;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
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
		Vector3f startingPosition = new Vector3f(this.startingPosition.x,this.startingPosition.y,this.startingPosition.z);
		return startingPosition;
	}
	public void setStartingPosition(Vector3f position){
		startingPosition=position;
	}
	
	public void createNetwork(){
		
		ArrayList<Node> nodesList = new ArrayList<Node>();
		Hole hole = null;
		//Get all the triangle that are on the ground
		for(Entity entity:entities){
			if(entity.getHole() != null){
				for(Triangle triangle: entity.getTriangles())
					nodesList.add(new Node(triangle, "a"));
				
				hole = entity.getHole();
			}
		}
		//Remove the ones clogged by obstacles
		Loader loader = new Loader();
		RawModel ballModel = OBJLoader.loadObjModel("golfBall", loader);
		Ball fakeBall = new Ball(new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("white"))),new Vector3f(0,0,0),0,0,0,1);
		
		for(int i=0; i<nodesList.size(); i++){
			fakeBall.setPosition(nodesList.get(i).getCentroid());//watch out, returns object reference
			fakeBall.setVelocity(new Vector3f(0.000001f,0,0.000001f));
			for(int j=0; j<entities.size(); j++){
				if(entities.get(j).getHole() == null){
					for(Triangle triangle : entities.get(j).getTriangles()){
						if(CollisionHandler.isNodeOccupied(fakeBall, triangle)){
							System.out.println("removed: "+ nodesList.get(i).getPosition());
							nodesList.remove(i);
							fakeBall.setPosition(nodesList.get(i).getCentroid());
							fakeBall.setVelocity(new Vector3f(0.000001f,0,0.000001f));
							j=0;
							break;
						}
					}
				}
			}
		}
		nodesList = hole.getNodesNetwork(nodesList);
		net = new NodeNetwork(nodesList);	
	}
	
	public void removeEntity(Entity entity) {
		for(int i = 0; i<entities.size(); i++){
			if(entities.get(i) == entity){
				entities.remove(i);
			}
		}
	}
	
}
