package entities;

import models.TexturedModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import collision.BoundingBox;
import geometry.Triangle;

public class Entity implements Serializable{
	
	private static final long serialVersionUID = 5625358789247881883L;

	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private int textureIndex = 0;
	private BoundingBox box;
	private ArrayList<Triangle> trianglesList = new ArrayList<Triangle>();
	private Hole hole;

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
		setTrianglesRealCoordinates();
		if(this.getModel().getRawModel().getHole()!=null)
			setHoleCoordinates();
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
	
	public ArrayList<Triangle> getTriangles(){
		return trianglesList;
	}
	private void setTrianglesRealCoordinates(){
		List<Triangle> rwTrianglesList = model.getRawModel().getTriangles();
		trianglesList = new ArrayList<Triangle>(rwTrianglesList);
		for(Triangle triangle: trianglesList){
			Vector3f p1 = triangle.getP1();
			Vector3f p2 = triangle.getP2();
			Vector3f p3 = triangle.getP3();
			Vector3f newP1 = new Vector3f( (p1.x*getScale()) + getPosition().x, (p1.y*getScale()) + getPosition().y, (p1.z*getScale()) + getPosition().z );
			Vector3f newP2 = new Vector3f( (p2.x*getScale()) + getPosition().x, (p2.y*getScale()) + getPosition().y, (p2.z*getScale()) + getPosition().z );
			Vector3f newP3 = new Vector3f( (p3.x*getScale()) + getPosition().x, (p3.y*getScale()) + getPosition().y, (p3.z*getScale()) + getPosition().z );
			triangle.setP1(newP1);
			triangle.setP2(newP2);
			triangle.setP3(newP3);
		}
		/*if(rotX !=0){
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
	}*/
	}
		private void setHoleCoordinates(){
			Hole hole = model.getRawModel().getHole();
			ArrayList<Vector3f> newPoints = new ArrayList<Vector3f>();
			for(Vector3f point: hole.getPoints()){
				Vector3f newP = new Vector3f( (point.x*getScale()) + getPosition().x, (point.y*getScale()) + getPosition().y, (point.z*getScale()) + getPosition().z );
				newPoints.add(newP);
			}
			/*if(rotX !=0){
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
		}*/	
		this.hole = new Hole(newPoints);
	}
	public Hole getHole(){
		return hole;
	}
	public void upDatePlaneConstant(){
		for(Triangle triangle:trianglesList){
			triangle.upDateEquation(triangle.getP1());
		}
	}

}
