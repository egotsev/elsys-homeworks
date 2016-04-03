package org.elsys.serializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSerializer implements Serializer {

	private boolean includeNullFields = false;
    private boolean isPretty = false;

	protected List<Field> getFieldsToSerialize(Class<?> cls) {
		List<Field> fields = new LinkedList<>();

        while (cls != Object.class) {
            for(Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Ignore.class) && field.isAnnotationPresent(MapBy.class)) {
                    throw new RuntimeException("Too lazy to write my own exception");
                }

                if (!field.isAnnotationPresent(Ignore.class)) {
                    fields.add(field);
                }
            }
			cls = cls.getSuperclass();
		}

		return fields;
	}

	protected boolean isArray(Object value) {
		return value.getClass().isArray();
	}

	protected boolean isCollection(Object value) {
		return value instanceof Collection;
	}

	protected boolean isDirectlySerializable(Object value) {
		return value instanceof Boolean
                || value instanceof Number
                || value instanceof Character
				|| value instanceof String;
	}

	@Override
	public boolean areIncludedNullFields() {
		return includeNullFields;
	}

	@Override
	public void includeNullFields(boolean includeNullFields) {
		this.includeNullFields = includeNullFields;
	}

	@Override
	public boolean isPretty() {
		return isPretty;
	}

	@Override
	public void setPretty(boolean pretty) {
		this.isPretty = pretty;
	}

    protected void whitespaceIfPretty(StringBuffer sb) {
        if (isPretty()) {
            sb.append(' ');
        }
    }

    protected void newlineIfPretty(StringBuffer sb) {
        if (isPretty()) {
            sb.append(String.format("%n"));
        }
    }

    protected void indent(StringBuffer sb, int spaces) {
        if (isPretty()) {
            for (int i = 0; i < spaces; i++) {
                sb.append(' ');
            }
        }
    }

    protected void sanitizeEnd(StringBuffer sb) {
        sb.deleteCharAt(sb.length() - 1);
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
