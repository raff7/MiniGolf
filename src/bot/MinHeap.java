package bot;
import java.util.ArrayList;

public class MinHeap {
	private ArrayList<Node> heap;
	
	public MinHeap(ArrayList<Node> heap){
		for(Node node:heap){
			this.heap.add(node);
			upHeap();
		}
	}
	public Node getMin(){
		return heap.get(0);
	}
	public Node removeMin(){
		Node temp = heap.get(0);
		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		downHeap();
		return temp;
	}
	public void addElement(Node node){
		heap.add(node);
		upHeap();
	}
	private void upHeap(){
		int index = heap.size();
		int parentIndex;
		if(index%2==0)
			parentIndex= index/2;
		else
			parentIndex= index-1/2;
		while((heap.get(index-1).getValue())< (heap.get(parentIndex-1).getValue())){
			Node temp = heap.get(index-1);
			heap.set(index-1, heap.get(parentIndex-1));
			heap.set(parentIndex-1, temp);
			index=parentIndex;
			if(index%2==0)
				parentIndex= index/2;
			else
				parentIndex= index-1/2;

		}	
	}
	private void downHeap(){
		int index = 1;
		int childIndex;
		if(heap.get(1).getValue()<heap.get(2).getValue()){
			childIndex=2;
		}else{
			childIndex=3;
		}
		while((heap.get(index-1).getValue())> (heap.get(childIndex-1).getValue())){
			Node temp = heap.get(index-1);
			heap.set(index-1, heap.get(childIndex-1));
			heap.set(childIndex-1, temp);
			index=childIndex;
			if(heap.get((index-1)*2).getValue()<heap.get((index-1)*2+1).getValue())
				childIndex=(index-1)*2;
			else
				childIndex=(index-1)*2+1;

		}
	}
}
