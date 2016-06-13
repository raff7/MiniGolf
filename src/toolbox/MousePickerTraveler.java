package toolbox;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import collision.CollisionHandler;
import entities.Camera;
import entities.Course;
import entities.Entity;
import geometry.Triangle;
import terrains.Terrain;

public class MousePickerTraveler{
	
	Vector3f position ;
	Vector3f direction ;
	MousePicker picker ;
	ArrayList<Entity> entitiesList = new ArrayList() ;
	ArrayList<Terrain> terrains = new ArrayList() ;
	ArrayList<Triangle> trianglesList = new ArrayList() ;
	Vector3f collisionLocation = null ;
	public float radius = 1f ;
	
	public MousePickerTraveler(Camera camera, MousePicker picker, Course course){
		
		this.position = camera.getPosition() ;
		this.direction = picker.getCurrentRay() ;
		this.entitiesList = course.getEntities() ;
		this.terrains = course.getTerrains() ;
		
		for(Entity entity:entitiesList){
			trianglesList.addAll(entity.getModel().getRawModel().getTriangles()) ;
		}
		for(Terrain terrain:terrains){
			trianglesList.addAll(terrain.getModel().getTriangles()) ;
		}
		
		
	}

	public Vector3f getPosition(){
		
		return position ;
	}
	
	public void setPosition(Vector3f newPosition){
		
		position = newPosition ;
	}
	
	public Vector3f getDirection(){
		
		return direction ;
	}
	
	public void setDirection(Vector3f newDirection){
		
		direction = newDirection ;
	}
	
	public Vector3f progress(){
	
		
		Vector3f dest = null ;
		direction = (Vector3f) direction.normalise() ;
		Vector3f.add(position, direction, dest) ;
		return dest ;
	}
	
	public boolean collision(){
		
		for(Triangle triangle:trianglesList){
			if(CollisionHandler.collide(this, triangle)){
				
				return true ;
			}
		}
		return false ;
		
	}
	
	public Vector3f collisionLocation(){
			
		for(Triangle triangle:trianglesList){
			if(CollisionHandler.collide(this, triangle)){
				collisionLocation = getPosition() ;
				return collisionLocation ;
			}
		}
		return null ;
	}
	
	
}

/* setPosition(progress()) ; should make it travel along the ray.. collision detection should be possible to handeld similar to ball or anything
 * need to add a method which will return the object it impacted with..*/
