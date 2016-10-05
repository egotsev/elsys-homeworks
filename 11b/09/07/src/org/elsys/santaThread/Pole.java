package org.elsys.santaThread;

public abstract class Pole {

	private final SantaClausThreads constructor;

	private final int Id;

	public Pole(int pId, SantaClausThreads constructor) {
		this.constructor = constructor;
		this.Id = pId;
	}

	public SantaClausThreads getConstructor() {
		return constructor;
	}

	public int getId() {
		return Id;
	}

	@Override
	public String toString() {
		return getFullName() + "-" + Id;
	}

	public abstract String getFullName();

}
