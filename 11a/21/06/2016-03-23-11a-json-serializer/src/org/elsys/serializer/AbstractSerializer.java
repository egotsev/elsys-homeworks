package org.elsys.serializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSerializer implements Serializer {
	private boolean pretty;
	private boolean includingNullFields;

	public AbstractSerializer() {
		pretty = false;
		includingNullFields = false;
	}

	protected List<Field> getFieldsToSerialize(Class<?> cls) {
		List<Field> fields = new LinkedList<>();
		while (cls != Object.class) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}

		checkForAnnotationConflicts(fields);
		return fields.stream().filter(field -> {
			return !field.isAnnotationPresent(Ignore.class);
		}).collect(Collectors.toList());
	}
	
	private void checkForAnnotationConflicts(List<Field> fields) {
		fields.forEach(field -> {
			// If has annotation MapBy
			if (field.getAnnotation(MapBy.class) != null) {
				// If has annotation Ignore
				if (field.getAnnotation(Ignore.class) != null) {
					throw new IllegalStateException();
				}
			}
		});
	}

	protected boolean isArray(Object value) {
		return value.getClass().isArray();
	}

	protected boolean isCollection(Object value) {
		return value instanceof Collection;
	}

	protected boolean isDirectlySerializable(Object value) {
		return value instanceof Boolean || value instanceof Number
				|| value instanceof Character || value instanceof String;
	}

	@Override
	public boolean areIncludedNullFields() {
		return includingNullFields;
	}

	@Override
	public void includeNullFields(boolean includeNullFields) {
		includingNullFields = includeNullFields;
	}

	@Override
	public boolean isPretty() {
		return pretty;
	}

	@Override
	public void setPretty(boolean value) {
		pretty = value;
	}

}
