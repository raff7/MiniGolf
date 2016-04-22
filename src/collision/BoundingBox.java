package collision;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import models.TexturedModel;

public class BoundingBox {
	
	private List<Vector3f> vertices;
	private Vector3f minX;
	private Vector3f maxX;
	private Vector3f minY;
	private Vector3f maxY;
	private Vector3f minZ;
	private Vector3f maxZ;
	
	public BoundingBox(TexturedModel model){
		this.vertices = model.getRawModel().getVertices();
	}
	public void getExtremePoints(){
		
		Vector3f minX=null;
		Vector3f maxX=null;
		Vector3f maxY=null;
		Vector3f minY=null;
		Vector3f maxZ=null;
		Vector3f minZ=null;
		
		for(int i=0; i<vertices.size(); i++){
			if(i==0){
				minX = vertices.get(i);
				maxX = vertices.get(i);
				maxY= vertices.get(i);
				minY = vertices.get(i);
				maxZ = vertices.get(i);
				minZ = vertices.get(i);
			}else{
				if(vertices.get(i).x < minX.x)
					minX = vertices.get(i);
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
	}
	
	public float getMinX(){
		return minX.x;
	}
	public float getMaxX(){
		return maxX.x;
	}
	public float getMinY(){
		return minY.y;
	}
	public float getMaxY(){
		return maxY.y;
	}
	public float getMinZ(){
		return minZ.z;
	}
	public float getMaxZ(){
		return maxZ.z;
	}
	
}
