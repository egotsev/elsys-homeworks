package org.elsys.json.serializer.main;

import java.util.Arrays;

import org.elsys.json.serializer.Serializer;

import org.elsys.json.serializer.json.JsonSerializer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student student = new Student();
		student.setName("Petar");
		student.setFatherName("Spas");
		student.setTelephoneNumber("088573231");
		student.setGrades(Arrays.asList(5,6,6));
		
		Serializer serializer = new JsonSerializer();
		serializer.setPretty(true);
		serializer.includeNullFields(false);
		String result = serializer.serialize(student);
		System.out.println(result);
	}

}
