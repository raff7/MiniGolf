package GameManager;

import entities.Ball;
import entities.Camera;

public abstract class Player {
	private int numberOfShots;
	private Ball ball;
	private Camera camera;
	
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
}
