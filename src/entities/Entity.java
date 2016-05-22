package entities;

import models.TexturedModel;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import collision.BoundingBox;
import geometry.Triangle;

public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	private int textureIndex = 0;
	
	private BoundingBox box;

	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		/* REALLY IMPORTANT 
		 * Here we set the coordinates of the vertices from the relative coordinates system
		 * to the world coordinates system.*/
		setVertexRealCoordinates();
		//update the value of every plane's constant to correspond to the R3 (in game) value
		upDatePlaneConstant();
		box = new BoundingBox(model,this);
	}
	public Entity(TexturedModel model,int index, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		this.model = model;
		this.textureIndex = index;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	public float getTextureXOffset(){
		int  column = textureIndex%model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	public float getTextureYOffset(){
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float) row/(float)model.getTexture().getNumberOfRows();
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public BoundingBox getBox(){
		return box;
	}
	
	public void setVertexRealCoordinates(){
		List<Vector3f> verticesList = model.getRawModel().getVertices();
		for(Vector3f vertex: verticesList)
			vertex.set( (vertex.x*getScale()) + getPosition().x, (vertex.y*getScale()) + getPosition().y, (vertex.z*getScale()) + getPosition().z);
		
		if(rotX !=0){
			float[][] rotMatrixX = new float[][]{{1f,	0f,						0f},
												 {0f,	(float)Math.cos(rotX), (float)-Math.sin(rotX)},
												 {0f, 	(float)Math.sin(rotX), (float) Math.cos(rotX)}};
			for(Vector3f vertex: verticesList){	
				System.out.println("Before: "+vertex);
				float x = position.x*rotMatrixX[0][0] + position.y*rotMatrixX[1][0] + position.z*rotMatrixX[2][0];
				float y = position.x*rotMatrixX[0][1] + position.y*rotMatrixX[1][1] + position.z*rotMatrixX[2][1];
				float z = position.x*rotMatrixX[0][2] + position.y*rotMatrixX[1][2] + position.z*rotMatrixX[2][2];
				vertex.set(x,y,z);
				System.out.println("After: "+vertex);
			}
		}
	}
	public void upDatePlaneConstant(){
		ArrayList<Triangle> trianglesList = model.getRawModel().getTriangles();
		for(Triangle triangle:trianglesList){
			triangle.upDateEquation(triangle.getP1());
		}
	}
}
