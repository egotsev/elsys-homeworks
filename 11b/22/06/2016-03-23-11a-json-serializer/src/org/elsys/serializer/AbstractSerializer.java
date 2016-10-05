package org.elsys.serializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.elsys.exceptions.InvalidAttributeCombinationException;

public abstract class AbstractSerializer implements Serializer {
	
	private Boolean isPretty = false;
	private Boolean includeNullFields = true;

	protected String getIndentation(int level) {
		if (!isPretty()) {
			return "";
		}
		StringBuffer builder = new StringBuffer();
		for (int i = 0; i < level; i++) {
			builder.append("    "); // 4 spaces
		}
		
		return builder.toString();
	}
	
	protected List<Field> getFieldsToSerialize(Class<?> cls) throws InvalidAttributeCombinationException {
		List<Field> fields = new LinkedList<>();
		while (cls != Object.class) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}
		return fields.stream().filter(field -> {
			if (field.isAnnotationPresent(Ignore.class) &&
				field.isAnnotationPresent(MapBy.class)) {
				throw new InvalidAttributeCombinationException();
			}
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
		return value instanceof Boolean || 
			   value instanceof Number || 
			   value instanceof Character || 
			   value instanceof String;
	}

	@Override
	public boolean areIncludedNullFields() {
		return includeNullFields;
	}

	@Override
	public void includeNullFields(boolean value) {
		includeNullFields = value;
	}

	@Override
	public boolean isPretty() {
		return isPretty;
	}

	@Override
	public void setPretty(boolean value) {
		isPretty = value;
	}

}
