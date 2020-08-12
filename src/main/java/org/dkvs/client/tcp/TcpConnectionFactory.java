package org.dkvs.client.tcp;

import org.dkvs.client.exception.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpConnectionFactory implements ConnectionFactory {

    private final static Logger logger = LoggerFactory.getLogger(TcpConnectionFactory.class);
    private static final TcpConnectionFactory instance = new TcpConnectionFactory();
    private TcpConnectionFactory() {}

    public static TcpConnectionFactory getInstance() {
        return instance;
    }

    public static Connection createConnection(String ip, int port) {
        return getInstance().create(ip, port);
    }

    @Override
    public Connection create(String ip, int port) {
        logger.debug("creating connection : [" + ip + ":" + port + "]");
        long startTime = System.currentTimeMillis();
        final Socket clientSocket;
        final PrintWriter out;
        final BufferedReader in;

        try {
            clientSocket = new Socket(ip, port);
        } catch (Exception e) {
            throw new SocketException("Error opening socket connection: [" + ip + ":" + port + "]. " + e.getMessage(), e);
        }

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException("Error while creating reader/writer using client socket. Socket : [" + ip + ":" + port + "]. " + e.getMessage(), e);
        }
        logger.debug("Created connection : [" + ip + ":" + port + "]");
        logger.debug("Connection creation time : " + (System.currentTimeMillis() - startTime));
        return new Connection(clientSocket, out, in);
    }
}
