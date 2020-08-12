package org.dkvs.client.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;

    private static final Logger logger = LoggerFactory.getLogger(Connection.class);

    public Connection(Socket clientSocket, PrintWriter out, BufferedReader in) {
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public PrintWriter getPrintWriter() {
        return out;
    }

    public BufferedReader getBufferedReader() {
        return in;
    }

    public void closeConnection() {
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (clientSocket != null && !clientSocket.isClosed())
                clientSocket.close();
        } catch (Exception e) {
            logger.error("Error while closing connection : " + e.getMessage(), e);
        }
    }
}
