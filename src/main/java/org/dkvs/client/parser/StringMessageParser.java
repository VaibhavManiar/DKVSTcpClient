package org.dkvs.client.parser;

public class StringMessageParser implements MessageParser<String> {
    @Override
    public String deSerialize(String str) {
        return str;
    }

    @Override
    public String serialize(String s) {
        return s;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }



}
