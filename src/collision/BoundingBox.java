package collision;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import models.TexturedModel;

public class BoundingBox {

	public List<Vector3f> vertices;
	private Vector3f minX;
	private Vector3f maxX;
	private Vector3f minY;
	private Vector3f maxY;
	private Vector3f minZ;
	private Vector3f maxZ;

	public BoundingBox(TexturedModel model, Vector3f position){
		this.vertices = model.getRawModel().getVertices();
		setVertexRealCoordinates(position);
		getExtremePoints();
	}
	public void getExtremePoints(){

		minX=new Vector3f();
		maxX=new Vector3f();
		maxY=new Vector3f();
		minY=new Vector3f();
		maxZ=new Vector3f();
		minZ=new Vector3f();

		for(int i=0; i<vertices.size(); i++){
			if(i==0){
				minX = vertices.get(i);
				maxX = vertices.get(i);
				minY = vertices.get(i);
				maxY= vertices.get(i);
				minZ = vertices.get(i);
				maxZ = vertices.get(i);	
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
	public void setVertexRealCoordinates(Vector3f position){
		for(int i=0; i<vertices.size(); i++){
			Vector3f vertex = vertices.get(i);
			vertex.set(vertex.x+position.x, vertex.y+position.y, vertex.z+position.z);
			vertices.set(i,vertex);
		}
	}
}

