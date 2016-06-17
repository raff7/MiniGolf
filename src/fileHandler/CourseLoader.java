package fileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import entities.Course;

public class CourseLoader {
	
	private ObjectInputStream in;
	
	public CourseLoader(int level){
		String filePath="res/course/level"+level+".txt";
		File file = new File(filePath);
		try {
			in = new ObjectInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Serializable load(){
			try {
				return  (Serializable) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
}
