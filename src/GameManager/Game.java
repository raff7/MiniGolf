package GameManager;

import java.util.ArrayList;

public class Game {
	
	private ArrayList<Player> players;
	private Player activePlayer;
	private int playerID;

	
	
	public Game(ArrayList<Player> players){
		this.players=players;
		activePlayer = players.get(playerID);
	}
	
	public void update(boolean BallIsInHole){
		if(BallIsInHole){
			players.remove(activePlayer);
		}
		if(players.size()==0){
			gameOver();
		}
		else if(playerID<players.size()-1){
			playerID++;
		}
		else{
			playerID=0;
		}
		
		activePlayer=players.get(playerID);

	}
}
