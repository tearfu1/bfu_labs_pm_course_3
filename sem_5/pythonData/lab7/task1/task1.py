import json
from genson import SchemaBuilder
from jsonschema import validate

json_file = 'ex_1.json'
json_error_file = 'ex_1_error.json'
with open(json_file, 'r') as f, open(json_error_file, 'r') as f_error:
    json_doc = json.load(f)
    json_error_doc = json.load(f_error)

builder = SchemaBuilder()
builder.add_schema({"type": "object", "properties": {}})
builder.add_object(json_doc)

with open('ex_1_schema.json', 'w') as f:
    f.write(builder.to_json(indent=2))

schema = builder.to_schema()


def validateJSON(json_doc, schema):
    try:
        validate(instance=json_doc, schema=schema)
        print('Valid JSON')
    except Exception as e:
        print('Invalid JSON')
        print(e)


# Пример валидации json без ошибки
validateJSON(json_doc, schema)

# Пример валидации json с ошибкой
validateJSON(json_error_doc, schema)
