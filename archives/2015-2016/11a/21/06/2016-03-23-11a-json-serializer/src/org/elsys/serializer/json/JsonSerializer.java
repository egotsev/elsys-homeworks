package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer {
	@Override
	public String serialize(Object obj) {
		return serialize(obj, 1);
	}
	
	public String serialize(Object obj, int depth) {
		if (obj == null) {
			return "\"null\"";
		}
		if (isDirectlySerializable(obj)) {
			return "\"" + obj.toString() + "\"";
		}
		if (isCollection(obj)) {
			return serializeCollection((Collection<?>) obj, depth);
		}
		if (isArray(obj)) {
			return serializeCollection(Arrays.asList(obj), depth);
		}
		final StringBuffer result = new StringBuffer();
		result.append("{");
		
		if(isPretty()){
			result.append("\n");
		}
		
		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());
		
		for (Field field : fieldsToSerialize) {
			field.setAccessible(true);

			try {
				Object value = field.get(obj);
				String serializeResult = serialize(value, depth + 1);

				//Skip field if null fields aren't included and it is null
				if (serializeResult.compareTo("\"null\"") == 0) {
					if (!areIncludedNullFields()) {
						continue;
					}
				}
				
				if(isPretty()) {
					result.append(getIndentation(depth));
				}
				
				result.append(getFieldSerializeName(field)).append(" : ")
						.append(serializeResult);
				result.append(", ");
				
				if(isPretty()){
					result.append("\n");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (!fieldsToSerialize.isEmpty()) {
			//Delete comma, space and new line at end of string
			for(int i = 0; i < 2; i++) {
				result.deleteCharAt(result.length() - 1);
			}
		}
		
		if(isPretty()) {
			result.append("\n");
		}
		
		result.append("}");
		return result.toString();
	}
	
	private String getIndentation(int depth) {
		String result = "";
		
		for(int i = 0; i < depth; i++) {
			result += "    ";
		}
		
		return result;
	}
	
	private String getFieldSerializeName(Field field) {
		MapBy mapByAnnotation = field.getAnnotation(MapBy.class);

		// If has annotation MapBy
		if (mapByAnnotation == null) {
			return field.getName();
		}
		
		return mapByAnnotation.value();
	}

	private String serializeCollection(Collection<?> collection, int depth) {
		final StringBuffer result = new StringBuffer();
		result.append("[");

		if(isPretty()) {
			result.append("\n");
			result.append(getIndentation(depth));
		}
		
		collection.forEach(obj -> {
			result.append(serialize(obj));
			result.append(',');

			if(isPretty()) {
				result.append("\n");
				result.append(getIndentation(depth));
			}
		});
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		result.append("]");
		return result.toString();
	}
}
