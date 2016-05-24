package fileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import entities.Course;
import renderEngine.DisplayManager;

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
	public Course load(){

			try {
				return (Course) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
}
