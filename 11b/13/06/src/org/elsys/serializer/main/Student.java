package org.elsys.serializer.main;

import java.util.List;

import org.elsys.serializer.MapBy;

public class Student {
	
	private String name;
	
	@MapBy("last_name")
	private String lastName;
	
	private List<Integer> grades;
	private School school;
	private List<Student> friends;
	
	@MapBy("father_name")
	private String fatherName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public School getSchool() {
		return this.school;
	}
	
	public void setSchool(School school) {
		this.school = school;
	}
	
	public List<Integer> getGrades() {
		return grades;
	}
	
	public void setGrades(List<Integer> grades) {
		this.grades = grades;
	}
	
	public List<Student> getFriends() {
		return friends;
	}
	
	public void setFriends(List<Student> friends) {
		this.friends = friends;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
}
