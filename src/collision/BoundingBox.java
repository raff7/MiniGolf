package collision;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;

public class BoundingBox {

	private static int count;
	public List<Vector3f> vertices;
	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private float minZ;
	private float maxZ;

	public BoundingBox(TexturedModel model, Entity entity){
		this.vertices = model.getRawModel().getVertices();
		setVertexRealCoordinates(entity);
		getExtremePoints();
	}
	public void getExtremePoints(){
		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		minZ = Integer.MAX_VALUE;
		
		maxX = Integer.MIN_VALUE;
		maxY = Integer.MIN_VALUE;
		maxZ = Integer.MIN_VALUE;


		for(int i=0; i<vertices.size(); i++){

				if(vertices.get(i).x < minX){
					minX = vertices.get(i).x;
				}

				
				if(vertices.get(i).x > maxX)
					maxX = vertices.get(i).x;
				
				if(vertices.get(i).y > maxY)
					maxY = vertices.get(i).y;
				
				if(vertices.get(i).y < minY )
					minY = vertices.get(i).y;
				
				if(vertices.get(i).z > maxZ)
					maxZ = vertices.get(i).z;
				
				if(vertices.get(i).z < minZ)
					minZ = vertices.get(i).z;
			
		}

	}

	public float getMinX(){
		return minX;
	}
	public float getMaxX(){
		return maxX;
	}
	public float getMinY(){
		return minY;
	}
	public float getMaxY(){
		return maxY;
	}
	public float getMinZ(){
		return minZ;
	}
	public float getMaxZ(){
		return maxZ;
	}
	public void setVertexRealCoordinates(Entity entity){
		for(int i=0; i<vertices.size(); i++){
			Vector3f vertex = vertices.get(i);
			vertex.set(vertex.x+entity.getPosition().x*entity.getScale(), vertex.y+entity.getPosition().y*entity.getScale(), vertex.z+entity.getPosition().z*entity.getScale());
			vertices.set(i,vertex);
		}
	}
}

