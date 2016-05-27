package GameManager;

import entities.Ball;
import entities.Camera;

public abstract class Player {
	private int numberOfShots;
	private Ball ball;
	private Camera camera;
	protected float power;
	private final float MAX_POWER = 300;
	
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public int getNumberOfShots() {
		return numberOfShots;
	}
	public abstract void shoot();
	
	public void increasePower(){
		power += 1.5;
		if(power > MAX_POWER)
			power = MAX_POWER;
	}
	
	public float getPower(){
		return power;
	}
	public void setPower(float pow){
		power =pow;
	}
}
