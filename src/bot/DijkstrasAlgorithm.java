package bot;

import java.util.ArrayList;

public class DijkstrasAlgorithm {
	public static float dijkstrasAlgorithm(NodeNetwork net, Node startingNode, Node endNode){
		ArrayList<Node> nodes =  net.getNodesList();
		for(Node node:nodes){
			node.setDistance(Float.MAX_VALUE);
		}
		startingNode.setDistance(0);
		MinHeap heap = new MinHeap(nodes);		
		Node expl=null;
		ArrayList<Node> adiacentNodes = null;
		Edge edge = null;
		System.out.println(heap);
		while(heap.size()>0){
			expl=heap.removeMin();
			adiacentNodes = net.getAdjacentNodes(expl);
			for(Node node:adiacentNodes){
				if(heap.contains(node)){
					edge = net.getEdge(expl,node);
					System.out.println(edge.getDistance());
					if(node.getDistance()> ( expl.getDistance()+edge.getDistance())){
						heap.changeValue(node,expl.getDistance()+edge.getDistance());
					}
				}
			}	
			System.out.println();
			System.out.println(heap);
			System.out.println();

		}
		return endNode.getDistance();
		
	}
}
