package bot;

import java.util.ArrayList;

import geometry.Triangle;

public class Testing {
	public static void main(String[] arg){
		
		ArrayList list = new ArrayList<Node>();
		Node temp;
		temp = new Node(10);
		list.add(temp);
		temp = new Node(5);
		list.add(temp);
		temp = new Node(11);
		list.add(temp);
		temp = new Node(72);
		list.add(temp);
		temp = new Node(72);
		list.add(temp);
		temp = new Node(62);
		list.add(temp);
		temp = new Node(101);
		list.add(temp);
		temp = new Node(23);
		list.add(temp);
		

		MinHeap heap = new MinHeap(list);
		System.out.println(heap);
		
		heap.removeMin();
		System.out.println(heap);
		

	}
}
