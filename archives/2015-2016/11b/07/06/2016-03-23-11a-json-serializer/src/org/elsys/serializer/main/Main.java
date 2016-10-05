package org.elsys.serializer.main;

import java.util.Arrays;

import org.elsys.serializer.Serializer;
import org.elsys.serializer.json.JsonSerializer;

public class Main {

	public static void main(String[] args) {
		Student student = new Student();
		student.setName("Paisii");
		student.setLastName("Hilendarski");
		student.setFatherName("Ivan");
		student.setGrades(Arrays.asList(4, 4, 3));

		Serializer serializer = new JsonSerializer();
		serializer.includeNullFields(true);
		serializer.setPretty(false);
		String result = serializer.serialize(student);
		System.out.println(result);
	}

}
