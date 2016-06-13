package entities;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

public class Hole {
	private ArrayList<Vector3f> points;
	public Hole(ArrayList<Vector3f> points){
		this.points = points;
	}
	public ArrayList<Vector3f> getPoints(){
		return points;
	}
}