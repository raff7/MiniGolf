package bot;

import java.util.ArrayList;

import geometry.Triangle;

public class Testing {
	public static void main(String[] arg){
		ArrayList<Node> nodes = new ArrayList<Node>();
		Edge first = new Edge(1);
		Edge second = new Edge(5);
		Edge third = new Edge(6);
		Edge fourth = new Edge(2);
		Edge fifth = new Edge(7);
		Edge sixth = new Edge(4);
		Edge seventh = new Edge(3);
		Edge eighth = new Edge(2);
		Edge nineth = new Edge(10);
		Edge tenth = new Edge(8);

		Node startNode = new Node();
		startNode.addEdge(first);
		startNode.addEdge(second);
		Node endNode = new Node();
		endNode.addEdge(nineth);
		endNode.addEdge(tenth);
		nodes.add(endNode);
		nodes.add(startNode);
		Node firstNode = new Node();
		firstNode.addEdge(first);
		firstNode.addEdge(fourth);
		firstNode.addEdge(third);
		firstNode.addEdge(seventh);
		nodes.add(firstNode);
		Node secondNode = new Node();
		secondNode.addEdge(second);
		secondNode.addEdge(third);
		secondNode.addEdge(fifth);
		secondNode.addEdge(sixth);
		nodes.add(secondNode);
		Node thirdNode = new Node();
		thirdNode.addEdge(fourth);
		thirdNode.addEdge(sixth);
		thirdNode.addEdge(eighth);
		thirdNode.addEdge(nineth);
		nodes.add(thirdNode);
		Node fourthNode = new Node();
		fourthNode.addEdge(fifth);
		fourthNode.addEdge(seventh);
		fourthNode.addEdge(eighth);
		fourthNode.addEdge(tenth);
		nodes.add(fourthNode);
		
		
		NodeNetwork net = new NodeNetwork(nodes);

		System.out.println("SOLUTION: "+DijkstrasAlgorithm.dijkstrasAlgorithm(net, startNode, endNode));
	}
}
