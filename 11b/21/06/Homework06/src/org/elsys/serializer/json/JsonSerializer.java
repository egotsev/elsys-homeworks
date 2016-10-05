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

	private String spaces = "    ";
	
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
		if(isPretty()){
			result.append("\n");
		}
		List<Field> fieldsToSerialize 
			= getFieldsToSerialize(obj.getClass());
		for (Field field : fieldsToSerialize) {
			field.setAccessible(true);
			try {				
				Object value = field.get(obj);
				MapBy map = field.getAnnotation(MapBy.class);
				String valueString = serialize(value);
				if((valueString == "\"null\"")){
				if(areIncludedNullFields()){
					if(map != null){
						if(isPretty()){
							result.append(spaces).append(map.value()).append(" : ");
						} else {
							result.append(map.value())
							.append(" : ");
						}
					} else {
						if(isPretty()){
							result.append(spaces).append(field.getName()).append(" : ");
						} else {
							result.append(field.getName())
							.append(" : ");
						}
					}
					result.append(valueString);
					if(isPretty()){
						result.append(", \n");
					} else {
						result.append(", ");
					}
					} 
				} else {
					if(map != null){
						if(isPretty()){
							result.append(spaces).append(map.value()).append(" : ");
						} else {
							result.append(map.value())
							.append(" : ");
						}
						} else {
							if(isPretty()){
								result.append(spaces).append(field.getName()).append(" : ");
							} else {
								result.append(field.getName())
								.append(" : ");
							}
						}
					result.append(valueString);
					if(isPretty()){
						result.append(", \n");
					} else {
						result.append(", ");
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
		}
		if(isPretty()){
			result.append("\n}");
		}else {
			result.append("}");
		}
		return result.toString();
	}
	
	private String serializeCollection(Collection<?> collection) {
		final StringBuffer result = new StringBuffer();
		if(isPretty()){
			result.append("[\n");
		} else {
			result.append("[");
		}
		
		collection.forEach(obj -> {
			if(isPretty()){
				result.append(spaces).append(spaces).append(serialize(obj));
				result.append(",\n");
			} else {
				result.append(serialize(obj));
				result.append(',');
			}
		});
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		if(isPretty()) {
			result.append("\n").append(spaces).append("]");
		} else {
			result.append("]");
		}		
		return result.toString();
	}

}
