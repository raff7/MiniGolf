package collision;

import java.util.ArrayList;

import entities.Ball;
import entities.Entity;

public class CollisionDetection {
	public static ArrayList<Entity> getHitObstacles(ArrayList<Entity> obstacles, Ball ball){
		ArrayList<Entity> hitObstacles = new ArrayList<Entity>();
		boolean xIntersection = false, yIntersection=false, zIntersection = false;

		for(int i=0; i<obstacles.size(); i++){
			Entity obstacle = obstacles.get(i);
			BoundingBox obsBox = obstacle.getBox();
			BoundingBox ballBox = ball.getBox();

			if(ballBox.getMinX() < obsBox.getMaxX() && ballBox.getMaxX() > obsBox.getMinX()){
				//Boxes intersect on X axis
				xIntersection = true;
			}
			if(xIntersection && ballBox.getMinY() < obsBox.getMaxY() && ballBox.getMaxY() > obsBox.getMinY()){
				//Boxes intersect on Y axis
				yIntersection = true;
			}
			if(yIntersection && ballBox.getMinZ() < obsBox.getMaxZ() && ballBox.getMaxZ() > obsBox.getMinZ()){
				//Boxes intersect on X axis
				zIntersection = true;
			}

			if(xIntersection && yIntersection && zIntersection){
				hitObstacles.add(obstacle);
			}
		}
		return hitObstacles;
	}
}
