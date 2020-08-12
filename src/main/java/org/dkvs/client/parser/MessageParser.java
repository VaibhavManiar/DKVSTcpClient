package org.dkvs.client.parser;

public interface MessageParser<T> {
    T deSerialize(String str);
    String serialize(T t);
    Class<T> getType();
}
