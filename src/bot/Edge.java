package bot;

public class Edge {
	float distance;
	
	public Edge(){
		
	}
	
	public float getDistance(){
		return distance;
	}
	
	public void setDistance(int distance){
		this.distance = distance;
	}
	public String toString(){
		return " "+distance;
	}
}
