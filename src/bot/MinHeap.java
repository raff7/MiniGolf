package bot;
import java.util.ArrayList;

public class MinHeap {
	private ArrayList<Node> heap=new ArrayList<Node>();
	
	public MinHeap(ArrayList<Node> heap){
		for(Node node:heap){
			addElement(node);
		}
	}
	public Node getMin(){
		return heap.get(0);
	}
	public Node removeMin(){
		Node temp = heap.get(0);
		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		downHeap(0);
		return temp;
	}
	
	public void addElement(Node node){
		heap.add(node);
		if(heap.size()>1)
			upHeap(heap.size()-1);
	}
	
	private void upHeap(int index){
		index += 1;
		int parentIndex;
		if(index%2==0)
			parentIndex= index/2;
		else
			parentIndex= (index-1)/2;
		while( index > 1 && (heap.get(index-1).getDistance())< (heap.get(parentIndex-1).getDistance())){
			
			Node temp = heap.get(index-1);
			heap.set(index-1, heap.get(parentIndex-1));
			heap.set(parentIndex-1, temp);
			index=parentIndex;
			if(index%2==0)
				parentIndex = index/2;
			else
				parentIndex = (index-1)/2;

		}	
	}

	private void downHeap(int index){
		index += 1; //index from 1 to heap.size, subtract 1 ONLY when accessing the element in the arrayList.
		int childIndex;
		if(2*index+1<=heap.size()){
			if(heap.get(2*index-1).getDistance()< heap.get(2*index).getDistance())
				childIndex= 2*index;
			else
				childIndex=2*index+1;
		}else if(2*index<=heap.size()){
			childIndex=2*index;
		}else
			return;
		while((heap.get(index-1).getDistance())> (heap.get(childIndex-1).getDistance())){

			Node temp = heap.get(index-1);
			heap.set(index-1, heap.get(childIndex-1));
			heap.set(childIndex-1, temp);
			index=childIndex;
			if((index*2)+1<=heap.size()){
				if(heap.get(((index)*2)-1).getDistance()< heap.get(((index)*2)).getDistance()){
					childIndex=((index)*2);

				}
				else{
					childIndex=(index)*2+1;
				}
			}else if((index*2)<=heap.size()){
				childIndex=(index)*2;
			}else{
				break;
			}
			
		}
	}
	@Override
	public String toString(){
		String res = null;
		for(Node node:heap){
			if(res!=null)
				res = res+" "+node.getDistance();
			else 
				res = ""+node.getDistance();

		}
		return res;
		
	}
	public int size(){
		return heap.size();
	}
	public boolean contains(Node node) {
		return heap.contains(node);
	}
	public void changeValue(Node node, float value) {
		int index = -1;
		node.setDistance(value);
		for(int i=0;i<heap.size();i++){
			if(node==heap.get(i)){
				index=i;
				break;
			}		
		}
		if(index!=-1){
			if(index !=0)
				upHeap(index);
			if(index != heap.size())
				downHeap(index);
		}
		else
			System.err.println("Node not in the hip");
	}
}
