package toolbox;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;

public class MousePickerTraveler{
	
	Vector3f position ;
	Vector3f direction ;
	
	public MousePickerTraveler(Camera camera, MousePicker picker){
		
		this.position = camera.getPosition() ;
		this.direction = picker.getCurrentRay() ;	
	}

	private Vector3f getPosition(){
		
		return position ;
	}
	
	private void setPosition(Vector3f newPosition){
		
		position = newPosition ;
	}
	
	private Vector3f getDirection(){
		
		return direction ;
	}
	
	private void setDirection(Vector3f newDirection){
		
		direction = newDirection ;
	}
	
	private Vector3f progress(){
	
		Vector3f dest = null ;
		Vector3f.add(position, direction, dest) ;
		return dest ;
	}
	
	private Entity collisionObject(){
		
		Entity temp = null ;
		temp = ;
		return temp ;
	}
	
	
}

/* setPosition(progress()) ; should make it travel along the ray.. collision detection should be possible to handeld similar to ball or anything
 * need to add a method which will return the object it impacted with..*/
