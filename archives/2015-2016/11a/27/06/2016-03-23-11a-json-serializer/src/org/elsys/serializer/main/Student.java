package org.elsys.serializer.main;

import java.util.List;

import org.elsys.serializer.Ignore;
import org.elsys.serializer.MapBy;

public class Student {
	@MapBy("first_name")
	private String firstName;
	
	@MapBy("last_name")
	private String lastName;
	
	private List<Integer> grades;
	
	@MapBy("father_name")
	private String fatherName;
	
	@MapBy("mother_name")
	private String motherName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Integer> getGrades() {
		return grades;
	}

	public void setGrades(List<Integer> grades) {
		this.grades = grades;
	}

	public String getFatherName() {
		return fatherName;
	}
	
	public String getMotherName() {
		return motherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	
}
