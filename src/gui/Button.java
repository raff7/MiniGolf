package gui;

import org.lwjgl.util.vector.Vector2f;

public class Button extends GuiTexture{

	private int selTexture;
	private boolean isSel=false;
	public Button(int texture,int selTexture, Vector2f position, Vector2f scale) {
		super(texture, position, scale);
		this.selTexture = selTexture;
	}
	public boolean isSel() {
		return isSel;
	}
	public void setSel(boolean isSel) {
		this.isSel = isSel;
	}
	public int getSelTexture() {
		return selTexture;
	}
	public int getTexture(){
		if(isSel)
			return selTexture;
		return super.getTexture();
	}
	

}
