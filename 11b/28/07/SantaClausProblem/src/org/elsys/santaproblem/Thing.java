package org.elsys.santaproblem;

public abstract class Thing {

	private final Main constructor;
	private final int id;
	
	public Thing(int id, Main constructor) {
		this.constructor = constructor;
		this.id = id;
	}
	
	public Main getConstructor() {
		return constructor;
	}

}
