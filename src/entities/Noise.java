package entities;

import org.lwjgl.util.vector.Vector3f;

public abstract class Noise {
	Vector3f noise;
	final float DELTA= 0.009f;
	public abstract void apply(Ball ball);
}
