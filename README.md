Parsing json with reflection

This JsonParser class will parse a json file to an Object with the same attribute names as the json's attrs within 2 code lines.

GameCharacter c = new GameCharacter();
c = (GameCharacter) JsonParser.parse(c, json);

Take note that if there is an attribute name in json that it's a reserved word in Java, you will have to define an alternative name for itt:

@AlternativeName("class")
private String classCharacter;
