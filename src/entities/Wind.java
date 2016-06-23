package entities;


import org.lwjgl.util.vector.Vector3f;

import toolbox.Operation;

public  class Wind extends Noise {
	public Wind(Vector3f wind){
		this.noise=wind;
	}
	public void apply(Ball ball) {
		if(super.noise.length()>ball.getFriction()){
			System.err.println("TOO MUCH WIND");
		}
			Vector3f noise = Operation.multiplyByScalar(DELTA, this.noise);
			ball.setVelocity(Operation.add(noise, ball.getVelocity()));
	}
}
