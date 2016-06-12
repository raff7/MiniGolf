package bot;

public class Edge {
	float distance;
	
	public Edge(float distance){
		this.distance=distance;
	}
	
	public float getDistance(){
		return distance;
	}
	
	public void setDistance(float distance){
		this.distance = distance;
	}
	public String toString(){
		return " "+distance;
	}
}
