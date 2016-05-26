package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class CoursePiece extends Entity {
	private Hole hole = null;
	public CoursePiece(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	public CoursePiece(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,Hole hole) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.hole=hole;
	}
	public Hole isHasHole() {
		return hole;
	}
}