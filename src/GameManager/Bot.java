package bot;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import collision.BoundingBox;
import entities.Ball;
import entities.Entity;
import entities.Player;
import geometry.Line;
import terrains.Terrain;

public class Bot extends Player{
	Ball ball;
	Terrain terrain;
	ArrayList<Entity> obstaclesList;
	Hole hole;
	
	public Bot(Ball ball, Terrain terrain){
		this.ball = ball;
		this.terrain = terrain;
	}
	
	public void shot(){
		if(isPathClear())
			
	}
	
	public boolean isPathClear(){
		Line path = new Line(ball.getPosition(), hole.getPosition());
		
		for(Entity entity: obstaclesList){
			BoundingBox box = entity.getBox();
			path.liesOnSameSide(box.getMinX(), box.getMaxX())
		}
		
		
	}
}
