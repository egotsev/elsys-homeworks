package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer {

	private int level = 0;

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
		level++;
		result.append("{");
		if (isPretty())
			result.append("\n");
		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());
		for (Field field : fieldsToSerialize) {
			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				if (!areIncludedNullFields() && value == null) {
				} else {
					if (isPretty()) {
						result.append(setSpaces(level));
					}
					if (field.isAnnotationPresent(MapBy.class))
						result.append("\"" + field.getDeclaredAnnotation(MapBy.class).value() + "\"").append(" : ");
					else {
						result.append("\"" + field.getName() + "\"").append(" : ");
					}
					result.append(serialize(value));
					result.append(", ");
					if (isPretty())
						result.append("\n");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (!fieldsToSerialize.isEmpty()) {
			if (!isPretty()) {
				result.deleteCharAt(result.length() - 1);
				result.deleteCharAt(result.length() - 1);
			} else {
				result.deleteCharAt(result.length() - 3);
			}
		}
		if (isPretty())
			result.append(setSpaces(level - 1));
		result.append("}");
		level--;
		return result.toString();
	}

	private String serializeCollection(Collection<?> collection) {
		level++;
		final StringBuffer result = new StringBuffer();
		result.append("[");
		if (isPretty())
			result.append("\n");
		collection.forEach(obj -> {
			if (isPretty()) {
				result.append(setSpaces(level)).append(serialize(obj));
			}
			else
				result.append(serialize(obj));
			result.append(',');
			if (isPretty())
				result.append("\n");
		});
		if (!collection.isEmpty()) {
			if (isPretty()) {
				result.deleteCharAt(result.length() - 2);
				result.deleteCharAt(result.length() - 1);
			} else
				result.deleteCharAt(result.length() - 1);

		}
		if (isPretty())
			result.append("\n").append(setSpaces(level - 1));
		result.append("]");
		level--;
		return result.toString();
	}

	private String setSpaces(int tabs) {
		String result = "";
		for (int i = 0; i < tabs; i++) {
			result += "    ";
		}
		return result;
	}

}
