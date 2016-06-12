package bot;

import java.util.ArrayList;

import geometry.Triangle;

public class NodeNetwork {
	
	ArrayList<Node> nodesList = new ArrayList<Node>();
	
	
	//for testing
	public NodeNetwork(ArrayList<Node> nodes){
		nodesList=nodes;
	}
//	//Constructor
//	public NodeNetwork(ArrayList<Triangle> trianglesList){
//		for(int i=0; i<trianglesList.size(); i++){
//			nodesList.add(new Node(trianglesList.get(i)));
//		}
//		
//		for(int i=0; i<nodesList.size(); i++){
//			Node node = nodesList.get(i);
//			ArrayList<Integer> indexesList = node.getNeighbourTriangles(trianglesList);
//			
//			for(int j=0; j < indexesList.size(); j++){
//				int index = indexesList.get(j);
//				Node connectedNode = nodesList.get(index);
//				Edge edge = new Edge(0);
//				node.addEdge(edge);
//				connectedNode.addEdge(edge);
//			}	
//		}
//	}
	
	public ArrayList<Node> getNodesList(){
		return nodesList;
	}
	
	public void setNodesList(ArrayList<Node> nodesList){
		this.nodesList = nodesList;
	}
	
	public boolean areConnected(int i, int j){
		return nodesList.get(i).isConnected(nodesList.get(j));
	}
	
	public ArrayList<Node> getAdjacentNodes(Node node){
		ArrayList<Node> adjacentNodes = new ArrayList<Node>();
		for(int i=0; i < nodesList.size(); i++){
			if(nodesList.get(i) != node){
				if(node.isConnected(nodesList.get(i)))
					adjacentNodes.add(nodesList.get(i));
			}
		}
		return adjacentNodes;
	}
	
	public Edge getEdge(Node node1, Node node2){
		return node1.getConnectionEdge(node2);
	}
	
	public String toString(){
		String s =" ";
		for(Node node: nodesList)
		s += node.getDistance()+"  "+node.getEdgesList();
		
		return s;
	}
}
