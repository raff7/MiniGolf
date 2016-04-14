package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Lamp extends Entity{
	Light light;

	public Lamp(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f colour) {
		super(model, position, rotX, rotY, rotZ, scale);
		model.getTexture().setUseFakeLight(true);
		light = new Light(new Vector3f(position.x,position.y+12.5f*scale,position.z),colour, new Vector3f(1,0.01f,0.002f));
	}
	
	public Light getLight(){
		return light;
	}


}
