package org.elsys.serializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.elsys.serializer.json.JsonSerializer;

public abstract class AbstractSerializer implements Serializer {

	protected List<Field> getFieldsToSerialize(Class<?> cls) {
		List<Field> fields = new LinkedList<>();
		while (cls != Object.class) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}
		return fields.stream().filter(field -> {
			// Ignore annotation = field.getAnnotation(Ignore.class);
			return !field.isAnnotationPresent(Ignore.class);
		}).collect(Collectors.toList());
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

	private boolean includeNullField = false;
	
	@Override
	public boolean areIncludedNullFields() {
		// TODO Auto-generated method stub
		return includeNullField;
	}

	@Override
	public void includeNullFields(boolean includeNullFields) {
		// TODO Auto-generated method stub
		includeNullFields = includeNullField;
	}

	@Override
	public boolean isPretty() {
		return false;
	}

	@Override
	public void setPretty(boolean pretty) {
		
	}

}
