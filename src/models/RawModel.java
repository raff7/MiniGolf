package models;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import collision.Triangle;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private List<Vector3f> vertices;
	private List<Triangle> triangles;
	
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

	public void serVertices(List<Vector3f> vertices) {
		this.vertices = vertices;	
	}
	public List<Vector3f> getVertices() {
		return vertices;	
	}

	public void setVertices(List<Vector3f> vertices) {
		this.vertices = vertices;
	}

	public List<Triangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(List<Triangle> triangles) {
		this.triangles = triangles;
	}
}
