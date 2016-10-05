# JSON serializer homework

Add to the existing JSON serializer the following options:
1. Take into account the `pretty` property.
2. Take into account the `includeNullProperty` property. If it's true, the null fields must be included in the
result with `field : "null"`.
3. Take into account the `quotedKeys` property. If it's true the keys must be in quotes. For example, a field with
value `true` should be serialized as `"field" : "true"`. The value is always in quotes.
4. Check the `@MapAs` annotation. It has obligatory property `name`. If the annotation is present on a field, it should be serialized with the name provided in the annotation. If it is not present, the actual name of the field
should be used.
You should throw `RuntimeException` in case on a field there are both `@Ignore` and `@MapAs` annotation.