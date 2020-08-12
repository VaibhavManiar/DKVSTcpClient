package org.dkvs.client.tcp;

import org.dkvs.client.Client;
import org.dkvs.client.handler.AsyncResponseHandler;
import org.dkvs.client.handler.ResponseHandler;
import org.dkvs.client.parser.MessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;

public class TcpClient<Req, Res> implements Client<Req, Res> {

    public final MessageParser<Req> requestMessageParser;
    public final MessageParser<Res> responseMessageParser;
    private final String ip;
    private final int port;
    private static final String DEFAULT_IP = "localhost";
    private static final int DEFAULT_PORT = 8666;
    private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);

    public TcpClient(MessageParser<Req> requestMessageParser,
                     MessageParser<Res> responseMessageParser,
                     String ip,
                     int port) {
        logger.debug("Creating tcp client : [" + ip + ":" + port + "]. ");
        this.requestMessageParser = requestMessageParser;
        this.responseMessageParser = responseMessageParser;
        this.ip = ip;
        this.port = port;
        logger.debug("Created tcp client : [" + ip + ":" + port + "]. ");
    }

    public TcpClient(MessageParser<Req> requestMessageParser,
                     MessageParser<Res> responseMessageParser) {
        this.ip = DEFAULT_IP;
        this.port = DEFAULT_PORT;
        logger.debug("Creating tcp client : [" + ip + ":" + port + "]. ");
        this.requestMessageParser = requestMessageParser;
        this.responseMessageParser = responseMessageParser;
        logger.debug("Created tcp client : [" + ip + ":" + port + "]. ");
    }

    @Override
    public void send(Req request, ResponseHandler<Res> responseHandler) {
        logger.debug("Sending request to: [{}], response: [{}]", ip + ":" + port, request);
        responseHandler.accept(this.sendMessage(request));
    }

    @Override
    public void send(Req request, AsyncResponseHandler<Res> responseHandler) {
        logger.debug("Sending request to: [{}], response: [{}]", ip + ":" + port, request);
        responseHandler.accept(this.sendMessage(request));
    }

    private Res sendMessage(Req request) {
        Connection connection = null;
        try {
            connection = TcpConnectionFactory.createConnection(ip, port);
            connection.getPrintWriter().println(this.requestMessageParser.serialize(request));
            return read(connection.getBufferedReader());
        } finally {
            if (connection != null)
                connection.closeConnection();
        }
    }

    private Res read(BufferedReader in) {
        try {
            String inputLine;
            StringBuilder resString = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                resString.append(inputLine);
            }
            return this.responseMessageParser.deSerialize(resString.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error while reading response : " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "TcpClient{" +
                "requestMessageParser=" + requestMessageParser +
                ", responseMessageParser=" + responseMessageParser +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
