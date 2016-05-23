import org.lwjgl.util.vector.Vector2f;
import geometry.Triangle2D;

public class Tester {
	public static void main(String[] args){
//		Vector2f p1= new Vector2f(13.5f,27.49f);
//		Vector2f p2= new Vector2f(-13.5f,0.3f);
//		Vector2f p3= new Vector2f(15.5f,0.3f);

		Vector2f p1= new Vector2f(13f,27.f);
		Vector2f p2= new Vector2f(-13f,0.3f);
		Vector2f p3= new Vector2f(15f,0.3f);
		
		Triangle2D t = new Triangle2D(p1,p2,p3);


		
		Vector2f p = new Vector2f(-0.0f,0f);
		
		System.out.println(t.distance(p));
	
		
		
		
		
		
		

		

	}
}
