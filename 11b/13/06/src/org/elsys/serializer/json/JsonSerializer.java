package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer {

	final int SPACES_COUNT = 4;
	
	// Implemented like that in order to be backwards compatible with the interface
	@Override
	public String serialize(Object obj) {
		return serialize(obj, 0);
	}
	
	public String serialize(Object obj, int currentSpacing) {
		if (obj == null) {
			return "\"null\"";
		}
		
		if (isDirectlySerializable(obj)) {
			return "\"" + obj.toString() + "\"";
		}
		
		if (isCollection(obj)) {
			return serializeCollection((Collection<?>) obj, currentSpacing);
		}
		
		if (isArray(obj)) {
			return serializeCollection(Arrays.asList(obj), currentSpacing);
		}
		
		final StringBuffer result = new StringBuffer();
		result.append("{");
		
		if (isPretty()) {
			result.append(System.lineSeparator());
		}
		
		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());
		for (Field field : fieldsToSerialize) {
			Object value = null;
			field.setAccessible(true);
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
			if (!areIncludedNullFields() && value == null) {
				continue;
			} else {
				MapBy mapByAnnotation = field.getAnnotation(MapBy.class);
				if (isPretty()) {
					result.append(spacePad(currentSpacing + SPACES_COUNT));
				}
				
				if (mapByAnnotation != null) {
					result.append(mapByAnnotation.value()).append(" : ");
				} else {
					result.append(field.getName()).append(" : ");
				}
				
				if (isPretty()) {
					result.append(serialize(value, currentSpacing + SPACES_COUNT));
				} else {
					result.append(serialize(value));
				}
				
				result.append(", ");
				
				if (isPretty()) {
					result.append(System.lineSeparator());
				}
			}
		}
		
		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
			
			if (isPretty()) {
				result.deleteCharAt(result.length() - 1);
			}
		}
		
		if (isPretty()) {
			result.append(System.lineSeparator());
			result.append(spacePad(currentSpacing));
		}
		
		result.append("}");
		return result.toString();
	}
	
	private String serializeCollection(Collection<?> collection, int currentSpacing) {
		final StringBuffer result = new StringBuffer();
		result.append("[");
		
		if (isPretty()) {
			result.append(System.lineSeparator());
		}
		
		collection.forEach(obj -> {
			if (isPretty()) {
				result.append(spacePad(currentSpacing + SPACES_COUNT));
			}

			result.append(serialize(obj, currentSpacing + SPACES_COUNT));
			result.append(", ");
			
			if (isPretty()) {
				result.append(System.lineSeparator());
			}
		});
		
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
			
			if (isPretty()) {
				result.deleteCharAt(result.length() - 1);
			}
		}
		
		if (isPretty()) {
			result.append(System.lineSeparator());
			result.append(spacePad(currentSpacing));
		}
		
		result.append("]");
		return result.toString();
	}

	private String spacePad(int n) {
		if (n == 0) {
			return "";
		}
		
	    return String.format("%" + n + "s", " ");
	}
}
