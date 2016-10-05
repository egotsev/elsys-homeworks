package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer {

    @Override
    public String serialize(Object obj) {
        return serialize(obj, 2);
    }

	public String serialize(Object obj, int indentation) {
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
			return serializeCollection(Collections.singletonList(obj));
		}

		final StringBuffer result = new StringBuffer();
		result.append("{");
        newlineIfPretty(result);

		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());

		for (Field field : fieldsToSerialize) {
			field.setAccessible(true);
			try {
				Object value = field.get(obj);
                if (this.areIncludedNullFields() || value != null) {
                    indent(result, indentation);

                    if (field.isAnnotationPresent(MapBy.class)) {
                        result.append(field.getAnnotation(MapBy.class).value());
                    } else {
                        result.append(field.getName());
                    }
                    result.append(":");
                    whitespaceIfPretty(result);

                    result.append(serialize(value, indentation + 2));
                    result.append(",");
                    newlineIfPretty(result);
                }
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if (!fieldsToSerialize.isEmpty()) {
			sanitizeEnd(result);
		}

        newlineIfPretty(result);
        indent(result, indentation - 2);
		result.append("}");

		return result.toString();
	}

    private String serializeCollection(Collection<?> collection, int indentation) {
        final StringBuffer result = new StringBuffer();

        result.append("[");
        newlineIfPretty(result);

        collection.forEach(obj -> {
            indent(result, indentation + 2);
            result.append(serialize(obj));
            result.append(',');
            newlineIfPretty(result);
        });

        if (!collection.isEmpty()) {
            sanitizeEnd(result);
        }

        newlineIfPretty(result);
        indent(result, indentation);
        result.append("]");

        return result.toString();
    }
	
	private String serializeCollection(Collection<?> collection) {
		return serializeCollection(collection, 2);
	}
}
