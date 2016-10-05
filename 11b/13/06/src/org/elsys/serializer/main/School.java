package org.elsys.serializer.main;

public class School {
	private String name;
	private int studentsCount;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setStudentsCount(int count) {
		studentsCount = count;
	}
	
	public int getStudentsCount() {
		return studentsCount;
	}
}