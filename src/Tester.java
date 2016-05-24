import java.io.File;

import engineTester.SampleCourse;
import fileHandler.CourseSaver;
import renderEngine.Loader;

public class Tester {
	public static void main(String[] args){
		File file = new File("res/Course/level0.txt");
		System.out.println(file.exists());
	}
}
