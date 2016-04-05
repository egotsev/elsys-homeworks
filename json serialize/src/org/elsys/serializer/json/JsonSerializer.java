package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer
	implements Serializer {

	@Override
	public String serialize(Object obj) {
		if (obj == null) {
		//	if(areIncludedNullFields()){
				return "\"null\"";
			//}else {
			//	return "";
			//}
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
		FormatPretty(result);
		
		List<Field> fieldsToSerialize 
			= getFieldsToSerialize(obj.getClass());
		
		for (Field field : fieldsToSerialize) 
		{
			field.setAccessible(true);
			//MapBy map = field.getAnnotation(MapBy.class);	
			try {
				Object value = field.get(obj);
				
				if (value == null && !areIncludedNullFields()) 
					continue;
				
				result.append(field.getName()).append(" : ");
				
				result.append(serialize(value));
				result.append(",");
				
				FormatPretty(result);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
		}
		
		
		//result.
		if(isPretty())
			result.delete(result.length() - 2,result.length());
		result.append("}");
		return result.toString();
	}
	
	private String serializeCollection(Collection<?> collection) {
		final StringBuffer result = new StringBuffer();
		result.append("[");
		spaces += 4;
		FormatPretty(result);
		
		collection.forEach(obj -> {
			result.append(serialize(obj));
			result.append(',');
			FormatPretty(result);
		});
		
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		
		spaces -= 4;
		if (isPretty()) {
			result.delete(result.length() - 4,result.length() - 1);
		}
		result.append("]");
		return result.toString();
	}
	private void FormatPretty(StringBuffer result){
		if (isPretty()) {
			result.append("\n");
			result.append(SpacesToStrin());
		}
	}
	private String SpacesToStrin(){
		StringBuffer str = new StringBuffer();
			for (int i = 0; i < spaces; i++) {
				str.append(" ");
			}
		return str.toString();
	}
}
