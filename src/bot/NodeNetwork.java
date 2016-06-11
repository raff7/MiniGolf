package bot;

import java.util.ArrayList;

import geometry.Triangle;

public class NodeNetwork {
	
	ArrayList<Node> nodesList = new ArrayList<Node>();
	
	//Constructor
	public NodeNetwork(ArrayList<Triangle> trianglesList){
		for(int i=0; i<trianglesList.size(); i++){
			nodesList.add(new Node(trianglesList.get(i)));
		}
		
		for(int i=0; i<nodesList.size(); i++){
			Node node = nodesList.get(i);
			ArrayList<Integer> indexesList = node.getTriangle().getNeighbourTriangles(trianglesList);
			
			for(int j=0; j < indexesList.size(); j++){
				int index = indexesList.get(j);
				Node connectedNode = nodesList.get(index);
				Edge edge = new Edge();
				node.addEdge(edge);
				connectedNode.addEdge(edge);
			}	
		}
	}
	
	public ArrayList<Node> getNodesList(){
		return nodesList;
	}
	
	public void setNodesList(ArrayList<Node> nodesList){
		this.nodesList = nodesList;
	}
	
	public boolean areConnected(int i, int j){
		return nodesList.get(i).isConnected(nodesList.get(j));
	}
}
