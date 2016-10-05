package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.Ignore;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer
	implements Serializer {
	int level = 0;
	
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
		result.append("{" + newlineIfPretty());
		level++;
		List<Field> fieldsToSerialize 
			= getFieldsToSerialize(obj.getClass());
		for (Field field : fieldsToSerialize) {
			try {
				field.setAccessible(true);
				Object value = field.get(obj);
				if (value == null && !areIncludedNullFields()) {
					continue;
				}
				String name;
				if (field.isAnnotationPresent(MapBy.class)) {
					MapBy mapBy = field.getAnnotation(MapBy.class);
					name = mapBy.value();
				} else {
					name = field.getName();
				}
				result.append(getIndentation(level) + name)
					.append(" : ");
				result.append(serialize(value));
				result.append("," + newlineIfPretty());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
		}
		result.append(newlineIfPretty() + "}");
		level--;
		return result.toString();
	}
	
	private String newlineIfPretty() {
		return (isPretty() ? "\n" : "");
	}
	
	private String serializeCollection(Collection<?> collection) {
		final StringBuffer result = new StringBuffer();
		result.append("[" + newlineIfPretty());
		collection.forEach(obj -> {
			result.append(getIndentation(level + 1) + serialize(obj));
			result.append(',' + newlineIfPretty());
		});
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 2);
		}
		result.append(getIndentation(level) + "]");
		return result.toString();
	}

}
