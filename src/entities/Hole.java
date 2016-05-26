package entities;

import org.lwjgl.util.vector.Vector3f;

public class Hole {
	private Vector3f coordinates;
	public Hole(Vector3f coordinates){
		this.coordinates = coordinates;
	}
	public Vector3f getCoordinates(){
		return coordinates;
	}
}
