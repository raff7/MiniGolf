package algorithms;

import java.util.ArrayList;

import bot.BotParameters;

public class GeneticAlgorithm {
	
	private final int POPULATION_NUMBER = 10000;
	private final int GENERATION_NUMBER = 10000;
	private BotParameters[] parentsPool = new BotParameters[POPULATION_NUMBER];
	
	public void generate(){
		for(int i=0; i<POPULATION_NUMBER; i++){
			BotParameters botParameters = new BotParameters();
			parentsPool[i] = botParameters;	
		}
	}
	
	public void setFitness(){
		for(int i=0; i<POPULATION_NUMBER; i++){
			
		}
	}
	
	public void crossOver(){
		
	}
	
	public void mutate(){
		
	}
}
