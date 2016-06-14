package org.elsys.serializer;

public class AnnotationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Parameterless Constructor
	public AnnotationException() {}

    //Constructor that accepts a message
    public AnnotationException(String message)
    {
       super(message);
    }
}

