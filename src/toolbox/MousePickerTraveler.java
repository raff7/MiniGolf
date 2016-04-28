package toolbox;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class MousePickerTraveler {
	
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
	
	private void progress(){
	
		position = position + direction ;
	}
}
