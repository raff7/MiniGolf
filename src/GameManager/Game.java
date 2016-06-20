package GameManager;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import gui.GuiTexture;
import renderEngine.Loader;

public class Game implements Observer {
	
	private ArrayList<Player> players;
	private ArrayList<Player> donePlayers= new ArrayList<Player>();
	private Player activePlayer;
	private int playerID;
	private ArrayList<GuiTexture> guis = new ArrayList<GuiTexture>();
	
	private boolean isPause=false;
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	GuiTexture shotPower = null ;
	public int redID, arrowID ;
	
	
	public Game(ArrayList<Player> players){
		this.players=players;
		activePlayer = players.get(playerID);

		for(Player player:players)
			player.getBall().attach(this);

	}
	
	public Game(Player player) {
		players = new ArrayList<Player>();
		players.add(player);
		activePlayer=player;
	}
	
	public void getShotPowerGraphics(){
		Loader load = Loader.getLoader() ;
		redID = load.loadTexture("red") ;
		Vector2f position = new Vector2f(-1.9f, -0.75f);
		Vector2f scale = new Vector2f(1f, (float)(activePlayer.getPower() /500) * 3);
		shotPower = new GuiTexture(redID, position, scale) ;
		guis.add(shotPower) ;
	}
	
	public void addShotArrow(){
		Loader load = Loader.getLoader() ;
		Vector2f position = new Vector2f(0f, -0.2f) ;
		Vector2f scale = new Vector2f(0.03125f, 0.0625f) ;
		
		
	}
	
	public void removeShotPowerGraphics(){
		guis.clear();
	}

	public void updateObserver(){
		if(activePlayer.getBall().getBallIsInHole()){
			donePlayers.add(activePlayer);
			players.remove(activePlayer);
		}
		if(players.size()==0){
			notify();
		}
		else if(playerID<players.size()-1){
			playerID++;
			activePlayer=players.get(playerID);

		}
		else{
			playerID=0;
			activePlayer=players.get(playerID);

		}
	}
	
	public void notifyObservers(){
		for(Observer observer:observers ){
			observer.updateObserver();
		}
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

	public Player getActivePlayer() {
		return activePlayer;
	}
	
}
