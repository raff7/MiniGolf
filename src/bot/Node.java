package bot;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import geometry.Triangle;
import toolbox.Operation;

public class Node extends Triangle {
	
	private float distance;
	private ArrayList<Edge> edgesList = new ArrayList<Edge>();
	private boolean isVisited;
	
	public Node(){		
		super(new Vector3f(0,0,0),new Vector3f(0,0,0),new Vector3f(0,0,0));
	}
	
	public Node(Triangle triangle){
		super(triangle.getP1(), triangle.getP2(), triangle.getP3());
	}
	
	public ArrayList<Edge> getEdgesList(){
		return edgesList;
	}
	
	public void setEdgesList(ArrayList<Edge> edgesList){
		this.edgesList = edgesList;
	}
	
	public void addEdge(Edge edge){
		edgesList.add(edge);
	}
	
	public int getDegree(){
		return edgesList.size();
	}
	
	public boolean isConnected(Node node2){
		for(int i=0; i<edgesList.size(); i++){
			for(int j=0; j<node2.getEdgesList().size(); j++){
				if( edgesList.get(i) == node2.getEdgesList().get(j) )
					return true;
			}
		}
		return false;
	}
	
	public Edge getConnectionEdge(Node node2){
		for(int i=0; i<edgesList.size(); i++){
			for(int j=0; j<node2.getEdgesList().size(); j++){
				if( edgesList.get(i) == node2.getEdgesList().get(j) )
					return edgesList.get(i);
			}
		}
		System.out.println("No connecting edge");
		return null;
	}
	
	public float getDistance(){
		return distance;
	}
	public void setDistance(float distance){
		this.distance=distance;
	}
	public Vector3f getPosition(){
		return getCentroid();
	}
	
	public boolean isVisited(){
		return isVisited;
	}
	
	public void setVisited(boolean value){
		isVisited = value;
	}
	
	public Vector3f getNormal(){
		return super.getNormal();
	}
	
	public ArrayList<Node> getNeighbourNodes(ArrayList<Node> nodesList){
		
		ArrayList<Node> connectedNodes = new ArrayList<Node>();
		ArrayList<Vector3f> verticesList = new ArrayList<Vector3f>();
		verticesList.add(this.getP1());
		verticesList.add(this.getP2());
		verticesList.add(this.getP3());
		Vector3f a = new Vector3f();
		Vector3f b = new Vector3f();
		Vector3f point = new Vector3f();
		Vector3f crossProduct= new Vector3f();
		float dotProduct=0;
		Vector3f bMinusA = new Vector3f();
		Vector3f pointMinusA = new Vector3f();
		float epsilon = 0.0001f;
		
		this.setVisited(true);
		
		for(int i=0; i<nodesList.size(); i++){
			
			if(nodesList.get(i)== this)
				return connectedNodes;
			Vector3f p1 = nodesList.get(i).getP1();
			Vector3f p2 = nodesList.get(i).getP2();
			Vector3f p3 = nodesList.get(i).getP3();
			
			for(int j=0; j<verticesList.size(); j++){
				point = verticesList.get(j);
				
				//a=p1, b=p2
				a=p1;
				b=p2;
				Vector3f.sub(b, a, bMinusA);
				Vector3f.sub(point, a, pointMinusA);
				Vector3f.cross(bMinusA, pointMinusA, crossProduct);		
				if(Math.abs( crossProduct.length() ) < epsilon){
					dotProduct = Vector3f.dot(bMinusA, pointMinusA);
					if(dotProduct >= 0){
						if(dotProduct <= Math.pow(bMinusA.length(), 2) ){
							double dot = Math.pow(Vector3f.dot(getNormal(), new Vector3f(0,1,0)),2);
							if( 0.5<dot && dot<1 ){
								if( !this.isConnected(nodesList.get(i))){
									distance = Operation.subtract(this.getPosition(), nodesList.get(i).getPosition()).length();
									distance = Math.abs(distance);
									Edge edge = new Edge(distance);
									nodesList.get(i).addEdge(edge);
									this.addEdge(edge);
								}
								if( !nodesList.get(i).isVisited){
									connectedNodes.add(nodesList.get(i));
									connectedNodes.addAll(nodesList.get(i).getNeighbourNodes(nodesList));
								}
								break;
							}
						}
					}
					
				}else{
					//a=p1, b=p3
					a=p1;
					b=p3;
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);	
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);			
					if(Math.abs( crossProduct.length() ) < epsilon){
						dotProduct = Vector3f.dot(bMinusA, pointMinusA);
						if(dotProduct >= 0){ 
							if(dotProduct <= Math.pow(bMinusA.length(), 2) ){
								double dot = Math.pow(Vector3f.dot(getNormal(), new Vector3f(0,1,0)),2);
								if( 0.5<dot && dot<1){
									if( !this.isConnected(nodesList.get(i))){
										distance = Operation.subtract(this.getPosition(), nodesList.get(i).getPosition()).length();
										distance = Math.abs(distance);
										Edge edge = new Edge(distance);
										nodesList.get(i).addEdge(edge);
										this.addEdge(edge);
									}
									if( !nodesList.get(i).isVisited){
										connectedNodes.add(nodesList.get(i));
										connectedNodes.addAll(nodesList.get(i).getNeighbourNodes(nodesList));
									}
									break;
								}
							}
						}
				}else{
					//a=p2, b=p3
					a=p2;
					b=p3;
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);	
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);				
					if(Math.abs( crossProduct.length() ) < epsilon){
						dotProduct = Vector3f.dot(bMinusA, pointMinusA);
						if(dotProduct >= 0){
							if(dotProduct <= Math.pow(bMinusA.length(), 2) ){
								double dot = Math.pow(Vector3f.dot(getNormal(), new Vector3f(0,1,0)),2);
								if(0.5<dot && dot<1){
									if( !this.isConnected(nodesList.get(i))){
										distance = Operation.subtract(this.getPosition(), nodesList.get(i).getPosition()).length();
										distance = Math.abs(distance);
										Edge edge = new Edge(distance);
										nodesList.get(i).addEdge(edge);
										this.addEdge(edge);
									}
									if( !nodesList.get(i).isVisited){
										connectedNodes.add(nodesList.get(i));
										connectedNodes.addAll(nodesList.get(i).getNeighbourNodes(nodesList));
									}
									break;
								}
							}
						}
					}
				}
		}
		}
		}
		return connectedNodes;
	}
}
