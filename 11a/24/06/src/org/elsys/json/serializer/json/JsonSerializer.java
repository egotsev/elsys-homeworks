package org.elsys.json.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.json.serializer.AbstractSerializer;
import org.elsys.json.serializer.MapBy;
import org.elsys.json.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer {
	
	private int prettinessLevel = 1;

	@Override
	public String serialize(Object obj) {
		if (obj == null) {
			return "\"null\"";
		}
		if (isDirectlySerializable(obj)) {
			return "\"" + obj.toString() + "\"";
		}
		if (isCollection(obj)) {
			prettinessLevel++;
			return serializeCollection((Collection<?>) obj);
		}
		if (isArray(obj)) {
			prettinessLevel++;
			return serializeCollection(Arrays.asList(obj));
		}
		final StringBuffer result = new StringBuffer();
		result.append("{");
		if(isPretty()) {
			result.append(makeItPretty());
		}
		List<Field> fieldsToSerialize 
			= getFieldsToSerialize(obj.getClass());
		for (Field field : fieldsToSerialize) {
			
			field.setAccessible(true);
			
			Object value = null;
			
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
			if(!areIncludedNullFields() && value == null) {
				continue;
			}
			
			if(field.isAnnotationPresent(MapBy.class)) {
				result.append(field.getAnnotation(MapBy.class).value())
				.append(" : ");
			}
			else {
				result.append(field.getName())
				.append(" : ");
			}
			
			result.append(serialize(value));
			result.append(", ");
			if(isPretty()) {
				result.append(makeItPretty());
			}
		}
		if (!fieldsToSerialize.isEmpty() && result.length() > 2) {
			result.deleteCharAt(result.lastIndexOf(","));
			if(isPretty()) {
				result.delete(result.length() - 4, result.length());
			}
			else {
				result.deleteCharAt(result.lastIndexOf(" "));
			}
		}
		result.append("}");
		return result.toString();
	}
	
	private String serializeCollection(Collection<?> collection) {
		final StringBuffer result = new StringBuffer();
		result.append("[");
		if(isPretty()) {
			result.append(makeItPretty());
		}
		collection.forEach(obj -> {
			result.append(serialize(obj));
			result.append(',');
			if(isPretty()) {
				result.append(makeItPretty());
			}
		});
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.lastIndexOf(","));
			if(isPretty()) {
				result.delete(result.length()-4, result.length());
				
			}
		}
		result.append("]");
		prettinessLevel--;
		return result.toString();
	}
	
	private String makeItPretty() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		for(int i = 0; i < prettinessLevel; i++) {
			buffer.append("    ");
		}
		
		return buffer.toString();
	}

}
