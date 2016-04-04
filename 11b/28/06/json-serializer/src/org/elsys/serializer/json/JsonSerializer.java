package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer
	implements Serializer {
	
	final String TAB = "	";
	static Integer counter = 0;

	@Override
	public String serialize(Object obj) {
		
		if (obj == null) {
			return "\"null\"";
		}
		if (isDirectlySerializable(obj)) {
			return "\"" + obj.toString() + "\"";
		}
		if (isCollection(obj)) {
			return serializeCollection((Collection<?>) obj);
		}
		if (isArray(obj)) {
			return serializeCollection(Arrays.asList(obj));
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
				value = field.get(obj);;
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
			if (areIncludedNullFields() == false && value == null) {
				continue;
			} else {
				
				if(isPretty()) {
					result.append(TAB);
				}
				
				if(field.getAnnotation(MapBy.class) == null) {
					result.append(field.getName()).append(" : ");
				} else {
					result.append(field.getAnnotation(MapBy.class).value()).append(" : ");
				}
				
				result.append(serialize(value));
				
				result.append(", ");
				
				if(isPretty()) {
					result.append(System.lineSeparator());
				}
				
			}
			
		}
		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
			
			if(isPretty()) {
				result.deleteCharAt(result.length() - 2);
			}
			
		}
		
		if (isPretty()) {
			result.append(System.lineSeparator());
		}
		
		result.append("}");
		return result.toString();
	}
	
	private String serializeCollection(Collection<?> collection) {
		final StringBuffer result = new StringBuffer();
		result.append("[");
		
		if (isPretty()) {
			result.append(System.lineSeparator());
		}
		
		collection.forEach(obj -> {
			
			counter++;
			
			if (isPretty()) {
				result.append(TAB + TAB);
			}
			result.append(serialize(obj));
			
			result.append(',');
			if (isPretty() && counter != collection.size()) {
				result.append(System.lineSeparator());
			}
		});
		
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		
		if (isPretty()) {
			result.append(System.lineSeparator()).append(TAB);
		}
		result.append("]");
		
		return result.toString();
	}

}
