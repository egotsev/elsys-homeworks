package org.elsys.json.serializer;

public interface Serializer {

	String serialize(Object obj);

	void setPretty(boolean pretty);
	
	boolean isPretty();
	
	void includeNullFields(boolean includeNullFields);
	
	boolean areIncludedNullFields();
	
}
