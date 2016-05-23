package GameManager;

import java.util.ArrayList;
import java.util.List;

import gui.GuiTexture;

public class Game implements Observer {
	
	private ArrayList<Player> players;
	private ArrayList<Player> donePlayers= new ArrayList<Player>();
	private Player activePlayer;
	private int playerID;
	private ArrayList<GuiTexture> guis = new ArrayList<GuiTexture>();
	
	private boolean isPause=false;

	
	
	public Game(ArrayList<Player> players){
		this.players=players;
		activePlayer = players.get(playerID);
	}
	
	public Game(Player player) {
		players = new ArrayList<Player>();
		players.add(player);
		activePlayer=player;
	}

	public void update(){
		if(activePlayer.getBall().getBallIsInHole()){
			donePlayers.add(activePlayer);
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
	
	public void pause(){
		isPause=true;
	}
	public void unPause(){
		isPause=false;
	}
	public boolean isPause(){
		return isPause;
	}

	public List<GuiTexture> getGUIs() {
		return guis;
	}
	public ArrayList<Player> getDonePlayers(){
		return donePlayers;
	}
	
}
