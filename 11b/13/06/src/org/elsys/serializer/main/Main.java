package org.elsys.serializer.main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.elsys.serializer.Serializer;
import org.elsys.serializer.json.JsonSerializer;

public class Main {
	public static void main(String[] args) {
		// To test indent for single nested object
		School tues = new School();
		tues.setName("TUES");
		tues.setStudentsCount(500);
		
		Student paisii = new Student();
		paisii.setName("Paisii");
		paisii.setLastName("Hilendarski");
		paisii.setGrades(Arrays.asList(2, 5, 6));
		paisii.setSchool(tues);
		
		Student pesho = new Student();
		pesho.setName("Pesho");
		pesho.setLastName("Petrov");
		pesho.setGrades(Arrays.asList(2, 2));
		
		Student gosho = new Student();
		gosho.setName("Gosho");
		gosho.setLastName("Goshev");
		gosho.setGrades(Arrays.asList(6, 6));
		
		// To test indent for collection of not only primitive types
		List<Student> paisiiFriends = new LinkedList<Student>();
		paisiiFriends.add(pesho);
		paisiiFriends.add(gosho);
		
		paisii.setFriends(paisiiFriends);
		
		Serializer serializer = new JsonSerializer();
		serializer.setPretty(true);
		serializer.includeNullFields(false);
		String result = serializer.serialize(paisii);
		System.out.println(result);
	}
}
