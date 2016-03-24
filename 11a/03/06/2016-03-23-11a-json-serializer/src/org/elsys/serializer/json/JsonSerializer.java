package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.Ignore;
import org.elsys.serializer.IllegalAnnotationException;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer
	implements Serializer {

	@Override
	public String serialize(Object obj) {
		return serialize(obj, 1);
	}
	
	private String serialize(Object obj, int level){
		if (obj == null) {
			return  "\"null\"";
		}
		
		if (isDirectlySerializable(obj)) {
			return "\"" + obj.toString() + "\"";
		}
		
		if (isCollection(obj)) {
			return serializeCollection((Collection<?>) obj, level);
		}
		
		if (isArray(obj)) {
			return serializeCollection(Arrays.asList(obj), level);
		}
		
		final StringBuffer result = new StringBuffer();
		result.append("{");
		
		if(isPretty()){
			result.append(GetNewPrettyLine(level));
		}
		
		List<Field> fieldsToSerialize 
			= getFieldsToSerialize(obj.getClass());
		
		for (Field field : fieldsToSerialize) {
			field.setAccessible(true);
			
			try {
				Object value = field.get(obj);
				
				if(areIncludedNullFields() && value == null){
					continue;
				}
				
				MapBy mapAnnotation = field.getAnnotation(MapBy.class);
				
				//Set name corresponding to annotation existence
				String name = (mapAnnotation != null) 
						? mapAnnotation.value() : field.getName(); 
				
				result.append('"').append(name)
				.append("\": ");
				
				result.append(serialize(value, level+1));
				result.append(", ");
				
				if(isPretty()){
					result.append(GetNewPrettyLine(level));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		// Check also for empty objects (example: {})
		if (!fieldsToSerialize.isEmpty() && result.length() > 2) {
			int pos = result.lastIndexOf(",");
			result.delete(pos, pos+2);
			
			if(isPretty()){
				result.delete(result.length()-4, result.length());
			}
		}
		
		result.append("}");
		return result.toString();
	}
	
	private String serializeCollection(Collection<?> collection, int level) {
		final StringBuffer result = new StringBuffer();
		result.append("[");
		
		if(isPretty()){
			result.append(GetNewPrettyLine(level));
		}
		
		collection.forEach(obj -> {
			result.append(serialize(obj));
			result.append(',');
			
			if(isPretty()){
				result.append(GetNewPrettyLine(level));				
			}
		});
		
		if (!collection.isEmpty()) {
			int pos = result.lastIndexOf(",");
			result.delete(pos, pos+1);
			
			if(isPretty()){
				result.delete(result.length()-4, result.length());
			}
		}
		
		result.append("]");
		return result.toString();
	}
	
	private String GetNewPrettyLine(int level){
		StringBuffer line = new StringBuffer();
		line.append('\n');
		
		for(int i = 0; i < level; i++){
			line.append("    ");
		}
		
		return line.toString();
	}
}
