package algorithms;

import org.lwjgl.util.vector.Vector3f;

import entities.Ball;

public abstract class BotAlgorithm {
	public abstract Vector3f execute(Ball ball);
}
