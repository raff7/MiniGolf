package engineTester;

public class Option implements GameState{

	private GameState activeGameState;
	
	public Option(GameState activeGameState){
		this.activeGameState = activeGameState;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeGameState(GameState newState) {
			
		
		MainGameLoop.Notify(activeGameState);
		
	}

}
