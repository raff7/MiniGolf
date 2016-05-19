package models;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import geometry.Triangle;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private List<Vector3f> vertices;
	private ArrayList<Triangle> triangles=new ArrayList<Triangle>();
	
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public List<Vector3f> getVertices() {
		return vertices;	
	}

	public void setVertices(List<Vector3f> vertices) {
		this.vertices = vertices;
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(ArrayList<Triangle> triangles) {
		this.triangles = triangles;
	}
}
