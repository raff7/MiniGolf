package bot;

import java.util.ArrayList;

public class DijkstrasAlgorithm {
	public static float dijkstrasAlgorithm(NodeNetwork net, Node startingNode, Node endNode){
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes = net.getNodesList();
		for(Node node:nodes){
			node.setDistance(Float.MAX_VALUE);
		}
		startingNode.setDistance(0);
		MinHeap heap = new MinHeap(nodes);
		
		Node expl=null;
		ArrayList<Node> adiacentNodes = null;
		Edge edge = null;
		while(heap.contains(endNode)){
			expl=heap.removeMin();
			adiacentNodes = net.getAdiacentNodes(expl);
			for(Node node:adiacentNodes){
				edge = net.getEdge(expl,node);
				if(node.getDistance()> ( expl.getDistance()+edge.getDistance())){
					node.setDistance(expl.getDistance()+edge.getDistance());
				}
			}
			return endNode.getDistance();
			
		}
		
		
	}
}
