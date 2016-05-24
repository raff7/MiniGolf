package water;

import java.io.Serializable;

public class WaterTile implements Serializable{
	
	public static final float TILE_SIZE = 300;
	
	private float height;
	private float x,z;
	
	public WaterTile(float centerX, float centerZ, float height){
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
	}

	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}



}
