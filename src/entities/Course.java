package entities;

import java.util.ArrayList;
import java.util.List;

import terrains.Terrain;

public class Course {
	
	private List<Entity> entities;
	private List<Light> lights;
	private List<Terrain> terrains;
	private Ball ball;	
	
	public Course(List<Entity> entities, List<Light> lights, List<Terrain> terrains, Ball ball) {
		this.entities = entities;
		this.lights = lights;
		this.terrains = terrains;
		this.ball = ball;
	}
	public Course(){
		entities = new ArrayList<Entity>();
		terrains = new ArrayList<Terrain>();
		lights = new ArrayList<Light>();

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
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	public Terrain getCurrentTerrain() {
		float x = ball.getPosition().x;
		float z = ball.getPosition().z;
		float terrainMaxX,terrainMaxZ,terrainMinX,terrainMinZ = 0;
		for(int i=0;i<terrains.size();i++)
		{
			terrainMaxX=terrains.get(i).getX();
			terrainMaxZ=terrains.get(i).getZ();
			terrainMinX=terrains.get(i).getX()+terrains.get(i).getSize();
			terrainMinX=terrains.get(i).getZ()+terrains.get(i).getSize();

			if(ball.getPosition().x>terrainMinX && ball.getPosition().x <terrainMaxX && ball.getPosition().z > terrainMinZ && ball.getPosition().z < terrainMaxZ){
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
	
}
