# Parser

The parser module is used to read / write [OpenAPI](https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md) files in JSON or YAML.

## Implemtation

The parser uses the [Jackson library](https://github.com/FasterXML/jackson) to read / write JSON or YAML.

The parser uses custom serializers and deserializers so that the [model](../model) is not annotated with any [Jackson](https://github.com/FasterXML/jackson) annotations.