package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.MapBy;
import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer
	implements Serializer {

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
			
			if(value != null || areIncludedNullFields()) {
			
				if(field.isAnnotationPresent(MapBy.class)) {
					result.append(field.getAnnotation(MapBy.class).value()).append(" : ");
				}
				else {
					result.append(field.getName()).append(" : ");
				}
				
				result.append(serialize(value));
				result.append(", ");
			}
		}
		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
		}
		result.append("}");
		return result.toString();
	}
	
	private String serializeCollection(Collection<?> collection) {
		final StringBuffer result = new StringBuffer();
		result.append("[");
		
		for (Object obj : collection) { //Java 8 doesn't like me :(
			result.append(serialize(obj));
			result.append(',');
		}
		
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		result.append("]");
		return result.toString();
	}
}