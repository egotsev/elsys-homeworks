package org.elsys.json.serializer;

public class AnnotationConflictException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9085315741090862675L;

	public AnnotationConflictException() {
		System.out.println("A field cannot have two conflicting annotations!");
	}

	public AnnotationConflictException(String message) {
		System.out.println(message);
	}

}
