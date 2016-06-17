package bot;

import java.util.ArrayList;

public class DijkstrasAlgorithm {
	public static void dijkstrasAlgorithm(NodeNetwork net, Node sourceNode){
		ArrayList<Node> nodes =  net.getNodesList();
		for(Node node:nodes){
			node.setDistance(Float.MAX_VALUE);
		}
		sourceNode.setDistance(0);
		MinHeap heap = new MinHeap(nodes);		
		Node expl=null;
		ArrayList<Node> adiacentNodes = null;
		Edge edge = null;
		while(heap.size()>0){
			expl=heap.removeMin();
			adiacentNodes = net.getAdjacentNodes(expl);
			for(Node node:adiacentNodes){
				if(heap.contains(node)){
					edge = net.getEdge(expl,node);
					if(node.getDistance()> ( expl.getDistance()+edge.getDistance())){
						heap.changeValue(node,expl.getDistance()+edge.getDistance());
					}
				}
			}	
		}		
	}
}
