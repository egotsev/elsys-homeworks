package org.elsys.serializer;

public class IllegalAnnotationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2790959604087535147L;
	
	public IllegalAnnotationException(){
		super("Annotation illegal!");
	}
	
	public IllegalAnnotationException(String message){
		super(message);
	}
}
