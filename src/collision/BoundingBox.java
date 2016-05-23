package collision;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import entities.Entity;
import models.TexturedModel;

public class BoundingBox {

	public List<Vector3f> vertices;
	private Vector3f minX;
	private Vector3f maxX;
	private Vector3f minY;
	private Vector3f maxY;
	private Vector3f minZ;
	private Vector3f maxZ;

	public BoundingBox(TexturedModel model, Entity entity){
		this.vertices = model.getRawModel().getVertices();
		getExtremePoints();
	}
	public void getExtremePoints(){
		minX.x = Integer.MAX_VALUE;
		minY.y = Integer.MAX_VALUE;
		minZ.z = Integer.MAX_VALUE;
		
		maxX.x = Integer.MIN_VALUE;
		maxY.y = Integer.MIN_VALUE;
		maxZ.z = Integer.MIN_VALUE;


		for(int i=0; i<vertices.size(); i++){

				if(vertices.get(i).x < minX.x){
					minX = vertices.get(i);
				}

				
				if(vertices.get(i).x > maxX.x)
					maxX = vertices.get(i);
				
				if(vertices.get(i).y > maxY.y)
					maxY = vertices.get(i);
				
				if(vertices.get(i).y < minY.y )
					minY = vertices.get(i);
				
				if(vertices.get(i).z > maxZ.z)
					maxZ = vertices.get(i);
				
				if(vertices.get(i).z < minZ.z)
					minZ = vertices.get(i);
			
		}

	}

	public Vector3f getMinX(){
		return minX;
	}
	public Vector3f getMaxX(){
		return maxX;
	}
	public Vector3f getMinY(){
		return minY;
	}
	public Vector3f getMaxY(){
		return maxY;
	}
	public Vector3f getMinZ(){
		return minZ;
	}
	public Vector3f getMaxZ(){
		return maxZ;
	}

}

