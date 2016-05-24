package fileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import entities.Course;

public class CourseSaver {
	private ObjectOutputStream out;
	public CourseSaver(int level){
		String filePath="res/Course/level"+level+".txt";
		File file = new File(filePath);

		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void salve(Course course){
		try {
			out.writeObject(course);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
