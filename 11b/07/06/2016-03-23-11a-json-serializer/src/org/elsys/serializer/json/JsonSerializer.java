package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer {

	final private String SPACES = "    ";
	private int inward = 0;

	private String addPrettyLine() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");

		for (int i = 0; i < inward; i++) {
			buffer.append(SPACES);
		}

		return buffer.toString();
	}

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
			inward++;
			result.append(addPrettyLine());
		}

		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());

		for (Field field : fieldsToSerialize) {
			MapBy mapBy = field.getAnnotation(MapBy.class);
			if (mapBy == null) {
				result.append(field.getName());
			} else {
				result.append(mapBy.value());
			}
			result.append(" : ");
			field.setAccessible(true);

			try {
				Object value = field.get(obj);
				if ((value == null && areIncludedNullFields()) || (value != null)) {
					result.append(serialize(value));
				}
				result.append(", ");

				if (isPretty()) {
					result.append(addPrettyLine());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.lastIndexOf(","));
			if (isPretty()) {
				result.delete(result.length() - 3, result.length());
			}
			result.delete(result.length() - 1, result.length());
		}
		result.append("}");

		if (isPretty()) {
			inward--;
		}

		return result.toString();
	}

	private String serializeCollection(Collection<?> collection) {
		final StringBuffer result = new StringBuffer();
		result.append("[");

		if (isPretty()) {
			inward++;
			result.append(addPrettyLine());
		}

		collection.forEach(obj -> {
			result.append(serialize(obj));
			result.append(',');
			if (isPretty()) {
				result.append(addPrettyLine());
			}
		});
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.lastIndexOf(","));
			if (isPretty()) {
				result.delete(result.length() - 3, result.length());
			}
			result.delete(result.length() - 1, result.length());
		}
		result.append("]");

		if (isPretty()) {
			inward--;
		}

		return result.toString();
	}

}
