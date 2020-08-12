package org.dkvs.client.tcp;

public interface ConnectionFactory {
    Connection create(String ip, int port);
}
