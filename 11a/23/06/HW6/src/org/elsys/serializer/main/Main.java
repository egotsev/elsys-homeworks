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
		
		Serializer serializer = new JsonSerializer();
		serializer.setPretty(true);
		serializer.includeNullFields(false);
		String result = serializer.serialize(student, 0);
		System.out.println(result);
	}
}
