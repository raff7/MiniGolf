package algorithms;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

public class GeneticAlgorithm {
	private ArrayList<float[]> parentsPool = new ArrayList<float[]>();
	private final int POPULATION_NUMBER = 10000;
	private final int GENERATION_NUMBER = 10000;
	
	public void generate(){
		float x,y,z,power;
		for(int i=0; i<POPULATION_NUMBER/10; i++){
			x = (float) (Math.random()*100);
			y = (float) (Math.random()*100);
			z = (float) (Math.random()*100);
			power = (float) (Math.random()*100);
			parentsPool.add(new float[]{x,y,z,power,0});	
		}
	}
	
	public void setFitness(){
		for(int i=0; i < POPULATION_NUMBER; i++){
			
		}
	}
	
	public void crossOver(){
		
	}
	
	public void mutate(){
		
	}
}
