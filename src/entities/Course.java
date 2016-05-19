package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import bot.Node;
import terrains.Terrain;
import water.WaterTile;

public class Course {
	
	private List<Entity> entities;
	private List<Light> lights;
	private List<Terrain> terrains;
	private List<WaterTile> waters;
	private Ball ball;
	private Vector3f startingPosition;	
	//might need to add some methods !!
	//private Node[][][] worldNodes ;
	
	public Course(List<Entity> entities, List<Light> lights, List<Terrain> terrains,List<WaterTile> waters, Ball ball/*, Node[][][] world*/){
		this.entities = entities;
		this.lights = lights;
		this.terrains = terrains;
		this.waters = waters;
		this.ball = ball;
		//this.worldNodes = world ;
		
		
	}
	public Course(int x, int y, int z){
		entities = new ArrayList<Entity>();
		terrains = new ArrayList<Terrain>();
		lights = new ArrayList<Light>();
		waters = new ArrayList<WaterTile>();
		//worldNodes = new Node[x][y][z] ;

	}
	public List<Entity> getEntities() {
		return entities;
	}
	public void addEntity(Entity  entity) {
		this.entities.add(entity);
	}
	public List<Light> getLights() {
		return lights;
	}
	public void addLight(Light light) {
		this.lights.add(light);
	}
	public List<Terrain> getTerrains() {
		return terrains;
	}
	public void addTerrain(Terrain terrain) {
		this.terrains.add(terrain);
	}
	public List<WaterTile> getWaters(){
		return this.waters;
	}
	public void addWater(WaterTile water) {
		this.waters.add(water);
	}
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	public Terrain getCurrentTerrain() {
		Terrain terrain= null;
		float x = ball.getPosition().x;
		float z = ball.getPosition().z;
		float terrainMaxX,terrainMaxZ,terrainMinX,terrainMinZ = 0;
		for(int i=0;i<terrains.size();i++)
		{
			terrain = terrains.get(i);
			terrainMaxX=terrain.getX()+terrain.getSize();
			terrainMaxZ=terrain.getZ()+terrain.getSize();
			terrainMinX=terrain.getX();
			terrainMinZ=terrain.getZ();
//			System.out.println("x: min: "+terrainMinX +" Max: "+ terrainMaxX);
//			System.out.println("z: "+terrainMinZ + terrainMaxZ);
			if(x>terrainMinX && x <terrainMaxX && z > terrainMinZ && z < terrainMaxZ){
				return terrains.get(i);
			}
		}
		return null;
	}
	public float getHeightOfTerrain(float x, float z) {
		Terrain terrain = getCurrentTerrain();
		if(terrain !=null)
			return getCurrentTerrain().getHeightOfTerrain(x, z);
		else
			return 0;
	}
	public Vector3f getStartingPosition() {

		return startingPosition;
	}
	
}
