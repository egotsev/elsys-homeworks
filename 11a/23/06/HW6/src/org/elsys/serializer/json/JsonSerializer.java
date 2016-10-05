package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.MapBy;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer {

	private void addSpacesIfPretty(StringBuffer s, int n) {
		if(!pretty) return;
		for(int i = 0; i < n*4; i++) {
			s.append(" ");
		}
	}

	@Override
	public String serialize(Object obj, int depth) {
		if(obj == null) {
			return "\"null\"";
		}
		if(isDirectlySerializable(obj)) {
			return "\"" + obj.toString() + "\"";
		}
		if(isCollection(obj)) {
			return serializeCollection((Collection<?>) obj, depth + 1);
		}
		if(isArray(obj)) {
			return serializeCollection(Arrays.asList(obj), depth + 1);
		}
		final StringBuffer result = new StringBuffer();
		addSpacesIfPretty(result, depth);
		result.append("{");
		if(pretty) {
			result.append("\n");
		}
		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());
		for(Field field : fieldsToSerialize) {
			field.setAccessible(true);
			try {
				if(field.get(obj) == null && !includeNullFields) {
					continue;
				}
				String field_map;
				if(field.isAnnotationPresent(MapBy.class)) {
					field_map = field.getAnnotation(MapBy.class).value();
				} else {
					field_map = field.getName();
				}
				addSpacesIfPretty(result, depth+1);
				result.append("\"" + field_map + "\"").append(" : ");
				Object value = field.get(obj);
				result.append(serialize(value, depth+1));
				result.append(",");
				if(pretty) {
					result.append("\n");
				} else {
					result.append(" ");
				}
			} catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
		}
		if(pretty) {
			result.append("\n");
		}
		addSpacesIfPretty(result, depth);
		result.append("}");
		return result.toString();
	}

	private String serializeCollection(Collection<?> collection, int depth) {
		final StringBuffer result = new StringBuffer();
		result.append("[");
		if(pretty) {
			result.append("\n");
		}
		collection.forEach(obj -> {
			addSpacesIfPretty(result, depth);
			result.append(serialize(obj, depth + 1));
			result.append(",");
			if(pretty) {
				result.append("\n");
			}
		});
		if(!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			if(pretty) {
				result.deleteCharAt(result.length() - 1);
				result.append("\n");
			}
		}
		addSpacesIfPretty(result, depth - 1);
		result.append("]");
		return result.toString();
	}

}
