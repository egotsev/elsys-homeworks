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

	@Override
	public String serialize(Object obj) {
		String newLine = System.getProperty("line.separator");
		int spacing = 4;
		String spaces = spacing(spacing);
		if (obj == null) {
				return "\"null\"";
		}
		
		if (isDirectlySerializable(obj)) {
			return "\"" + obj.toString() + "\"";
		}
		
		if (isCollection(obj)) {
			return serializeCollection((Collection<?>) obj, spacing);
		}
		
		if (isArray(obj)) {
			return serializeCollection(Arrays.asList(obj), spacing);
		}
		
		final StringBuffer result = new StringBuffer();
		
		
		if(isPretty() == true)
		{
			result.append("{").append(newLine);
		}
		else
		{
			result.append("{");
		}
		
		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());
		
		for (Field field : fieldsToSerialize) {
			Object value = null;
			field.setAccessible(true);
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			if(areIncludedNullFields() != false && value == null)
			{
					if(isPretty() == true)
					{
						result.append(spaces);
					}
					
					if(field.isAnnotationPresent(MapBy.class))
					{
						MapBy val = field.getAnnotation(MapBy.class);
						if(isPretty() == true)
						{
							result.append(val.value()).append(" : ");
						}
					}
					else
					{
						result.append(field.getName()).append(" : ");	
					}
					result.append(serialize(value));
					if(isPretty() == true)
					{
						result.append(", ").append(newLine);
					}
					else
					{
						result.append(", ");
					}
			}
			else if (value != null)
			{
				if(isPretty() == true)
				{
					result.append(spaces);
				}
				if(field.isAnnotationPresent(MapBy.class))
				{
					MapBy val = field.getAnnotation(MapBy.class);
					result.append(val.value()).append(" : ");

				}
				else
				{
					result.append(field.getName()).append(" : ");
				}
				result.append(serialize(value));
				if(isPretty() == true)
				{
					result.append(", ").append(newLine);
				}
				else
				{
					result.append(", ");
				}
			}

			
		}
		
		if (!fieldsToSerialize.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
			if(isPretty() == true)
			{
				result.deleteCharAt(result.length() - 1);
			}
		}
		
		if(isPretty() == true)
		{
			result.append(newLine).append("}");
		}
		else
		{
			result.append("}");
		}
		
		return result.toString();
		
	}
	
	private String serializeCollection(Collection<?> collection, int spacing) {
		final StringBuffer result = new StringBuffer();
		String newLine = System.getProperty("line.separator");
		if(isPretty() == true)
		{
			result.append("[").append(newLine);
		}
		else
		{
			result.append("[");
		}
		collection.forEach(obj -> {
			if(isPretty() == true)
			{
				result.append(spacing(spacing + 4));
			}
			result.append(serialize(obj));
			if(isPretty() == true)
			{
				result.append(',').append(newLine);
			}
			else
			{
				result.append(',');
			}
		});
		if (!collection.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		if(isPretty() == true)
		{
			result.append(newLine).append(spacing(spacing)).append("]");
		}
		else
		{
			result.append("]");
		}
		return result.toString();
	}

	private String spacing(int spaceNumber)
	{
		String space = "";	
		for(int i = 0 ; i < spaceNumber; ++i)
		{
			space += " ";
		}
		return space;
	}
}
