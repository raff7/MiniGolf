package engineTester;


public interface GameState {
	public void update();
	public void render();
	public void changeGameState(GameState newState);
}
