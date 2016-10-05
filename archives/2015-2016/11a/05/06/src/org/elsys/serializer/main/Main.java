package org.elsys.serializer.main;

import java.util.Arrays;

import org.elsys.serializer.Serializer;
import org.elsys.serializer.json.JsonSerializer;

public class Main {

	public static void main(String[] args) {
		Student student = new Student();
		student.setName("Paisii");
		student.setFatherName("Ivan");
		student.setGrades(Arrays.asList(2, 5, 6));
		
		Student student2 = new Student();
		student2.setName("Velioo");
		student2.setFatherName("Novko");
		student2.setGrades(Arrays.asList(6, 6, 5));
		
		student.setTest(student2);	
		
		Student student3 = new Student();
		student3.setName("Luke");
		student3.setFatherName("Vader");

		student2.setTest(student3);
		
		Serializer serializer = new JsonSerializer();
		serializer.setPretty(true);
		serializer.includeNullFields(false);
		String result = serializer.serialize(student);
		
		System.out.println(result);

	}

}
