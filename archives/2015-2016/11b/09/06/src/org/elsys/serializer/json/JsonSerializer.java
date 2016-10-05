package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer {
	String four_spaces = "    ";

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
		setPretty(true);
		if (isPretty() == true) {
			result.append('{').append('\n');
		} else {
			result.append("{");
		}
		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());
		includeNullFields(false);
		for (Field field : fieldsToSerialize) {
			if (areIncludedNullFields() == true) {
				field.setAccessible(true);
				MapBy mapby = field.getAnnotation(MapBy.class);
				if (isPretty() == true) {
					if (mapby != null) {
						result.append(spaces(4)).append(mapby.value()).append(" : ");
					} else {
						result.append(spaces(4)).append(field.getName()).append(" : ");
					}
					try {
						Object value = field.get(obj);
						result.append(serialize(value));
						result.append(",").append("\n");
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					if (mapby != null) {
						result.append(mapby.value()).append(" : ");
					} else {
						result.append(field.getName()).append(" : ");
					}
					try {
						Object value = field.get(obj);
						result.append(serialize(value));
						result.append(",");
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			} else if (areIncludedNullFields == false) {
				field.setAccessible(true);
				try {
					if (field.get(obj) != null && isPretty() == true) {
						MapBy mapby = field.getAnnotation(MapBy.class);
						if (mapby != null) {
							result.append(spaces(4)).append(mapby.value()).append(" : ");
						} else {
							result.append(spaces(4)).append(field.getName()).append(" : ");
						}
						try {
							Object value = field.get(obj);
							result.append(serialize(value));
							result.append(",").append("\n");
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					} else if (field.get(obj) != null && isPretty() == false) {
						MapBy mapby = field.getAnnotation(MapBy.class);
						if (mapby != null) {
							result.append(mapby.value()).append(" : ");
						} else {
							result.append(field.getName()).append(" : ");
						}
						try {
							Object value = field.get(obj);
							result.append(serialize(value));
							result.append(",");
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
		}
		if (isPretty() == true) {
			result.append("\n").append("}");
		} else {
			result.append("}");
		}
		return result.toString();
	}

	private String serializeCollection(Collection<?> collection) {
		final StringBuffer result = new StringBuffer();
		if (isPretty() == true) {
			result.append("[").append("\n");
			collection.forEach(obj -> {
				result.append(spaces(8)).append(serialize(obj));
				result.append(',').append("\n");
			});
			if (!collection.isEmpty()) {
				result.deleteCharAt(result.length() - 1);
			}
			result.append("\n").append(spaces(4)).append("]");
		} else {
			result.append("[");
			collection.forEach(obj -> {
				result.append(serialize(obj));
				result.append(',');
			});
			if (!collection.isEmpty()) {
				result.deleteCharAt(result.length() - 1);
			}
			result.append("]");
		}
		return result.toString();
	}
	
	private String spaces(int space){
		String result = "";
		int i = 0;
		while(i<space){
			i++;
			result += " ";
		}
		return result;
	}
}
