package bot;

public class Node {
	
	private int distance ;
	private boolean hasBall = false ;
	private boolean hasHole = false ;
	private boolean isFree = false ;
	
	
	private void setDistance(int dist){
		
		distance = dist ;
	}
	
	private int getDistance(){
		
		return distance ;
	}
	
	private void setOccupied(){
		
		isFree = false ;
	}
	
	private void setFree(){
		
		isFree = true ;
	}
	
	private boolean isFree(){
		
		return isFree ;
	}
	
	private void addHole(){
		
		hasHole = true ;
	}
	
	private void noHole(){
		
		hasHole = false ;
	}
	
	private boolean hasHole(){
		
		return hasHole ;
	}
	
	private void addBall(){
		
		hasBall = true ;
	}
	
	private void removeBall(){
		
		hasBall = false ;
	}
	
	//backtracking ability.. incase you run in to dead end when adding fitness++ while expanding AWAY from hole.. (how the nodes will be constructed)
	

}
