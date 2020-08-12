package org.dkvs.client.handler;

import org.dkvs.client.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public abstract class AsyncResponseHandler<Res> implements ResponseHandler<Res> {
    private static final Logger logger = LoggerFactory.getLogger(AsyncResponseHandler.class);
    private final ExecutorService es;

    public AsyncResponseHandler(ExecutorService es) {
        this.es = es;
    }

    @Override
    public void accept(Res response) {
        logger.debug("Accepting response : " + response);
        try {
            es.submit(() -> {
                handle(response);
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new HandlerException("Error while handling exception : " + e.getMessage(), e);
        }
    }

    public abstract void handle(Res response);
}
