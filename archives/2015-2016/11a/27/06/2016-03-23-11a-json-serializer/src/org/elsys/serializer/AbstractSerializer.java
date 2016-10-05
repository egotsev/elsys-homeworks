package org.elsys.serializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSerializer implements Serializer {

	private boolean nullFields = true;
	protected List<Field> getFieldsToSerialize(Class<?> cls) {
		List<Field> fields = new LinkedList<>();
		while (cls != Object.class) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}
		List<Field> notIgnored = new LinkedList<>();  
	
		for(Field f : fields) {
			if (f.isAnnotationPresent(Ignore.class) == true && f.isAnnotationPresent(MapBy.class) == true) {
				throw new RuntimeException("Can't have those two conflicting annotations");
			}
		}
		
		for (Field f : fields) {
			if(!f.isAnnotationPresent(Ignore.class)){
				notIgnored.add(f);
			}
		}
		
		return notIgnored;
	}

	protected boolean isArray(Object value) {
		return value.getClass().isArray();
	}

	protected boolean isCollection(Object value) {
		return value instanceof Collection;
	}

	protected boolean isDirectlySerializable(Object value) {
		return value instanceof Boolean || value instanceof Number || value instanceof Character
				|| value instanceof String;
	}

	@Override
	public boolean areIncludedNullFields() {
		return nullFields;
	}

	@Override
	public void includeNullFields(boolean includeNullFields) {
		this.nullFields = includeNullFields;
	}

	@Override
	public boolean isPretty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPretty(boolean pretty) {
		// TODO Auto-generated method stub

	}

}
