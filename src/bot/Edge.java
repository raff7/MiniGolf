package bot;

public class Edge {
	float distance;
	String name;
	
	public Edge(float distance){
		this.distance=distance;
		//this.name = name;
	}
	
	public float getDistance(){
		return distance;
	}
	
	public void setDistance(float distance){
		this.distance = distance;
	}
	public String toString(){
		return distance+"";
	}
}
