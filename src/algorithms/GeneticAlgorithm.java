package algorithms;

import java.util.ArrayList;

import bot.BotParameters;

public class GeneticAlgorithm {
	
	private final int POPULATION_NUMBER = 1000;
	private final int GENERATION_NUMBER = 10000;
	private BotParameters[] population = new BotParameters[POPULATION_NUMBER];
	
	public void generate(){
		for(int i=0; i<population.length; i++){
			BotParameters botParameters = new BotParameters();
			population[i] = botParameters;	
		}
	}
	
	public void setFitness(){
		for(int i=0; i<population.length; i++){
			population[i].getShooter().execute(ball);
		}
	}
	
	public void crossOver(){
		
	}
	
	public void mutate(){
		int random = Math.random()*4;
		
	}
}
