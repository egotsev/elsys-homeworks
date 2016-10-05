package org.elsys.serializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSerializer implements Serializer {

	protected boolean nullFields;
	protected boolean pretty;
	
	protected List<Field> getFieldsToSerialize(Class<?> cls) {
		List<Field> fields = new LinkedList<>();
		while (cls != Object.class) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}
		return fields.stream().filter(field -> {
				if(field.isAnnotationPresent(Ignore.class) && field.isAnnotationPresent(MapBy.class))
				{
					throw new AnnotationException("Too much arguments");
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
		return value instanceof Boolean || value instanceof Number || value instanceof Character
				|| value instanceof String;
	}

	@Override
	public boolean areIncludedNullFields() {
		return nullFields;
	}

	@Override
	public void includeNullFields(boolean includeNullFields) {
		nullFields = includeNullFields; 

	}

	@Override
	public boolean isPretty() {
		return pretty;
	}

	@Override
	public void setPretty(boolean pretty) {
		this.pretty = pretty;
	}

}
