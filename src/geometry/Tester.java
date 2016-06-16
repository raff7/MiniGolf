package geometry;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import bot.Node;
import bot.NodeNetwork;
import entities.Hole;
import models.RawModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;

public class Tester {
	public static void main(String[] arg){
	
		Vector3f p1 = new Vector3f(2,0,2);
		Vector3f p2 = new Vector3f(3,0,2);
		Vector3f p3 = new Vector3f(3,0,3);
		Vector3f p4= new Vector3f(2,0,3);
		Vector3f p5 = new Vector3f(2.5f,0,1);
		Vector3f p6 = new Vector3f(4,0,2.5f);
		Vector3f p7 = new Vector3f(2.5f,0,4);
		Vector3f p8 = new Vector3f(1,0,2.5f);
//		Vector3f p9 = new Vector3f(4,0,2);
//		Vector3f p10 = new Vector3f(4,0,3);
//		Vector3f p11 = new Vector3f(5,0,3);
//		Vector3f p12 = new Vector3f(5,0,2);
//		Vector3f p13 = new Vector3f(5,1,2);
//		Vector3f p14 = new Vector3f(5,1,3);
//			
		Triangle triangle1 = new Triangle(p1,p2,p5);
		Triangle triangle2 = new Triangle(p1,p8,p4);
		Triangle triangle3 = new Triangle(p4,p7,p3);
		Triangle triangle4 = new Triangle(p3,p6,p2);
//		Triangle triangle5 = new Triangle(p10,p11,p9);
//		Triangle triangle6 = new Triangle(p11,p12,p9);
//		Triangle triangle7 = new Triangle(p11,p14,p12);
//		Triangle triangle8 = new Triangle(p14,p13,p12);
		
		ArrayList<Vector3f> points = new ArrayList<Vector3f>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		
		//nodes
		Hole hole = new Hole(points);
		ArrayList<Node> nodesList = new ArrayList<Node>();
		Node node1 = new Node(triangle1,"1");
		Node node2 = new Node(triangle2,"2");
		Node node3 = new Node(triangle3,"3");
		Node node4 = new Node(triangle4,"4");
//		Node node5 = new Node(triangle5);
//		Node node6 = new Node(triangle6);
//		Node node7 = new Node(triangle7);
//		Node node8 = new Node(triangle8);
//		
		nodesList.add(node1);
		nodesList.add(node2);
		nodesList.add(node3);
		nodesList.add(node4);
//		nodesList.add(node5);
//		nodesList.add(node6);
//		nodesList.add(node7);
//		nodesList.add(node8);
//		
		long time1 = System.currentTimeMillis();
		NodeNetwork net = new NodeNetwork(hole.getNodesNetwork(nodesList));
		long time2 = System.currentTimeMillis();
		
		System.out.println("network's size: "+net.getNodesList().size());
		System.out.println("Run time: "+(time2-time1));
		System.out.println();
		for(Node node:net.getNodesList()){
			System.out.print("\nNode "+node+" is connected to: ");
			if(net.getAdjacentNodes(node).size()==0){
				System.out.print("no Nodes");
			}else{
				for(Node connectedNode:net.getAdjacentNodes(node)){
					System.out.println("node "+connectedNode);
				}
				System.out.println();
			}
		}
	}
	public static  float getNumber(){
		float number = (float) (Math.random()*20);
		return number;
	}
}
