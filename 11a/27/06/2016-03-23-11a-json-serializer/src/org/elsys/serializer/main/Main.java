package org.elsys.serializer.main;

import java.util.Arrays;

import org.elsys.serializer.Serializer;
import org.elsys.serializer.json.JsonSerializer;

public class Main {

	public static void main(String[] args) {
		Student student = new Student();
		student.setFirstName("NeverLucky");
		student.setLastName("MLG");
		student.setGrades(Arrays.asList(6, 6, 6));
		student.setFatherName("Pepe");
		student.setMotherName("Pepelina");
		
		Serializer serializer = new JsonSerializer();
		serializer.setPretty(false);
		serializer.includeNullFields(false);
		String result = serializer.serialize(student);
		
		System.out.println(result);
	}

}
