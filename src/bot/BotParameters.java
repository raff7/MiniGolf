package bot;

public class BotParameters {
	
	private float[] weights = new float[3];
	private float fitness;
	Shooter shooter = new Shooter();
	
	public BotParameters(Ball ball){
		for(int i=0; i<weights.length; i++)
			weights[i] = (float) Math.random();
	}
	
	public float getFitness(){
		return fitness;
	}
	
	public void setFitness(float fitness){
		this.fitness = fitness;
	}
	
	public Shooter getShooter(){
		return shooter;
	}
	
	
}
