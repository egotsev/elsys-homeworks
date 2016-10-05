package org.elsys.santaclaus;

public abstract class FactoryEntity {

	private final int entityId;
	
	private final ToyFactory toyFactory;

	public FactoryEntity(int entityId, ToyFactory toyFactory) {
		this.entityId = entityId;
		this.toyFactory = toyFactory;
	}

	public int getEntityId() {
		return entityId;
	}
	
	public ToyFactory getFactory() {
		return toyFactory;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%d)", getEntityName(), entityId);
	}

	public abstract String getEntityName();
}