package org.elsys.serializer.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elsys.serializer.AbstractSerializer;
import org.elsys.serializer.MapBy;
import org.elsys.serializer.Serializer;

public class JsonSerializer extends AbstractSerializer implements Serializer
{

	@Override
	public String serialize(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		if (obj == null)
		{
			return "\"null\"";
		}
		if (isDirectlySerializable(obj))
		{
			return "\"" + obj.toString() + "\"";
		}
		if (isCollection(obj))
		{
			return serializeCollection((Collection<?>) obj);
		}
		if (isArray(obj))
		{
			return serializeCollection(Arrays.asList(obj));
		}
		final StringBuffer res = new StringBuffer();
		setPretty(true);

		if (isPretty() == true)
		{
			res.append("{\n");
		} else
		{
			res.append("{");
		}

		List<Field> fieldsToSerialize = getFieldsToSerialize(obj.getClass());
		includeNullFields(false);

		for (Field f : fieldsToSerialize)
		{
			if (areIncludedNullFields() == true)
			{
				f.setAccessible(true);
				MapBy map = f.getAnnotation(MapBy.class);
				if (isPretty() == true)
				{
					if (map != null)
					{
						res.append(map.value() + " : ");
					} else
					{
						res.append(f.getName() + " : ");
					}
					Object value = f.get(obj);
					res.append(serialize(value) + ",\n");
				} 
				else
				{
					if (map != null)
					{
						res.append(map.value() + " : ");
					} else
					{
						res.append(f.getName() + " : ");
					}
					Object value = f.get(obj);
					res.append(serialize(value) + ",");
				}
			} else if (areIncludedNullFields() == false)
			{
				f.setAccessible(true);
				if (f.get(obj) != null && isPretty() == true)
				{
					MapBy map = f.getAnnotation(MapBy.class);
					if (map != null)
					{
						res.append(map.value() + " : ");
					} else
					{
						res.append(f.getName() + " : ");
					}
						
					Object value = f.get(obj);
					res.append(serialize(value) + ",\n");
				
					} 
					else if (f.get(obj) != null && isPretty() == false)
					{
						MapBy map = f.getAnnotation(MapBy.class);
						if (map != null)
						{
							res.append(map.value() + " : ");
						} else
						{
							res.append(f.getName() + " : ");
						}
						Object value = f.get(obj);
						res.append(serialize(value) + ",");
					}
				} 
				
		}
		if (!fieldsToSerialize.isEmpty())
		{
			res.deleteCharAt(res.length() - 1);
			res.deleteCharAt(res.length() - 1);
		}
		if (isPretty() == true)
		{
			res.append("\n}");
		}
		else
		{
			res.append("}");
		}
		return res.toString();
	}

	private String serializeCollection(Collection<?> collection)
	{
		final StringBuffer result = new StringBuffer();
		if (isPretty() == true)
		{
			result.append("[\n");
			collection.forEach(obj ->
			{
				try
				{
					result.append((serialize(obj) + ",\n"));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			});
			if (!collection.isEmpty())
			{
				result.deleteCharAt(result.length() - 1);
			}
			result.append("\n]");
		} 
		else
		{
			result.append("[");
			collection.forEach(obj ->
			{
				try
				{
					result.append(serialize(obj) + ',');
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			});
			if (!collection.isEmpty())
			{
				result.deleteCharAt(result.length() - 1);
			}
			result.append("]");
		}
		return result.toString();
	}
}
