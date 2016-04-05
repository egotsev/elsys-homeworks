package org.elsys.serializer.json;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.elsys.serializer.Serializer;
import org.elsys.serializer.main.Student;
import org.junit.Before;
import org.junit.Test;

public class JsonSerializedTestCase {
	Student student;
	Serializer serializer;

	@Before
	public void setUp() throws Exception {
		student = new Student();
		student.setName("Paisii");
		student.setFatherName("Ivan");
		student.setGrades(Arrays.asList(2, 5, 6));

		serializer = new JsonSerializer();
	}

	@Test
	public void testBasicSerialize() {
		String result = serializer.serialize(student);
		String expectedResult = "{name : \"Paisii\", grades : [\"2\",\"5\",\"6\"], father_name : \"Ivan\"}";
		assertEquals(expectedResult, result);
	}

	@Test
	public void testIncludingNullFields() {
		serializer.includeNullFields(true);

		String result = serializer.serialize(student);
		String expectedResult = "{name : \"Paisii\", last_name : \"null\", grades : [\"2\",\"5\",\"6\"], father_name : \"Ivan\"}";
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testPretty() {
		serializer.setPretty(true);

		String result = serializer.serialize(student);
		
		StringBuilder expectedResult = new StringBuilder();
		expectedResult.append("{\n");
		expectedResult.append("    name : \"Paisii\", \n");
		expectedResult.append("    grades : [\n");
		expectedResult.append("        \"2\",\n");
		expectedResult.append("        \"5\",\n");
		expectedResult.append("        \"6\",\n");
		expectedResult.append("       ], \n");
		expectedResult.append("    father_name : \"Ivan\",\n");
		expectedResult.append("}");
		
		assertEquals(expectedResult.toString(), result);
	}

}
