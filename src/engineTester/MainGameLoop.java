package engineTester;


import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;

public class MainGameLoop{
	
	private static GameState actualState;
	

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		actualState = new MainMenu();
		
		
		while(!Display.isCloseRequested() && actualState !=null){
			actualState.update();
			actualState.render();
			
		}
		DisplayManager.closeDisplay();
		System.exit(0);

	}

	public static void changeGameState(GameState newState){

		actualState = newState;
	}
	
}
